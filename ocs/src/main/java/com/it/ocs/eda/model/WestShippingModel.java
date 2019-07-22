package com.it.ocs.eda.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WestShippingModel extends OrderExportOfUSWestModel implements Serializable{
	private Long id;
	private String status;
	private Integer is_hand;
	private String orderOcsId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getIs_hand() {
		return is_hand;
	}
	public void setIs_hand(Integer is_hand) {
		this.is_hand = is_hand;
	}
	
	public String getOrderOcsId() {
		return orderOcsId;
	}
	public void setOrderOcsId(String orderOcsId) {
		this.orderOcsId = orderOcsId;
	}
	
	/**
	 * 西仓数据转东仓数据
	 * @param westShip
	 * @return
	 */
	public static EDAOrderModel toEastModel(List<WestShippingModel> westShip) {
		EDAOrderModel edaOrder = new EDAOrderModel();
		WestShippingModel west = westShip.get(0);
		edaOrder.setPlatform(west.getPlatform());
		String orderId = west.getMerchantFulfillmentOrderID();
		edaOrder.setOrderId(orderId);
		if(orderId.indexOf("W_")> -1){
			edaOrder.setOrderOcsId("W_"+west.getOrderOcsId()+orderId.substring(orderId.lastIndexOf("_")));
		}else{
			edaOrder.setOrderOcsId(west.getOrderOcsId());
		}
		edaOrder.setEdaAccount("API_LE_West");
		//组合地址信息
		JSONObject json = new JSONObject();
		json.put("addressLine1", west.getAddressFieldOne());
		json.put("addressLine2", west.getAddressFieldTwo()+" "+west.getAddressFieldThree());
		json.put("city", west.getAddressCity());
		json.put("country", west.getAddressCountryCode());
		json.put("name", west.getAddressName());
		json.put("phone", west.getAddressPhoneNumber());
		json.put("postalCode", west.getAddressPostalCode());
		json.put("provState", west.getAddressStateOrRegion());
		edaOrder.setShippingAddress(json.toString());
		//组合sku信息
		JSONArray ja = new JSONArray();
		Map<String,List<WestShippingModel>> map = new HashMap<>();
		for(WestShippingModel westModel : westShip){
			String sku = westModel.getMerchantSKU();
			List<WestShippingModel> cwsm = map.get(sku);
			if(null == cwsm){
				cwsm = new ArrayList<>();
			}
			cwsm.add(westModel);
			map.put(sku, cwsm);
		}
		
		for(Map.Entry<String, List<WestShippingModel>> entry : map.entrySet()){
			JSONObject item = new JSONObject();
			String sku = entry.getKey();
			item.put("warehouseSku", sku);
			int qty = 0;
			List<WestShippingModel> skuInfos = entry.getValue();
			for(WestShippingModel ws : skuInfos){
				qty = qty + Integer.parseInt(ws.getQuantity());
			}
			item.put("quantity", qty);
			item.put("unitPrice", "");
			ja.add(item);
		}
		edaOrder.setItems(ja.toString());
		return edaOrder;
	}
	
	
}
