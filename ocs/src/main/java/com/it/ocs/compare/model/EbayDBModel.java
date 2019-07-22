package com.it.ocs.compare.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class EbayDBModel {
	@ExcelLink(title = "MerchantSKU", index = 0)
	private String sku;
	@ExcelLink(title = "Sales record number", index = 1)
	private String salesRecordNumber;
	@ExcelLink(title = "Sold price", index = 2)
	private Double itemTotalPrice;
	@ExcelLink(title = "Sub Total", index = 3)
	private String subTotal;
	@ExcelLink(title = "Total", index = 4)
	private String total;
	@ExcelLink(title = "account", index = 5)
	private String account;
	@ExcelLink(title = "order status", index = 6)
	private String orderStatus;
	@ExcelLink(title = "Sale Date", index = 7)
	private Date createdTime;
	@ExcelLink(title = "Pay Status", index = 8)
	private String payStatus;
	@ExcelLink(title = "Ship Date", index = 9)
	private Date shipDate;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public Double getItemTotalPrice() {
		return itemTotalPrice;
	}

	public void setItemTotalPrice(Double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getSalesRecordNumber() {
		return salesRecordNumber;
	}

	public void setSalesRecordNumber(String salesRecordNumber) {
		this.salesRecordNumber = salesRecordNumber;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

}
