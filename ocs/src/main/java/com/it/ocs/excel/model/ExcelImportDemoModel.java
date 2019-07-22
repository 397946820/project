package com.it.ocs.excel.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class ExcelImportDemoModel {
	
	@ExcelLink(title="orderID",index=0)
	private String orderId;
	
	@ExcelLink(title="SellingRecordNumber",index=1)
	private String sellingRecordNumber;
	
	@ExcelLink(title="Tracking number",index=8)
	private String trackingNumber;
	
	@ExcelLink(title="Post date",index=9)
	private String postdate;
	
	@ExcelLink(title="Carrier",index=10)
	private String carrier;
}
