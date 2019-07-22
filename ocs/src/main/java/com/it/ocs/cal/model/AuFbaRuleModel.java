package com.it.ocs.cal.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class AuFbaRuleModel extends BaseRuleModel {

	@ExcelLink(title = "价格", index = 4)
	private Double regular;

	@ExcelLink(title = "折扣价格", index = 5)
	private Double discount;

	@ExcelLink(title = "适用月份", index = 6)
	private String isMonth;

	@ExcelLink(title = "排序", index = 7)
	private Integer sortOrder;

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

}
