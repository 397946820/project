package com.it.ocs.synchronou.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;
import com.it.ocs.amazon.model.AmazonRequestMode;
import com.it.ocs.amazon.order.AmazonClientRun;
import com.it.ocs.amazon.order.AmazonRequest;
import com.it.ocs.amazon.order.GetAmazonClient;
import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.dao.ISyncAmazonOrderDao;
import com.it.ocs.synchronou.model.AmazonAccountModel;
import com.it.ocs.synchronou.model.ParseAmazonOderXMLModel;
import com.it.ocs.synchronou.model.ParseAmazonOrderItemXMLModel;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.task.core.TaskExecutorUtil;
import com.it.ocs.task.core.TaskRunnable;
import com.it.ocs.task.core.Timer;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.support.spring.SpringUtils;

public class SyncAmazonOrderServiceSupport {
private final static Logger log = Logger.getLogger(SyncAmazonOrderServiceSupport.class);
	
	
	@Autowired
	private ISyncAmazonOrderDao syncAmazonOrderDao;
	
	@Autowired
	private SyncAmazonDaoService syncAmazonDaoService;
	
	

	
	/**
	 * 根据账号和时间同步数据
	 * @param account
	 * @param startTime 2017-8-10T00:02:00.000Z
	 * @param endTime
	 */
	public void syncOrderDataByAccount(AmazonAccountModel account,String startTime, String endTime){
		//Thread.currentThread().setName("Thread-"+account.getSellerId());
		//开始计时
		Timer timer = new Timer(2*60*60*1000L);//2*60*60*1000L
		AmazonRequestMode requestMode = createAmazonRequestMode(account);
        requestMode.setUpdatedAfter(startTime);
        requestMode.setUpdatedBefore(endTime);
        syncOrder(account, requestMode,AmazonRequest.BY_UPDATE_DATE);
        log.info("账号"+account.getSellerId()+"同步订单,运行"+timer.getUseTotalTime()+"毫秒");
        //有效剩余时间修复之前订单
        if(timer.isContinue()){
        	repairOrder(timer.getRemainingTime(),requestMode,account);
        }
        
        
	}
	/**
	 * 修补订单
	 * @param remainingTime
	 * @param requestMode
	 * @param account
	 */
	private void repairOrder(long remainingTime, AmazonRequestMode requestMode, AmazonAccountModel account) {
		String startTime = syncAmazonOrderDao.getTimeForm(account.getSellerId());
		int id = syncAmazonOrderDao.getTimeSetId();
		Map<String,Object> timeSetMap = new HashMap<>();
		timeSetMap.put("id", id);
		timeSetMap.put("timeFrom", startTime);
		timeSetMap.put("account", account.getSellerId());
		syncAmazonOrderDao.insertTimeSet(timeSetMap);
		Timer timer = new Timer(remainingTime);
		if(timer.isContinue()){
			startTime = startTime.replace(" ", "T")+"Z";
			String endTime = UTCTimeUtils.addHour(startTime,1);
			if(null == endTime){
				return;
			}
			requestMode.setCreatedAfter(startTime);
			requestMode.setCreatedBefore(endTime);
			syncOrder(account, requestMode,AmazonRequest.BY_CREATE_DATE);
			
			endTime = endTime.replace("T", " ");
			endTime = endTime.replace("Z", "");
			timeSetMap.put("timeTo", endTime);
			syncAmazonOrderDao.udpateTimeSet(timeSetMap);
		}
	}
	
	private void syncOrder(AmazonAccountModel account, AmazonRequestMode requestMode,int type) {
		MarketplaceWebServiceOrdersClient client = GetAmazonClient.createClient(requestMode);
        ListOrdersRequest request = AmazonRequest.createOrderRequest(requestMode,type);
        // Make the call.
        String responseXML = AmazonClientRun.invokeListOrders(client, request);
        try {
			String nextToken = parseOrderListData(requestMode, client, account, responseXML);
			if(null != nextToken && !"".equals(nextToken)){
				while(null != nextToken && !"".equals(nextToken)){
					nextToken = syncOrderByNextToken(nextToken,requestMode,client,account);
				}
			}
		} catch (DocumentException e) {
			log.info("订单解析失败", e);
		}
	}
	private AmazonRequestMode createAmazonRequestMode(AmazonAccountModel account) {
		AmazonRequestMode requestMode = new AmazonRequestMode();
		requestMode.setServiceURL(account.getUrl());
		requestMode.setAccessKey(account.getAccessKey());//"AKIAIQJPPHYX7ZMKPTNQ"
		requestMode.setSecretKey(account.getSecretKey());//"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"
		requestMode.setSellerId(account.getSellerId());//AV7KSH7XB8RNM
        requestMode.setMarketplaceId(account.getMarketplaceId());
		return requestMode;
	}
	
