package com.it.ocs.eda.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class EastShipExportModel {
	@ExcelLink(title="platform",index=0)
	private String platform;
	
	@ExcelLink(title="orderId",index=1)
	private String orderId;
	
	@ExcelLink(title="ocsEdaOrderId",index=2)
	private String ocsEdaOrderId;
	
	@ExcelLink(title="edaOrderId",index=3)
	private String edaOrderId;
	
	@ExcelLink(title="edaAccount",index=4)
	private String edaAccount;
	
	@ExcelLink(title="sku",index=5)
	private String sku;
	
	@ExcelLink(title="qty",index=6)
	private String qty;
	
	@ExcelLink(title="createTime",index=7)
	private String createDate;
}
