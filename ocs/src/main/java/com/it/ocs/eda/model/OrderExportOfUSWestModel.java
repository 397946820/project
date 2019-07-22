package com.it.ocs.eda.model;

import java.lang.reflect.Field;

import com.it.ocs.excel.annotation.ExcelLink;

public class OrderExportOfUSWestModel {
	
	@ExcelLink(title="平台",index=0)
	private String platform ;
	
	@ExcelLink(title="账号",index=1)
	private String account ;
	
	@ExcelLink(title="OMS订单号",index=2)
	private String orderId;
	
	@ExcelLink(title="MerchantFulfillmentOrderID",index=3)
	private String MerchantFulfillmentOrderID;
	
	@ExcelLink(title="isMPS",index=4)
	private String isMPS;
	
	@ExcelLink(title="DisplayableOrderID",index=5)
	private String DisplayableOrderID;
	
	@ExcelLink(title="DisplayableOrderDate",index=6)
	private String DisplayableOrderDate;
	
	@ExcelLink(title="MerchantSKU",index=7)
	private String MerchantSKU;
	
	@ExcelLink(title="Quantity",index=8)
	private String Quantity;
	
	@ExcelLink(title="MerchantFulfillmentOrderItemID",index=9)
	private String MerchantFulfillmentOrderItemID;
	
	@ExcelLink(title="GiftMessage",index=10)
	private String GiftMessage;
	
	@ExcelLink(title="DisplayableComment",index=11)
	private String DisplayableComment;
	
	@ExcelLink(title="PerUnitDeclaredValue",index=12)
	private String PerUnitDeclaredValue;
	
	@ExcelLink(title="DisplayableOrderComment",index=13)
	private String DisplayableOrderComment;
	
	@ExcelLink(title="DeliverySLA",index=14)
	private String DeliverySLA;
	
	@ExcelLink(title="AddressName",index=15)
	private String AddressName;
	
	@ExcelLink(title="AddressFieldOne",index=16)
	private String AddressFieldOne;
	
	@ExcelLink(title="AddressFieldTwo",index=17)
	private String AddressFieldTwo;
	
	@ExcelLink(title="AddressFieldThree",index=18)
	private String AddressFieldThree;
	
	@ExcelLink(title="AddressCity",index=19)
	private String AddressCity;
	
	@ExcelLink(title="AddressCountryCode",index=20)
	private String AddressCountryCode;
	
	@ExcelLink(title="AddressStateOrRegion",index=21)
	private String AddressStateOrRegion;
	
	@ExcelLink(title="AddressPostalCode",index=22)
	private String AddressPostalCode;
	
	@ExcelLink(title="AddressPhoneNumber",index=23)
	private String AddressPhoneNumber;
	
	@ExcelLink(title="NotificationEmail",index=24)
	private String NotificationEmail;
	
	@ExcelLink(title="CarrierServiceTypeCode",index=25)
	private String CarrierServiceTypeCode;
	
	@ExcelLink(title="PackageLength",index=26)
	private String PackageLength;
	
	@ExcelLink(title="PackageWidth",index=27)
	private String PackageWidth;
	
	@ExcelLink(title="PackageHeight",index=28)
	private String PackageHeight;
	
	@ExcelLink(title="PackageWeight",index=29)
	private String PackageWeight;
	
	@ExcelLink(title="TranckingNumber",index=30)
	private String tranckingNumber;
	
	
	
	public String getPlatform() {
		return platform;
	}



	public void setPlatform(String platform) {
		this.platform = platform;
	}



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



	public String getMerchantFulfillmentOrderID() {
		return MerchantFulfillmentOrderID;
	}



	public void setMerchantFulfillmentOrderID(String merchantFulfillmentOrderID) {
		MerchantFulfillmentOrderID = merchantFulfillmentOrderID;
	}



	public String getIsMPS() {
		return isMPS;
	}



	public void setIsMPS(String isMPS) {
		this.isMPS = isMPS;
	}



	public String getDisplayableOrderID() {
		return DisplayableOrderID;
	}



	public void setDisplayableOrderID(String displayableOrderID) {
		DisplayableOrderID = displayableOrderID;
	}



	public String getDisplayableOrderDate() {
		return DisplayableOrderDate;
	}



	public void setDisplayableOrderDate(String displayableOrderDate) {
		DisplayableOrderDate = displayableOrderDate;
	}



	public String getMerchantSKU() {
		return MerchantSKU;
	}



	public void setMerchantSKU(String merchantSKU) {
		MerchantSKU = merchantSKU;
	}



	public String getQuantity() {
		return Quantity;
	}



