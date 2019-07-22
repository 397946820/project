package com.it.ocs.salesStatistics.model;

import java.util.Date;

public class WalmartOrderModel {
	private Long id;

	private String purchaseOrderId;

	private String customerOrderId;

	private String customerEmailId;

	private Date orderDateUtc;

	private String phone;

	private Date estimatedDeliveryDateUtc;

	private Date estimatedShipDateUtc;

	private String methodCode;

	private String name;

	private String address1;

	private String address2;

	private String city;

	private String state;

	private String postalCode;

	private String country;

	private String addressType;

	private Long orderLineTotal;

	private Long processStatus;

	private Date createdAt;

	private Date updatedAt;

	private String orderAllStatus;

	public String getOrderAllStatus() {
		return orderAllStatus;
	}

	public void setOrderAllStatus(String orderAllStatus) {
		this.orderAllStatus = orderAllStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId == null ? null : purchaseOrderId.trim();
	}

	public String getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(String customerOrderId) {
		this.customerOrderId = customerOrderId == null ? null : customerOrderId.trim();
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId == null ? null : customerEmailId.trim();
	}

	public Date getOrderDateUtc() {
		return orderDateUtc;
	}

	public void setOrderDateUtc(Date orderDateUtc) {
		this.orderDateUtc = orderDateUtc;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public Date getEstimatedDeliveryDateUtc() {
		return estimatedDeliveryDateUtc;
	}

	public void setEstimatedDeliveryDateUtc(Date estimatedDeliveryDateUtc) {
		this.estimatedDeliveryDateUtc = estimatedDeliveryDateUtc;
	}

	public Date getEstimatedShipDateUtc() {
		return estimatedShipDateUtc;
	}

	public void setEstimatedShipDateUtc(Date estimatedShipDateUtc) {
		this.estimatedShipDateUtc = estimatedShipDateUtc;
	}

	public String getMethodCode() {
		return methodCode;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode == null ? null : methodCode.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1 == null ? null : address1.trim();
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2 == null ? null : address2.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode == null ? null : postalCode.trim();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType == null ? null : addressType.trim();
	}

	public Long getOrderLineTotal() {
		return orderLineTotal;
	}

	public void setOrderLineTotal(Long orderLineTotal) {
		this.orderLineTotal = orderLineTotal;
	}

	public Long getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Long processStatus) {
		this.processStatus = processStatus;
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

	@Override
	public String toString() {
		return "WalmartOrderModel [id=" + id + ", purchaseOrderId=" + purchaseOrderId + ", customerOrderId="
				+ customerOrderId + ", customerEmailId=" + customerEmailId + ", orderDateUtc=" + orderDateUtc
				+ ", phone=" + phone + ", estimatedDeliveryDateUtc=" + estimatedDeliveryDateUtc
				+ ", estimatedShipDateUtc=" + estimatedShipDateUtc + ", methodCode=" + methodCode + ", name=" + name
				+ ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state
				+ ", postalCode=" + postalCode + ", country=" + country + ", addressType=" + addressType
				+ ", orderLineTotal=" + orderLineTotal + ", processStatus=" + processStatus + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", orderAllStatus=" + orderAllStatus + "]";
	}

}