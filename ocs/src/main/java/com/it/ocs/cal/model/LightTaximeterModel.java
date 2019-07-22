package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightTaximeterModel {
	public LightTaximeterModel() {
	}

	public LightTaximeterModel(String platform) {
		this.platform = platform;
	}

	public LightTaximeterModel(String platform, String country, String sku, String shippingType, Short transactionMode,
			Short isCostOf, Short isStorageCharges) {
		this.platform = platform;
		this.country = country;
		this.sku = sku;
		this.shippingType = shippingType;
		this.transactionMode = transactionMode;
		this.isCostOf = isCostOf;
		this.isStorageCharges = isStorageCharges;
	}

	private Long entityId;

	@ExcelLink(title = "sku", index = 0, isAuto = true)
	private String sku;

	@ExcelLink(title = "平台", index = 1, isAuto = true)
	private String platform;

	@ExcelLink(title = "国家", index = 2, isAuto = true)
	private String country;

	@ExcelLink(title = "运输方式", index = 3, isAuto = true)
	private String shippingType;

	@ExcelLink(title = "采购成本", index = 4, isAuto = true)
	private String purchaseCost;

	@ExcelLink(title = "CIF", index = 5, isAuto = true)
	private String cif;

	@ExcelLink(title = "仓租费", index = 6, isAuto = true)
	private Double storageCharges;

	@ExcelLink(title = "杂费", index = 7, isAuto = true)
	private Double sundryCharges;

	@ExcelLink(title = "入库费", index = 8, isAuto = true)
	private Double wec;

	@ExcelLink(title = "出库费", index = 9, isAuto = true)
	private Double warehouseOutCharge;

	@ExcelLink(title = "耗材", index = 10, isAuto = true)
	private Double consumable;

	@ExcelLink(title = "人工", index = 11, isAuto = true)
	private Double labour;

	@ExcelLink(title = "订单费", index = 12, isAuto = true)
	private Double orderCost;

	@ExcelLink(title = "包装费", index = 13, isAuto = true)
	private Double packingExpense;

	@ExcelLink(title = "本地配送费", index = 14, isAuto = true)
	private Double localDeliveryFee;

	@ExcelLink(title = "最终成本", index = 15, isAuto = true)
	private Double finalCost;

	@ExcelLink(title = "平台费率", index = 16, isAuto = true)
	private Double referralRate;

	@ExcelLink(title = "PayPal费率", index = 17, isAuto = true)
	private Double paypalFee;

	@ExcelLink(title = "不可用率", index = 18, isAuto = true)
	private Double unfulliableRate;

	@ExcelLink(title = "补发率", index = 19, isAuto = true)
	private Double replacementRate;

	@ExcelLink(title = "利润率", index = 20, isAuto = true)
	private Double profitRate;

	@ExcelLink(title = "税率", index = 21, isAuto = true)
	private Double vat;

	@ExcelLink(title = "广告费率", index = 22, isAuto = true)
	private Double advertisingRate;

	@ExcelLink(title = "利润系数", index = 23, isAuto = true)
	private Double profitRateAction;

	@ExcelLink(title = "建议售价", index = 24, isAuto = true)
	private Double finalPrice;

	@ExcelLink(title = "交易模式", index = 25, isAuto = true)
	private Short transactionMode;

	@ExcelLink(title = "是否用占用费比", index = 26, isAuto = true)
	private Short isCostOf;

	@ExcelLink(title = "是否用仓租费", index = 27, isAuto = true)
	private Short isStorageCharges;

	private String currencySymbol;

	private Double sendWeight;

	private Double warehousingWeight;

	private Long qty;

	private Integer userId;

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

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType == null ? null : shippingType.trim();
	}

	public String getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(String purchaseCost) {
		this.purchaseCost = purchaseCost == null ? null : purchaseCost.trim();
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public Double getStorageCharges() {
		return storageCharges;
	}

	public void setStorageCharges(Double storageCharges) {
		this.storageCharges = storageCharges;
	}

	public Double getSundryCharges() {
		return sundryCharges;
	}

	public void setSundryCharges(Double sundryCharges) {
		this.sundryCharges = sundryCharges;
	}

	public Double getWec() {
		return wec;
	}

	public void setWec(Double wec) {
		this.wec = wec;
	}

	public Double getWarehouseOutCharge() {
		return warehouseOutCharge;
	}

	public void setWarehouseOutCharge(Double warehouseOutCharge) {
		this.warehouseOutCharge = warehouseOutCharge;
	}

	public Double getConsumable() {
		return consumable;
	}

	public void setConsumable(Double consumable) {
		this.consumable = consumable;
	}

	public Double getLabour() {
		return labour;
	}

	public void setLabour(Double labour) {
		this.labour = labour;
	}

	public Double getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(Double orderCost) {
		this.orderCost = orderCost;
	}

	public Double getPackingExpense() {
		return packingExpense;
	}

	public void setPackingExpense(Double packingExpense) {
		this.packingExpense = packingExpense;
	}

	public Double getLocalDeliveryFee() {
		return localDeliveryFee;
	}

	public void setLocalDeliveryFee(Double localDeliveryFee) {
		this.localDeliveryFee = localDeliveryFee;
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

	public Double getUnfulliableRate() {
		return unfulliableRate;
	}

	public void setUnfulliableRate(Double unfulliableRate) {
		this.unfulliableRate = unfulliableRate;
	}

	public Double getReplacementRate() {
		return replacementRate;
	}

	public void setReplacementRate(Double replacementRate) {
		this.replacementRate = replacementRate;
	}

	public Double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(Double profitRate) {
		this.profitRate = profitRate;
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

	public Short getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(Short transactionMode) {
		this.transactionMode = transactionMode;
	}

	public Short getIsCostOf() {
		return isCostOf;
	}

	public void setIsCostOf(Short isCostOf) {
		this.isCostOf = isCostOf;
	}

	public Short getIsStorageCharges() {
		return isStorageCharges;
	}

	public void setIsStorageCharges(Short isStorageCharges) {
		this.isStorageCharges = isStorageCharges;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public Double getSendWeight() {
		return sendWeight;
	}

	public void setSendWeight(Double sendWeight) {
		this.sendWeight = sendWeight;
	}

	public Double getWarehousingWeight() {
		return warehousingWeight;
	}

	public void setWarehousingWeight(Double warehousingWeight) {
		this.warehousingWeight = warehousingWeight;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
