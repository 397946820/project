package com.it.ocs.cal.model;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class PricePlanModel extends ImportModel {

	@ExcelRead(title = "国家")
	@ModelProp(name = "国家", colIndex = 0, nullable = false)
	private String countryId;

	@ExcelRead(title = "SKU")
	@ModelProp(name = "SKU", colIndex = 1, nullable = false)
	private String sku;

	@ExcelRead(title = "物流方式")
	@ModelProp(name = "物流方式", colIndex = 2, nullable = false)
	private String shippingType;

	@ExcelRead(title = "利润系数")
	@ModelProp(name = "利润系数", colIndex = 3, nullable = false)
	private Double profitRateAction;

	@ExcelRead(title = "建议售价")
	@ModelProp(name = "建议售价", colIndex = 4, nullable = false)
	private Double finalPrice;

	@ExcelRead(title = "到岸价")
	private String cif;

	@ExcelRead(title = "到岸价(USD)")
	private String cifusd;

	@ExcelRead(title = "到岸价(RMB)")
	private String cifrmb;

	@ExcelRead(title = "是否Oversize")
	private String isOversize;

	@ExcelRead(title = "采购成本")
	private String sourcingCost;

	@ExcelRead(title = "FBA推算费用")
	private Double fbaFee;

	@ExcelRead(title = "FBA实际费用")
	private Double amzFba;

	@ExcelRead(title = "仓储费")
	private Double storageFee;

	@ExcelRead(title = "最终成本")
	private Double finalCost;

	@ExcelRead(title = "推介费率")
	private Double referralRate;

	@ExcelRead(title = "损坏率")
	private Double unfulliableRate;

	@ExcelRead(title = "补发率")
	private Double replacementRate;

	@ExcelRead(title = "利润率")
	private Double profitRate;

	@ExcelRead(title = "税率")
	private Double vat;

	private Double unexpectedLoss;

	private Long userId;

	private String currencySymbol;

	private String status;
	
	private String finalPriceStr;
	
	private String productType;
	private String discontinue;
	private Double returnRate; 
	
	public String getCountryId() {
		return countryId;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCifusd() {
		return cifusd;
	}

	public void setCifusd(String cifusd) {
		this.cifusd = cifusd;
	}

	public String getCifrmb() {
		return cifrmb;
	}

	public void setCifrmb(String cifrmb) {
		this.cifrmb = cifrmb;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId == null ? null : countryId.trim();
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

	public String getIsOversize() {
		return isOversize;
	}

	public void setIsOversize(String isOversize) {
		this.isOversize = isOversize;
	}

	public String getSourcingCost() {
		return sourcingCost;
	}

	public void setSourcingCost(String sourcingCost) {
		this.sourcingCost = sourcingCost;
	}

	public Double getFbaFee() {
		return fbaFee;
	}

	public void setFbaFee(Double fbaFee) {
		this.fbaFee = fbaFee;
	}

	public Double getAmzFba() {
		return amzFba;
	}

	public void setAmzFba(Double amzFba) {
		this.amzFba = amzFba;
	}

	public Double getStorageFee() {
		return storageFee;
	}

	public void setStorageFee(Double storageFee) {
		this.storageFee = storageFee;
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

	public Double getProfitRateAction() {
		return profitRateAction;
	}

	public void setProfitRateAction(Double profitRateAction) {
		this.profitRateAction = profitRateAction;
	}

	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Double getUnexpectedLoss() {
		return unexpectedLoss;
	}

	public void setUnexpectedLoss(Double unexpectedLoss) {
		this.unexpectedLoss = unexpectedLoss;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFinalPriceStr() {
		return finalPriceStr;
	}

	public void setFinalPriceStr(String finalPriceStr) {
		this.finalPriceStr = finalPriceStr;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDiscontinue() {
		return discontinue;
	}

	public void setDiscontinue(String discontinue) {
		this.discontinue = discontinue;
	}

	public Double getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(Double returnRate) {
		this.returnRate = returnRate;
	}
	
}