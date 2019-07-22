package com.it.ocs.sys.model;

import java.util.Date;

public class OperatingProfitCostModel {
	private Long entityId;

	private Double cost;

	private Double meter;

	private String monthOfYear;

	private Date createdAt;

	private Date updatedAt;

	public OperatingProfitCostModel() {
		super();
	}

	public OperatingProfitCostModel(String monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getMeter() {
		return meter;
	}

	public void setMeter(Double meter) {
		this.meter = meter;
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
