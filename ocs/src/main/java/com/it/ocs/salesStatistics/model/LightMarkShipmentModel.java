package com.it.ocs.salesStatistics.model;

import java.util.Date;

public class LightMarkShipmentModel {
	
	public static final String TNUPLOADSTATUS_WAITING = "waiting";
	public static final String TNUPLOADSTATUS_UPLOADED = "uploaded";
	public static final String TNUPLOADSTATUS_FAILED = "failed";
	public static final String TNUPLOADSTATUS_CANCELED = "canceled";
	
	private Long id;
	private String actions;
	private String platform;
	private String account;
	private Long marker;
	private String orderId;
	private String itemId;
	private String tn01;
	private String carrier01;
	private String tn02;
	private String carrier02;
	private String tn03;
	private String carrier03;
	private String tn04;
	private String carrier04;
	private String tnUploadStatus;
	private Date shippedAt;
	private String cause;
	private String enabledFlag;
	private Date createdAt;
	private Date updatedAt;
	private Date tnInitAt;
	
	public Date getTnInitAt() {
		return tnInitAt;
	}
	public void setTnInitAt(Date tnInitAt) {
		this.tnInitAt = tnInitAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getMarker() {
		return marker;
	}
	public void setMarker(Long marker) {
		this.marker = marker;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getTn01() {
		return tn01;
	}
	public void setTn01(String tn01) {
		this.tn01 = tn01;
	}
	public String getCarrier01() {
		return carrier01;
	}
	public void setCarrier01(String carrier01) {
		this.carrier01 = carrier01;
	}
	public String getTn02() {
		return tn02;
	}
	public void setTn02(String tn02) {
		this.tn02 = tn02;
	}
	public String getCarrier02() {
		return carrier02;
	}
	public void setCarrier02(String carrier02) {
		this.carrier02 = carrier02;
	}
	public String getTn03() {
		return tn03;
	}
	public void setTn03(String tn03) {
		this.tn03 = tn03;
	}
	public String getCarrier03() {
		return carrier03;
	}
	public void setCarrier03(String carrier03) {
		this.carrier03 = carrier03;
	}
	public String getTn04() {
		return tn04;
	}
	public void setTn04(String tn04) {
		this.tn04 = tn04;
	}
	public String getCarrier04() {
		return carrier04;
	}
	public void setCarrier04(String carrier04) {
		this.carrier04 = carrier04;
	}
	public String getTnUploadStatus() {
		return tnUploadStatus;
	}
	public void setTnUploadStatus(String tnUploadStatus) {
		this.tnUploadStatus = tnUploadStatus;
	}
	public Date getShippedAt() {
		return shippedAt;
	}
	public void setShippedAt(Date shippedAt) {
		this.shippedAt = shippedAt;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
