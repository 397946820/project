package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;
/**
 * 欧洲专线 关税等级规则
 * @author yecaiqing
 *
 */
public class SmallTariffRateModel {
	private Long entityId;

	@ExcelLink(title = "开始重量",index = 0)
	private Double fromWeight;

	@ExcelLink(title = "结束重量",index = 1)
	private Double toWeight;

	@ExcelLink(title = "最低申报价格",index = 2)
	private Double minimumDeclaredPrice;

	private Date createdAt;

	private Date updatedAt;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
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

	public Double getMinimumDeclaredPrice() {
		return minimumDeclaredPrice;
	}

	public void setMinimumDeclaredPrice(Double minimumDeclaredPrice) {
		this.minimumDeclaredPrice = minimumDeclaredPrice;
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
		sb.append(", fromWeight=").append(fromWeight);
		sb.append(", toWeight=").append(toWeight);
		sb.append(", minimumDeclaredPrice=").append(minimumDeclaredPrice);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append("]");
		return sb.toString();
	}
}
