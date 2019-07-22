package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

/**
 * @author yecaiqing
 */
public class CurrencyRateModel extends ImportModel {

	private Long entityId;

	@ExcelRead(title = "国家")
	@ModelProp(name = "国家", colIndex = 0, nullable = false)
	private String countryId;

	@ExcelRead(title = "货币代码")
	@ModelProp(name = "货币代码", colIndex = 1, nullable = false)
	private String currencyCode;

	@ExcelRead(title = "货币符号")
	@ModelProp(name = "货币符号", colIndex = 2, nullable = false)
	private String currencySymbol;

	@ExcelRead(title = "货币汇率")
	@ModelProp(name = "货币汇率", colIndex = 3, nullable = false)
	private Double currencyRate;

	@ExcelRead(title = "风险因素")
	@ModelProp(name = "风险因素", colIndex = 4, nullable = false)
	private Double riskFactor;

	@ExcelRead(title = "排序")
	@ModelProp(name = "排序", colIndex = 5, nullable = false)
	private Integer sortOrder;

	@Override
	public Long getEntityId() {
		return entityId;
	}

	@Override
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId == null ? null : countryId.trim();
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol == null ? null : currencySymbol.trim();
	}

	public Double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public Double getRiskFactor() {
		return riskFactor;
	}

	public void setRiskFactor(Double riskFactor) {
		this.riskFactor = riskFactor;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}