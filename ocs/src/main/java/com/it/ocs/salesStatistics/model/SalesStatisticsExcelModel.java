package com.it.ocs.salesStatistics.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class SalesStatisticsExcelModel {

	@ExcelLink(title = "account", index = 0)
	private String account;

	@ExcelLink(title = "transactionSiteId", index = 1)
	private String transactionSiteId;

	@ExcelLink(title = "orderStatus", index = 2)
	private String orderStatus;

	@ExcelLink(title = "Sales Record Number", index = 3)
	private String salesRecordNumber;

	@ExcelLink(title = "User Id", index = 4)
	private String buyerUserId;

	@ExcelLink(title = "Order ID", index = 5)
	private String orderId;

	@ExcelLink(title = "Item ID", index = 6)
	private String itemId;

	@ExcelLink(title = "Transaction ID", index = 7)
	private String transactionId;

	@ExcelLink(title = "Item Title", index = 8)
	private String itemTitle;

	@ExcelLink(title = "Custom Label", index = 9)
	private String itemSku;

	@ExcelLink(title = "Quantity", index = 10)
	private String quantityPurchased;

	@ExcelLink(title = "Sale Price", index = 11)
	private String salePrice;

	@ExcelLink(title = "Shipping And Handling", index = 12)
	private String shipCost;

	@ExcelLink(title = "Refund Price", index = 13)
	private String refundPrice;

	@ExcelLink(title = "Total Price", index = 14)
	private String total;

	@ExcelLink(title = "Payment Method", index = 15)
	private String paymentMethods;

	@ExcelLink(title = "PayPal Transaction ID", index = 16)
	private String paypalTransactionId;

	@ExcelLink(title = "Sale Date", index = 17)
	private String createdTime;

	@ExcelLink(title = "Paid on Date", index = 18)
	private String paidTime;

	@ExcelLink(title = "Shipped on Date", index = 19)
	private String shippedTime;

	@ExcelLink(title = "Shipping Service", index = 20)
	private String shippingService;

	@ExcelLink(title = "shi to country", index = 21)
	private String shipToCountry;

	@ExcelLink(title = "shi to city", index = 22)
	private String shipToCity;

	@ExcelLink(title = "shi to state", index = 23)
	private String shipToState;

	private String shippingAddress;

	private String paypalTransactionInfo;

	public String getShipToCountry() {
		return shipToCountry;
	}

	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToState() {
		return shipToState;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getPaypalTransactionInfo() {
		return paypalTransactionInfo;
	}

	public void setPaypalTransactionInfo(String paypalTransactionInfo) {
		this.paypalTransactionInfo = paypalTransactionInfo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTransactionSiteId() {
		return transactionSiteId;
	}

	public void setTransactionSiteId(String transactionSiteId) {
		this.transactionSiteId = transactionSiteId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSalesRecordNumber() {
		return salesRecordNumber;
	}

	public void setSalesRecordNumber(String salesRecordNumber) {
		this.salesRecordNumber = salesRecordNumber;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(String quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getShipCost() {
		return shipCost;
	}

	public String getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(String refundPrice) {
		this.refundPrice = refundPrice;
	}

	public void setShipCost(String shipCost) {
		this.shipCost = shipCost;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(String paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public String getPaypalTransactionId() {
		return paypalTransactionId;
	}

	public void setPaypalTransactionId(String paypalTransactionId) {
		this.paypalTransactionId = paypalTransactionId;
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

	public String getShippingService() {
		return shippingService;
	}

	public void setShippingService(String shippingService) {
		this.shippingService = shippingService;
	}

	public String getItemSku() {
		return itemSku;
	}

	public void setItemSku(String itemSku) {
		this.itemSku = itemSku;
	}

}
