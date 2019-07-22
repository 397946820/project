package com.it.ocs.cal.common.model;

import java.io.Serializable;

public class ShippingFeeModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5793378896766023999L;

	private Double cost;

	private String currency;

	private Double cost_fluctuation;

	private Double profit_rate;

	private Double multi_profit_rate;

	private String delete;

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getCost_fluctuation() {
		return cost_fluctuation;
	}

	public void setCost_fluctuation(Double cost_fluctuation) {
		this.cost_fluctuation = cost_fluctuation;
	}

	public Double getProfit_rate() {
		return profit_rate;
	}

	public void setProfit_rate(Double profit_rate) {
		this.profit_rate = profit_rate;
	}

	public Double getMulti_profit_rate() {
		return multi_profit_rate;
	}

	public void setMulti_profit_rate(Double multi_profit_rate) {
		this.multi_profit_rate = multi_profit_rate;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

}