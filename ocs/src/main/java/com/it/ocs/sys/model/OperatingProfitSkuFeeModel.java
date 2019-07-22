package com.it.ocs.sys.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitSkuFeeModel {
	private Long entityId;

	@ExcelLink(title = "国家", index = 0)
	private String country;

	@ExcelLink(title = "SKU", index = 1)
	private String sku;

	@ExcelLink(title = "广告费", index = 2)
	private Double fee;

	private String monthOfYear;

	private Date createdAt;

	private Date updatedAt;

	public OperatingProfitSkuFeeModel() {
		super();
	}

	public OperatingProfitSkuFeeModel(String country, String sku, String monthOfYear) {
		this.country = country;
		this.sku = sku;
		this.monthOfYear = monthOfYear;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", entityId=").append(entityId);
		sb.append(", sku=").append(sku);
		sb.append(", fee=").append(fee);
		sb.append(", monthOfYear=").append(monthOfYear);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append("]");
		return sb.toString();
	}
}
