package com.it.ocs.cal.model;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class ProductCostModel extends ImportModel {

	private Long parentId;

	private String username;

	@ExcelRead(title = "SKU")
	@ModelProp(name = "SKU", colIndex = 0, nullable = false)
	private String sku;

	@ExcelRead(title = "采购货币")
	@ModelProp(name = "采购货币", colIndex = 1, nullable = false)
	private String currency;

	@ExcelRead(title = "采购金额")
	@ModelProp(name = "采购金额", colIndex = 2, nullable = false)
	private Double price;

	@ExcelRead(title = "退税率")
	@ModelProp(name = "退税率", colIndex = 3, nullable = false)
	private Double taxRebateRate;

	@ExcelRead(title = "利率")
	@ModelProp(name = "利率", colIndex = 4, nullable = false)
	private Double interestRate;

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTaxRebateRate() {
		return taxRebateRate;
	}

	public void setTaxRebateRate(Double taxRebateRate) {
		this.taxRebateRate = taxRebateRate;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

}