package com.it.ocs.salesStatistics.model;

public class EbayFooter extends BaseFooter {

	private String station = "用户/站点";
	private String qty = "订单数量";
	private String currencycode = "总数";

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

}
