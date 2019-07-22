package com.it.ocs.salesStatistics.model;

public class AmazonAndLightFooter extends BaseFooter {

	private String currencycode = "账号/站点";
	private String deduction = "订单数量";
	private String taxrate = "总数";

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getDeduction() {
		return deduction;
	}

	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}

	public String getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(String taxrate) {
		this.taxrate = taxrate;
	}

}
