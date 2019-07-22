package com.it.ocs.customerCenter.vo;

import java.io.Serializable;

import com.it.ocs.customerCenter.model.CustomerRefundModel;

public class CustomerRefundVO extends CustomerRefundModel implements Serializable {

	private String startDate;
	private String endDate;
	private String sort;
	private String order;
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
