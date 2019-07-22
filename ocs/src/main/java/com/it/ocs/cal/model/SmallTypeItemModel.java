package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

/**
 * 
 * @author yecaiqing
 *
 */
public class SmallTypeItemModel {
	private Long entityId;

	private Long parentId;

	@ExcelLink(title = "类型名称", index = 0)
	private String typeName;

	@ExcelLink(title = "运输方式", index = 1)
	private String shippingType;

	@ExcelLink(title = "最小重量", index = 2)
	private Double minWeight;

	@ExcelLink(title = "开始重量", index = 3)
	private Double fromWeight;

	@ExcelLink(title = "结束重量", index = 4)
	private Double toWeight;

	@ExcelLink(title = "操作费/挂号费", index = 5)
	private Double operatingExpenses;

	@ExcelLink(title = "公斤重", index = 6)
	private Double kilogramWeight;

	private Date createdAt;

	private Date updatedAt;

	public SmallTypeItemModel() {
		super();
	}

	public SmallTypeItemModel(String typeName, String shippingType) {
		this.typeName = typeName;
		this.shippingType = shippingType;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName == null ? null : typeName.trim();
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType == null ? null : shippingType.trim();
	}

	public Double getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(Double minWeight) {
		this.minWeight = minWeight;
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

	public Double getOperatingExpenses() {
		return operatingExpenses;
	}

	public void setOperatingExpenses(Double operatingExpenses) {
		this.operatingExpenses = operatingExpenses;
	}

	public Double getKilogramWeight() {
		return kilogramWeight;
	}

	public void setKilogramWeight(Double kilogramWeight) {
		this.kilogramWeight = kilogramWeight;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", entityId=").append(entityId);
		sb.append(", parentId=").append(parentId);
		sb.append(", shippingType=").append(shippingType);
		sb.append(", minWeight=").append(minWeight);
		sb.append(", fromWeight=").append(fromWeight);
		sb.append(", toWeight=").append(toWeight);
		sb.append(", operatingExpenses=").append(operatingExpenses);
		sb.append(", kilogramWeight=").append(kilogramWeight);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append("]");
		return sb.toString();
	}
}
