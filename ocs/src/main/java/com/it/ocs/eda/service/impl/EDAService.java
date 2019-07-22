package com.it.ocs.eda.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.EDARequestModel;
import com.it.ocs.eda.model.InventoryHistoryModel;
import com.it.ocs.eda.model.ShipOrderInfo;
import com.it.ocs.eda.service.IEDAService;
import com.it.ocs.eda.utils.EDAHttpUtil;
import com.it.ocs.eda.utils.EDAInterfaceUtils;
import com.it.ocs.eda.utils.EDAJsonUtil;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.IEbayMessageService;
import com.it.ocs.synchronou.service.impl.TemplateService;
import com.it.ocs.task.core.TaskExecutorUtil;
import com.it.ocs.task.core.TaskRunnable;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * EDA服务集成服务
 * 
 * @author chenyong
 *
 */
@Service
public class EDAService implements IEDAService {
	private final static Logger log = Logger.getLogger(EDAService.class);

	@Autowired
	private IEDADao iEDADao;
	//@Autowired
	//private ILightSaleOrderService lightService;
	//@Autowired
	//private IWalmartSaleOrderService walmartService;
	//@Autowired
	//private IEbaySellerListService ebayService;
	
	@Autowired
	private TemplateService templateService;
	@Autowired
	private IEbayMessageService ebayMessageService;
	

	
	@Autowired
	private EDAUploadTrackingNumberService edaUploadTrackingNumberService;

	private EDARequestModel getEDARequestModel(String methodName, String edaAccount) {
		Map<String, Object> map = iEDADao.getEDAAccount(methodName, edaAccount);
		return new EDARequestModel(String.valueOf(map.get("URL")), String.valueOf(map.get("ACCOUNT")),
				String.valueOf(map.get("PASSWORD")));
	}

	/**
	 * 定时任务
	 * 创建eda发货订单
	 */
	public void createEDAOrder() {

		List<Map<String, Object>> orders = iEDADao.getCreateEDAOrder();
		for (Map<String, Object> order : orders) {
			String pf = (String) order.get("PLATFORM");
			String poNumber = String.valueOf(order.get("PONUMBER"));
			order.put("PONUMBER", pf.substring(0, 2).toUpperCase() + poNumber);
			EDARequestModel requestModel = this.getEDARequestModel("createOrder", (String) order.get("EDAACCOUNT"));
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			data.add(order);
			Map<String, Object> map = EDAJsonUtil.createData(EDAInterfaceUtils.getRequestCreatrOrderKeySet(), data);
			requestModel.setData(map);
			String response = EDAHttpUtil.httpPost(requestModel);
			String edaOrderId = (String) EDAJsonUtil.parseResponseJSON(EDAInterfaceUtils.getResponseKeySet(), response)
					.get("data");
			if (null != edaOrderId && !"".equals(edaOrderId)) {
				order.put("edaOrderId", edaOrderId);
				order.put("createJson", requestModel.getRequestSaveJson());
				iEDADao.updateEDAOrder(order);
			}
		}
	}
	
	
	/**
	 * 定时上传美西仓订单跟踪号
	 */
	public void uploadEDAOrderShipNumber() {
		List<Map<String,Object>> list = iEDADao.getImportShips();
		//根据平台分线程处理
		List<Map<String,Object>> ebay  = new ArrayList<>();
		List<Map<String,Object>> walmrt  = new ArrayList<>();
		List<Map<String,Object>> light  = new ArrayList<>();
		for(Map<String,Object> map : list){
			String platform = map.get("PLATFORM").toString();
			//处理补发单，更新补发单信息
			String wOrderId = map.get("ORDERID").toString();
			if(wOrderId.indexOf("W_")> -1){
				map.put("CARRIER", EDAUploadTrackingNumberService.formatShipCarrier(map.get("CARRIER").toString()));
				String orderId = wOrderId.substring(2, wOrderId.lastIndexOf("_"));
				String times = wOrderId.substring(wOrderId.lastIndexOf("_")+1);
				if("walmart".equals(platform)){
					orderId = iEDADao.getCOrderId(orderId);
				}
				map.put("wOrderId", orderId);
				map.put("times", times);
				iEDADao.updateWOrderTranckNumber(map);
				map.put("status", 7);
				iEDADao.updateWestShipStatus(map);
				//ebay补发单主动向客户发送消息
				if("ebay".equals(platform)){
					wOrderSendMessage(map);
				}
				continue;
			}
			//线下单跟踪号回填
			if(wOrderId.indexOf("OCS")> -1){
				map.put("CARRIER", EDAUploadTrackingNumberService.formatShipCarrier(map.get("CARRIER").toString()));
				map.put("wOrderId", wOrderId);
				map.put("times", 1);
				iEDADao.updateWOrderTranckNumber(map);
				map.put("status", 7);
				iEDADao.updateWestShipStatus(map);
				continue;
			}
			//String platform = map.get("platform").toString();
			//edaUploadTrackingNumberService.uploadTranckingNumber(map);
			
			if("ebay".equals(platform)){
				ebay.add(map);
			}else if("light".equals(platform)){
				light.add(map);
			}else if("walmart".equals(platform)){
				walmrt.add(map);
			}
		}
		
		createThreadRun("ebay",ebay);
		createThreadRun("light",light);
		createThreadRun("walmart",walmrt);
		
	}

