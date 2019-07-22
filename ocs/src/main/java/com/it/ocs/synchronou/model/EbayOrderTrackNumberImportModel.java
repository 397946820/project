package com.it.ocs.synchronou.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class EbayOrderTrackNumberImportModel {
	@ExcelLink(title="eBay账号(必填)",index=0)
	private String account;
	
	@ExcelLink(title="SKU",index=1)
    private String sku;
	
	@ExcelLink(title="物品号(必填)",index=2)
    private String itemId;
	
	@ExcelLink(title="销售号Sales Record Number",index=3)
    private String saleRecordNumber;
	
	@ExcelLink(title="eBay交易号(必填)",index=4)
    private String transactionId;
	
	@ExcelLink(title="ebay订单ID(必填)",index=5)
	private String orderId;
	
	@ExcelLink(title="物流号trackingNumber(必填)",index=6)
	private String trackingNumber;
	
	@ExcelLink(title="物流服务Carrier(必填)",index=7)
	private String carrier;
	
	@ExcelLink(title="Tracking number 2",index=8)
	private String trackingNumber2;
	
	@ExcelLink(title="Carrier 2",index=9)
	private String carrier2;
	
	@ExcelLink(title="Tracking number 3",index=10)
	private String trackingNumber3;
	
	@ExcelLink(title="Carrier 3",index=11)
	private String carrier3;

}
