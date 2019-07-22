package com.it.ocs.compare.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class EbayExcelModel {
	@ExcelLink(title = "Sales Record Number", index = 0)
	private String saleRecordNumber;
	@ExcelLink(title = "User Id", index = 1)
	private String userId;
	@ExcelLink(title = "Buyer Fullname", index = 2)
	private String buyerFullName;
	@ExcelLink(title = "Buyer Phone Number", index = 3)
	private String buyerPhoneNumber;
	@ExcelLink(title = "Buyer Phone Number", index = 4)
	private String buyerEmail;
	@ExcelLink(title = "Buyer Address 1", index = 5)
	private String buyerAddress1;
	@ExcelLink(title = "Buyer Address 2", index = 6)
	private String buyerAddress2;
	@ExcelLink(title = "Buyer City", index = 7)
	private String buyerCity;
	@ExcelLink(title = "Buyer State", index = 8)
	private String buyerState;
	@ExcelLink(title = "Buyer Zip", index = 9)
	private String buyerZip;
	@ExcelLink(title = "Buyer Country", index = 10)
	private String buyerCountry;
	@ExcelLink(title = "Order ID", index = 11)
	private String orderId;
	@ExcelLink(title = "Item ID", index = 12)
	private String itemId;
	@ExcelLink(title = "Transaction ID", index = 13)
	private String transactionId;
	@ExcelLink(title = "Item Title", index = 14)
	private String itemTitle;
	@ExcelLink(title = "Quantity", index = 15)
	private String quantity;
	@ExcelLink(title = "Sale Price", index = 16)
	private String salePrice;
	@ExcelLink(title = "Shipping And Handling", index = 17)
	private String shippingAndHandling;
	@ExcelLink(title = "Sales Tax", index = 18)
	private String salesTax;
	@ExcelLink(title = "Insurance", index = 19)
	private String insurance;
	@ExcelLink(title = "Total Price", index = 20)
	private String totalPrice;
	@ExcelLink(title = "Payment Method", index = 21)
	private String paymentMethod;
	@ExcelLink(title = "PayPal Transaction ID", index = 22)
	private String paypalTransactionId;
	@ExcelLink(title = "Sale Date", index = 23)
	private String saleDate;
	@ExcelLink(title = "Checkout Date", index = 24)
	private String checkoutDate;
	@ExcelLink(title = "Paid on Date", index = 25)
	private String paidOnDate;
	@ExcelLink(title = "Shipped on Date", index = 26)
	private String shippedOnDate;
	@ExcelLink(title = "Shipping Service", index = 27)
	private String shippingService;
	@ExcelLink(title = "Feedback Left", index = 28)
	private String feedBackLeft;
	@ExcelLink(title = "Feedback Received", index = 29)
	private String feedbackReceived;
	@ExcelLink(title = "Notes to Yourself", index = 30)
	private String notesToYourself;
	@ExcelLink(title = "Custom Label", index = 31)
	private String customLabel;
	@ExcelLink(title = "Listed On", index = 32)
	private String listedOn;
	@ExcelLink(title = "Sold On", index = 33)
	private String soldOn;
	@ExcelLink(title = "Private Notes", index = 34)
	private String privateNotes;
	@ExcelLink(title = "Product ID Type", index = 35)
	private String productIdType;
	@ExcelLink(title = "Product ID Value", index = 36)
	private String productIdValue;
	@ExcelLink(title = "Product ID Value 2", index = 37)
	private String productIdValue2;
	@ExcelLink(title = "Variation Details", index = 38)
	private String variationDetails;
	@ExcelLink(title = "Product Reference ID", index = 39)
	private String productReferenceId;
	@ExcelLink(title = "dbTotalPrice", index = 40)
	private Double dbTotalPrice;
	@ExcelLink(title = "dbSubTotalPrice", index = 41)
	private Double dbSubTotalPrice;

	public Double getDbSubTotalPrice() {
		return dbSubTotalPrice;
	}

	public void setDbSubTotalPrice(Double dbSubTotalPrice) {
		this.dbSubTotalPrice = dbSubTotalPrice;
	}

	public String getProductIdType() {
		return productIdType;
	}

	public void setProductIdType(String productIdType) {
		this.productIdType = productIdType;
	}

	public String getProductIdValue() {
		return productIdValue;
	}

	public void setProductIdValue(String productIdValue) {
		this.productIdValue = productIdValue;
	}

	public String getProductIdValue2() {
		return productIdValue2;
	}

	public void setProductIdValue2(String productIdValue2) {
		this.productIdValue2 = productIdValue2;
	}

	public String getVariationDetails() {
		return variationDetails;
	}

	public void setVariationDetails(String variationDetails) {
		this.variationDetails = variationDetails;
	}

	public String getProductReferenceId() {
		return productReferenceId;
	}

	public void setProductReferenceId(String productReferenceId) {
		this.productReferenceId = productReferenceId;
	}

	public Double getDbTotalPrice() {
		return dbTotalPrice;
	}

	public void setDbTotalPrice(Double dbTotalPrice) {
		this.dbTotalPrice = dbTotalPrice;
	}

	public String getSaleRecordNumber() {
		return saleRecordNumber;
	}

	public void setSaleRecordNumber(String saleRecordNumber) {
		this.saleRecordNumber = saleRecordNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBuyerFullName() {
		return buyerFullName;
	}

	public void setBuyerFullName(String buyerFullName) {
		this.buyerFullName = buyerFullName;
	}

	public String getBuyerPhoneNumber() {
		return buyerPhoneNumber;
	}

	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		this.buyerPhoneNumber = buyerPhoneNumber;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerAddress1() {
		return buyerAddress1;
	}

	public void setBuyerAddress1(String buyerAddress1) {
		this.buyerAddress1 = buyerAddress1;
	}

	public String getBuyerAddress2() {
		return buyerAddress2;
	}

	public void setBuyerAddress2(String buyerAddress2) {
		this.buyerAddress2 = buyerAddress2;
	}

	public String getBuyerCity() {
		return buyerCity;
	}

	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}

	public String getBuyerState() {
		return buyerState;
	}

	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}

	public String getBuyerZip() {
		return buyerZip;
	}

	public void setBuyerZip(String buyerZip) {
		this.buyerZip = buyerZip;
	}

	public String getBuyerCountry() {
		return buyerCountry;
	}

	public void setBuyerCountry(String buyerCountry) {
		this.buyerCountry = buyerCountry;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getShippingAndHandling() {
		return shippingAndHandling;
	}

	public void setShippingAndHandling(String shippingAndHandling) {
		this.shippingAndHandling = shippingAndHandling;
	}

	public String getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(String salesTax) {
		this.salesTax = salesTax;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaypalTransactionId() {
		return paypalTransactionId;
	}

	public void setPaypalTransactionId(String paypalTransactionId) {
		this.paypalTransactionId = paypalTransactionId;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getPaidOnDate() {
		return paidOnDate;
	}

	public void setPaidOnDate(String paidOnDate) {
		this.paidOnDate = paidOnDate;
	}

	public String getShippedOnDate() {
		return shippedOnDate;
	}

	public void setShippedOnDate(String shippedOnDate) {
		this.shippedOnDate = shippedOnDate;
	}

	public String getShippingService() {
		return shippingService;
	}

	public void setShippingService(String shippingService) {
		this.shippingService = shippingService;
	}

	public String getFeedBackLeft() {
		return feedBackLeft;
	}

	public void setFeedBackLeft(String feedBackLeft) {
		this.feedBackLeft = feedBackLeft;
	}

	public String getFeedbackReceived() {
		return feedbackReceived;
	}

	public void setFeedbackReceived(String feedbackReceived) {
		this.feedbackReceived = feedbackReceived;
	}

	public String getNotesToYourself() {
		return notesToYourself;
	}

	public void setNotesToYourself(String notesToYourself) {
		this.notesToYourself = notesToYourself;
	}

	public String getCustomLabel() {
		return customLabel;
	}

	public void setCustomLabel(String customLabel) {
		this.customLabel = customLabel;
	}

	public String getListedOn() {
		return listedOn;
	}

	public void setListedOn(String listedOn) {
		this.listedOn = listedOn;
	}

	public String getSoldOn() {
		return soldOn;
	}

	public void setSoldOn(String soldOn) {
		this.soldOn = soldOn;
	}

	public String getPrivateNotes() {
		return privateNotes;
	}

	public void setPrivateNotes(String privateNotes) {
		this.privateNotes = privateNotes;
	}

}
