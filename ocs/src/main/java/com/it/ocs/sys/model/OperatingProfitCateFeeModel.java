package com.it.ocs.sys.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitCateFeeModel {
	private Long entityId;

	@ExcelLink(title = "国家", index = 0)
	private String country;

	@ExcelLink(title = "类目", index = 1)
	private String category;

	@ExcelLink(title = "广告费-google", index = 2)
	private Double advertisingFeeGoogle;

	@ExcelLink(title = "广告费-BING", index = 3)
	private Double advertisingFeeBing;

	private String monthOfYear;

	private Date createdAt;

	private Date updatedAt;

	public OperatingProfitCateFeeModel() {
		super();
	}

	public OperatingProfitCateFeeModel(String country, String category, String monthOfYear) {
		this.country = country;
		this.category = category;
		this.monthOfYear = monthOfYear;
	}

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
		this.country = country;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
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

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear == null ? null : monthOfYear.trim();
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
