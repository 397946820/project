package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightEbayCustomerModel {
	private Long entityId;

	@ExcelLink(title = "国家" ,index = 0)
	private String country;

	@ExcelLink(title = "地区" ,index = 1)
	private String region;

	@ExcelLink(title = "运输方式" ,index = 2)
	private String shippingType;

	@ExcelLink(title = "起始重量" ,index = 3)
	private Double fromWeight;

	@ExcelLink(title = "结束重量" ,index = 4)
	private Double toWeight;

	@ExcelLink(title = "单价" ,index = 5)
	private Double unitPrice;

	@ExcelLink(title = "货币" ,index = 6)
	private String currencyCode;

	@ExcelLink(title = "体积重系数" ,index = 7)
	private Double volumeWeightCoefficient;

	@ExcelLink(title = "备注" ,index = 8)
	private String remarks;

	private Date createdAt;

	private Date updatedAt;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region == null ? null : region.trim();
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType == null ? null : shippingType.trim();
	}

	public Double getFromWeight() {
		return fromWeight;
	}

	public void setFromWeight(Double fromWeight) {
		this.fromWeight = fromWeight;
	}

	public Double getToWeight() {
		return toWeight;
	}

	public void setToWeight(Double toWeight) {
		this.toWeight = toWeight;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	public Double getVolumeWeightCoefficient() {
		return volumeWeightCoefficient;
	}

	public void setVolumeWeightCoefficient(Double volumeWeightCoefficient) {
		this.volumeWeightCoefficient = volumeWeightCoefficient;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
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