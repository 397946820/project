package com.it.ocs.publication.vo;

import java.io.Serializable;

import com.it.ocs.publication.model.EBayTimingPlanModel;

public class TimingPlanVO extends EBayTimingPlanModel implements Serializable {
	private String startDate;
	private String endDate;
	private String createDate;
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
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
