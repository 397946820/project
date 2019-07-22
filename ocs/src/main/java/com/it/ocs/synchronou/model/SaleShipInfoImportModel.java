package com.it.ocs.synchronou.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class SaleShipInfoImportModel {
	
	@ExcelLink(title="orderID",index=0)
	private String orderId;
	
	@ExcelLink(title="SellingRecordNumber",index=1)
	private String sellingRecordNumber;
	
	@ExcelLink(title="ItemID",index=2)
	private String itemId;
	
	@ExcelLink(title="SKU",index=3)
	private String sku;
	
	@ExcelLink(title="Title",index=4)
	private String title;
	
	@ExcelLink(title="eBay transaction ID",index=5)
	private String transactionId;
	
	@ExcelLink(title="eBay account",index=6)
	private String ebayAccount;
	
	@ExcelLink(title="eBay buyer ID",index=7)
	private String buyerId;
	
	@ExcelLink(title="Tracking number",index=8)
	private String trackingNumber;
	
	@ExcelLink(title="Post date",index=9)
	private String postdate;
	
	@ExcelLink(title="Carrier",index=10)
	private String carrier;
	
	@ExcelLink(title="Method",index=11)
	private String method;
	
	@ExcelLink(title="Tracking number 2",index=12)
	private String trackingNumber2;
	
	@ExcelLink(title="Post date 2",index=13)
	private String postdate2;
	
	@ExcelLink(title="Carrier 2",index=14)
	private String carrier2;
	
	@ExcelLink(title="Method 2",index=15)
	private String method2;
	
	@ExcelLink(title="Tracking number 3",index=16)
	private String trackingNumber3;
	
	@ExcelLink(title="Post date 3",index=17)
	private String postdate3;
	
	@ExcelLink(title="Carrier 3",index=18)
	private String carrier3;
	
	@ExcelLink(title="Method 3",index=19)
	private String method3;
}
