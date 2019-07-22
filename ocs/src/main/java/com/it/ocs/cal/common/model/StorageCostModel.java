package com.it.ocs.cal.common.model;

import java.io.Serializable;

public class StorageCostModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1088444003589150239L;

	private String month;

	private Double standard_size;

	private Double over_size;

	private Double param_one;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getStandard_size() {
		return standard_size;
	}

	public void setStandard_size(Double standard_size) {
		this.standard_size = standard_size;
	}

	public Double getOver_size() {
		return over_size;
	}

	public void setOver_size(Double over_size) {
		this.over_size = over_size;
	}

	public Double getParam_one() {
		return param_one;
	}

	public void setParam_one(Double param_one) {
		this.param_one = param_one;
	}

}