	/**
	 * 为保存的订单增加默认信息
	 * @param account
	 * @param map
	 */
	private void formateOderData(AmazonAccountModel account, Map<String, Object> map) {
		map.put("platform", account.getPlatform());
		String createDate = UTCTimeUtils.getUTCTimeStr(0);
		createDate = createDate.replace("T", " ");
		createDate = createDate.replace("Z", "");
		map.put("createDate", createDate);
		String street = "";
		String addressLine1 = (String)map.get("AddressLine1");
		if(!"".equals(addressLine1)){
			street = addressLine1;
		}
		String addressLine2 = (String)map.get("AddressLine2");
		if(!"".equals(addressLine2)){
			street = street +" "+ addressLine2;
		}
		String addressLine3 = (String)map.get("AddressLine3");
		if(!"".equals(addressLine3)){
			street = street +" "+  addressLine3;
		}
		map.put("street", street);
	}
	/**
	 * 通过byNextToken同步订单清单数据
	 * @param nextToken
	 * @param requestMode
	 * @param client
	 * @param account
	 * @return
	 * @throws DocumentException
	 */
	public String syncOrderByNextToken(String nextToken,AmazonRequestMode requestMode,MarketplaceWebServiceOrdersClient client,AmazonAccountModel account) throws DocumentException{
		requestMode.setNextToken(nextToken);
		ListOrdersByNextTokenRequest request = AmazonRequest.createOrderByNextTokenRequest(requestMode);
		String responseXML = AmazonClientRun.invokeListOrdersByNextToken(client, request);
		return parseOrderListData(requestMode, client, account, responseXML);
	}
	
	/**
	 * 解析保存订单清单数据
	 * @param requestMode
	 * @param client
	 * @param account
	 * @param responseXML
	 * @return
	 * @throws DocumentException
	 */
	private String parseOrderListData(AmazonRequestMode requestMode, MarketplaceWebServiceOrdersClient client,
			AmazonAccountModel account, String responseXML) throws DocumentException {
		//开始计时
		long startTime = System.currentTimeMillis();
		Document doc = DocumentHelper.parseText(responseXML);
		ParseAmazonOderXMLModel parse = new ParseAmazonOderXMLModel(doc,"https://mws.amazonservices.com/Orders/2013-09-01");
		List<Map<String,Object>> parseResult = parse.getResult();
		for(Map<String,Object> map : parseResult){
			formateOderData(account, map);	
			saveOrUpdateOrderListData(map,requestMode,client);
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		if(time < 60000){
			try {
				Thread.sleep(60000-time);
			} catch (InterruptedException e) {
				
			}
		}
		return parse.getNextToken();
	}
	
	/**
	 * 保存订单清单数据到数据库
	 * @param map
	 * @param requestMode
	 * @param client
	 */
	private  void saveOrUpdateOrderListData(Map<String, Object> map,AmazonRequestMode requestMode,MarketplaceWebServiceOrdersClient client) {
		String orderId = (String)map.get("AmazonOrderId");
		Integer id = syncAmazonOrderDao.isExist(orderId);
		if(null == id||id == 0){
			id = syncAmazonOrderDao.getId();
			map.put("id", id);
			syncAmazonDaoService.insertData(map);
			syncOrderItem(id,orderId,requestMode,client);
		}else{
			String price = syncAmazonOrderDao.getOrderPrice(id);
			if(null == price){
				price = "0";
			}
			String newPrice = (String)map.get("Amount");
			if("".equals(newPrice)){
				newPrice = "0";
			}
			if(Double.parseDouble(price)!= Double.parseDouble(newPrice)||syncAmazonOrderDao.orderItemIsExistByPid(id) == 0){
				syncOrderItem(id,orderId,requestMode,client);
			}
			syncAmazonDaoService.updateData(map);
		}
		
	}
	
	/**
	 * 同步订单详情信息
	 * @param parentId
	 * @param orderId
	 * @param requestMode
	 * @param client
	 */
	private void syncOrderItem(int parentId,String orderId, AmazonRequestMode requestMode,
			MarketplaceWebServiceOrdersClient client) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("AmazonOrderId", orderId);
		requestMode.setParam(param);
		ListOrderItemsRequest request = AmazonRequest.createOrderItemRequest(requestMode);
		String responseXML = AmazonClientRun.invokeListOrderItems(client, request);
		//开始计时
		long startTime = System.currentTimeMillis();
		try {
			Document doc = DocumentHelper.parseText(responseXML);
			ParseAmazonOrderItemXMLModel parse = new ParseAmazonOrderItemXMLModel(doc,"https://mws.amazonservices.com/Orders/2013-09-01");
			List<Map<String,Object>> parseResult = parse.getResult();
			for(Map<String,Object> map : parseResult){
				String createDate = UTCTimeUtils.getUTCTimeStr(0);
				createDate = createDate.replace("T", " ");
				createDate = createDate.replace("Z", "");
				map.put("createDate", createDate);
				map.put("parentId", parentId);
				saveOrUpdateOrderItem(map);
			}
			
		} catch (DocumentException e) {
			log.info("订单详情解析失败", e);
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		if(time < 2500){
			try {
				Thread.sleep(2500-time);
			} catch (InterruptedException e) {
				
			}
		}
		
	}
	
	/**
	 * 保存订单详情信息到数据库
	 * @param map
	 */
	private void saveOrUpdateOrderItem(Map<String, Object> map) {
		String itemId = (String)map.get("OrderItemId");
		if(syncAmazonOrderDao.itemIsExist(itemId) == 0){
			syncAmazonDaoService.insertItemData(map);
		}else{
			syncAmazonDaoService.updateItemData(map);
		}
		
	}
}
