package com.it.ocs.cal.model;

import java.util.Date;

public class LightExpensesModel {
	private Long entityId;

	private String country;

	private Double rentalFeeTotal;

	private Double sundryChargesTotal;

	private Double costTotal;

	private Double labourCostTotal;

	private Double feeTotal;

	private Date createdAt;

	private Date updatedAt;

	public LightExpensesModel() {
		super();
	}

	public LightExpensesModel(String country) {
		super();
		this.country = country;
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

	public Double getRentalFeeTotal() {
		return rentalFeeTotal;
	}

	public void setRentalFeeTotal(Double rentalFeeTotal) {
		this.rentalFeeTotal = rentalFeeTotal;
	}

	public Double getSundryChargesTotal() {
		return sundryChargesTotal;
	}

	public void setSundryChargesTotal(Double sundryChargesTotal) {
		this.sundryChargesTotal = sundryChargesTotal;
	}

	public Double getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(Double costTotal) {
		this.costTotal = costTotal;
	}

	public Double getLabourCostTotal() {
		return labourCostTotal;
	}

	public void setLabourCostTotal(Double labourCostTotal) {
		this.labourCostTotal = labourCostTotal;
	}

	public Double getFeeTotal() {
		return feeTotal;
	}

	public void setFeeTotal(Double feeTotal) {
		this.feeTotal = feeTotal;
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