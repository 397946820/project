package com.it.ocs.sys.excel.model;

import java.math.BigDecimal;

import com.it.ocs.excel.annotation.ExcelLink;

public class FbaRuleBackupsModel {
	private Long entityId;

	@ExcelLink(title = "国家", index = 0)
	private String countryId;

	@ExcelLink(title = "标题", index = 1)
	private String title;

	@ExcelLink(title = "处理费", index = 2)
	private BigDecimal handlingFee;

	@ExcelLink(title = "包装费", index = 3)
	private BigDecimal pickpackFee;

	@ExcelLink(title = "长", index = 4)
	private BigDecimal length;

	@ExcelLink(title = "宽", index = 5)
	private BigDecimal width;

	@ExcelLink(title = "高", index = 6)
	private BigDecimal height;

	@ExcelLink(title = "标志", index = 7)
	private String flag;

	@ExcelLink(title = "起始重量", index = 8)
	private BigDecimal fromWeight;

	@ExcelLink(title = "结束重量", index = 9)
	private BigDecimal toWeight;

	@ExcelLink(title = "最大重量", index = 10)
	private BigDecimal maxWeight;

	@ExcelLink(title = "价格", index = 11)
	private String price;

	@ExcelLink(title = "EFN费用", index = 12)
	private BigDecimal efnFee;

	@ExcelLink(title = "FBA额外费用", index = 13)
	private BigDecimal extraFee;

	@ExcelLink(title = "适用月份", index = 14)
	private String isMonth;

	@ExcelLink(title = "排序", index = 15)
	private Integer sortOrder;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public BigDecimal getHandlingFee() {
		return handlingFee;
	}

	public void setHandlingFee(BigDecimal handlingFee) {
		this.handlingFee = handlingFee;
	}

	public BigDecimal getPickpackFee() {
		return pickpackFee;
	}

	public void setPickpackFee(BigDecimal pickpackFee) {
		this.pickpackFee = pickpackFee;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag == null ? null : flag.trim();
	}

	public BigDecimal getFromWeight() {
		return fromWeight;
	}

	public void setFromWeight(BigDecimal fromWeight) {
		this.fromWeight = fromWeight;
	}

	public BigDecimal getToWeight() {
		return toWeight;
	}

	public void setToWeight(BigDecimal toWeight) {
		this.toWeight = toWeight;
	}

	public BigDecimal getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(BigDecimal maxWeight) {
		this.maxWeight = maxWeight;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price == null ? null : price.trim();
	}

	public BigDecimal getEfnFee() {
		return efnFee;
	}

	public void setEfnFee(BigDecimal efnFee) {
		this.efnFee = efnFee;
	}

	public BigDecimal getExtraFee() {
		return extraFee;
	}

	public void setExtraFee(BigDecimal extraFee) {
		this.extraFee = extraFee;
	}

	public String getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(String isMonth) {
		this.isMonth = isMonth == null ? null : isMonth.trim();
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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
		sb.append(", title=").append(title);
		sb.append(", handlingFee=").append(handlingFee);
		sb.append(", pickpackFee=").append(pickpackFee);
		sb.append(", length=").append(length);
		sb.append(", width=").append(width);
		sb.append(", height=").append(height);
		sb.append(", flag=").append(flag);
		sb.append(", fromWeight=").append(fromWeight);
		sb.append(", toWeight=").append(toWeight);
		sb.append(", maxWeight=").append(maxWeight);
		sb.append(", price=").append(price);
		sb.append(", efnFee=").append(efnFee);
		sb.append(", extraFee=").append(extraFee);
		sb.append(", isMonth=").append(isMonth);
		sb.append(", sortOrder=").append(sortOrder);
		sb.append(", monthOfYear=").append(monthOfYear);
		sb.append("]");
		return sb.toString();
	}
}
