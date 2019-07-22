package com.it.ocs.cal.model;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class CostModel extends ImportModel {

	@ExcelRead(title = "国家")
	@ModelProp(name = "国家", colIndex = 0, nullable = false)
	private String countryId;

	@ExcelRead(title = "SKU")
	@ModelProp(name = "SKU", colIndex = 1, nullable = false)
	private String sku;

	@ExcelRead(title = "运输方式")
	@ModelProp(name = "运输方式", colIndex = 2, nullable = false)
	private String shippingType;

	@ExcelRead(title = "亚马逊售价")
	@ModelProp(name = "亚马逊售价", colIndex = 3, nullable = false)
	private String price;

	@ExcelRead(title = "亚马逊活动费")
	@ModelProp(name = "亚马逊活动费", colIndex = 4, nullable = false)
	private Double cost;

	@ExcelRead(title = "预计销量")
	@ModelProp(name = "预计销量", colIndex = 5, nullable = false)
	private long qty;

	@ExcelRead(title = "销售利润")
	private Double salesProfit;

	@ExcelRead(title = "推算结果")
	private String results;

	@ExcelRead(title = "保本销量")
	private String count;

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public long getQty() {
		return qty;
	}

	public void setQty(long qty) {
		this.qty = qty;
	}

	public Double getSalesProfit() {
		return salesProfit;
	}

	public void setSalesProfit(Double salesProfit) {
		this.salesProfit = salesProfit;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
