package com.it.ocs.cal.model;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class TaxModel extends ImportModel {

	@ExcelRead(title = "国家")
	@ModelProp(name = "国家", colIndex = 0, nullable = false)
	private String countryId;

	@ExcelRead(title = "关税类型")
	@ModelProp(name = "关税类型", colIndex = 1, nullable = false)
	private String type;

	@ExcelRead(title = "销价增值税")
	@ModelProp(name = "销价增值税", colIndex = 2, nullable = false)
	private Double vat;

	@ExcelRead(title = "空运关税浮动")
	@ModelProp(name = "空运关税浮动", colIndex = 3, nullable = false)
	private Double afFluctuation;

	@ExcelRead(title = "海运关税浮动")
	@ModelProp(name = "海运关税浮动", colIndex = 4, nullable = false)
	private Double sfFluctuation;

	@ExcelRead(title = "快递关税浮动")
	@ModelProp(name = "快递关税浮动", colIndex = 5, nullable = false)
	private Double coFluctuation;

	@ExcelRead(title = "清关系数")
	@ModelProp(name = "清关系数", colIndex = 6, nullable = false)
	private Double clearCoefficient;

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId == null ? null : countryId.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	public Double getAfFluctuation() {
		return afFluctuation;
	}

	public void setAfFluctuation(Double afFluctuation) {
		this.afFluctuation = afFluctuation;
	}

	public Double getSfFluctuation() {
		return sfFluctuation;
	}

	public void setSfFluctuation(Double sfFluctuation) {
		this.sfFluctuation = sfFluctuation;
	}

	public Double getCoFluctuation() {
		return coFluctuation;
	}

	public void setCoFluctuation(Double coFluctuation) {
		this.coFluctuation = coFluctuation;
	}

	public Double getClearCoefficient() {
		return clearCoefficient;
	}

	public void setClearCoefficient(Double clearCoefficient) {
		this.clearCoefficient = clearCoefficient;
	}

}