package com.it.ocs.salesStatistics.model;

import java.util.Date;

public class LightOrderModel {
	private Long entityId;

	private String platform;

	private String orderId;

	private String state;

	private String status;

	private Double discountAmount;

	private Double subtotal;

	private Double grandTotal;

	private Double shippingAmount;

	private Double taxAmount;

	private Long totalCanceled;

	private Long totalInvoiced;

	private Long totalPaid;

	private Long totalQtyOrdered;

	private Double totalRefunded;

	private String giftMessage;

	private Double weight;

	private String customerEmail;

	private String customerFirstname;

	private String customerLastname;

	private String customerMiddlename;

	private String globalCurrencyCode;

	private String orderCurrencyCode;

	private String remoteIp;

	private String shippingMethod;

	private Date lightCreatedAt;

	private Date lightUpdatedAt;

	private Date createdAt;

	private Date updatedAt;

	private Double avs;

	private String method;

	private String shippingDescription;

	private String name;

	private Integer deliveryStatus;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Double getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(Double shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Long getTotalCanceled() {
		return totalCanceled;
	}

	public void setTotalCanceled(Long totalCanceled) {
		this.totalCanceled = totalCanceled;
	}

	public Long getTotalInvoiced() {
		return totalInvoiced;
	}

	public void setTotalInvoiced(Long totalInvoiced) {
		this.totalInvoiced = totalInvoiced;
	}

	public Long getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(Long totalPaid) {
		this.totalPaid = totalPaid;
	}

	public Long getTotalQtyOrdered() {
		return totalQtyOrdered;
	}

	public void setTotalQtyOrdered(Long totalQtyOrdered) {
		this.totalQtyOrdered = totalQtyOrdered;
	}

	public Double getTotalRefunded() {
		return totalRefunded;
	}

	public void setTotalRefunded(Double totalRefunded) {
		this.totalRefunded = totalRefunded;
	}

	public String getGiftMessage() {
		return giftMessage;
	}

	public void setGiftMessage(String giftMessage) {
		this.giftMessage = giftMessage == null ? null : giftMessage.trim();
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail == null ? null : customerEmail.trim();
	}

	public String getCustomerFirstname() {
		return customerFirstname;
	}

	public void setCustomerFirstname(String customerFirstname) {
		this.customerFirstname = customerFirstname == null ? null : customerFirstname.trim();
	}

	public String getCustomerLastname() {
		return customerLastname;
	}

	public void setCustomerLastname(String customerLastname) {
		this.customerLastname = customerLastname == null ? null : customerLastname.trim();
	}

	public String getCustomerMiddlename() {
		return customerMiddlename;
	}

	public void setCustomerMiddlename(String customerMiddlename) {
		this.customerMiddlename = customerMiddlename == null ? null : customerMiddlename.trim();
	}

	public String getGlobalCurrencyCode() {
		return globalCurrencyCode;
	}

	public void setGlobalCurrencyCode(String globalCurrencyCode) {
		this.globalCurrencyCode = globalCurrencyCode == null ? null : globalCurrencyCode.trim();
	}

	public String getOrderCurrencyCode() {
		return orderCurrencyCode;
	}

	public void setOrderCurrencyCode(String orderCurrencyCode) {
		this.orderCurrencyCode = orderCurrencyCode == null ? null : orderCurrencyCode.trim();
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp == null ? null : remoteIp.trim();
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod == null ? null : shippingMethod.trim();
	}

	public Date getLightCreatedAt() {
		return lightCreatedAt;
	}

	public void setLightCreatedAt(Date lightCreatedAt) {
		this.lightCreatedAt = lightCreatedAt;
	}

	public Date getLightUpdatedAt() {
		return lightUpdatedAt;
	}

	public void setLightUpdatedAt(Date lightUpdatedAt) {
		this.lightUpdatedAt = lightUpdatedAt;
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

	public Double getAvs() {
		return avs;
	}

	public void setAvs(Double avs) {
		this.avs = avs;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method == null ? null : method.trim();
	}

	public String getShippingDescription() {
		return shippingDescription;
	}

	public void setShippingDescription(String shippingDescription) {
		this.shippingDescription = shippingDescription == null ? null : shippingDescription.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	@Override
	public String toString() {
		return "LightOrderModel [entityId=" + entityId + ", platform=" + platform + ", orderId=" + orderId + ", state="
				+ state + ", status=" + status + ", discountAmount=" + discountAmount + ", subtotal=" + subtotal
				+ ", grandTotal=" + grandTotal + ", shippingAmount=" + shippingAmount + ", taxAmount=" + taxAmount
				+ ", totalCanceled=" + totalCanceled + ", totalInvoiced=" + totalInvoiced + ", totalPaid=" + totalPaid
				+ ", totalQtyOrdered=" + totalQtyOrdered + ", totalRefunded=" + totalRefunded + ", giftMessage="
				+ giftMessage + ", weight=" + weight + ", customerEmail=" + customerEmail + ", customerFirstname="
				+ customerFirstname + ", customerLastname=" + customerLastname + ", customerMiddlename="
				+ customerMiddlename + ", globalCurrencyCode=" + globalCurrencyCode + ", orderCurrencyCode="
				+ orderCurrencyCode + ", remoteIp=" + remoteIp + ", shippingMethod=" + shippingMethod
				+ ", lightCreatedAt=" + lightCreatedAt + ", lightUpdatedAt=" + lightUpdatedAt + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", avs=" + avs + ", method=" + method
				+ ", shippingDescription=" + shippingDescription + ", name=" + name + ", deliveryStatus="
				+ deliveryStatus + "]";
	}

}