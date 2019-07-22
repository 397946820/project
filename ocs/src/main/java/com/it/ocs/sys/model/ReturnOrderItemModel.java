package com.it.ocs.sys.model;

import java.util.Date;

public class ReturnOrderItemModel {
	private Long entityId;

	private Long parentId;

	private String sku;

	private Long qty;

	private Double returnCost;

	private Long badProductQty;

	private String productCaseType;

	private Long useHourDay;

	private Date createDate;

	private Date udpateDate;

	private Long qtyOrdered;

	private Double rowTotal;

	private Long lineNumber;

	private Integer inventoryQuantity;

	private String asin;

	private String cancellationType;

	private Long orderItemId;

	private String mSku;

	private String remarks;

	private String tarckingService;

	private String tarckingNum;

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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Double getReturnCost() {
		return returnCost;
	}

	public void setReturnCost(Double returnCost) {
		this.returnCost = returnCost;
	}

	public Long getBadProductQty() {
		return badProductQty;
	}

	public void setBadProductQty(Long badProductQty) {
		this.badProductQty = badProductQty;
	}

	public String getProductCaseType() {
		return productCaseType;
	}

	public void setProductCaseType(String productCaseType) {
		this.productCaseType = productCaseType == null ? null : productCaseType.trim();
	}

	public Long getUseHourDay() {
		return useHourDay;
	}

	public void setUseHourDay(Long useHourDay) {
		this.useHourDay = useHourDay;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUdpateDate() {
		return udpateDate;
	}

	public void setUdpateDate(Date udpateDate) {
		this.udpateDate = udpateDate;
	}

	public Long getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(Long qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public Double getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(Double rowTotal) {
		this.rowTotal = rowTotal;
	}

	public Long getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(Integer inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getCancellationType() {
		return cancellationType;
	}

	public void setCancellationType(String cancellationType) {
		this.cancellationType = cancellationType;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getmSku() {
		return mSku;
	}

	public void setmSku(String mSku) {
		this.mSku = mSku;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTarckingService() {
		return tarckingService;
	}

	public void setTarckingService(String tarckingService) {
		this.tarckingService = tarckingService;
	}

	public String getTarckingNum() {
		return tarckingNum;
	}

	public void setTarckingNum(String tarckingNum) {
		this.tarckingNum = tarckingNum;
	}

	@Override
	public String toString() {
		return "ReturnOrderItemModel [entityId=" + entityId + ", parentId=" + parentId + ", sku=" + sku + ", qty=" + qty
				+ ", returnCost=" + returnCost + ", badProductQty=" + badProductQty + ", productCaseType="
				+ productCaseType + ", useHourDay=" + useHourDay + ", createDate=" + createDate + ", udpateDate="
				+ udpateDate + ", qtyOrdered=" + qtyOrdered + ", rowTotal=" + rowTotal + ", lineNumber=" + lineNumber
				+ ", inventoryQuantity=" + inventoryQuantity + ", asin=" + asin + ", cancellationType="
				+ cancellationType + ", orderItemId=" + orderItemId + ", mSku=" + mSku + ", remarks=" + remarks
				+ ", tarckingService=" + tarckingService + ", tarckingNum=" + tarckingNum + "]";
	}

}