	private void wOrderSendMessage(Map<String, Object> map) {
		Map<String,Object> order =  iEDADao.getOrderByWOrder(map);
		Map<String,Object> xmlValueMap = new HashMap<>();
		xmlValueMap.put("QuestionTypeCodeType", "Shipping");
		xmlValueMap.put("subject", "Tracking number of the replacement");
		xmlValueMap.put("media","");
		xmlValueMap.put("account", order.get("ACCOUNT"));
		xmlValueMap.put("itemId", order.get("ITEMID"));
		xmlValueMap.put("buyerId", order.get("BUYERID"));
		String site = order.get("SITEID").toString();
		
		TemplateModel template = null;
		if("US".equals(site)||"UK".equals(site)){
			template = templateService.getTemplateContent("ebayShipMessageTempEN", "ebay");
		}else{
			template = templateService.getTemplateContent("ebayShipMessageTempDE", "ebay");
		}
		Map<String,String> shipInfo = new HashMap<>();
		shipInfo.put("carrier", map.get("CARRIER").toString());
		shipInfo.put("trackingNo", map.get("TRANCKNUMBER").toString());
		xmlValueMap.put("messageTxt",TemplateService.formatTemplat(shipInfo, template.getContent()));
		OperationResult result = ebayMessageService.sendUseMessage(xmlValueMap);
		if(!"success".equals(result.getData().toString())){
			log.error("补发单上传跟踪号通知客户失败："+site+"--"+order.get("ACCOUNT")+"--"+order.get("ORDERID")+"--"+order.get("ITEMID"));
		}else{
			map.put("account", order.get("ACCOUNT"));
			iEDADao.updateEbayOrderRemark(map);
		}
	}
	
	private void createThreadRun(String platform,List<Map<String,Object>> list){
		TaskExecutorUtil.threadRun(new TaskRunnable() {
			@Override
			public void runTask() {
				for(Map<String,Object> map : list){
					edaUploadTrackingNumberService.uploadTranckingNumber(platform,map);
				}
			}
			
		});
	}
	/**
	 * 定时任务
	 * 查询eda发货订单详情
	 */
	public void getEDAOrderInfo() {
		List<Map<String, Object>> orders = iEDADao.getEDAOrder();
		for (Map<String, Object> order : orders) {
			try {
				syncEDAOrderInfo(order);
			} catch (Exception e) {
				log.error("[syncEDAOrderInfo(...)] - " + e.getMessage() + ": eda_order.id=" + (order != null && order.containsKey("ID") ? order.get("ID") : null), e);
			}
		}
	}

