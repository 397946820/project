package com.it.ocs.synchronou.model;

import java.io.Serializable;

public class EbayShipUploadModel implements Serializable{
	private Integer id;
	private String account;
	private String orderId;
	private String transactionId;
	private String itemId;
	private String trackingNumber;
	private String carrier;
	private Integer toLine;
	private String shippedTime;
	private String cause;
	private String uploadDate;
	private String type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public Integer getToLine() {
		return toLine;
	}
	public void setToLine(Integer toLine) {
		this.toLine = toLine;
	}
	public String getShippedTime() {
		return shippedTime;
	}
	public void setShippedTime(String shippedTime) {
		this.shippedTime = shippedTime;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
