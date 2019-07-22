package com.it.ocs.cal.model;

import java.util.ArrayList;
import java.util.List;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightEbayTaxBaseModel {

	@ExcelLink(title = "SKU", index = 0)
	private String sku;

	@ExcelLink(title = "国家", index = 1)
	private String country;

	@ExcelLink(title = "累计库存数量", index = 2)
	private String inventoryQuantity;

	@ExcelLink(title = "累计销售量", index = 3)
	private String saleTotal;

	@ExcelLink(title = "不可用率", index = 4)
	private String unavailability;

	@ExcelLink(title = "补发率", index = 5)
	private String replacementRate;

	@ExcelLink(title = "存储天数", index = 6)
	private String storageDays;

	@ExcelLink(title = "占用费比", index = 7)
	private String costThan;

	@ExcelLink(title = "波动因子", index = 8)
	private String volatilityFactor;

	private List<LightEbayTaxModel> models = new ArrayList<>();

	public List<LightEbayTaxModel> getModels() {
		if (isEmpty()) {
			for (int i = 0; i < 3; i++) {
				LightEbayTaxModel model = new LightEbayTaxModel();
				model.setSku(this.sku);
				model.setCountry(formateData(this.country)[i]);
				model.setInventoryQuantity(new Long(formateData(this.inventoryQuantity)[i]));
				model.setSaleTotal(new Long(formateData(this.saleTotal)[i]));
				model.setUnavailability(new Double(formateData(this.unavailability)[i]));
				model.setReplacementRate(new Double(formateData(this.replacementRate)[i]));
				model.setStorageDays(new Long(formateData(this.storageDays)[i]));
				model.setCostThan(new Double(formateData(this.costThan)[i]));
				model.setVolatilityFactor(new Double(formateData(this.volatilityFactor)[i]));
				this.models.add(model);
			}
		}
		return models;
	}

	private String[] formateData(String data) {
		if (null != data) {
			return data.split(",");
		}
		return new String[] {};
	}

	private Boolean isEmpty() {
		return formateData(this.country).length == 3 && formateData(this.inventoryQuantity).length == 3
				&& formateData(this.saleTotal).length == 3 && formateData(this.unavailability).length == 3
				&& formateData(this.replacementRate).length == 3 && formateData(this.storageDays).length == 3
				&& formateData(this.costThan).length == 3 && formateData(this.volatilityFactor).length == 3;
	}

	public void setModels(List<LightEbayTaxModel> models) {
		this.models = models;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(String inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public String getSaleTotal() {
		return saleTotal;
	}

	public void setSaleTotal(String saleTotal) {
		this.saleTotal = saleTotal;
	}

	public String getUnavailability() {
		return unavailability;
	}

	public void setUnavailability(String unavailability) {
		this.unavailability = unavailability;
	}

	public String getReplacementRate() {
		return replacementRate;
	}

	public void setReplacementRate(String replacementRate) {
		this.replacementRate = replacementRate;
	}

	public String getStorageDays() {
		return storageDays;
	}

	public void setStorageDays(String storageDays) {
		this.storageDays = storageDays;
	}

	public String getCostThan() {
		return costThan;
	}

	public void setCostThan(String costThan) {
		this.costThan = costThan;
	}

	public String getVolatilityFactor() {
		return volatilityFactor;
	}

	public void setVolatilityFactor(String volatilityFactor) {
		this.volatilityFactor = volatilityFactor;
	}
}