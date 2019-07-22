package com.it.ocs.synchronou.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class EbaySmallPackageExportModel {
	@ExcelLink(title="eBay账号",index=0)
	private String account ;
	
	@ExcelLink(title="eBay订单号",index=1)
	private String orderId;
	
	@ExcelLink(title="eBay交易号",index=2)
	private String transactionId;
	
	@ExcelLink(title="eBay物品号",index=3)
	private String itemId;
	
	@ExcelLink(title="SKU",index=4)
	private String sku;
	
	@ExcelLink(title="数量",index=5)
	private String qty;
	
	@ExcelLink(title="地址1",index=6)
	private String addressLine1;
	
	@ExcelLink(title="地址2",index=7)
	private String addressLine2;
	
	@ExcelLink(title="城市",index=8)
	private String city;
	
	@ExcelLink(title="邮编",index=9)
	private String postalCode;
	
	@ExcelLink(title="省",index=10)
	private String provState;
	
	@ExcelLink(title="国家",index=11)
	private String country;
	
	@ExcelLink(title="姓名",index=12)
	private String name;
	
	@ExcelLink(title="电话号码",index=13)
	private String phone;
	
	@ExcelLink(title="运输服务",index=14)
	private String carrier1;
	
	@ExcelLink(title="物流号",index=15)
	private String tranckingNumber1;
	
	
	
}
