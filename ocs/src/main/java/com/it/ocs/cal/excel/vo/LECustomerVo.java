package com.it.ocs.cal.excel.vo;

import com.it.ocs.excel.annotation.ExcelLink;

public class LECustomerVo {

	@ExcelLink(title = "国家", index = 0)
	private String country;

	@ExcelLink(title = "地区", index = 1)
	private String region;

	@ExcelLink(title = "SKU", index = 2)
	private String sku;

	@ExcelLink(title = "运输方式", index = 3)
	private String shippingType;

	@ExcelLink(title = "最少数量", index = 4)
	private String qty;

	@ExcelLink(title = "利润系数", index = 5)
	private String profitRateAction;

	@ExcelLink(title = "建议售价", index = 6)
	private String finalPrice;

	@ExcelLink(title = "装箱数量", index = 7)
	private String packingQty;

	@ExcelLink(title = "总箱数", index = 8)
	private String count;

	@ExcelLink(title = "平均单个报价", index = 9)
	private String unitPrice;

	@ExcelLink(title = "单箱报价", index = 10)
	private String price;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getProfitRateAction() {
		return profitRateAction;
	}

	public void setProfitRateAction(String profitRateAction) {
		this.profitRateAction = profitRateAction;
	}

	public String getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getPackingQty() {
		return packingQty;
	}

	public void setPackingQty(String packingQty) {
		this.packingQty = packingQty;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
