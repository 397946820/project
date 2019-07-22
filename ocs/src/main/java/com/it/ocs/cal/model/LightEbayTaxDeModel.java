package com.it.ocs.cal.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightEbayTaxDeModel extends LightEbayTaxBaseModel {

	@ExcelLink(title = "storageCharges", index = 4)
	private Double storageCharges;

	@ExcelLink(title = "salary", index = 5)
	private Double salary;

	@ExcelLink(title = "consumable", index = 6)
	private Double consumable;

	public Double getStorageCharges() {
		return storageCharges;
	}

	public void setStorageCharges(Double storageCharges) {
		this.storageCharges = storageCharges;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Double getConsumable() {
		return consumable;
	}

	public void setConsumable(Double consumable) {
		this.consumable = consumable;
	}

}
