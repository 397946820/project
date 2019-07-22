package com.it.ocs.task.model;

import java.sql.Timestamp;

import com.it.ocs.common.model.BaseModel;

public class AmazonOrderModel extends BaseModel{

	private Long id;
	private String platform;
	private String channel;
	private String order_id;
	private String status;
	private String payment_method;
	private Timestamp purchase_at;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Timestamp amazon_updated_at;
	private Timestamp lastest_ship_date;
	private Timestamp lastest_delivery_date;
	private String customer_name;
	private String email;
	private Double amount;
	private String currency_code;
	private String country_code;
	private String name;
	private String state_or_region;
	private String postal_code;
	private String phone;
	private String city;
	private String street;
	private Integer parentId;
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public Timestamp getPurchase_at() {
		return purchase_at;
	}
	public void setPurchase_at(Timestamp purchase_at) {
		this.purchase_at = purchase_at;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public Timestamp getAmazon_updated_at() {
		return amazon_updated_at;
	}
	public void setAmazon_updated_at(Timestamp amazon_updated_at) {
		this.amazon_updated_at = amazon_updated_at;
	}
	public Timestamp getLastest_ship_date() {
		return lastest_ship_date;
	}
	public void setLastest_ship_date(Timestamp lastest_ship_date) {
		this.lastest_ship_date = lastest_ship_date;
	}
	public Timestamp getLastest_delivery_date() {
		return lastest_delivery_date;
	}
	public void setLastest_delivery_date(Timestamp lastest_delivery_date) {
		this.lastest_delivery_date = lastest_delivery_date;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public String getState_or_region() {
		return state_or_region;
	}
	public void setState_or_region(String state_or_region) {
		this.state_or_region = state_or_region;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
