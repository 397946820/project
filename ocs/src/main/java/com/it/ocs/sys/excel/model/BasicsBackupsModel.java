package com.it.ocs.sys.excel.model;

import java.math.BigDecimal;

import com.it.ocs.excel.annotation.ExcelLink;

public class BasicsBackupsModel {
	private Long entityId;

	@ExcelLink(title = "国家", index = 0)
	private String countryId;

	@ExcelLink(title = "货币代码", index = 1)
	private String currencyCode;

	@ExcelLink(title = "货币汇率", index = 2)
	private BigDecimal currencyRate;

	@ExcelLink(title = "空运运费信息", index = 3)
	private String afShippingFee;

	@ExcelLink(title = "海运运费信息", index = 4)
	private String sfShippingFee;

	@ExcelLink(title = "快递运费信息", index = 5)
	private String coShippingFee;

	@ExcelLink(title = "仓储费信息", index = 6)
	private String storageCost;

	@ExcelLink(title = "操作费", index = 7)
	private BigDecimal operatingFee;

	@ExcelLink(title = "关税类型", index = 8)
	private String type;

	@ExcelLink(title = "销价增值税", index = 9)
	private BigDecimal vat;

	@ExcelLink(title = "空运关税浮动", index = 10)
	private BigDecimal afFluctuation;

	@ExcelLink(title = "海运关税浮动", index = 11)
	private BigDecimal sfFluctuation;

	@ExcelLink(title = "快递关税浮动", index = 12)
	private BigDecimal coFluctuation;

	@ExcelLink(title = "清关系数", index = 13)
	private BigDecimal clearCoefficient;

	private String monthOfYear;

	public Long getEntityId() {
		return entityId;
	}

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

	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getAfShippingFee() {
		return afShippingFee;
	}

	public void setAfShippingFee(String afShippingFee) {
		this.afShippingFee = afShippingFee == null ? null : afShippingFee.trim();
	}

	public String getSfShippingFee() {
		return sfShippingFee;
	}

	public void setSfShippingFee(String sfShippingFee) {
		this.sfShippingFee = sfShippingFee == null ? null : sfShippingFee.trim();
	}

	public String getCoShippingFee() {
		return coShippingFee;
	}

	public void setCoShippingFee(String coShippingFee) {
		this.coShippingFee = coShippingFee == null ? null : coShippingFee.trim();
	}

	public String getStorageCost() {
		return storageCost;
	}

	public void setStorageCost(String storageCost) {
		this.storageCost = storageCost == null ? null : storageCost.trim();
	}

	public BigDecimal getOperatingFee() {
		return operatingFee;
	}

	public void setOperatingFee(BigDecimal operatingFee) {
		this.operatingFee = operatingFee;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getAfFluctuation() {
		return afFluctuation;
	}

	public void setAfFluctuation(BigDecimal afFluctuation) {
		this.afFluctuation = afFluctuation;
	}

	public BigDecimal getSfFluctuation() {
		return sfFluctuation;
	}

	public void setSfFluctuation(BigDecimal sfFluctuation) {
		this.sfFluctuation = sfFluctuation;
	}

	public BigDecimal getCoFluctuation() {
		return coFluctuation;
	}

	public void setCoFluctuation(BigDecimal coFluctuation) {
		this.coFluctuation = coFluctuation;
	}

	public BigDecimal getClearCoefficient() {
		return clearCoefficient;
	}

	public void setClearCoefficient(BigDecimal clearCoefficient) {
		this.clearCoefficient = clearCoefficient;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear == null ? null : monthOfYear.trim();
	}

	@Override
	public String toString() {
		return "BasicsBackupsModel [entityId=" + entityId + ", countryId=" + countryId + ", currencyCode="
				+ currencyCode + ", currencyRate=" + currencyRate + ", afShippingFee=" + afShippingFee
				+ ", sfShippingFee=" + sfShippingFee + ", coShippingFee=" + coShippingFee + ", storageCost="
				+ storageCost + ", operatingFee=" + operatingFee + ", type=" + type + ", vat=" + vat
				+ ", afFluctuation=" + afFluctuation + ", sfFluctuation=" + sfFluctuation + ", coFluctuation="
				+ coFluctuation + ", clearCoefficient=" + clearCoefficient + ", monthOfYear=" + monthOfYear + "]";
	}

}
