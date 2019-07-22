package com.it.ocs.mongo.model;

import java.util.Date;

import com.it.ocs.mongo.annotation.MongoTableName;

@MongoTableName(name = "AMAZON_ORDER")
public class AmazonOrderModel {
	private String sku;
	private String asin;
	private String platform;
	private String station;
	private double price;
	private String currencycode;// 币种
	private String status;// 订单状态
	private double deduction;// 折扣额
	private double taxrate;// 税额
	private int count;// 总数
	private String orderId;
	private String title;
	private String name;
	private String stateOrRegion;
	private String postalCode;
	private String phone;
	private String city;
	private String street;
	private String customerName;
	private Long entityId;
	private String itemId;
	private Date itemUpdatedat;
	private double amount;
	private Date purchaseat;// 购买时间
	private Date lastestshipdate;// 发货时间
	private Date updatedat;// 更新时间

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(double taxrate) {
		this.taxrate = taxrate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStateOrRegion() {
		return stateOrRegion;
	}

	public void setStateOrRegion(String stateOrRegion) {
		this.stateOrRegion = stateOrRegion;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Date getItemUpdatedat() {
		return itemUpdatedat;
	}

	public void setItemUpdatedat(Date itemUpdatedat) {
		this.itemUpdatedat = itemUpdatedat;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getPurchaseat() {
		return purchaseat;
	}

	public void setPurchaseat(Date purchaseat) {
		this.purchaseat = purchaseat;
	}

	public Date getLastestshipdate() {
		return lastestshipdate;
	}

	public void setLastestshipdate(Date lastestshipdate) {
		this.lastestshipdate = lastestshipdate;
	}

	public Date getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

}
