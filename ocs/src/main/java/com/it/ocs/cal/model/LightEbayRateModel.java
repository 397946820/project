package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightEbayRateModel {
	private Long entityId;

	@ExcelLink(title = "平台", index = 0)
	private String platform;

	@ExcelLink(title = "国家", index = 1)
	private String country;

	@ExcelLink(title = "运输方式", index = 2)
	private String shippingType;

	@ExcelLink(title = "平台费率", index = 3)
	private Double referralRate;

	@ExcelLink(title = "毛利率", index = 4)
	private Double grossProfitRate;

	@ExcelLink(title = "paypal手续费", index = 5)
	private Double paypalFee;

	@ExcelLink(title = "增值税", index = 6)
	private Double vat;

	@ExcelLink(title = "广告费率", index = 7)
	private Double advertisingRate;

	private Date createdAt;

	private Date updatedAt;

	public LightEbayRateModel() {
		super();
	}

	public LightEbayRateModel(String platform, String country, String shippingType) {
		super();
		this.platform = platform;
		this.country = country;
		this.shippingType = shippingType;
	}

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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType == null ? null : shippingType.trim();
	}

	public Double getReferralRate() {
		return referralRate;
	}

	public void setReferralRate(Double referralRate) {
		this.referralRate = referralRate;
	}

	public Double getGrossProfitRate() {
		return grossProfitRate;
	}

	public void setGrossProfitRate(Double grossProfitRate) {
		this.grossProfitRate = grossProfitRate;
	}

	public Double getPaypalFee() {
		return paypalFee;
	}

	public void setPaypalFee(Double paypalFee) {
		this.paypalFee = paypalFee;
	}

	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	public Double getAdvertisingRate() {
		return advertisingRate;
	}

	public void setAdvertisingRate(Double advertisingRate) {
		this.advertisingRate = advertisingRate;
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