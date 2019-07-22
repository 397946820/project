package com.it.ocs.sys.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitViewModel {

	private String saleOfMaori;

	private String grossMarginSales;

	private String newSales;

	private String newProductRatio;

	private Long entityId;

	@ExcelLink(title = "平台", index = 0)
	private String platform;

	private String account;

	@ExcelLink(title = "国家", index = 1)
	private String site;

	@ExcelLink(title = "SKU", index = 2)
	private String sku;

	@ExcelLink(title = "分类", index = 3)
	private String category;

	@ExcelLink(title = "数量", index = 4)
	private Long qty;

	@ExcelLink(title = "销售额", index = 5)
	private String price;

	@ExcelLink(title = "销售成本", index = 6)
	private String salesCost;

	@ExcelLink(title = "亚马逊费用", index = 7)
	private Double feeAmazon;

	@ExcelLink(title = "运费", index = 8)
	private Double freight;

	@ExcelLink(title = "客服-60K", index = 9)
	private Double customerFee1;

	@ExcelLink(title = "客服-通讯费", index = 10)
	private Double customerFee2;

	@ExcelLink(title = "客服-ZENDESK-INC", index = 11)
	private Double customerFee3;

	@ExcelLink(title = "客服-RESELLERRATINGS.COM", index = 12)
	private Double customerFee4;

	@ExcelLink(title = "客服-TrustedShopsGmbH", index = 13)
	private Double customerFee5;

	@ExcelLink(title = "平台费-eBay", index = 14)
	private Double feeEbay;

	@ExcelLink(title = "打包费-Onlinepack", index = 15)
	private Double packingCharge;

	@ExcelLink(title = "清关费", index = 16)
	private Double clearFee;

	@ExcelLink(title = "其他费用", index = 17)
	private Double otherFee;

	@ExcelLink(title = "广告费-google", index = 18)
	private Double advertisingFeeGoogle;

	@ExcelLink(title = "广告费-BING", index = 19)
	private Double advertisingFeeBing;

	@ExcelLink(title = "推广费-市场部", index = 20)
	private Double promotionFee;

	@ExcelLink(title = "工资", index = 21)
	private Double salary;

	@ExcelLink(title = "经营利润", index = 22)
	private String operatingProfit;

	@ExcelLink(title = "周转", index = 23)
	private Double inventoryTurnover;

	@ExcelLink(title = "经营利润率", index = 24)
	private String operatingProfitMargin;

	@ExcelLink(title = "保底售价", index = 25)
	private Double bottomPrice;

	@ExcelLink(title = "是否新品", index = 26)
	private String isNewProduct;

	@ExcelLink(title = "季度", index = 27)
	private Short quarter;

	@ExcelLink(title = "年月", index = 28)
	private String monthOfYear;

	private String year;

	private String month;

	private String nick;

	private Date createdAt;

	private Date updatedAt;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site == null ? null : site.trim();
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear == null ? null : monthOfYear.trim();
	}

	public Short getQuarter() {
		return quarter;
	}

	public void setQuarter(Short quarter) {
		this.quarter = quarter;
	}

	public String getSaleOfMaori() {
		return saleOfMaori;
	}

	public void setSaleOfMaori(String saleOfMaori) {
		this.saleOfMaori = saleOfMaori;
	}

	public String getGrossMarginSales() {
		return grossMarginSales;
	}

	public void setGrossMarginSales(String grossMarginSales) {
		this.grossMarginSales = grossMarginSales;
	}

	public String getOperatingProfit() {
		return operatingProfit;
	}

	public void setOperatingProfit(String operatingProfit) {
		this.operatingProfit = operatingProfit;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNewProductRatio() {
		return newProductRatio;
	}

	public void setNewProductRatio(String newProductRatio) {
		this.newProductRatio = newProductRatio;
	}

	public String getSalesCost() {
		return salesCost;
	}

	public void setSalesCost(String salesCost) {
		this.salesCost = salesCost;
	}

	public String getOperatingProfitMargin() {
		return operatingProfitMargin;
	}

	public void setOperatingProfitMargin(String operatingProfitMargin) {
		this.operatingProfitMargin = operatingProfitMargin;
	}

	public String getNewSales() {
		return newSales;
	}

	public void setNewSales(String newSales) {
		this.newSales = newSales;
	}

	public Double getInventoryTurnover() {
		return inventoryTurnover;
	}

	public void setInventoryTurnover(Double inventoryTurnover) {
		this.inventoryTurnover = inventoryTurnover;
	}

	public Double getFeeAmazon() {
		return feeAmazon;
	}

	public void setFeeAmazon(Double feeAmazon) {
		this.feeAmazon = feeAmazon;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Double getCustomerFee1() {
		return customerFee1;
	}

	public void setCustomerFee1(Double customerFee1) {
		this.customerFee1 = customerFee1;
	}

	public Double getCustomerFee2() {
		return customerFee2;
	}

	public void setCustomerFee2(Double customerFee2) {
		this.customerFee2 = customerFee2;
	}

	public Double getCustomerFee3() {
		return customerFee3;
	}

	public void setCustomerFee3(Double customerFee3) {
		this.customerFee3 = customerFee3;
	}

	public Double getCustomerFee4() {
		return customerFee4;
	}

	public void setCustomerFee4(Double customerFee4) {
		this.customerFee4 = customerFee4;
	}

	public Double getCustomerFee5() {
		return customerFee5;
	}

	public void setCustomerFee5(Double customerFee5) {
		this.customerFee5 = customerFee5;
	}

	public Double getFeeEbay() {
		return feeEbay;
	}

	public void setFeeEbay(Double feeEbay) {
		this.feeEbay = feeEbay;
	}

	public Double getPackingCharge() {
		return packingCharge;
	}

	public void setPackingCharge(Double packingCharge) {
		this.packingCharge = packingCharge;
	}

	public Double getClearFee() {
		return clearFee;
	}

	public void setClearFee(Double clearFee) {
		this.clearFee = clearFee;
	}

	public Double getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}

	public Double getAdvertisingFeeGoogle() {
		return advertisingFeeGoogle;
	}

	public void setAdvertisingFeeGoogle(Double advertisingFeeGoogle) {
		this.advertisingFeeGoogle = advertisingFeeGoogle;
	}

	public Double getAdvertisingFeeBing() {
		return advertisingFeeBing;
	}

	public void setAdvertisingFeeBing(Double advertisingFeeBing) {
		this.advertisingFeeBing = advertisingFeeBing;
	}

	public Double getPromotionFee() {
		return promotionFee;
	}

	public void setPromotionFee(Double promotionFee) {
		this.promotionFee = promotionFee;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Double getBottomPrice() {
		return bottomPrice;
	}

	public void setBottomPrice(Double bottomPrice) {
		this.bottomPrice = bottomPrice;
	}

	public String getIsNewProduct() {
		return isNewProduct;
	}

	public void setIsNewProduct(String isNewProduct) {
		this.isNewProduct = isNewProduct;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
