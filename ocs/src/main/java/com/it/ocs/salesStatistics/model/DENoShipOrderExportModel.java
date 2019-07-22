package com.it.ocs.salesStatistics.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class DENoShipOrderExportModel {

	@ExcelLink(title = "PlatformAccount", index = 0)
	private String platformAccount;
	
	@ExcelLink(title = "OCS ID", index = 1)
	private String id;
	
	@ExcelLink(title = "Short Order ID", index = 2)
	private String shortOrderId;
	
	@ExcelLink(title = "ItemId", index = 3)
	private String itemId;

	@ExcelLink(title = "TransactionId", index = 4)
	private String transactionId;

	@ExcelLink(title = "SellingSKU", index = 5)
	private String sellingSKU;// 销售SKU

	@ExcelLink(title = "QTY", index = 6)
	private Integer qty;// 销售数量

	@ExcelLink(title = "MerchantFulfillmentOrderID", index = 7)
	private String merchantFulfillmentOrderID;// 订单号

	@ExcelLink(title = "DisplayableOrderID", index = 8)
	private String displayableOrderID;// 订单号

	@ExcelLink(title = "DisplayableOrderDate", index = 9)
	private String displayableOrderDate;// 下单时间

	@ExcelLink(title = "MerchantSKU", index = 10)
	private String merchantSKU;// 原始SKU

	@ExcelLink(title = "Quantity", index = 11)
	private Integer quantity;// 发货数量

	@ExcelLink(title = "MerchantFulfillmentOrderItemID", index = 12)
	private String merchantFulfillmentOrderItemID;// 原始SKU

	@ExcelLink(title = "GiftMessage", index = 13)
	private String giftMessage;// 留空

	@ExcelLink(title = "DisplayableComment", index = 14)
	private String displayableComment;// 留空

	@ExcelLink(title = "PerUnitDeclaredValue", index = 15)
	private String perUnitDeclaredValue;// 留空

	@ExcelLink(title = "DisplayableOrderComment", index = 16)
	private String displayableOrderComment;// 固定值：Thank you for order

	@ExcelLink(title = "DeliverySLA", index = 17)
	private String deliverySLA;// 固定值：Standard

	@ExcelLink(title = "AddressName", index = 18)
	private String addressName;// 姓名

	@ExcelLink(title = "AddressFieldOne", index = 19)
	private String addressFieldOne;// 地址一

	@ExcelLink(title = "AddressFieldTwo", index = 20)
	private String addressFieldTwo;// 地址二

	@ExcelLink(title = "AddressFieldThree", index = 21)
	private String addressFieldThree;// 地址三

	@ExcelLink(title = "AddressCity", index = 22)
	private String addressCity;// 城市

	@ExcelLink(title = "AddressCountryCode", index = 23)
	private String addressCountryCode;// 国家代码

	@ExcelLink(title = "AddressStateOrRegion", index = 24)
	private String addressStateOrRegion;// 州

	@ExcelLink(title = "AddressPostalCode", index = 25)
	private String addressPostalCode;// 邮编

	@ExcelLink(title = "AddressPhoneNumber", index = 26)
	private String addressPhoneNumber;// 电话

	@ExcelLink(title = "NotificationEmail", index = 27)
	private String notificationEmail;// 电话

	@ExcelLink(title = "Carrier", index = 28)
	private String carrier;

	@ExcelLink(title = "Tracking No.", index = 29)
	private String trackingNo;

	public String getPlatformAccount() {
		return platformAccount;
	}

	public void setPlatformAccount(String platformAccount) {
		this.platformAccount = platformAccount;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShortOrderId() {
		return shortOrderId;
	}

	public void setShortOrderId(String shortOrderId) {
		this.shortOrderId = shortOrderId;
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

	public String getMerchantFulfillmentOrderID() {
		return merchantFulfillmentOrderID;
	}

	public void setMerchantFulfillmentOrderID(String merchantFulfillmentOrderID) {
		this.merchantFulfillmentOrderID = merchantFulfillmentOrderID;
	}

	public String getDisplayableOrderID() {
		return displayableOrderID;
	}

	public void setDisplayableOrderID(String displayableOrderID) {
		this.displayableOrderID = displayableOrderID;
	}

	public String getDisplayableOrderDate() {
		return displayableOrderDate;
	}

	public void setDisplayableOrderDate(String displayableOrderDate) {
		this.displayableOrderDate = displayableOrderDate;
	}

	public String getSellingSKU() {
		return sellingSKU;
	}

	public void setSellingSKU(String sellingSKU) {
		this.sellingSKU = sellingSKU;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getMerchantSKU() {
		return merchantSKU;
	}

	public void setMerchantSKU(String merchantSKU) {
		this.merchantSKU = merchantSKU;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getMerchantFulfillmentOrderItemID() {
		return merchantFulfillmentOrderItemID;
	}

	public void setMerchantFulfillmentOrderItemID(String merchantFulfillmentOrderItemID) {
		this.merchantFulfillmentOrderItemID = merchantFulfillmentOrderItemID;
	}

	public String getGiftMessage() {
		return giftMessage;
	}

	public void setGiftMessage(String giftMessage) {
		this.giftMessage = giftMessage;
	}

	public String getDisplayableComment() {
		return displayableComment;
	}

	public void setDisplayableComment(String displayableComment) {
		this.displayableComment = displayableComment;
	}

	public String getPerUnitDeclaredValue() {
		return perUnitDeclaredValue;
	}

	public void setPerUnitDeclaredValue(String perUnitDeclaredValue) {
		this.perUnitDeclaredValue = perUnitDeclaredValue;
	}

	public String getDisplayableOrderComment() {
		return displayableOrderComment;
	}

	public void setDisplayableOrderComment(String displayableOrderComment) {
		this.displayableOrderComment = displayableOrderComment;
	}

	public String getDeliverySLA() {
		return deliverySLA;
	}

	public void setDeliverySLA(String deliverySLA) {
		this.deliverySLA = deliverySLA;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressFieldOne() {
		return addressFieldOne;
	}

	public void setAddressFieldOne(String addressFieldOne) {
		this.addressFieldOne = addressFieldOne;
	}

	public String getAddressFieldTwo() {
		return addressFieldTwo;
	}

	public void setAddressFieldTwo(String addressFieldTwo) {
		this.addressFieldTwo = addressFieldTwo;
	}

	public String getAddressFieldThree() {
		return addressFieldThree;
	}

	public void setAddressFieldThree(String addressFieldThree) {
		this.addressFieldThree = addressFieldThree;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressCountryCode() {
		return addressCountryCode;
	}

	public void setAddressCountryCode(String addressCountryCode) {
		this.addressCountryCode = addressCountryCode;
	}

	public String getAddressStateOrRegion() {
		return addressStateOrRegion;
	}

	public void setAddressStateOrRegion(String addressStateOrRegion) {
		this.addressStateOrRegion = addressStateOrRegion;
	}

	public String getAddressPostalCode() {
		return addressPostalCode;
	}

	public void setAddressPostalCode(String addressPostalCode) {
		this.addressPostalCode = addressPostalCode;
	}

	public String getAddressPhoneNumber() {
		return addressPhoneNumber;
	}

	public void setAddressPhoneNumber(String addressPhoneNumber) {
		this.addressPhoneNumber = addressPhoneNumber;
	}

	public String getNotificationEmail() {
		return notificationEmail;
	}

	public void setNotificationEmail(String notificationEmail) {
		this.notificationEmail = notificationEmail;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

}