	public void syncEDAOrderInfo(Map<String, Object> order) {
		EDARequestModel requestModel = this.getEDARequestModel("getOrderInfoById", (String) order.get("EDAACCOUNT"));
		String edaId = String.valueOf(order.get("EDAID"));
		String id = String.valueOf(order.get("ID"));
		requestModel.setData(edaId);
		String response = EDAHttpUtil.httpPost(requestModel);
		Map<String, Object> data = EDAJsonUtil.parseDataJSON(EDAInterfaceUtils.getOrderInfoKeySet(), response);
		if (null == data || data.isEmpty()) {
			return;
		} else {
			data.put("parentId", id);
			if (iEDADao.isExist(id) == 0) {
				iEDADao.addEDAOrderInfo(data);
			} else {
				//查询物流号是否一样
				String shipInfosOld = iEDADao.getEDAOrderShipInfoByParentId(id);
				Object shipInfosNewObj = data.get("shipmentInfos");
				boolean isChange = shippingNumberIsChange(shipInfosOld, shipInfosNewObj);
				if(isChange){
					//跟踪号改变，重新上传跟踪号
					iEDADao.updateEDAOrderIsUpload(id);
				}
				iEDADao.updateEDAOrderInfo(data);
			}
			
			String status = String.valueOf(data.get("orderStatus"));// SHIPPED
			if ("SHIPPED".equals(status)) {
				iEDADao.updateEDAOrderInfoGetStatus(id);
			}
			
			/*//上传跟踪号
			String shipInfos = data.get("shipmentInfos").toString();
			if(null != shipInfos && !"".equals(shipInfos) && "0".equals(String.valueOf(order.get("ISUPLOAD")))){
				JSONArray json = JSONArray.fromObject(shipInfos);
				edaUploadTrackingNumberService.uploadTranckingNumber(json,order);
			}*/
			
			
		}
	}
	
	/**
	 * 判断跟踪号是否改变
	 * @param shipInfosOld
	 * @param shipInfosNewObj
	 * @return
	 */
	private boolean shippingNumberIsChange(String shipInfosOld, Object shipInfosNewObj) {
		boolean isChange = false;
		if(null != shipInfosNewObj&&shipInfosNewObj.toString().startsWith("[")&&null != shipInfosOld && !"".equals(shipInfosOld)){
			String shipInfosNew = shipInfosNewObj.toString();
			JSONArray ja = JSONArray.fromObject(shipInfosNew);
			
			for(int i =0;i<ja.size();i++){
				JSONObject ship = ja.getJSONObject(i);
				if(shipInfosOld.indexOf(ship.getString("trackingNum")) == -1){
					isChange = true;
					break;
				}
			}
		}
		return isChange;
	}
	
	private List<Map<String,String>> getShippingListByShippingJSON(String jsonArrayStr){
		List<Map<String,String>> reList = new ArrayList<>();
		JSONArray ships = JSONArray.fromObject(jsonArrayStr);
		for(int i=0;i<ships.size();i++){
			JSONObject j = ships.getJSONObject(i);
			Map<String,String> map = new HashMap<>();
			map.put("carrier", j.getString("courierName"));
			map.put("trankingNumber", j.getString("trackingNum"));
			reList.add(map);
		}
		return reList;
	}
	
	/**
	 * 定时上传EDA跟踪号
	 */
	public void uploadTranckingNumberTime(){
		List<Map<String, Object>> orders = iEDADao.noUploadOrder();
		List<Map<String,Object>> ebay  = new ArrayList<>();
		List<Map<String,Object>> walmrt  = new ArrayList<>();
		List<Map<String,Object>> light  = new ArrayList<>();
		for(Map<String, Object> map : orders){
			String platform = map.get("PLATFORM").toString();
			//处理补发单
			String wOrderId = map.get("ORDERID").toString();
			if(wOrderId.indexOf("W_")> -1){
				String orderId = wOrderId.substring(2, wOrderId.lastIndexOf("_"));
				String times = wOrderId.substring(wOrderId.lastIndexOf("_")+1);
				if("walmart".equals(platform)){
					orderId = iEDADao.getCOrderId(orderId);
				}
				map.put("wOrderId", orderId);
				map.put("times", times);
				//解析跟踪号
				List<Map<String,String>> ships = getShippingListByShippingJSON( map.get("SHIPINFOS").toString());
				//解析sku
				String items = map.get("ITEMS").toString();
				JSONArray itemInfos  = JSONArray.fromObject(items);
				for(int i=0;i<itemInfos.size();i++){
					String sku = itemInfos.getJSONObject(i).getString("warehouseSku");
					map.put("SKU", sku);
					Map<String,String> ship = new HashMap<>();
					if(ships.size() > i){
						ship = ships.get(i);
					}else{
						ship = ships.get(ships.size()-1);
					}
					map.put("CARRIER", ship.get("carrier"));	
					map.put("TRANCKNUMBER", ship.get("trankingNumber"));
					
					iEDADao.updateWOrderTranckNumber(map);
					if("ebay".equals(platform)){
						wOrderSendMessage(map);
					}
				}
				iEDADao.updateUploadStatus(map);
				continue;
			}
			
			if(wOrderId.indexOf("OCS")> -1){
				map.put("wOrderId", wOrderId);
				map.put("times", 1);
				//解析跟踪号
				List<Map<String,String>> ships = getShippingListByShippingJSON( map.get("SHIPINFOS").toString());
				//解析sku
				String items = map.get("ITEMS").toString();
				JSONArray itemInfos  = JSONArray.fromObject(items);
				for(int i=0;i<itemInfos.size();i++){
					String sku = itemInfos.getJSONObject(i).getString("warehouseSku");
					map.put("SKU", sku);
					Map<String,String> ship = new HashMap<>();
					if(ships.size() > i){
						ship = ships.get(i);
					}else{
						ship = ships.get(ships.size()-1);
					}
					map.put("CARRIER", ship.get("carrier"));	
					map.put("TRANCKNUMBER", ship.get("trankingNumber"));
					iEDADao.updateWOrderTranckNumber(map);
				}
				iEDADao.updateUploadStatus(map);
				continue;
			}
			
			if("ebay".equals(platform)){
				ebay.add(map);
			}else if("light".equals(platform)){
				light.add(map);
			}else if("walmart".equals(platform)){
				walmrt.add(map);
			}
		}
		
		createThreadRunEDA("ebay",ebay);
		createThreadRunEDA("light",light);
		createThreadRunEDA("walmart",walmrt);
	}
	
