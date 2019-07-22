package com.it.ocs.sys.excel.model;

import java.math.BigDecimal;

import com.it.ocs.excel.annotation.ExcelLink;

public class PricePlanBackupsModel {
	private Long entityId;

	@ExcelLink(title = "国家", index = 0)
	private String countryId;

	@ExcelLink(title = "运输方式", index = 1)
	private String shippingType;

	@ExcelLink(title = "sku", index = 2)
	private String sku;

	@ExcelLink(title = "是否Oversize", index = 3)
	private String isOversize;

	@ExcelLink(title = "到岸价", index = 4)
	private String cif;

	@ExcelLink(title = "到岸价(USD)", index = 5)
	private String cifusd;

	@ExcelLink(title = "到岸价(RMB)", index = 6)
	private String cifrmb;

	@ExcelLink(title = "采购成本", index = 7)
	private String sourcingCost;

	@ExcelLink(title = "FBA推算费用", index = 8)
	private BigDecimal fbaFee;

	@ExcelLink(title = "FBA实际费用", index = 9)
	private BigDecimal amzFba;

	@ExcelLink(title = "仓储费", index = 10)
	private BigDecimal storageFee;

	@ExcelLink(title = "最终成本", index = 11)
	private BigDecimal finalCost;

	@ExcelLink(title = "推介费率", index = 12)
	private BigDecimal referralRate;

	@ExcelLink(title = "损坏率", index = 13)
	private BigDecimal unfulliableRate;

	@ExcelLink(title = "补发率", index = 14)
	private BigDecimal replacementRate;

	@ExcelLink(title = "利润率", index = 15)
	private BigDecimal profitRate;

	@ExcelLink(title = "利润系数", index = 16)
	private BigDecimal profitRateAction;

	@ExcelLink(title = "税率", index = 17)
	private BigDecimal vat;

	@ExcelLink(title = "建议售价", index = 18)
	private BigDecimal finalPrice;

	@ExcelLink(title = "SOURCING_COST", index = 19)
	private BigDecimal price;

	private String monthOfYear;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getCountryId() {
		return countryId;
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
		this.isOversize = isOversize == null ? null : isOversize.trim();
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif == null ? null : cif.trim();
	}

	public String getCifusd() {
		return cifusd;
	}

	public void setCifusd(String cifusd) {
		this.cifusd = cifusd == null ? null : cifusd.trim();
	}

	public String getCifrmb() {
		return cifrmb;
	}

	public void setCifrmb(String cifrmb) {
		this.cifrmb = cifrmb == null ? null : cifrmb.trim();
	}

	public String getSourcingCost() {
		return sourcingCost;
	}

	public void setSourcingCost(String sourcingCost) {
		this.sourcingCost = sourcingCost == null ? null : sourcingCost.trim();
	}

	public BigDecimal getFbaFee() {
		return fbaFee;
	}

	public void setFbaFee(BigDecimal fbaFee) {
		this.fbaFee = fbaFee;
	}

	public BigDecimal getAmzFba() {
		return amzFba;
	}

	public void setAmzFba(BigDecimal amzFba) {
		this.amzFba = amzFba;
	}

	public BigDecimal getStorageFee() {
		return storageFee;
	}

	public void setStorageFee(BigDecimal storageFee) {
		this.storageFee = storageFee;
	}

	public BigDecimal getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(BigDecimal finalCost) {
		this.finalCost = finalCost;
	}

	public BigDecimal getReferralRate() {
		return referralRate;
	}

	public void setReferralRate(BigDecimal referralRate) {
		this.referralRate = referralRate;
	}

	public BigDecimal getUnfulliableRate() {
		return unfulliableRate;
	}

	public void setUnfulliableRate(BigDecimal unfulliableRate) {
		this.unfulliableRate = unfulliableRate;
	}

	public BigDecimal getReplacementRate() {
		return replacementRate;
	}

	public void setReplacementRate(BigDecimal replacementRate) {
		this.replacementRate = replacementRate;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public BigDecimal getProfitRateAction() {
		return profitRateAction;
	}

	public void setProfitRateAction(BigDecimal profitRateAction) {
		this.profitRateAction = profitRateAction;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear == null ? null : monthOfYear.trim();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", entityId=").append(entityId);
		sb.append(", countryId=").append(countryId);
		sb.append(", shippingType=").append(shippingType);
		sb.append(", sku=").append(sku);
		sb.append(", isOversize=").append(isOversize);
		sb.append(", cif=").append(cif);
		sb.append(", cifusd=").append(cifusd);
		sb.append(", cifrmb=").append(cifrmb);
		sb.append(", sourcingCost=").append(sourcingCost);
		sb.append(", fbaFee=").append(fbaFee);
		sb.append(", amzFba=").append(amzFba);
		sb.append(", storageFee=").append(storageFee);
		sb.append(", finalCost=").append(finalCost);
		sb.append(", referralRate=").append(referralRate);
		sb.append(", unfulliableRate=").append(unfulliableRate);
		sb.append(", replacementRate=").append(replacementRate);
		sb.append(", profitRate=").append(profitRate);
		sb.append(", profitRateAction=").append(profitRateAction);
		sb.append(", vat=").append(vat);
		sb.append(", finalPrice=").append(finalPrice);
		sb.append(", price=").append(price);
		sb.append(", monthOfYear=").append(monthOfYear);
		sb.append("]");
		return sb.toString();
	}
}
