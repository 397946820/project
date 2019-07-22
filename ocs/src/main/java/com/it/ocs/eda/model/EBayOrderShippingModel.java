package com.it.ocs.eda.model;

import com.it.ocs.common.model.BaseModel;

public class EBayOrderShippingModel extends BaseModel {
	
	private String account;
	private String orderId;
	private String transactionId;
	private String itemId;
	private String shipTime;
	private String sku;
	private String carrier;
	private String trackingNumber;
	
	private Long ocsOrderId;
	
	private String carrier1;
	private String trackingNumber1;
	
	private String carrier2;
	private String trackingNumber2;
	
	private String carrier3;
	private String trackingNumber3;
	
	private String carrier4;
	private String trackingNumber4;
	
	public void setShipInfo(String _carrier,String trackingNumber){
		if(null == carrier){
			this.carrier = _carrier;
			this.trackingNumber = trackingNumber;
			return;
		}
		if(null == carrier1){
			this.carrier1 = _carrier;
			this.trackingNumber1= trackingNumber;
			return;
		}
		if(null == carrier2){
			this.carrier2 = _carrier;
			this.trackingNumber2 = trackingNumber;
			return;
		}
		if(null == carrier3){
			this.carrier3 = _carrier;
			this.trackingNumber3 = trackingNumber;
			return;
		}
		if(null == carrier4){
			this.carrier4 = _carrier;
			this.trackingNumber4 = trackingNumber;
			return;
		}
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getShipTime() {
		return shipTime;
	}
	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getCarrier1() {
		return carrier1;
	}
	public void setCarrier1(String carrier1) {
		this.carrier1 = carrier1;
	}
	public String getTrackingNumber1() {
		return trackingNumber1;
	}
	public void setTrackingNumber1(String trackingNumber1) {
		this.trackingNumber1 = trackingNumber1;
	}
	public String getCarrier2() {
		return carrier2;
	}
	public void setCarrier2(String carrier2) {
		this.carrier2 = carrier2;
	}
	public String getTrackingNumber2() {
		return trackingNumber2;
	}
	public void setTrackingNumber2(String trackingNumber2) {
		this.trackingNumber2 = trackingNumber2;
	}
	public String getCarrier3() {
		return carrier3;
	}
	public void setCarrier3(String carrier3) {
		this.carrier3 = carrier3;
	}
	public String getTrackingNumber3() {
		return trackingNumber3;
	}
	public void setTrackingNumber3(String trackingNumber3) {
		this.trackingNumber3 = trackingNumber3;
	}
	public String getCarrier4() {
		return carrier4;
	}
	public void setCarrier4(String carrier4) {
		this.carrier4 = carrier4;
	}
	public String getTrackingNumber4() {
		return trackingNumber4;
	}
	public void setTrackingNumber4(String trackingNumber4) {
		this.trackingNumber4 = trackingNumber4;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Long getOcsOrderId() {
		return ocsOrderId;
	}
	public void setOcsOrderId(Long ocsOrderId) {
		this.ocsOrderId = ocsOrderId;
	}
	
	
	
}