	private void createThreadRunEDA(String platform,List<Map<String,Object>> list){
		TaskExecutorUtil.threadRun(new TaskRunnable() {
			@Override
			public void runTask() {
				for(Map<String,Object> map : list){
					String shipInfos = map.get("SHIPINFOS").toString();
					JSONArray json = JSONArray.fromObject(shipInfos);
					//edaUploadTrackingNumberService.uploadTranckingNumber(json,map);
					edaUploadTrackingNumberService.uploadTranckingNumber(platform,json,map);
				}
			}
			
		});
	}
	
	public boolean cancelEDAOrder(Map<String, Object> order) {
		EDARequestModel requestModel = this.getEDARequestModel("cancelOrder", (String) order.get("EDAACCOUNT"));
		String bussnessNum = String.valueOf(order.get("BUSINESSNUM"));
		requestModel.setData(bussnessNum);
		String response = EDAHttpUtil.httpPost(requestModel);
		String flag = (String) EDAJsonUtil.parseResponseJSON(EDAInterfaceUtils.getResponseKeySet(), response)
				.get("data");
		if (null != flag && "true".equals(flag)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 定时任务
	 * 同步库存
	 */
	public void syncEDASkuStockInfo() {
		String method = "queryInventory";
		List<Map<String, Object>> acounts = iEDADao.getAllAccount(method);
		for (Map<String, Object> account : acounts) {
			syncAllStockInfo(account);
			break;
		}
	}
	public void syncEDASkuStockInfoHis() {
		String method = "queryInventory";
		List<Map<String, Object>> acounts = iEDADao.getAllAccount(method);
		for (Map<String, Object> account : acounts) {
			syncAllStockInfoHis(account);
			break;
		}
	}
	private void syncAllStockInfoHis(Map<String, Object> account) {
		String url = String.valueOf(account.get("URL"));
		String userName = String.valueOf(account.get("ACCOUNT"));
		String pwd = String.valueOf(account.get("PASSWORD"));
		EDARequestModel requestModel = new EDARequestModel(url, userName, pwd);
		requestModel.setData("*");
		String response = EDAHttpUtil.httpPost(requestModel);
		List<Map<String, Object>> data = EDAJsonUtil.parseDataListJSON(EDAInterfaceUtils.getSKUStockInfoKeySet(),
				response);
		CollectionUtil.each(data, new IAction<Map<String,Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				iEDADao.addSkuStockInfoHis(map);
			}
		});

	}

	private void syncAllStockInfo(Map<String, Object> account) {
		String url = String.valueOf(account.get("URL"));
		String userName = String.valueOf(account.get("ACCOUNT"));
		String pwd = String.valueOf(account.get("PASSWORD"));
		EDARequestModel requestModel = new EDARequestModel(url, userName, pwd);
		requestModel.setData("*");
		String response = EDAHttpUtil.httpPost(requestModel);
		List<Map<String, Object>> data = EDAJsonUtil.parseDataListJSON(EDAInterfaceUtils.getSKUStockInfoKeySet(),
				response);
		if (null != data) {
			for (Map<String, Object> map : data) {
				if (iEDADao.skuStockIsExist(map) == 0) {
					iEDADao.addSkuStockInfo(map);
				} else {
					iEDADao.updateSkuStockInfo(map);
				}
			}
		}

	}

