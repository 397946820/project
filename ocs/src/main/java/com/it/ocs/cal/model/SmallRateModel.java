package com.it.ocs.cal.model;

import java.util.Date;

public class SmallRateModel {
	private Long entityId;

	private String platform;

	private Double referralRate;

	private Double grossProfitRate;

	private Double paypalFee;

	private Double vat;

	private Double advertisingRate;

	private Date createdAt;

	private Date updatedAt;

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", entityId=").append(entityId);
		sb.append(", platform=").append(platform);
		sb.append(", referralRate=").append(referralRate);
		sb.append(", grossProfitRate=").append(grossProfitRate);
		sb.append(", paypalFee=").append(paypalFee);
		sb.append(", vat=").append(vat);
		sb.append(", advertisingRate=").append(advertisingRate);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append("]");
		return sb.toString();
	}
}
