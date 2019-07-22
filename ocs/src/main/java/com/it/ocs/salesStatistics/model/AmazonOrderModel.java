package com.it.ocs.salesStatistics.model;

import java.util.Date;

public class AmazonOrderModel {
	private Long id;

	private String platform;

	private String channel;

	private String orderId;

	private String status;

	private String paymentMethod;

	private Date purchaseAt;

	private Date createdAt;

	private Date updatedAt;

	private Date amazonUpdatedAt;

	private Date lastestShipDate;

	private Date lastestDeliveryDate;

	private String customerName;

	private String email;

	private Double amount;

	private String currencyCode;

	private String countryCode;

	private String name;

	private String stateOrRegion;

	private String postalCode;

	private String phone;

	private String city;

	private String street;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel == null ? null : channel.trim();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod == null ? null : paymentMethod.trim();
	}

	public Date getPurchaseAt() {
		return purchaseAt;
	}

	public void setPurchaseAt(Date purchaseAt) {
		this.purchaseAt = purchaseAt;
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

	public Date getAmazonUpdatedAt() {
		return amazonUpdatedAt;
	}

	public void setAmazonUpdatedAt(Date amazonUpdatedAt) {
		this.amazonUpdatedAt = amazonUpdatedAt;
	}

	public Date getLastestShipDate() {
		return lastestShipDate;
	}

	public void setLastestShipDate(Date lastestShipDate) {
		this.lastestShipDate = lastestShipDate;
	}

	public Date getLastestDeliveryDate() {
		return lastestDeliveryDate;
	}

	public void setLastestDeliveryDate(Date lastestDeliveryDate) {
		this.lastestDeliveryDate = lastestDeliveryDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode == null ? null : countryCode.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getStateOrRegion() {
		return stateOrRegion;
	}

	public void setStateOrRegion(String stateOrRegion) {
		this.stateOrRegion = stateOrRegion == null ? null : stateOrRegion.trim();
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode == null ? null : postalCode.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street == null ? null : street.trim();
	}

	@Override
	public String toString() {
		return "AmazonOrderModel [id=" + id + ", platform=" + platform + ", channel=" + channel + ", orderId=" + orderId
				+ ", status=" + status + ", paymentMethod=" + paymentMethod + ", purchaseAt=" + purchaseAt
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", amazonUpdatedAt=" + amazonUpdatedAt
				+ ", lastestShipDate=" + lastestShipDate + ", lastestDeliveryDate=" + lastestDeliveryDate
				+ ", customerName=" + customerName + ", email=" + email + ", amount=" + amount + ", currencyCode="
				+ currencyCode + ", countryCode=" + countryCode + ", name=" + name + ", stateOrRegion=" + stateOrRegion
				+ ", postalCode=" + postalCode + ", phone=" + phone + ", city=" + city + ", street=" + street + "]";
	}

}