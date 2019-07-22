package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightHandlingChargesModel {
	private Long id;

	@ExcelLink(title = "起始重量", index = 0)
	private Double fromWeight;

	@ExcelLink(title = "结束重量", index = 1)
	private Double toWeight;

	@ExcelLink(title = "费用", index = 2)
	private Double cost;

	@ExcelLink(title = "计费规则", index = 3)
	private String accountingRules;

	@ExcelLink(title = "最高费用", index = 4)
	private Double ultimateCost;

	@ExcelLink(title = "排序", index = 5)
	private Integer sortOrder;

	@ExcelLink(title = "规则类型", index = 6)
	private Integer type = 1;

	private Date createdAt;

	private Date updatedAt;

	public LightHandlingChargesModel() {
		super();
	}

	public LightHandlingChargesModel(Integer sortOrder, Integer type) {
		super();
		this.sortOrder = sortOrder;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getFromWeight() {
		return fromWeight;
	}

	public void setFromWeight(Double fromWeight) {
		this.fromWeight = fromWeight;
	}

	public Double getToWeight() {
		return toWeight;
	}

	public void setToWeight(Double toWeight) {
		this.toWeight = toWeight;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getAccountingRules() {
		return accountingRules;
	}

	public void setAccountingRules(String accountingRules) {
		this.accountingRules = accountingRules;
	}

	public Double getUltimateCost() {
		return ultimateCost;
	}

	public void setUltimateCost(Double ultimateCost) {
		this.ultimateCost = ultimateCost;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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