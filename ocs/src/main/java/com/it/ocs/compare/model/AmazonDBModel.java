package com.it.ocs.compare.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class AmazonDBModel {
	private Long id;
	
	@ExcelLink(title = "date/time", index = 0)
	private String dateTime;
	
	@ExcelLink(title = "settlement id", index = 1)
	private String settlementId;
	
	@ExcelLink(title = "type", index = 2)
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
	
	@ExcelLink(title="report id",index=22)
	private String report_id;
	
	@ExcelLink(title="platform",index=23)
	private String platform;
	
	@ExcelLink(title="create date",index=24)
	private String createDate;
	
	@ExcelLink(title="update date",index=25)
	private String updateDate;
	
	@ExcelLink(title="site",index=26)
	private String site;
	
	@ExcelLink(title="self date",index=26)
	private String selfDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMarketplace() {
		return marketplace;
	}

	public void setMarketplace(String marketplace) {
		this.marketplace = marketplace;
	}

	public String getFulfillment() {
		return fulfillment;
	}

	public void setFulfillment(String fulfillment) {
		this.fulfillment = fulfillment;
	}

	public String getOrderCity() {
		return orderCity;
	}

	public void setOrderCity(String orderCity) {
		this.orderCity = orderCity;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getOrderPostal() {
		return orderPostal;
	}

	public void setOrderPostal(String orderPostal) {
		this.orderPostal = orderPostal;
	}

	public String getProductSales() {
		return productSales;
	}

	public void setProductSales(String productSales) {
		this.productSales = productSales;
	}

	public String getShippingCredits() {
		return shippingCredits;
	}

	public void setShippingCredits(String shippingCredits) {
		this.shippingCredits = shippingCredits;
	}

	public String getGiftWrapCredits() {
		return giftWrapCredits;
	}

	public void setGiftWrapCredits(String giftWrapCredits) {
		this.giftWrapCredits = giftWrapCredits;
	}

	public String getPromotionalRebates() {
		return promotionalRebates;
	}

	public void setPromotionalRebates(String promotionalRebates) {
		this.promotionalRebates = promotionalRebates;
	}

	public String getSalesTaxCollected() {
		return salesTaxCollected;
	}

	public void setSalesTaxCollected(String salesTaxCollected) {
		this.salesTaxCollected = salesTaxCollected;
	}

	public String getSellingFees() {
		return sellingFees;
	}

	public void setSellingFees(String sellingFees) {
		this.sellingFees = sellingFees;
	}

	public String getFbaFees() {
		return fbaFees;
	}

	public void setFbaFees(String fbaFees) {
		this.fbaFees = fbaFees;
	}

	public String getOtherTransactionFees() {
		return otherTransactionFees;
	}

	public void setOtherTransactionFees(String otherTransactionFees) {
		this.otherTransactionFees = otherTransactionFees;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getReport_id() {
		return report_id;
	}

	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSelfDate() {
		return selfDate;
	}

	public void setSelfDate(String selfDate) {
		this.selfDate = selfDate;
	}
	
	
}
