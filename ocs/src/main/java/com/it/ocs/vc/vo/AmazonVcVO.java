package com.it.ocs.vc.vo;

import java.io.Serializable;

import com.it.ocs.vc.model.AmazonVcModel;

public class AmazonVcVO extends AmazonVcModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -599323019447733411L;

	private String startDate;
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
