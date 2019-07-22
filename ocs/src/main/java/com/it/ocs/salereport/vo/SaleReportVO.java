package com.it.ocs.salereport.vo;

import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;

public class SaleReportVO extends SalesStatisticsVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6862252901524569200L;

	private String plateForm;

	private String account;

	public String getPlateForm() {
		return plateForm;
	}

	public void setPlateForm(String plateForm) {
		this.plateForm = plateForm;
	}
	@Override
	public String getAccount() {
		return account;
	}
	@Override
	public void setAccount(String account) {
		this.account = account;
	}

}
