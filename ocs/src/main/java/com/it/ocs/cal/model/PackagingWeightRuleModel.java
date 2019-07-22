package com.it.ocs.cal.model;

import java.math.BigDecimal;
import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class PackagingWeightRuleModel extends BaseRuleModel {

	@ExcelLink(title = "标题", index = 4)
	private String title;

	@ExcelLink(title = "包装重量", index = 5)
	private Double packagingWeight;

	@ExcelLink(title = "排序", index = 6)
	private Integer sortOrder;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Double getPackagingWeight() {
		return packagingWeight;
	}

	public void setPackagingWeight(Double packagingWeight) {
		this.packagingWeight = packagingWeight;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}