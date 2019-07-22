package com.it.ocs.cal.model;

import java.util.Date;

/**
 * 
 * @author yecaiqing
 *
 */
public class SmallTaximeterModel {
	private Long entityId;

	private String platform;

	private String typeName;

	private String shippingType;

	private String sku;

	private Integer qty;

	private String tradingMode;

	private Double finalCost;

	private Double referralRate;

	private Double paypalFee;

	private Double vat;

	private Double advertisingRate;

	private Double profitRate;

	private Double profitRateAction;

	private Double finalPrice;

	private String currencyCode;

	private Double finalPrice_;

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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName == null ? null : typeName.trim();
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType == null ? null : shippingType.trim();
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getTradingMode() {
		return tradingMode;
	}

	public void setTradingMode(String tradingMode) {
		this.tradingMode = tradingMode == null ? null : tradingMode.trim();
	}

	public Double getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(Double finalCost) {
		this.finalCost = finalCost;
	}

	public Double getReferralRate() {
		return referralRate;
	}

	public void setReferralRate(Double referralRate) {
		this.referralRate = referralRate;
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

	public Double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(Double profitRate) {
		this.profitRate = profitRate;
	}

	public Double getProfitRateAction() {
		return profitRateAction;
	}

	public void setProfitRateAction(Double profitRateAction) {
		this.profitRateAction = profitRateAction;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	public Double getFinalPrice_() {
		return finalPrice_;
	}

	public void setFinalPrice_(Double finalPrice_) {
		this.finalPrice_ = finalPrice_;
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
