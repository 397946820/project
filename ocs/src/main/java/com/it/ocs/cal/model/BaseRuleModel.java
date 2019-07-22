package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class BaseRuleModel {

	private Long entityId;

	@ExcelLink(title = "重量", index = 0)
	private Double weight;

	@ExcelLink(title = "长", index = 1)
	private Double length;

	@ExcelLink(title = "宽", index = 2)
	private Double width;

	@ExcelLink(title = "高", index = 3)
	private Double height;

	private Integer type = 1;

	private Date createdAt;

	private Date updatedAt;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
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
