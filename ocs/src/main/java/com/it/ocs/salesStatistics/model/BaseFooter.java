package com.it.ocs.salesStatistics.model;

public class BaseFooter {

	private String price = "同比";

	private String count = "环比";

	private String sametermrate = "站点金额统计";

	private String ringperiodrate = "美元统计";

	private String status = "人民币统计";
	// 用来区分是汇总行还是普通行
	private boolean isFooter = true;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSametermrate() {
		return sametermrate;
	}

	public void setSametermrate(String sametermrate) {
		this.sametermrate = sametermrate;
	}

	public String getRingperiodrate() {
		return ringperiodrate;
	}

	public void setRingperiodrate(String ringperiodrate) {
		this.ringperiodrate = ringperiodrate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isFooter() {
		return isFooter;
	}

	public void setFooter(boolean isFooter) {
		this.isFooter = isFooter;
	}

}
