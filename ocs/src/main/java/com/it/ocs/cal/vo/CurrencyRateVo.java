package com.it.ocs.cal.vo;

import java.io.Serializable;

import com.it.ocs.cal.model.CurrencyRateModel;

public class CurrencyRateVo extends CurrencyRateModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7127736580667365550L;

	private Double from = 1d;

	public Double getFrom() {
		return from;
	}

	public void setFrom(Double from) {
		this.from = from;
	}

}
