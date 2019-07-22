package com.it.ocs.salesStatistics.model;

import com.it.ocs.cal.utils.ExcelRead;

public class SalesStatisticsAllModel {

	@ExcelRead(title = "平台")
	private String terrace;

	private String sku;

	@ExcelRead(title = "数量")
	private Long qty;

	@ExcelRead(title = "总数")
	private Long count;

	@ExcelRead(title = "美元统计")
	private Double priceUsd;

	@ExcelRead(title = "人民币统计")
	private Double priceRmb;

	@ExcelRead(title = "af总毛利额(美元)")
	private Double totalAf;

	@ExcelRead(title = "sf总毛利额(美元)")
	private Double totalSf;

	@ExcelRead(title = "co总毛利额(美元)")
	private Double totalCo;

	@ExcelRead(title = "af总毛润率")
	private Double totalAfRate;

	@ExcelRead(title = "sf总毛润率")
	private Double totalSfRate;

	@ExcelRead(title = "co总毛润率")
	private Double totalCoRate;

	@ExcelRead(title = "同比")
	private String sametermrate;

	@ExcelRead(title = "周环比")
	private String weekrate;

	@ExcelRead(title = "月环比")
	private String monthrate;

	public String getTerrace() {
		return terrace;
	}

	public void setTerrace(String terrace) {
		this.terrace = terrace;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Double getPriceUsd() {
		return priceUsd;
	}

	public void setPriceUsd(Double priceUsd) {
		this.priceUsd = priceUsd;
	}

	public Double getPriceRmb() {
		return priceRmb;
	}

	public void setPriceRmb(Double priceRmb) {
		this.priceRmb = priceRmb;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Double getTotalAf() {
		return totalAf;
	}

	public void setTotalAf(Double totalAf) {
		this.totalAf = totalAf;
	}

	public Double getTotalSf() {
		return totalSf;
	}

	public void setTotalSf(Double totalSf) {
		this.totalSf = totalSf;
	}

	public Double getTotalCo() {
		return totalCo;
	}

	public void setTotalCo(Double totalCo) {
		this.totalCo = totalCo;
	}

	public Double getTotalAfRate() {
		return totalAfRate;
	}

	public void setTotalAfRate(Double totalAfRate) {
		this.totalAfRate = totalAfRate;
	}

	public Double getTotalSfRate() {
		return totalSfRate;
	}

	public void setTotalSfRate(Double totalSfRate) {
		this.totalSfRate = totalSfRate;
	}

	public Double getTotalCoRate() {
		return totalCoRate;
	}

	public void setTotalCoRate(Double totalCoRate) {
		this.totalCoRate = totalCoRate;
	}

	public String getSametermrate() {
		return sametermrate;
	}

	public void setSametermrate(String sametermrate) {
		this.sametermrate = sametermrate;
	}

	public String getWeekrate() {
		return weekrate;
	}

	public void setWeekrate(String weekrate) {
		this.weekrate = weekrate;
	}

	public String getMonthrate() {
		return monthrate;
	}

	public void setMonthrate(String monthrate) {
		this.monthrate = monthrate;
	}

}
