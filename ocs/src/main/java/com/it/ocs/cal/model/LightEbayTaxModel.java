package com.it.ocs.cal.model;

import java.util.Date;

public class LightEbayTaxModel {

	private Long entityId;

	private String sku;

	private String country;

	private Long inventoryQuantity;

	private Long saleTotal;

	private Double unavailability;

	private Double replacementRate;

	private Long storageDays;

	private Double costThan;

	private Double volatilityFactor;

	private Date createdAt;

	private Date updatedAt;

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

	public Long getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(Long inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public Long getSaleTotal() {
		return saleTotal;
	}

	public void setSaleTotal(Long saleTotal) {
		this.saleTotal = saleTotal;
	}

	public Double getUnavailability() {
		return unavailability;
	}

	public void setUnavailability(Double unavailability) {
		this.unavailability = unavailability;
	}

	public Double getReplacementRate() {
		return replacementRate;
	}

	public void setReplacementRate(Double replacementRate) {
		this.replacementRate = replacementRate;
	}

	public Long getStorageDays() {
		return storageDays;
	}

	public void setStorageDays(Long storageDays) {
		this.storageDays = storageDays;
	}

	public Double getCostThan() {
		return costThan;
	}

	public void setCostThan(Double costThan) {
		this.costThan = costThan;
	}

	public Double getVolatilityFactor() {
		return volatilityFactor;
	}

	public void setVolatilityFactor(Double volatilityFactor) {
		this.volatilityFactor = volatilityFactor;
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
