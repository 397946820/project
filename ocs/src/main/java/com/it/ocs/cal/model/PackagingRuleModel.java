package com.it.ocs.cal.model;

public class PackagingRuleModel extends BaseRuleModel {

	private String title;

	private Double packagingWeight;

	private Double regular;

	private Double discount;

	private String isMonth;

	private Integer sortOrder;

	private Double girth;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPackagingWeight() {
		return packagingWeight;
	}

	public void setPackagingWeight(Double packagingWeight) {
		this.packagingWeight = packagingWeight;
	}

	public Double getRegular() {
		return regular;
	}

	public void setRegular(Double regular) {
		this.regular = regular;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(String isMonth) {
		this.isMonth = isMonth;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Double getGirth() {
		return girth;
	}

	public void setGirth(Double girth) {
		this.girth = girth;
	}

}
