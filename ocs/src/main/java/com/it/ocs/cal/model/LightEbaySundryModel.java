package com.it.ocs.cal.model;

import java.util.Date;

public class LightEbaySundryModel {
	private Long entityId;

	private String country;

	private Double overWeight;

	private Double overLength;

	private Double overWidth;

	private Double overweightFee;

	private Double packingCharge;

	private Double handlingFee;

	private Double extraFee;

	private Double tpsDeliveryFee;

	private Double tpsProportion;

	private Double tpnDeliveryFee;

	private Double tpnProportion;

	private Double pfPrice;

	private Date createdAt;

	private Date updatedAt;
	
	public LightEbaySundryModel() {
		super();
	}

	public LightEbaySundryModel(String country) {
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
		this.country = country == null ? null : country.trim();
	}

	public Double getOverWeight() {
		return overWeight;
	}

	public void setOverWeight(Double overWeight) {
		this.overWeight = overWeight;
	}

	public Double getOverLength() {
		return overLength;
	}

	public void setOverLength(Double overLength) {
		this.overLength = overLength;
	}

	public Double getOverWidth() {
		return overWidth;
	}

	public void setOverWidth(Double overWidth) {
		this.overWidth = overWidth;
	}

	public Double getOverweightFee() {
		return overweightFee;
	}

	public void setOverweightFee(Double overweightFee) {
		this.overweightFee = overweightFee;
	}

	public Double getPackingCharge() {
		return packingCharge;
	}

	public void setPackingCharge(Double packingCharge) {
		this.packingCharge = packingCharge;
	}

	public Double getHandlingFee() {
		return handlingFee;
	}

	public void setHandlingFee(Double handlingFee) {
		this.handlingFee = handlingFee;
	}

	public Double getExtraFee() {
		return extraFee;
	}

	public void setExtraFee(Double extraFee) {
		this.extraFee = extraFee;
	}

	public Double getTpsDeliveryFee() {
		return tpsDeliveryFee;
	}

	public void setTpsDeliveryFee(Double tpsDeliveryFee) {
		this.tpsDeliveryFee = tpsDeliveryFee;
	}

	public Double getTpsProportion() {
		return tpsProportion;
	}

	public void setTpsProportion(Double tpsProportion) {
		this.tpsProportion = tpsProportion;
	}

	public Double getTpnDeliveryFee() {
		return tpnDeliveryFee;
	}

	public void setTpnDeliveryFee(Double tpnDeliveryFee) {
		this.tpnDeliveryFee = tpnDeliveryFee;
	}

	public Double getTpnProportion() {
		return tpnProportion;
	}

	public void setTpnProportion(Double tpnProportion) {
		this.tpnProportion = tpnProportion;
	}

	public Double getPfPrice() {
		return pfPrice;
	}

	public void setPfPrice(Double pfPrice) {
		this.pfPrice = pfPrice;
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