	/**
	 * 订单分仓
	 */
	public void selectionOrder() {
		//EDA东部仓库
		// ebay
		Map<String,Map<String, List<ShipOrderInfo>>> ebayOrder = getOrderForEast(iEDADao.getEBayUSShipOrders());
		Map<String, List<ShipOrderInfo>> eastOrders = ebayOrder.get("east");
		formatAndSaveEdaOrder(eastOrders);
		//walmart
		Map<String,Map<String, List<ShipOrderInfo>>> walmartOrder = getOrderForEast(iEDADao.getWalmartShipOrders());
		eastOrders = walmartOrder.get("east");
		formatAndSaveEdaOrder(eastOrders);
		//官网
		Map<String,Map<String, List<ShipOrderInfo>>> lightOrder = getOrderForEast(iEDADao.getLightShipOrders());
		eastOrders = lightOrder.get("east");
		formatAndSaveEdaOrder(eastOrders);
		//补发数据
		Map<String,Map<String, List<ShipOrderInfo>>> wOrder = getOrderForEast(iEDADao.getWShipOrders());
		formatAndSaveEdaOrder(wOrder.get("east"));
		
		//线下单
		Map<String,Map<String, List<ShipOrderInfo>>> ocsOrder = getOrderForEast(iEDADao.getOCSShipOrders());
		formatAndSaveEdaOrder(ocsOrder.get("east"));
		//LE西部仓库
		formatAndSaveLEWestOrder(ebayOrder.get("west"));
		formatAndSaveLEWestOrder(walmartOrder.get("west"));
		formatAndSaveLEWestOrder(lightOrder.get("west"));
		formatAndSaveLEWestOrder(wOrder.get("west"));
		formatAndSaveEdaOrder(ocsOrder.get("west"));
	}
	/**
	 * LE西部仓库发货订单格式化与保存
	 */
	private void formatAndSaveLEWestOrder(Map<String, List<ShipOrderInfo>> westOrder) {
		// ebay
		List<ShipOrderInfo> westOrders = new ArrayList<ShipOrderInfo>();
		for (Map.Entry<String, List<ShipOrderInfo>> entry : westOrder.entrySet()) {
			westOrders.addAll(entry.getValue());
		}
		//根据订单分组
		Map<String, List<ShipOrderInfo>> map = new HashMap<>();
		//遍历查询sku重量
		for (ShipOrderInfo shipOrder : westOrders) {
			String ocsId = shipOrder.getOrderOCSId();
			shipOrder.setWeight(iEDADao.getSKUInfo(shipOrder.getWarehouseSku()));
			List<ShipOrderInfo> list = map.get(ocsId);
			if (null == list) {
				list = new ArrayList<>();
				list.add(shipOrder);
				map.put(ocsId, list);
			} else {
				list.add(shipOrder);
				//map.put(ocsId, list);
			}
		}
		List<Map<String, Object>> returnList = new ArrayList<>();
		for (Map.Entry<String, List<ShipOrderInfo>> entry : map.entrySet()) {
			returnList.addAll(USWestShipPackageSelectionSupport.formatPackage(entry.getValue()));
		}
		for(Map<String, Object> west : returnList){
			if(iEDADao.isExistInWest(west)== 0){
				if(iEDADao.isCreateInEast(west) == 0){
					west.put("isHand", 0);
					iEDADao.addWest(west);
				}
			}
		}
	}

	/**
	 * 东部仓库EDA订单格式化并保存
	 *
	 * @param eastOrders
	 */
	public void formatAndSaveEdaOrder(Map<String, List<ShipOrderInfo>> map) {
		//遍历每一组合并 并保存
		for (Map.Entry<String, List<ShipOrderInfo>> entry : map.entrySet()) {
			ShipOrderInfo edaOrder = null;
			JSONArray ja = new JSONArray();
			for(ShipOrderInfo s : entry.getValue()){
				if(edaOrder == null){
					edaOrder = s;
				}
				ja.add(s.getItemInfo());
			}
			edaOrder.setItems(ja);
			//存入eda待推送表
			//walmart快速发货问题，快速发货切换到快速发货的子账号
			if("Express".equals(edaOrder.getShippingMethod())&&"walmart".equals(edaOrder.getPlatform())){
				edaOrder.setEdaAccount("API_LE_Transport");
			}else{
				edaOrder.setEdaAccount("API_LE_West");
			}
			Map<String,String> param = new HashMap<>();
			param.put("edaAccount", edaOrder.getEdaAccount());
			param.put("ocsId", entry.getKey());
			if(iEDADao.edaOrderIsExist(param) == 0){
				//判断不能在西仓
				if(iEDADao.isCreateInWest(edaOrder) == 0){
					iEDADao.addEdaOrders(edaOrder);
				}
			}
		}
	}

