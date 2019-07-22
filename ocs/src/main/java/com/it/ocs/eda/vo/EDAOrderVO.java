package com.it.ocs.eda.vo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class EDAOrderVO implements Serializable{
	private Integer id;
	private String platform;
	private String orderId;
	private String edaOrderId;
	private String edaPlatformOrderId;
	private String edaAccount;
	private String skuInfo;
	private String shipInfo;
	private String shipDate;
	private String buyerName;
	private String shipStatus;
	private String edaCreateDate;
	private Integer isCreate;
	private String shippingAddress;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
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
	public String getEdaPlatformOrderId() {
		return edaPlatformOrderId;
	}
	public void setEdaPlatformOrderId(String edaPlatformOrderId) {
		this.edaPlatformOrderId = edaPlatformOrderId;
	}
	public String getSkuInfo() {
		return skuInfo;
	}
	public void setSkuInfo(String skuInfo) {
		this.skuInfo = skuInfo;
	}
	public String getShipInfo() {
		return shipInfo;
	}
	public void setShipInfo(String shipInfo) {
		this.shipInfo = shipInfo;
	}
	public String getShipDate() {
		return shipDate;
	}
	public void setShipDate(Long shipDate) {
		this.shipDate = formartDate(shipDate);
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getShipStatus() {
		return shipStatus;
	}
	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}
	public String getEdaCreateDate() {
		return edaCreateDate;
	}
	public void setEdaCreateDate(Long edaCreateDate) {
		this.edaCreateDate = formartDate(edaCreateDate);
	}
	
	public String getEdaAccount() {
		return edaAccount;
	}
	public void setEdaAccount(String edaAccount) {
		this.edaAccount = edaAccount;
	}
	
	public Integer getIsCreate() {
		return isCreate;
	}
	public void setIsCreate(Integer isCreate) {
		this.isCreate = isCreate;
	}
	
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public static String formartDate(Long dateNumber){
		if(null != dateNumber){
			Date date = new Date(dateNumber);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getDefault());
			return sdf.format(date);
		}
		return "";
	}
	
}
