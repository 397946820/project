package com.it.ocs.eda.model;

import com.it.ocs.common.model.BaseModel;

public class EDAOrderModel extends BaseModel {
	
	private String platform;
	
	private String orderOcsId;
	
	private String shipmentinfos;
	
	private String items;
	
	private String orderId;
	
	private String edaOrderId;
	
	private String shippingMethod;
	
	private String shippingAddress;
	
	private String edaAccount;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public String getOrderOcsId() {
		return orderOcsId;
	}

	public void setOrderOcsId(String orderOcsId) {
		this.orderOcsId = orderOcsId;
	}

	public String getShipmentinfos() {
		return shipmentinfos;
	}

	public void setShipmentinfos(String shipmentinfos) {
		this.shipmentinfos = shipmentinfos;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEdaOrderId() {
		return edaOrderId;
	}

	public void setEdaOrderId(String edaOrderId) {
		this.edaOrderId = edaOrderId;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getEdaAccount() {
		return edaAccount;
	}

	public void setEdaAccount(String edaAccount) {
		this.edaAccount = edaAccount;
	}
	
	
	
}
