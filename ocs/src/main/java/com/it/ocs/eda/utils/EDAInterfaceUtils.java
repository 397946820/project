package com.it.ocs.eda.utils;

import java.util.HashMap;
import java.util.Map;

public class EDAInterfaceUtils {
	
	public static Map<String,Object> getRequestCreatrOrderKeySet(){
		Map<String,Object> keys = new HashMap<>();
		keys.put("poNumber", "poNumber");// 平台订单id
		keys.put("currency", "currency");// 否
		keys.put("shippingMethod", "shippingMethod");//寄件类型
		keys.put("shippingAddress", "shippingAddress");
		keys.put("items", "items");
		return keys;
	}
	
	public static Map<String,Object> getResponseKeySet(){
		Map<String,Object> keys = new HashMap<>();
		keys.put("errors", "errors");
		keys.put("data", "data");
		keys.put("nonceToken", "nonceToken");
		keys.put("errorsToString", "errorsToString");
		return keys;
	}

	public static Map<String,Object> getOrderInfoKeySet() {
		Map<String,Object> keys = new HashMap<>();
		keys.put("channelOrderId", "channelOrderId");
		keys.put("businessNum", "businessNum");
		keys.put("omniOrderId", "omniOrderId");
		keys.put("type", "type");
		keys.put("orderDate", "orderDate");
		keys.put("shippedDate", "shippedDate");
		keys.put("fulfilment", "fulfilment");
		keys.put("orderStatus", "orderStatus");
		keys.put("buyerName", "buyerName");
		keys.put("shippingService", "shippingService");
		keys.put("shippingCost", "shippingCost");
		keys.put("note", "note");
		keys.put("itemDetails", "itemDetails");
		keys.put("shippingAddress", "shippingAddress");
		keys.put("shipmentInfos", "shipmentInfos");
		return keys;
	}

	public static Map<String, Object> getSKUStockInfoKeySet() {
		Map<String,Object> keys = new HashMap<>();
		keys.put("sku", "sku");
		keys.put("qty", "qty");
		keys.put("warehouseId", "warehouseId");
		keys.put("warehouseName", "warehouseName");
		keys.put("totalInventory", "totalInventory");
		keys.put("forOutboundInventory", "forOutboundInventory");
		return keys;
	}
}
