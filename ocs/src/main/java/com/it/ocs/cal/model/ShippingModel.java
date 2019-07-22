package com.it.ocs.cal.model;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class ShippingModel extends ImportModel {

	@ExcelRead(title = "国家")
	@ModelProp(name = "国家", colIndex = 19, nullable = false)
	private String countryId;

	private String afShippingFee;

	private String sfShippingFee;

	private String coShippingFee;

	private String storageCost;

	@ExcelRead(title = "操作费")
	@ModelProp(name = "操作费", colIndex = 20, nullable = false)
	private Double operatingFee;

	private String unexpectedLoss;

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId == null ? null : countryId.trim();
	}

	public Double getOperatingFee() {
		return operatingFee;
	}

	public void setOperatingFee(Double operatingFee) {
		this.operatingFee = operatingFee;
	}

	public String getAfShippingFee() {
		return afShippingFee;
	}

	public void setAfShippingFee(String afShippingFee) {
		this.afShippingFee = afShippingFee;
	}

	public String getSfShippingFee() {
		return sfShippingFee;
	}

	public void setSfShippingFee(String sfShippingFee) {
		this.sfShippingFee = sfShippingFee;
	}

	public String getCoShippingFee() {
		return coShippingFee;
	}

	public void setCoShippingFee(String coShippingFee) {
		this.coShippingFee = coShippingFee;
	}

	public String getStorageCost() {
		return storageCost;
	}

	public void setStorageCost(String storageCost) {
		this.storageCost = storageCost;
	}

	public String getUnexpectedLoss() {
		return unexpectedLoss;
	}

	public void setUnexpectedLoss(String unexpectedLoss) {
		this.unexpectedLoss = unexpectedLoss;
	}

}