package com.it.ocs.synchronou.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.it.ocs.synchronou.dao.ISyncLightingOrderDao;
import com.it.ocs.synchronou.model.LightAccountModel;
import com.it.ocs.synchronou.model.ParseLightOrderItemXMLModel;
import com.it.ocs.synchronou.model.ParseLightOrderXMLNode;
import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.util.HTTPUtils;
import com.it.ocs.synchronou.util.UTCTimeUtils;

import net.sf.json.JSONArray;


public class SyncLightingOrderServiceSupport {
	private final static Logger log = Logger.getLogger(SyncLightingOrderServiceSupport.class);

	private ISyncLightingOrderDao iSyncLightingOrderDao;

	private TemplateService templateService;
	
	public SyncLightingOrderServiceSupport(ISyncLightingOrderDao iSyncLightingOrderDao,TemplateService templateService){
		this.iSyncLightingOrderDao = iSyncLightingOrderDao;
		this.templateService = templateService;
	}
	
	public static List<Map<String,String>> splitTimeForOneHourList(String fromTime,int hour){
		List<Map<String,String>> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			Date fromDate = sdf.parse(fromTime);
			Calendar curCalendar = Calendar.getInstance();
			curCalendar.add(Calendar.HOUR, -8);
			curCalendar.add(Calendar.MINUTE, -2);
			Date toDate = curCalendar.getTime();
			long l = toDate.getTime() - fromDate.getTime();
			if(l > hour*60*60*1000){
				int count = (int) (l/(hour*60*60*1000));
				long y = l%(hour*60*60*1000);
				if(y>0){
					count = count + 1;
				}
				curCalendar.setTime(fromDate);
				String start = sdfUTC.format(fromDate);
				String end = "";
				for(int i=0;i<count;i++){
					curCalendar.add(Calendar.HOUR, hour);
					if(i == count-1){
						end = sdfUTC.format(toDate);
					}else{
						end = sdfUTC.format(curCalendar.getTime());
					}
					
					Map<String,String> map = new HashMap<>();
					map.put("fromTime", start);
					map.put("toTime", end);
					log.info(start + "----"+end);
					start = end;
					list.add(map);
				}
			}else{
				Map<String,String> map = new HashMap<>();
				map.put("fromTime", sdfUTC.format(fromDate));
				map.put("toTime", sdfUTC.format(toDate));
				log.info(sdfUTC.format(fromDate) + "----"+sdfUTC.format(toDate));
				list.add(map);
			}
		} catch (ParseException e) {
			log.info("解析较长时间分段失败:"+fromTime+"--"+hour);
		}
		return list;
	}
	
	
	/**
	 * 按照更新时间同步2小时内的订单
	 * @param account
	 */
	public void getOrderByAccount(LightAccountModel account) {
		Map<String,String> xmlValueMap = new HashMap();
		String fromTime = iSyncLightingOrderDao.getTimeSetStartTime(account.getPlatform());
		if(null == fromTime || "".equals(fromTime)){
			fromTime = iSyncLightingOrderDao.getLastUpdateDate(account.getPlatform());
		}
		Map<String,Object> timeSetMap = new HashMap<>();
		timeSetMap.put("account", account.getPlatform());
		timeSetMap.put("fromTime", fromTime);
		int id = iSyncLightingOrderDao.getTimeSetId();
		timeSetMap.put("id", id);
		iSyncLightingOrderDao.addTimeSet(timeSetMap);
		List<Map<String,String>> timeList = splitTimeForOneHourList(fromTime,2);
		for(Map<String,String> map : timeList){
			xmlValueMap.put("fromTime", map.get("fromTime"));
			xmlValueMap.put("toTime", map.get("toTime"));
			TemplateModel template = templateService.getTemplateContent("GetOrders", "light");
			syncOrder(xmlValueMap, account, template);
			String toTime =  map.get("toTime");
			toTime = toTime.replace("T", " ");
			toTime = toTime.replace("Z", "");
			timeSetMap.put("toTime",toTime);
			iSyncLightingOrderDao.updateTimeSet(timeSetMap);
		}
		
	}

	private void syncOrder(Map<String, String> xmlValueMap, LightAccountModel account, TemplateModel template) {
		String sessionId = this.getSessionIdByAccount(account);
		xmlValueMap.put("sessionId", sessionId);
		String requestXml = TemplateService.formatTemplat(xmlValueMap, template.getContent());
		String responseXML = HTTPUtils.httpPostByXML(account.getUrl(), requestXml);
		//responseXML = ParseXMLModel.getNodeStringInXMLString(responseXML, "callReturn");
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(responseXML);
			ParseLightOrderXMLNode parse = new ParseLightOrderXMLNode(doc, "");
			List<Map<String,Object>> parseResult = parse.getResult();
			saveOrUpdateData(parseResult,account,sessionId);
		} catch (DocumentException e) {
			log.info("解析官网订单列表数据失败",e);
		}
	}
	private synchronized void saveOrUpdateData(List<Map<String, Object>> parseResult,LightAccountModel account,String sessionId) {
		for(Map<String, Object> map :parseResult){
			map.put("platform", account.getPlatform());
			//map.put("fix",UTCTimeUtils.getTimeFixByCountry(account.getPlatform()));
			String orderId = (String)map.get("order_id");
			Integer id = iSyncLightingOrderDao.getIdByOrderId(map);
			if(null == id){
				id = iSyncLightingOrderDao.getId();
				map.put("entity_id", id);
				iSyncLightingOrderDao.insertOrderData(map);
			}else{//更新
				map.put("entity_id", id);
				iSyncLightingOrderDao.updateOrderData(map);
			}
			syncOrderItem(account,orderId,sessionId,id);
		}
		
	}


	private void syncOrderItem(LightAccountModel account, String orderId,String sessionId,Integer parentId) {
		
		TemplateModel template = templateService.getTemplateContent("GetOrderInfo", "light");
		Map<String,String> xmlValueMap = new HashMap();
		xmlValueMap.put("sessionId", sessionId);
		xmlValueMap.put("orderId", orderId);
		String requestXml = TemplateService.formatTemplat(xmlValueMap, template.getContent());
		String responseXML = HTTPUtils.httpPostByXML(account.getUrl(), requestXml);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(responseXML);
			ParseLightOrderItemXMLModel parse = new ParseLightOrderItemXMLModel(doc, "");
			List<Map<String,Object>> parseResult = parse.getResult();
			saveOrUpdateItemData(parseResult,account,parentId);
		} catch (DocumentException e) {
			log.info("解析官网订单详情数据失败",e);
		}
		
	}


	private synchronized void saveOrUpdateItemData(List<Map<String, Object>> parseResult, LightAccountModel account,Integer parentId) {
		for(Map<String, Object> map :parseResult){
			map.put("parent_id", parentId);
			//map.put("fix",UTCTimeUtils.getTimeFixByCountry(account.getPlatform()));
			String itemId = (String)map.get("light_item_id");
			map.put("light_item_id", itemId);
			Integer id = iSyncLightingOrderDao.getIdByOrderItemId(map);
			if(null == id){
				id = iSyncLightingOrderDao.getOrderItemId();
				map.put("entity_id", id);
				iSyncLightingOrderDao.insertOrderItemData(map);
			}else{//更新
				map.put("entity_id", id);
				iSyncLightingOrderDao.updateOrderItemData(map);
			}
		}
		
	}


	/**
	 * 模拟登陆获取sessionId
	 * @param account
	 * @return
	 */
	public String getSessionIdByAccount(LightAccountModel account) {
		TemplateModel template = templateService.getTemplateContent("getLoginSessionId", "light");
		Map<String,String> xmlValueMap = new HashMap();
		xmlValueMap.put("account", account.getAccount());
		xmlValueMap.put("secret", account.getSecret());
		String requestXml = TemplateService.formatTemplat(xmlValueMap, template.getContent());
		log.info("----------获取sessionID requestXml----------:"+requestXml);
		String responseXML = HTTPUtils.httpPostByXML(account.getUrl(), requestXml);
		log.info("----------获取sessionID responseXML----------:"+responseXML);
		return ParseXMLModel.getNodeValue(responseXML, "", "loginReturn");
	}

	public void repairOrder(LightAccountModel account) {
		int hour= -24*31;
		Calendar calender = UTCTimeUtils.getUTCCalendar();
		calender.add(Calendar.HOUR, hour);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		List<Map<String,String>> timeList = splitTimeForOneHourList(sdf.format(calender.getTime()),24);
		//List<Map<String,String>> timeList = splitTimeForOneHourList("2017-10-29 00:00:00",24);
		//Map<String,String> xmlValueMap = new HashMap();
		//TemplateModel template = templateService.getTemplateContent("repairOrder", "light");
		
		for(Map<String,String> map : timeList){
			getOrders(account,map.get("fromTime"), map.get("toTime"));
			log.info("官网订单按照创建时间修补("+account.getPlatform()+"):"+map.get("fromTime")+"--"+map.get("toTime"));
		}
		
	}
	
	public void getOrderByAccountNew(LightAccountModel account) {
		Map<String,String> xmlValueMap = new HashMap();
		String fromTime = iSyncLightingOrderDao.getTimeSetStartTime(account.getPlatform());
		if(null == fromTime || "".equals(fromTime)){
			fromTime = iSyncLightingOrderDao.getLastUpdateDate(account.getPlatform());
		}
		Map<String,Object> timeSetMap = new HashMap<>();
		timeSetMap.put("account", account.getPlatform());
		timeSetMap.put("fromTime", fromTime);
		int id = iSyncLightingOrderDao.getTimeSetId();
		timeSetMap.put("id", id);
		iSyncLightingOrderDao.addTimeSet(timeSetMap);
		
		List<Map<String,String>> timeList = splitTimeForOneHourList(fromTime,24);
		for(Map<String,String> map : timeList){
			getOrders(account,map.get("fromTime"), map.get("toTime"));
			log.info("官网订单同步（"+account.getPlatform()+"）:"+map.get("fromTime")+"--"+map.get("toTime"));
			String toTime =  map.get("toTime");
			toTime = toTime.replace("T", " ");
			toTime = toTime.replace("Z", "");
			timeSetMap.put("toTime",toTime);
			iSyncLightingOrderDao.updateTimeSet(timeSetMap);
		}
		/*String fromTime = "2018-07-05T02:40:17Z";
		String toTime = "2018-07-05T02:50:17Z";
		getOrders(account, fromTime, toTime);*/
	}

	private void getOrders(LightAccountModel account, String fromTime, String toTime) {
		String url = account.getUrl();
		url = url.substring(0, url.indexOf(".php/")+5);
		//"http://192.168.31.231/us_rebuild/index.php/sales/oms/order/from/2016-2-01T00:00:00Z/to/2016-02-02T00:00:00Z";
		url = url +"sales/oms/order/from/"+fromTime+"/to/"+toTime;
		JSONArray jsonArray = JSONArray.fromObject(HTTPUtils.httpGet(url));
		Object str[] = jsonArray.toArray();
		if(str.length > 2){
			String orderColumnStr = (String)str[0];
			String orderItemColumnStr = (String)str[1];
			//防止错位
			if(orderColumnStr.startsWith("I")){
				orderItemColumnStr = (String)str[0];
				orderColumnStr = (String)str[1];
			}
			String orderColumn[] = orderColumnStr.split("=\\|=");
			Map<Integer,String> orderColumnMap = new HashMap<>();
			for(int i = 1;i<orderColumn.length;i++){
				orderColumnMap.put(i, orderColumn[i]);
			}
			
			
			String orderItemColumn[] = orderItemColumnStr.split("=\\|=");
			Map<Integer,String> orderItemColumnMap = new HashMap<>();
			for(int i = 1;i<orderItemColumn.length;i++){
				orderItemColumnMap.put(i, orderItemColumn[i]);
			}
			
			List<Map<String,Object>> orderList = new ArrayList<>();
			List<Map<String,Object>> orderItemList = new ArrayList<>();
			
			for(int i=2;i<str.length;i++){
				String lineStr = (String)str[i];
				if(null != lineStr && !"".equals(lineStr)){
					String lines[] = lineStr.split("=\\|=");
					if(lineStr.startsWith("O")){
						Map<String,Object> orderMap = new HashMap<>();
						for(int j= 1;j<lines.length;j++){
							orderMap.put(orderColumnMap.get(j), lines[j]);
						}
						orderList.add(orderMap);
					}else{
						Map<String,Object> orderItemMap = new HashMap<>();
						for(int j= 1;j<lines.length;j++){
							orderItemMap.put(orderItemColumnMap.get(j), lines[j]);
						}
						orderItemList.add(orderItemMap);
					}
				}
			}
			saveOrUpdateOrder(orderList,account);
			saveOrUpdateOrderItem(orderItemList,account);
		}
	}
	
	private  void saveOrUpdateOrderItem(List<Map<String, Object>> orderItemList, LightAccountModel account) {
		for(Map<String, Object> map :orderItemList){
			map.put("platform", account.getPlatform());
			Integer parentId = iSyncLightingOrderDao.getIdByOrderId(map);
			map.put("parent_id", parentId);
			String itemId = (String)map.get("item_id");
			map.put("light_item_id", itemId);
			map.put("shipping_firstname", map.get("firstname"));
			map.put("shipping_middlename", map.get("middlename"));
			map.put("shipping_lastname", map.get("lastname"));
			map.put("light_created_at", map.get("created_at"));
			map.put("light_updated_at", map.get("updated_at"));
			map.put("gift_message", "");
			Object parentItemId = map.get("parent_item_id");
			if(null == parentItemId || "".equals(parentItemId.toString())){
				Integer id = null;
				//判断此订单sku存不存在
				if(iSyncLightingOrderDao.orderSkuHasExist(map)==0){//不存在直接插入
					id = iSyncLightingOrderDao.getOrderItemId();
					map.put("entity_id", id);
					iSyncLightingOrderDao.insertOrderItemData(map);
				}else{//sku 存在，
					// 判断是否是同一条数据
					id = iSyncLightingOrderDao.getIdByOrderItemId(map);
					if(null == id){//不是同一条 插入新的附表
						id = iSyncLightingOrderDao.getIdByOrderItemIdForBSKU(map);
						if(null == id){
							id = iSyncLightingOrderDao.getOrderItemId();
							map.put("entity_id", id);
							iSyncLightingOrderDao.insertOrderItemDataForBSKU(map);
						}else{//更新
							map.put("entity_id", id);
							iSyncLightingOrderDao.updateOrderItemDataForBSKU(map);
						}
					}else{//更新
						map.put("entity_id", id);
						iSyncLightingOrderDao.updateOrderItemData(map);
					}
				}
			}else{
				//判断老数据
				int hasAdd = iSyncLightingOrderDao.hasOldAdd(map);
				if(hasAdd > 0){
					Integer id = iSyncLightingOrderDao.getIdByOrderItemId(map);
					map.put("entity_id", id);
					iSyncLightingOrderDao.updateOrderItemData(map);
				}else{
					Integer id = iSyncLightingOrderDao.getIdByOrderItemIdForBSKU(map);
					if(null == id){
						id = iSyncLightingOrderDao.getOrderItemId();
						map.put("entity_id", id);
						iSyncLightingOrderDao.insertOrderItemDataForBSKU(map);
					}else{//更新
						map.put("entity_id", id);
						iSyncLightingOrderDao.updateOrderItemDataForBSKU(map);
					}
				}
				
			}
		}
		
	}

	private  void saveOrUpdateOrder(List<Map<String, Object>> orderList, LightAccountModel account) {
		for(Map<String, Object> map :orderList){
			map.put("platform", account.getPlatform());
			map.put("light_created_at", map.get("created_at"));
			map.put("light_updated_at", map.get("updated_at"));
			String orderId = (String)map.get("order_id");
			Integer id = iSyncLightingOrderDao.getIdByOrderId(map);
			if(null == id){
				id = iSyncLightingOrderDao.getId();
				map.put("entity_id", id);
				iSyncLightingOrderDao.insertOrderData(map);
			}else{//更新
				map.put("entity_id", id);
				iSyncLightingOrderDao.updateOrderData(map);
			}
		}
		
	}

	public static void main(String[] args) {
		String url = "https://www.lightingever.co.uk/index.php/api/soap/index/";
		//
		url = url.substring(0, url.indexOf(".php/")+5);
		url = url +"sales/oms/order/from/2018-07-05T02:40:17Z/to/2018-07-05T02:50:17Z";
		JSONArray jsonArray = JSONArray.fromObject(HTTPUtils.httpGet(url));
		Object str[] = jsonArray.toArray();
		if(str.length > 2){
			String orderColumnStr = (String)str[0];
			String orderItemColumnStr = (String)str[1];
			//防止错位
			if(orderColumnStr.startsWith("I")){
				orderItemColumnStr = (String)str[0];
				orderColumnStr = (String)str[1];
			}
			String orderColumn[] = orderColumnStr.split("=\\|=");
			Map<Integer,String> orderColumnMap = new HashMap<>();
			for(int i = 1;i<orderColumn.length;i++){
				orderColumnMap.put(i, orderColumn[i]);
			}
			
			
			String orderItemColumn[] = orderItemColumnStr.split("=\\|=");
			Map<Integer,String> orderItemColumnMap = new HashMap<>();
			for(int i = 1;i<orderItemColumn.length;i++){
				orderItemColumnMap.put(i, orderItemColumn[i]);
			}
			
			List<Map<String,Object>> orderList = new ArrayList<>();
			List<Map<String,Object>> orderItemList = new ArrayList<>();
			
			for(int i=2;i<str.length;i++){
				String lineStr = (String)str[i];
				if(null != lineStr && !"".equals(lineStr)){
					String lines[] = lineStr.split("=\\|=");
					if(lineStr.startsWith("O")){
						Map<String,Object> orderMap = new HashMap<>();
						for(int j= 1;j<lines.length;j++){
							orderMap.put(orderColumnMap.get(j), lines[j]);
						}
						orderList.add(orderMap);
					}else{
						Map<String,Object> orderItemMap = new HashMap<>();
						for(int j= 1;j<lines.length;j++){
							orderItemMap.put(orderItemColumnMap.get(j), lines[j]);
							if("row_total_incl_tax".equals(orderItemColumnMap.get(j))){
								String str1 = lines[j];
								if(null == str1 ||"".equals(str1)){
									
									System.out.println("--------------------------------");
								}
							}
						}
						orderItemList.add(orderItemMap);
					}
				}
			}
/*			//////////////////////////
			Object obj = map.get("row_total_incl_tax");
			if(null == obj||"".equals(String.valueOf(obj))||"null".equals(obj)){
				
			}*/
			System.out.println(jsonArray.get(0));
		}
	}
}
