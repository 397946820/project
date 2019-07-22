package com.it.ocs.salesStatistics.model;

import java.io.Serializable;

public class FooterModel implements Serializable {

	private String weekrate = "账号/站点";

	private String monthrate = "订单总数";

	private String status = "卖的总数";

	private String af = "周环比";

	private String sf = "月环比";

	private String co = "同比";

	private String afRate = "站点金额统计";

	private String sfRate = "美元统计";

	private String coRate = "人民币统计";
	// 用来区分是汇总行还是普通行
	private boolean isFooter = true;

	public String getMonthrate() {
		return monthrate;
	}

	public void setMonthrate(String monthrate) {
		this.monthrate = monthrate;
	}

	public String getWeekrate() {
		return weekrate;
	}

	public void setWeekrate(String weekrate) {
		this.weekrate = weekrate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAf() {
		return af;
	}

	public void setAf(String af) {
		this.af = af;
	}

	public String getSf() {
		return sf;
	}

	public void setSf(String sf) {
		this.sf = sf;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getAfRate() {
		return afRate;
	}

	public void setAfRate(String afRate) {
		this.afRate = afRate;
	}

	public String getSfRate() {
		return sfRate;
	}

	public void setSfRate(String sfRate) {
		this.sfRate = sfRate;
	}

	public String getCoRate() {
		return coRate;
	}

	public void setCoRate(String coRate) {
		this.coRate = coRate;
	}

	public boolean isFooter() {
		return isFooter;
	}

	public void setFooter(boolean isFooter) {
		this.isFooter = isFooter;
	}

}
