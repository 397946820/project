package com.it.ocs.sys.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitNewSkuModel {

	private Long entityId;

	@ExcelLink(title = "SKU", index = 0)
	private String sku;

	private String monthOfYear;

	public OperatingProfitNewSkuModel() {
		super();
	}

	public OperatingProfitNewSkuModel(String sku, String monthOfYear) {
		this.sku = sku;
		this.monthOfYear = monthOfYear;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

}
