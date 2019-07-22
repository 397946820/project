package com.it.ocs.cal.model;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class ProductPriceModel extends ImportModel {

	@ExcelRead(title = "清仓发货SKU")
	@ModelProp(name = "清仓发货SKU", colIndex = 0, nullable = false)
	private String sku;
	
	@ExcelRead(title = "变更后价格")
	@ModelProp(name = "变更后价格", colIndex = 4, nullable = false)	
	private Double price;

	@ExcelRead(title = "币种")
	@ModelProp(name = "币种", colIndex = 10, nullable = true)
	private String currency;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}