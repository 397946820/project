package com.it.ocs.synchronou.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class SaleNoShipDataExportModel {
	
	@ExcelLink(title="EBay Account",index=0)
	private String account;
	@ExcelLink(title="DisplayableOrderDate",index=1)
    private String createTime;
	@ExcelLink(title="MerchantSKU",index=2)
    private String sku;
	@ExcelLink(title="Quantity",index=3)
    private String quantity;
	@ExcelLink(title="MerchantFulfillmentOrderItemID",index=4)
    private String itemId;
	@ExcelLink(title="Sales record number",index=5)
    private String saleRecordNumber;
	@ExcelLink(title="Sold price",index=6)
    private String soldPrice;
	
	@ExcelLink(title="PerUnitDeclaredValue",index=7)
    private String currency;
	
	@ExcelLink(title="DisplayableOrderComment",index=8)
    private String transactionId;
	
	@ExcelLink(title="DeliverySLA",index=9)
    private String shipService;

	@ExcelLink(title="AddressName",index=10)
    private String name;
	
	@ExcelLink(title="AddressFieldOne",index=11)
    private String street1;
	
	@ExcelLink(title="AddressFieldTwo",index=12)
    private String stree2;
	
	@ExcelLink(title="AddressCity",index=13)
    private String cityName;
	
	@ExcelLink(title="AddressCountryCode",index=14)
    private String countryCode;
	
	@ExcelLink(title="AddressPostalCode",index=15)
    private String postalCode;
	
	@ExcelLink(title="AddressPhoneNumber",index=16)
    private String phone;
	
	@ExcelLink(title="pay time",index=17)
    private String payTime;//sellerEmail;
	
	@ExcelLink(title="orderID",index=18)
	private String orderId;
	
	@ExcelLink(title="Tracking number",index=19)
	private String trackingNumber;
	
	@ExcelLink(title="Post date",index=20)
	private String postdate;
	
	@ExcelLink(title="Carrier",index=21)
	private String carrier;
	
	@ExcelLink(title="Method",index=22)
	private String method;
	
	@ExcelLink(title="Tracking number 2",index=23)
	private String trackingNumber2;
	
	@ExcelLink(title="Post date 2",index=24)
	private String postdate2;
	
	@ExcelLink(title="Carrier 2",index=25)
	private String carrier2;
	
	@ExcelLink(title="Method 2",index=26)
	private String method2;
	
	@ExcelLink(title="Tracking number 3",index=27)
	private String trackingNumber3;
	
	@ExcelLink(title="Post date 3",index=28)
	private String postdate3;
	
	@ExcelLink(title="Carrier 3",index=29)
	private String carrier3;
	
	@ExcelLink(title="Method 3",index=30)
	private String method3;
	
    /**
     * {"@xmlns":"urn:ebay:apis:eBLBaseComponents","Name":null,"Street1":null,"Street2":null,"CityName":null,"StateOrProvince":null,"CountryName":null,"Phone":null,"PostalCode":null,"ExternalAddressID":null}
     */
    private String shippingAddress;
}
