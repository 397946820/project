package com.it.ocs.cal.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.it.ocs.cal.model.ShippingModel;
import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class ShippingVo extends ShippingModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4721357762352300653L;

	@JsonIgnore
	@ExcelRead(title = "af_cost")
	@ModelProp(name = "af_cost", colIndex = 0, nullable = false)
	private Double af_cost;

	@JsonIgnore
	@ExcelRead(title = "af_currency")
	@ModelProp(name = "af_currency", colIndex = 1, nullable = false)
	private String af_currency;

	@JsonIgnore
	@ExcelRead(title = "af_cost_fluctuation")
	@ModelProp(name = "af_cost_fluctuation", colIndex = 2, nullable = false)
	private Double af_cost_fluctuation;

	@JsonIgnore
	@ExcelRead(title = "af_profit_rate")
	@ModelProp(name = "af_profit_rate", colIndex = 3, nullable = false)
	private Double af_profit_rate;

	@JsonIgnore
	@ExcelRead(title = "af_multi_profit_rate")
	@ModelProp(name = "af_multi_profit_rate", colIndex = 4, nullable = false)
	private Double af_multi_profit_rate;

	@JsonIgnore
	@ExcelRead(title = "sf_cost")
	@ModelProp(name = "sf_cost", colIndex = 5, nullable = false)
	private Double sf_cost;

	@JsonIgnore
	@ExcelRead(title = "sf_currency")
	@ModelProp(name = "sf_currency", colIndex = 6, nullable = false)
	private String sf_currency;

	@JsonIgnore
	@ExcelRead(title = "sf_cost_fluctuation")
	@ModelProp(name = "sf_cost_fluctuation", colIndex = 7, nullable = false)
	private Double sf_cost_fluctuation;

	@JsonIgnore
	@ExcelRead(title = "sf_profit_rate")
	@ModelProp(name = "sf_profit_rate", colIndex = 8, nullable = false)
	private Double sf_profit_rate;

	@JsonIgnore
	@ExcelRead(title = "sf_multi_profit_rate")
	@ModelProp(name = "sf_multi_profit_rate", colIndex = 9, nullable = false)
	private Double sf_multi_profit_rate;

	@JsonIgnore
	@ExcelRead(title = "co_cost")
	@ModelProp(name = "co_cost", colIndex = 10, nullable = false)
	private Double co_cost;

	@JsonIgnore
	@ExcelRead(title = "co_currency")
	@ModelProp(name = "co_currency", colIndex = 11, nullable = false)
	private String co_currency;

	@JsonIgnore
	@ExcelRead(title = "co_cost_fluctuation")
	@ModelProp(name = "co_cost_fluctuation", colIndex = 12, nullable = false)
	private Double co_cost_fluctuation;

	@JsonIgnore
	@ExcelRead(title = "co_profit_rate")
	@ModelProp(name = "co_profit_rate", colIndex = 13, nullable = false)
	private Double co_profit_rate;

	@JsonIgnore
	@ExcelRead(title = "co_multi_profit_rate")
	@ModelProp(name = "co_multi_profit_rate", colIndex = 14, nullable = false)
	private Double co_multi_profit_rate;

	@JsonIgnore
	@ExcelRead(title = "month")
	@ModelProp(name = "month", colIndex = 15, nullable = false)
	private String cost_month;

	@JsonIgnore
	@ExcelRead(title = "standard_size")
	@ModelProp(name = "standard_size", colIndex = 16, nullable = false)
	private Double cost_standard_size;

	@JsonIgnore
	@ExcelRead(title = "over_size")
	@ModelProp(name = "over_size", colIndex = 17, nullable = false)
	private Double cost_over_size;

	@JsonIgnore
	@ExcelRead(title = "param_one")
	@ModelProp(name = "param_one", colIndex = 18, nullable = false)
	private Double cost_param_one;

	public Double getAf_cost() {
		return af_cost;
	}

	public void setAf_cost(Double af_cost) {
		this.af_cost = af_cost;
	}

	public String getAf_currency() {
		return af_currency;
	}

	public void setAf_currency(String af_currency) {
		this.af_currency = af_currency;
	}

	public Double getAf_cost_fluctuation() {
		return af_cost_fluctuation;
	}

	public void setAf_cost_fluctuation(Double af_cost_fluctuation) {
		this.af_cost_fluctuation = af_cost_fluctuation;
	}

	public Double getAf_profit_rate() {
		return af_profit_rate;
	}

	public void setAf_profit_rate(Double af_profit_rate) {
		this.af_profit_rate = af_profit_rate;
	}

	public Double getAf_multi_profit_rate() {
		return af_multi_profit_rate;
	}

	public void setAf_multi_profit_rate(Double af_multi_profit_rate) {
		this.af_multi_profit_rate = af_multi_profit_rate;
	}

	public Double getSf_cost() {
		return sf_cost;
	}

	public void setSf_cost(Double sf_cost) {
		this.sf_cost = sf_cost;
	}

	public String getSf_currency() {
		return sf_currency;
	}

	public void setSf_currency(String sf_currency) {
		this.sf_currency = sf_currency;
	}

	public Double getSf_cost_fluctuation() {
		return sf_cost_fluctuation;
	}

	public void setSf_cost_fluctuation(Double sf_cost_fluctuation) {
		this.sf_cost_fluctuation = sf_cost_fluctuation;
	}

	public Double getSf_profit_rate() {
		return sf_profit_rate;
	}

	public void setSf_profit_rate(Double sf_profit_rate) {
		this.sf_profit_rate = sf_profit_rate;
	}

	public Double getSf_multi_profit_rate() {
		return sf_multi_profit_rate;
	}

	public void setSf_multi_profit_rate(Double sf_multi_profit_rate) {
		this.sf_multi_profit_rate = sf_multi_profit_rate;
	}

	public Double getCo_cost() {
		return co_cost;
	}

	public void setCo_cost(Double co_cost) {
		this.co_cost = co_cost;
	}

	public String getCo_currency() {
		return co_currency;
	}

	public void setCo_currency(String co_currency) {
		this.co_currency = co_currency;
	}

	public Double getCo_cost_fluctuation() {
		return co_cost_fluctuation;
	}

	public void setCo_cost_fluctuation(Double co_cost_fluctuation) {
		this.co_cost_fluctuation = co_cost_fluctuation;
	}

	public Double getCo_profit_rate() {
		return co_profit_rate;
	}

	public void setCo_profit_rate(Double co_profit_rate) {
		this.co_profit_rate = co_profit_rate;
	}

	public Double getCo_multi_profit_rate() {
		return co_multi_profit_rate;
	}

	public void setCo_multi_profit_rate(Double co_multi_profit_rate) {
		this.co_multi_profit_rate = co_multi_profit_rate;
	}

	public String getCost_month() {
		return cost_month;
	}

	public void setCost_month(String cost_month) {
		this.cost_month = cost_month;
	}

	public Double getCost_standard_size() {
		return cost_standard_size;
	}

	public void setCost_standard_size(Double cost_standard_size) {
		this.cost_standard_size = cost_standard_size;
	}

	public Double getCost_over_size() {
		return cost_over_size;
	}

	public void setCost_over_size(Double cost_over_size) {
		this.cost_over_size = cost_over_size;
	}

	public Double getCost_param_one() {
		return cost_param_one;
	}

	public void setCost_param_one(Double cost_param_one) {
		this.cost_param_one = cost_param_one;
	}

}
