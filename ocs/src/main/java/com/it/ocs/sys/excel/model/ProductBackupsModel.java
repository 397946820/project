package com.it.ocs.sys.excel.model;

import java.math.BigDecimal;

import com.it.ocs.excel.annotation.ExcelLink;

public class ProductBackupsModel {
	private Long entityId;

	@ExcelLink(title = "sku", index = 0)
	private String sku;

	@ExcelLink(title = "长(m)", index = 1)
	private BigDecimal length;

	@ExcelLink(title = "宽(m)", index = 2)
	private BigDecimal width;

	@ExcelLink(title = "高(m)", index = 3)
	private BigDecimal height;

	@ExcelLink(title = "净重(kg)", index = 4)
	private BigDecimal netWeight;

	@ExcelLink(title = "毛重(kg)", index = 5)
	private BigDecimal grossWeight;

	@ExcelLink(title = "装箱数量", index = 6)
	private BigDecimal packingQty;

	@ExcelLink(title = "外箱体积", index = 7)
	private BigDecimal outerVolume;

	@ExcelLink(title = "外箱重量", index = 8)
	private BigDecimal outerWeight;

	@ExcelLink(title = "是否多一个包", index = 9)
	private String isMultiOne;

	@ExcelLink(title = "是否PEU", index = 10)
	private String isPeu;

	@ExcelLink(title = "产品状态", index = 11)
	private String status;

	@ExcelLink(title = "货币", index = 12)
	private String currency;

	@ExcelLink(title = "成本", index = 13)
	private BigDecimal price;

	@ExcelLink(title = "退税率", index = 14)
	private BigDecimal taxRebateRate;

	@ExcelLink(title = "利率", index = 15)
	private BigDecimal interestRate;

	@ExcelLink(title = "国家", index = 16)
	private String countryId;

	@ExcelLink(title = "产品分类", index = 17)
	private String category;

	@ExcelLink(title = "库存周转率", index = 18)
	private String turnoverRate;

	@ExcelLink(title = "订单数量", index = 19)
	private String qtyOrdered;

	@ExcelLink(title = "平均存储月份", index = 20)
	private String averageMonth;

	@ExcelLink(title = "不可用率", index = 21)
	private String unfulliableRate;

	@ExcelLink(title = "补发比率", index = 22)
	private String replacementRate;

	@ExcelLink(title = "清关价", index = 23)
	private String clearPrice;

	@ExcelLink(title = "亚马逊FBA费用", index = 24)
	private String amzFba;

	@ExcelLink(title = "EFN费用", index = 25)
	private String efnFee;

	@ExcelLink(title = "关税税率", index = 26)
	private String dutyRate;

	private String monthOfYear;

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

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getPackingQty() {
		return packingQty;
	}

	public void setPackingQty(BigDecimal packingQty) {
		this.packingQty = packingQty;
	}

	public BigDecimal getOuterVolume() {
		return outerVolume;
	}

	public void setOuterVolume(BigDecimal outerVolume) {
		this.outerVolume = outerVolume;
	}

	public BigDecimal getOuterWeight() {
		return outerWeight;
	}

	public void setOuterWeight(BigDecimal outerWeight) {
		this.outerWeight = outerWeight;
	}

	public String getIsMultiOne() {
		return isMultiOne;
	}

	public void setIsMultiOne(String isMultiOne) {
		this.isMultiOne = isMultiOne == null ? null : isMultiOne.trim();
	}

	public String getIsPeu() {
		return isPeu;
	}

	public void setIsPeu(String isPeu) {
		this.isPeu = isPeu == null ? null : isPeu.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTaxRebateRate() {
		return taxRebateRate;
	}

	public void setTaxRebateRate(BigDecimal taxRebateRate) {
		this.taxRebateRate = taxRebateRate;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId == null ? null : countryId.trim();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	public String getTurnoverRate() {
		return turnoverRate;
	}

	public void setTurnoverRate(String turnoverRate) {
		this.turnoverRate = turnoverRate;
	}

	public String getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(String qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public String getAverageMonth() {
		return averageMonth;
	}

	public void setAverageMonth(String averageMonth) {
		this.averageMonth = averageMonth;
	}

	public String getUnfulliableRate() {
		return unfulliableRate;
	}

	public void setUnfulliableRate(String unfulliableRate) {
		this.unfulliableRate = unfulliableRate;
	}

	public String getReplacementRate() {
		return replacementRate;
	}

	public void setReplacementRate(String replacementRate) {
		this.replacementRate = replacementRate;
	}

	public String getClearPrice() {
		return clearPrice;
	}

	public void setClearPrice(String clearPrice) {
		this.clearPrice = clearPrice;
	}

	public String getAmzFba() {
		return amzFba;
	}

	public void setAmzFba(String amzFba) {
		this.amzFba = amzFba;
	}

	public String getEfnFee() {
		return efnFee;
	}

	public void setEfnFee(String efnFee) {
		this.efnFee = efnFee;
	}

	public String getDutyRate() {
		return dutyRate;
	}

	public void setDutyRate(String dutyRate) {
		this.dutyRate = dutyRate;
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
		sb.append(", sku=").append(sku);
		sb.append(", length=").append(length);
		sb.append(", width=").append(width);
		sb.append(", height=").append(height);
		sb.append(", netWeight=").append(netWeight);
		sb.append(", grossWeight=").append(grossWeight);
		sb.append(", packingQty=").append(packingQty);
		sb.append(", outerVolume=").append(outerVolume);
		sb.append(", outerWeight=").append(outerWeight);
		sb.append(", isMultiOne=").append(isMultiOne);
		sb.append(", isPeu=").append(isPeu);
		sb.append(", status=").append(status);
		sb.append(", currency=").append(currency);
		sb.append(", price=").append(price);
		sb.append(", taxRebateRate=").append(taxRebateRate);
		sb.append(", interestRate=").append(interestRate);
		sb.append(", countryId=").append(countryId);
		sb.append(", category=").append(category);
		sb.append(", turnoverRate=").append(turnoverRate);
		sb.append(", qtyOrdered=").append(qtyOrdered);
		sb.append(", averageMonth=").append(averageMonth);
		sb.append(", unfulliableRate=").append(unfulliableRate);
		sb.append(", replacementRate=").append(replacementRate);
		sb.append(", clearPrice=").append(clearPrice);
		sb.append(", amzFba=").append(amzFba);
		sb.append(", efnFee=").append(efnFee);
		sb.append(", dutyRate=").append(dutyRate);
		sb.append(", monthOfYear=").append(monthOfYear);
		sb.append("]");
		return sb.toString();
	}
}
