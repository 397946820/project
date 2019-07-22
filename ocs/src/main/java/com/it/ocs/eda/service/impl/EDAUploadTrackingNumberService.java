package com.it.ocs.eda.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.EBayOrderShippingModel;
import com.it.ocs.eda.model.EDAOrderModel;
import com.it.ocs.salesStatistics.service.ILightSaleOrderService;
import com.it.ocs.salesStatistics.service.IWalmartSaleOrderService;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.RequestModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.impl.BaseHttpsService;
import com.it.ocs.synchronou.service.impl.EbayAccountService;
import com.it.ocs.synchronou.service.impl.TemplateService;
import com.it.ocs.synchronou.util.UTCTimeUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class EDAUploadTrackingNumberService {
	
	private final static Logger log = Logger.getLogger(EDAUploadTrackingNumberService.class);
	@Autowired
	private IEDADao iEDADao;
	
	@Autowired
	private TemplateService templateService;

	@Autowired
	private BaseHttpsService baseHttpService;
	
	@Autowired
	private EbayAccountService ebayAccountService;
	
	@Autowired
	private ILightSaleOrderService iLightSaleOrderService;
	
	@Autowired
	private IWalmartSaleOrderService iWalmartSaleOrderService;

	public void uploadTranckingNumber(String platform,JSONArray json, Map<String, Object> order) {
		//平台
		if("ebay".equals(platform)){
			uploadEbay(json,order);
		}else if("light".equals(platform)){
			uploadLight(json,order);
		}else if("walmart".equals(platform)){
			uploadWarlmat(json,order);
		}
	}
	
	/**
	 * walamrt上传跟踪号
	 * @param jsonArray
	 * @param order
	 */
	private void uploadWarlmat(JSONArray jsonArray, Map<String, Object> order) {
		List<Map<String,Object>> lists = iEDADao.getWalmartOrderInfoByShip(order);
		String orderId = null;
		for (int i = 0; i < lists.size(); i++) {
			Map<String,Object> row = lists.get(i);
			JSONObject shipJson = null;
			if(i < jsonArray.size()){
				shipJson = jsonArray.getJSONObject(i);
			}else{
				shipJson = jsonArray.getJSONObject(jsonArray.size()-1);
			}
			if(null == orderId){
				orderId = row.get("ORDER_ID").toString();
			}
			row.put("lineNumber", row.get("LINE_NUMBER"));
			row.put("shipCarrier", shipJson.getString("courierName"));
			row.put("shipTrackingNumber", shipJson.getString("trackingNum"));
		}
		OperationResult or = iWalmartSaleOrderService.uploadTranNumberByJSON(orderId, lists);
		if(null != or){
			iEDADao.updateUploadStatus(order);
		}
	}
	
	/**
	 * 官网上传跟踪号
	 * @param jsonArray
	 * @param order
	 */
	private void uploadLight(JSONArray jsonArray, Map<String, Object> order) {
		String orderId = iEDADao.getLightOrderForShip(order);
		boolean isSuccess = true;
		List<Map<String,Object>> ships = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject shipJson = jsonArray.getJSONObject(i);
			Map<String,Object> map = new HashMap<>();
			map.put("tranNumber", shipJson.getString("trackingNum"));
			map.put("carrier", shipJson.getString("courierName"));
			ships.add(map);
		}
		//解析发货的sku信息
		JSONArray itemArray = JSONArray.fromObject(order.get("ITEMS").toString());
		/*List<Map<String,Object>> list = new ArrayList<>();
		for(int i=0;i<itemArray.size();i++){
			Map<String,Object> map = new HashMap<>();
			JSONObject sku = itemArray.getJSONObject(i);
			map.put("qty", sku.getInt("quantity"));
			//获取itemid
			map.put("orderId", orderId);
			map.put("sku", sku.getString("warehouseSku"));
			String itemId = iEDADao.getLightOrderItemIdBySku(map);
			map.put("order_item_id", itemId);
		}*/
		
		OperationResult or = iLightSaleOrderService.uploadTranNumber(orderId,ships,null);
		if(null == or){
			isSuccess = false;
		}
		if(isSuccess){
			iEDADao.updateUploadStatus(order);
		}
		//iLightSaleOrderService.uploadTranNumber(orderId, tranNumber, carrier);
		
	}

	private void uploadEbay(JSONArray shipInfos, Map<String, Object> order) {
		EDAOrderModel edaModel = new EDAOrderModel();
		edaModel.setId(Long.parseLong(order.get("OCSID").toString()));
		//edaModel.setItems(String.valueOf(order.get("ITEMS")));
		List<EBayOrderShippingModel> skuOrders = iEDADao.getEbayOrder(edaModel);
		boolean isSuccess = true;
		// 上传跟踪号
		for (int i = 0; i < skuOrders.size(); i++) {
			EBayOrderShippingModel skuOrder = skuOrders.get(i);
			List<JSONObject> ships = new ArrayList<>();
			//默认取一个
			if (i < shipInfos.size()) {
				//当时最后一个还没取完，全部给最后一个
				if(i == skuOrders.size()-1){
					for (int j = i; j < shipInfos.size(); j++) {
						ships.add(shipInfos.getJSONObject(j));
					}
				}else{
					ships.add(shipInfos.getJSONObject(i));
				}
			}else{
				ships.add(shipInfos.getJSONObject(shipInfos.size() - 1));
			}
			
			isSuccess = uploadEbayShippingNumber(skuOrder, ships);
		}
		
		if(isSuccess){
			iEDADao.updateUploadStatus(order);
		}else{
			iEDADao.updateUploadStatusFail(order);
		}
	}
	
	public boolean uploadEbayShippingNumber(EBayOrderShippingModel skuOrder, List<JSONObject> ships) {
		TemplateModel template = templateService.getTemplateContent("CompleteSale", "ebay");
		Map<String, String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("transactionID", skuOrder.getTransactionId());
		xmlValueMap.put("orderId", skuOrder.getOrderId());
		xmlValueMap.put("orderLineItemId", skuOrder.getItemId() + "-" + skuOrder.getTransactionId());
		EbayAccountModel account = ebayAccountService.getAccountModelByName(skuOrder.getAccount());
		account.setSiteId("201");
		if (null != ships && ships.size() > 0) {
			StringBuffer shipment = new StringBuffer();
			for (JSONObject info : ships) {
				String carrier = info.getString("courierName");
				String trackingNumber = info.getString("trackingNum");
				skuOrder.setShipInfo(carrier, trackingNumber);
				shipment.append("<ShipmentTrackingDetails>");
				shipment.append("<ShipmentTrackingNumber>" + trackingNumber + "</ShipmentTrackingNumber>");
				shipment.append("<ShippingCarrierUsed>" + carrier + "</ShippingCarrierUsed>");
				shipment.append("</ShipmentTrackingDetails>");
			}

			String shipTime = UTCTimeUtils.getUTCTimeStr(0);
			shipment.append("<ShippedTime>" + shipTime + "</ShippedTime>");
			xmlValueMap.put("shipment", shipment.toString());

			RequestModel requetModel = new RequestModel(template, account, xmlValueMap);
			log.info(requetModel.getUrl());
			log.info(requetModel.getRequestHead());
			log.info(requetModel.getRequestXML());
			Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(),
					requetModel.getRequestXML());
			String message = ParseXMLModel.getRequstMessage(doc, "urn:ebay:apis:eBLBaseComponents");
			if ("".equals(message)) {
				shipTime = shipTime.replace("T", " ");
				shipTime = shipTime.replace("Z", "");
				skuOrder.setShipTime(shipTime);
				// 更新订单//更新标示
				iEDADao.updateEbayOrderShipInfo(skuOrder);
				log.info("orderId:" + skuOrder.getOrderId() + " EDA发货并上传EBay成功：" + skuOrder.getCarrier() + ","
						+ skuOrder.getTrackingNumber());
				return true;
			} else {
				log.error("orderId:" + skuOrder.getOrderId() + " 上传快递运输号失败，错误信息：" + message);
				return false;
			}
		}
		return false;
	
	}
	
	/**
	 * 美国西部仓库导入上传
	 * @param map
	 */
	public void uploadTranckingNumber(String platform,Map<String, Object> map) {
		if("ebay".equals(platform)){
			uploadEbay(map);
		}else if("light".equals(platform)){
			uploadLight(map);
		}else if("walmart".equals(platform)){
			uploadWarlmat(map);
		}
	}

	private void uploadEbay(Map<String, Object> map) {
		EBayOrderShippingModel shipModel = iEDADao.getEbayOrderItemById(map);
		JSONObject ship = new JSONObject();
		ship.put("courierName", formatShipCarrier(map.get("CARRIER").toString()));
		ship.put("trackingNum", map.get("TRANCKNUMBER").toString());
		List<JSONObject> ships = new ArrayList<>();
		ships.add(ship);
		boolean b = uploadEbayShippingNumber(shipModel,ships);
		if(b){
			map.put("status", 2);
			
		}else{
			map.put("status", 3);
			
		}
		iEDADao.updateWestShipStatus(map);
	}



	private void uploadLight(Map<String, Object> map) {
		List<Map<String,Object>> ships = new ArrayList<>();
		Map<String,Object> ship = new HashMap<>();
		ship.put("tranNumber", map.get("TRANCKNUMBER").toString());
		ship.put("carrier", formatShipCarrier(map.get("CARRIER").toString()));
		ships.add(ship);
		OperationResult or = null;
		if(iEDADao.shippingNumberHasUpload(map)> 0){
			or = new OperationResult();
		}else{
			or = iLightSaleOrderService.uploadTranNumber(map.get("ORDERID").toString(), ships,null);
		}
		
		if(null == or){
			map.put("status", 3);
		}else{
			map.put("status", 2);
		}
		iEDADao.updateWestShipStatus(map);
	}

	private void uploadWarlmat(Map<String, Object> map) {
		List<Map<String,Object>> lists = iEDADao.getWalmartOrderItemInfoByShip(map);
		String orderId = null;
		for (int i = 0; i < lists.size(); i++) {
			Map<String,Object> row = lists.get(i);
			if(null == orderId){
				orderId = row.get("ORDER_ID").toString();
			}
			row.put("lineNumber", row.get("LINE_NUMBER"));
			row.put("shipCarrier", formatShipCarrier(map.get("CARRIER").toString()));
			row.put("shipTrackingNumber",  map.get("TRANCKNUMBER").toString());
		}
		OperationResult or = iWalmartSaleOrderService.uploadTranNumberByJSON(orderId, lists);
		//walmart上传失败下次继续上传
		if(null != or){
			map.put("status", 2);
			iEDADao.updateWestShipStatus(map);
		}
	}
	
	public static String formatShipCarrier(String string) {
		if("USPS01".equals(string)||"USPS02".equals(string)){
			return "USPS";
		}
		if("FDX01".equals(string)||"FDX02".equals(string)){
			return "FedEx";
		}
		
		return string;
	}
}