	public Map<String,Map<String, List<ShipOrderInfo>>> getOrderForEast(List<ShipOrderInfo> orders) {
		// 合并同一个订单的详情信息
		Map<String,Map<String, List<ShipOrderInfo>>> returnMap = new HashMap<>();
		Map<String, List<ShipOrderInfo>> map = new HashMap<>();
		//分组存入map中
		for (ShipOrderInfo shipOrder : orders) {
			String ocsId = shipOrder.getOrderOCSId();
			if (null == map.get(ocsId)) {
				List<ShipOrderInfo> list = new ArrayList<>();
				list.add(shipOrder);
				map.put(ocsId, list);
			} else {
				List<ShipOrderInfo> list = map.get(ocsId);
				list.add(shipOrder);
				map.put(ocsId, list);
			}
		}
		Map<String, List<ShipOrderInfo>> east = new HashMap<>();
		Map<String, List<ShipOrderInfo>> west = new HashMap<>();
		//遍历每一组合并 判断仓库
		for (Map.Entry<String, List<ShipOrderInfo>> entry : map.entrySet()) {
			boolean isEast = true;
			for(ShipOrderInfo s : entry.getValue()){
				isEast = isEast(s);
				if(!isEast){
					break;
				}
			}
			if(isEast){
				east.put(entry.getKey(),entry.getValue());
			}else{
				west.put(entry.getKey(),entry.getValue());
			}
			
		}
		returnMap.put("east", east);
		returnMap.put("west", west);
		return returnMap;
	}
	
	
	public  boolean isEast(ShipOrderInfo info) {
	/*	
		String postalCode = info.getPostalCode();
		if (null == postalCode || "".equals(postalCode)) {
			return false;
		}
		String[] eastCode = { "0", "1", "2", "3", "4", "50", "51", "52", "53", "54", "55", "56", "60", "61", "62", "63",
				"64", "65", "70", "71", "72" };
		boolean f = false;
		for (String code : eastCode) {
			if (postalCode.startsWith(code)) {
				f = true;
				break;
			}
		}*/
		//判断库存
		boolean i = false;
		Integer iqty = iEDADao.getIQty(info.getWarehouseSku());
		if(null != iqty && iqty >= info.getQuantity()){
			i = true;
		}else{
			i = false;
		}
		return i;
	}

	public  List<ShipOrderInfo> getOrderForWest(List<ShipOrderInfo> orders) {
		// 合并同一个订单的详情信息
		Map<String, List<ShipOrderInfo>> map = new HashMap<>();
		//分组存入map中
		for (ShipOrderInfo shipOrder : orders) {
			String ocsId = shipOrder.getOrderOCSId();
			if (null == map.get(ocsId)) {
				List<ShipOrderInfo> list = new ArrayList<>();
				list.add(shipOrder);
				map.put(ocsId, list);
			} else {
				List<ShipOrderInfo> list = map.get(ocsId);
				list.add(shipOrder);
				map.put(ocsId, list);
			}
		}
		
		List<ShipOrderInfo> list = new ArrayList<>();
		//遍历每一组合并 判断仓库
		for (Map.Entry<String, List<ShipOrderInfo>> entry : map.entrySet()) {
			boolean isEast = false;
			for(ShipOrderInfo s : entry.getValue()){
				isEast = isEast(s);
				if(isEast){
					break;
				}
			}
			if(!isEast){
				list.addAll(entry.getValue());
			}
			
		}
		
		return list;
	}
	
	public void syncInventoryHistory(){
		String method = "queryInventoryHistory";
		List<Map<String, Object>> acounts = iEDADao.getAllAccount(method);
		for (Map<String, Object> account : acounts) {
			account.put("ACCOUNT", "API_LE_Transport");
			account.put("PASSWORD", "hycj7HGY");
			account.put("URL", "http://wms.edayun.cn/omniv4/webservice/queryInventoryHistory");
			//使用其中一个账号同步库存流水
			getInventoryHistoryByAccount(account,7);
			getInventoryHistoryByAccount(account,2);
			break;
		}
	}
	
