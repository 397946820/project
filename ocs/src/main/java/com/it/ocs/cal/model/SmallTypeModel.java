package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

/**
 * 
 * @author yecaiqing
 *
 */
public class SmallTypeModel {
	private Long entityId;

	@ExcelLink(title = "类型名称", index = 0)
	private String typeName;

	@ExcelLink(title = "GST", index = 1)
	private Double gst;

	@ExcelLink(title = "关税", index = 2)
	private Double tariffRate;

	@ExcelLink(title = "清关价调整比例", index = 3)
	private Double clearPriceAdjustmentRatio;

	public SmallTypeModel() {
		super();
	}

	public SmallTypeModel(String typeName) {
		this.typeName = typeName;
	}

	private Date createdAt;

	private Date updatedAt;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName == null ? null : typeName.trim();
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getTariffRate() {
		return tariffRate;
	}

	public void setTariffRate(Double tariffRate) {
		this.tariffRate = tariffRate;
	}

	public Double getClearPriceAdjustmentRatio() {
		return clearPriceAdjustmentRatio;
	}

	public void setClearPriceAdjustmentRatio(Double clearPriceAdjustmentRatio) {
		this.clearPriceAdjustmentRatio = clearPriceAdjustmentRatio;
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
		sb.append(", typeName=").append(typeName);
		sb.append(", gst=").append(gst);
		sb.append(", tariffRate=").append(tariffRate);
		sb.append(", clearPriceAdjustmentRatio=").append(clearPriceAdjustmentRatio);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append("]");
		return sb.toString();
	}
}
