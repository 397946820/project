package com.it.ocs.amazon.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class AmazonReportExportModel {
	@ExcelLink(title="data/time",index=0)
	private String selfDate;
	@ExcelLink(title="settlement id",index=1)
    private String settlementId;
	@ExcelLink(title="type",index=2)
    private String type;
	@ExcelLink(title="order id",index=3)
    private String orderId;
	@ExcelLink(title="sku",index=4)
    private String sku;
	@ExcelLink(title="description",index=5)
    private String description;
	@ExcelLink(title="quantity",index=6)
    private String quantity;
	@ExcelLink(title="marketplace",index=7)
    private String marketplace;
	@ExcelLink(title="fulfillment",index=8)
    private String fulfillment;
	@ExcelLink(title="order city",index=9)
    private String orderCity;
	@ExcelLink(title="order state",index=10)
    private String orderState;
	@ExcelLink(title="order postal",index=11)
    private String orderPostal;
	@ExcelLink(title="product sales",index=12)
    private String productSales;
	@ExcelLink(title="shipping credits",index=13)
    private String shippingCredits;
	@ExcelLink(title="gift wrap credits",index=14)
    private String giftWrapCredits;
	@ExcelLink(title="promotional rebates",index=15)
    private String promotionalRebates;
	@ExcelLink(title="sales tax collected",index=16)
    private String salesTaxCollected;
	@ExcelLink(title="selling fees",index=17)
    private String sellingFees;
	@ExcelLink(title="fba fees",index=18)
    private String fbaFees;
	@ExcelLink(title="other transaction fees",index=19)
    private String otherTransactionFees;
	@ExcelLink(title="other",index=20)
    private String other;
	@ExcelLink(title="total",index=21)
    private String total;
	@ExcelLink(title="platform",index=22)
    private String platform;
}
