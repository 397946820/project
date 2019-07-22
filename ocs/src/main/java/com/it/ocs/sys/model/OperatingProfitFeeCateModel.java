package com.it.ocs.sys.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitFeeCateModel {
	private Long entityId;

	@ExcelLink(title = "平台", index = 0)
	private String platform;

	@ExcelLink(title = "类目", index = 1)
	private String category;

	@ExcelLink(title = "推广费-市场部", index = 2)
	private Double promotionFee;

	private String monthOfYear;

	private Date createdAt;

	private Date updatedAt;

	public OperatingProfitFeeCateModel() {
		super();
	}

	public OperatingProfitFeeCateModel(String platform, String category, String monthOfYear) {
		this.platform = platform;
		this.category = category;
		this.monthOfYear = monthOfYear;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	public Double getPromotionFee() {
		return promotionFee;
	}

	public void setPromotionFee(Double promotionFee) {
		this.promotionFee = promotionFee;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", entityId=").append(entityId);
		sb.append(", category=").append(category);
		sb.append(", promotionFee=").append(promotionFee);
		sb.append(", monthOfYear=").append(monthOfYear);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append(", platform=").append(platform);
		sb.append("]");
		return sb.toString();
	}
}
