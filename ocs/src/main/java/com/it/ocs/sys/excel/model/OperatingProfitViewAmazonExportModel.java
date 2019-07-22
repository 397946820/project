package com.it.ocs.sys.excel.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitViewAmazonExportModel {

	@ExcelLink(title = "账号", index = 0)
	private String platform;

	@ExcelLink(title = "站点", index = 1)
	private String site;

	@ExcelLink(title = "类型", index = 2)
	private String type;

	@ExcelLink(title = "SKU", index = 3)
	private String sku;

	@ExcelLink(title = "数量", index = 4)
	private Long qty;

	@ExcelLink(title = "销售额(原币种)", index = 5)
	private Double price;

	@ExcelLink(title = "销售额(USD)", index = 6)
	private Double price_;

	@ExcelLink(title = "亚马逊FEE(原币种)", index = 7)
	private Double fee;

	@ExcelLink(title = "亚马逊FEE(USD)", index = 8)
	private Double fee_;

	@ExcelLink(title = "年月", index = 9)
	private String monthOfYear;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPrice_() {
		return price_;
	}

	public void setPrice_(Double price_) {
		this.price_ = price_;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getFee_() {
		return fee_;
	}

	public void setFee_(Double fee_) {
		this.fee_ = fee_;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

}