	public void setQuantity(String quantity) {
		Quantity = quantity;
	}



	public String getMerchantFulfillmentOrderItemID() {
		return MerchantFulfillmentOrderItemID;
	}



	public void setMerchantFulfillmentOrderItemID(String merchantFulfillmentOrderItemID) {
		MerchantFulfillmentOrderItemID = merchantFulfillmentOrderItemID;
	}



	public String getGiftMessage() {
		return GiftMessage;
	}



	public void setGiftMessage(String giftMessage) {
		GiftMessage = giftMessage;
	}



	public String getDisplayableComment() {
		return DisplayableComment;
	}



	public void setDisplayableComment(String displayableComment) {
		DisplayableComment = displayableComment;
	}



	public String getPerUnitDeclaredValue() {
		return PerUnitDeclaredValue;
	}



	public void setPerUnitDeclaredValue(String perUnitDeclaredValue) {
		PerUnitDeclaredValue = perUnitDeclaredValue;
	}



	public String getDisplayableOrderComment() {
		return DisplayableOrderComment;
	}



	public void setDisplayableOrderComment(String displayableOrderComment) {
		DisplayableOrderComment = displayableOrderComment;
	}



	public String getDeliverySLA() {
		return DeliverySLA;
	}



	public void setDeliverySLA(String deliverySLA) {
		DeliverySLA = deliverySLA;
	}



	public String getAddressName() {
		return AddressName;
	}



	public void setAddressName(String addressName) {
		AddressName = addressName;
	}



	public String getAddressFieldOne() {
		return AddressFieldOne;
	}



	public void setAddressFieldOne(String addressFieldOne) {
		AddressFieldOne = addressFieldOne;
	}



	public String getAddressFieldTwo() {
		return AddressFieldTwo;
	}



	public void setAddressFieldTwo(String addressFieldTwo) {
		AddressFieldTwo = addressFieldTwo;
	}



	public String getAddressFieldThree() {
		return AddressFieldThree;
	}



	public void setAddressFieldThree(String addressFieldThree) {
		AddressFieldThree = addressFieldThree;
	}



	public String getAddressCity() {
		return AddressCity;
	}



	public void setAddressCity(String addressCity) {
		AddressCity = addressCity;
	}



	public String getAddressCountryCode() {
		return AddressCountryCode;
	}



	public void setAddressCountryCode(String addressCountryCode) {
		AddressCountryCode = addressCountryCode;
	}



	public String getAddressStateOrRegion() {
		return AddressStateOrRegion;
	}



	public void setAddressStateOrRegion(String addressStateOrRegion) {
		AddressStateOrRegion = addressStateOrRegion;
	}



	public String getAddressPostalCode() {
		return AddressPostalCode;
	}



	public void setAddressPostalCode(String addressPostalCode) {
		AddressPostalCode = addressPostalCode;
	}



	public String getAddressPhoneNumber() {
		return AddressPhoneNumber;
	}



	public void setAddressPhoneNumber(String addressPhoneNumber) {
		AddressPhoneNumber = addressPhoneNumber;
	}



	public String getNotificationEmail() {
		return NotificationEmail;
	}



	public void setNotificationEmail(String notificationEmail) {
		NotificationEmail = notificationEmail;
	}



	public String getCarrierServiceTypeCode() {
		return CarrierServiceTypeCode;
	}



	public void setCarrierServiceTypeCode(String carrierServiceTypeCode) {
		CarrierServiceTypeCode = carrierServiceTypeCode;
	}



	public String getPackageLength() {
		return PackageLength;
	}



	public void setPackageLength(String packageLength) {
		PackageLength = packageLength;
	}



	public String getPackageWidth() {
		return PackageWidth;
	}



	public void setPackageWidth(String packageWidth) {
		PackageWidth = packageWidth;
	}



	public String getPackageHeight() {
		return PackageHeight;
	}



	public void setPackageHeight(String packageHeight) {
		PackageHeight = packageHeight;
	}



	public String getPackageWeight() {
		return PackageWeight;
	}



	public void setPackageWeight(String packageWeight) {
		PackageWeight = packageWeight;
	}



	public String getTranckingNumber() {
		return tranckingNumber;
	}



	public void setTranckingNumber(String tranckingNumber) {
		this.tranckingNumber = tranckingNumber;
	}



	public static void main(String[] args) {
		Field fields[] = OrderExportOfUSWestModel.class.getDeclaredFields();
		for(Field f : fields){
			System.out.println("#{"+f.getName().toUpperCase()+",jdbcType=VARCHAR},");
		}
	}
}
