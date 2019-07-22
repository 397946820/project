package com.it.ocs.synchronou.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class SaleInfoModel extends BaseModel{
	private String account;
    private String orderId;
    private String orderStatus;
    private Integer hasPushed;
    private String amountPaid;
    private String paymentMethods;
    private String total;
    private String buyerUserId;
    private String createdTime;
    private String paidTime;
    private String shippedTime;
    private String lastModifiedTime;
    private String lastFetchTime;
    
    private String saleRecordId;
    
    private String shippingDetails;
    private String orderLineItemID;
    
    private String sku;
    private String siteId;
    private String itemId;
    private String remark;
    
    private String paypalTrantionCode;
    private String buyerEmail;
    private String paypalAccount;
    
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getHasPushed() {
		return hasPushed;
	}
	public void setHasPushed(Integer hasPushed) {
		this.hasPushed = hasPushed;
	}
	public String getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(String paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getBuyerUserId() {
		return buyerUserId;
	}
	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getPaidTime() {
		return paidTime;
	}
	public void setPaidTime(String paidTime) {
		this.paidTime = paidTime;
	}
	public String getShippedTime() {
		return shippedTime;
	}
	public void setShippedTime(String shippedTime) {
		this.shippedTime = shippedTime;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getLastFetchTime() {
		return lastFetchTime;
	}
	public void setLastFetchTime(String lastFetchTime) {
		this.lastFetchTime = lastFetchTime;
	}
	public String getShippingDetails() {
		return shippingDetails;
	}
	public void setShippingDetails(String shippingDetails) {
		this.shippingDetails = shippingDetails;
	}
	public String getSaleRecordId() {
		return saleRecordId;
	}
	public void setSaleRecordId(String saleRecordId) {
		this.saleRecordId = saleRecordId;
	}
	public String getOrderLineItemID() {
		return orderLineItemID;
	}
	public void setOrderLineItemID(String orderLineItemID) {
		this.orderLineItemID = orderLineItemID;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPaypalTrantionCode() {
		return paypalTrantionCode;
	}
	public void setPaypalTrantionCode(String paypalTrantionCode) {
		this.paypalTrantionCode = paypalTrantionCode;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getPaypalAccount() {
		return paypalAccount;
	}
	public void setPaypalAccount(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}
	
    
}
