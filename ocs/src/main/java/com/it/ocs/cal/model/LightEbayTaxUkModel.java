package com.it.ocs.cal.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightEbayTaxUkModel extends LightEbayTaxBaseModel {

	@ExcelLink(title = "warehouseEntryFee", index = 4)
	private Double warehouseEntryFee;

	@ExcelLink(title = "storageCharges", index = 5)
	private Double storageCharges;

	@ExcelLink(title = "restockingFee", index = 6)
	private Double restockingFee;

	public Double getWarehouseEntryFee() {
		return warehouseEntryFee;
	}

	public void setWarehouseEntryFee(Double warehouseEntryFee) {
		this.warehouseEntryFee = warehouseEntryFee;
	}

	public Double getStorageCharges() {
		return storageCharges;
	}

	public void setStorageCharges(Double storageCharges) {
		this.storageCharges = storageCharges;
	}

	public Double getRestockingFee() {
		return restockingFee;
	}

	public void setRestockingFee(Double restockingFee) {
		this.restockingFee = restockingFee;
	}

}
