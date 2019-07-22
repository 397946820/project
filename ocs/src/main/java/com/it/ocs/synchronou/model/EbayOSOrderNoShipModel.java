package com.it.ocs.synchronou.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class EbayOSOrderNoShipModel {
	
	@ExcelLink(title="序号",index=0)
	private String index;
	
	@ExcelLink(title="发货时间",index=1)
	private String shipTime;
	
	@ExcelLink(title="LE销售订单号",index=2)
	private String leOrderId;
	
	@ExcelLink(title="LE SKU",index=3)
	private String leSku;
	
	@ExcelLink(title="JJ SKU",index=4)
	private String jjSku;
	
	@ExcelLink(title="数量",index=5)
	private String qty;
	
	@ExcelLink(title="申报价值",index=6)
	private String sCost;
	
	@ExcelLink(title="收货人姓名",index=7)
	private String buyerName;
	
	@ExcelLink(title="电话",index=8)
	private String phone;
	
	@ExcelLink(title="地址",index=9)
	private String address;
	
	@ExcelLink(title="城市",index=10)
	private String city;
	
	@ExcelLink(title="省份",index=11)
	private String province;
	
	@ExcelLink(title="邮编",index=12)
	private String postCode;
	
	@ExcelLink(title="产品结算金额",index=13)
	private String str1;
	
	@ExcelLink(title="运费",index=14)
	private String str2;
	
	@ExcelLink(title="订单金额",index=15)
	private String str3;
	
	@ExcelLink(title="运输渠道",index=16)
	private String shipService;
	
	@ExcelLink(title="快递号",index=17)
	private String shipTranckingNumber;
	
	@ExcelLink(title="订单详情SaleRecordNumber(上传需使用数据)",index=18)
	private String iSaleRecordNumber;
}
