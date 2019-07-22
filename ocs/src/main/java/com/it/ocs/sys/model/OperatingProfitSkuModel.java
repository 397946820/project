package com.it.ocs.sys.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitSkuModel {
	private Long entityId;

	@ExcelLink(title = "平台", index = 0)
	private String platform;

	@ExcelLink(title = "国家", index = 1)
	private String country;

	@ExcelLink(title = "SKU", index = 2)
	private String sku;

	@ExcelLink(title = "库存数量", index = 3)
	private Long stock;

	@ExcelLink(title = "运输方式", index = 4)
	private String shippingType;

	private String status;

	private String monthOfYear;

	private Date createdAt;

	private Date updatedAt;

	public OperatingProfitSkuModel() {
		super();
	}

	public OperatingProfitSkuModel(String monthOfYear, String platform, String country, String sku,String shippingType) {
		this.monthOfYear = monthOfYear;
		this.platform = platform;
		this.country = country;
		this.sku = sku;
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType == null ? null : shippingType.trim();
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear;
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
		return "OperatingProfitSkuModel [entityId=" + entityId + ", platform=" + platform + ", country=" + country
				+ ", sku=" + sku + ", stock=" + stock + ", shippingType=" + shippingType + ", status=" + status
				+ ", monthOfYear=" + monthOfYear + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