	private void getInventoryHistoryByAccount(Map<String, Object> account,int warehouseId) {
		String url = String.valueOf(account.get("URL"));
		String userName = String.valueOf(account.get("ACCOUNT"));
		String pwd = String.valueOf(account.get("PASSWORD"));
		EDARequestModel requestModel = new EDARequestModel(url, userName, pwd);
		Map<String,Object> map = new HashMap<>();
		map.put("warehouseId", warehouseId);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cd = Calendar.getInstance();
		map.put("dateTo", sdf.format(cd.getTime()));
		//向前推3天
		cd.add(Calendar.HOUR, -24*5);
		map.put("dateFrom",sdf.format(cd.getTime()));
		
		
		int offset = 0;
		int total = 0;
		do{
			map.put("offset", offset);
			requestModel.setData(map);
			String response = EDAHttpUtil.httpPost(requestModel);
			JSONObject json = JSONObject.fromObject(response);
			JSONObject data = json.getJSONObject("data"); 
			total = data.getInt("total");
			offset = offset + 50;
			
			JSONArray rows = data.getJSONArray("rows");
			insertInventoryHistory(rows);
		}while(offset < total);
	}

	private void insertInventoryHistory(JSONArray rows) {
		if(null == rows || rows.size() == 0){
			return;
		}
		for(int i= 0;i<rows.size();i++){
			JSONObject obj = rows.getJSONObject(i);
			InventoryHistoryModel ih = InventoryHistoryModel.createInventoryHistoryModel(obj);
			if("".equals(ih.getBillNum())){
				if(iEDADao.inventoryHistoryExist2(ih) == 0){
					iEDADao.insertInventoryHistory(ih);
				}
			}else{
				if(iEDADao.inventoryHistoryExist(ih) == 0){
					iEDADao.insertInventoryHistory(ih);
				}
			}
			
		}
		
	}
	public static void main(String arg[]){
		/**
		 * <dependency>
    <groupId>cpdetector</groupId>
    <artifactId>cpdetector</artifactId>
    <version>1.0.10</version>
    <scope>system</scope>
    <systemPath>${basedir}/lib/cpdetector_1.0.10.jar</systemPath>
</dependency>
		 */
        String path = "D:/test/cmall/src/lib";
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File f : files){
        	String name = f.getName();
            System.out.println("<dependency>");
            System.out.println("	<groupId>com.cy.cmall</groupId>");
            System.out.println("	<artifactId>"+name.substring(0,name.length()-4)+"</artifactId>");
            System.out.println("	<version>1.0.0</version>");
            System.out.println("	<scope>system</scope>");
            System.out.println("	<systemPath>${basedir}/"+name+"</systemPath>");
            System.out.println("</dependency>");
        }

    }
	public static void main1(String[] args) {
		/*
		 * EDARequestModel requestModel = new EDARequestModel(
		 * "http://wms.omniselling.net:48080/omniv4/webservice/getOrderInfoById"
		 * ,"API_LE_West","123456"); String edaId = "100102444";
		 * requestModel.setData(edaId); String response =
		 * EDAHttpUtil.httpPost(requestModel); Map<String,Object> data =
		 * EDAJsonUtil.parseDataJSON(EDAInterfaceUtils.getOrderInfoKeySet(),
		 * response); System.out.println(data);
		 */
		EDARequestModel requestModel = new EDARequestModel(
				"http://wms.edayun.cn/omniv4/webservice/queryInventoryHistory", "API_LE_Transport", "hycj7HGY");
		Map<String,Object> map = new HashMap<>();
		map.put("warehouseId", 7);
		map.put("dateFrom", "2018-01-01");
		map.put("dateTo", "2018-06-01");
		map.put("offset", 0);
		requestModel.setData(map);
		String response = EDAHttpUtil.httpPost(requestModel);
		System.out.println(response);
		// Map<String,Object> data =
		// EDAJsonUtil.parseDataJSON(EDAInterfaceUtils.getOrderInfoKeySet(),
		// response);
		//System.out.println((String) EDAJsonUtil.parseResponseJSON(EDAInterfaceUtils.getResponseKeySet(), response).get("data"));
	}

}
