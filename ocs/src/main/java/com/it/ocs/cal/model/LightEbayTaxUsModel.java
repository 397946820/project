package com.it.ocs.cal.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightEbayTaxUsModel extends LightEbayTaxBaseModel {

	@ExcelLink(title = "firstEntryFee", index = 4)
	private Double firstEntryFee;

	@ExcelLink(title = "storageCharges", index = 5)
	private Double storageCharges;

	@ExcelLink(title = "restockingFee", index = 6)
	private Double restockingFee;

	@ExcelLink(title = "sundryCharges", index = 7)
	private Double sundryCharges;

	public Double getFirstEntryFee() {
		return firstEntryFee;
	}

	public void setFirstEntryFee(Double firstEntryFee) {
		this.firstEntryFee = firstEntryFee;
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

	public Double getSundryCharges() {
		return sundryCharges;
	}

	public void setSundryCharges(Double sundryCharges) {
		this.sundryCharges = sundryCharges;
	}

}
