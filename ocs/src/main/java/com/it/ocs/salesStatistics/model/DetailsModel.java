package com.it.ocs.salesStatistics.model;

import java.util.Date;

import com.it.ocs.cal.utils.ExcelRead;

public class DetailsModel {

	@ExcelRead(title = "sku")
	private String sku;

	@ExcelRead(title = "Asin")
	private String asin;

	@ExcelRead(title = "账号")
	private String platform;

	@ExcelRead(title = "站点")
	private String station;

	@ExcelRead(title = "订单数量")
	private int qty;

	@ExcelRead(title = "单价")
	private double unitprice;

	@ExcelRead(title = "币种")
	private String currencycode;

	@ExcelRead(title = "折扣额")
	private double deduction;

	@ExcelRead(title = "税额")
	private double taxrate;

	@ExcelRead(title = "金额")
	private double price;

	@ExcelRead(title = "金额(含税)")
	private double priceTax;

	@ExcelRead(title = "金额(不含税)")
	private double priceExcludingTax;

	@ExcelRead(title = "总数")
	private int count;
	// af利润率
	@ExcelRead(title = "详情af利润率")
	private Double afRate;
	// sf利润率
	@ExcelRead(title = "详情sf利润率")
	private Double sfRate;
	// co利润率
	@ExcelRead(title = "详情co利润率")
	private Double coRate;
	// 开始时间
	@ExcelRead(title = "开始时间")
	private Date fromtime;
	// 结束时间
	@ExcelRead(title = "结束时间")
	private Date totime;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(double taxrate) {
		this.taxrate = taxrate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPriceTax() {
		return priceTax;
	}

	public void setPriceTax(double priceTax) {
		this.priceTax = priceTax;
	}

	public double getPriceExcludingTax() {
		return priceExcludingTax;
	}

	public void setPriceExcludingTax(double priceExcludingTax) {
		this.priceExcludingTax = priceExcludingTax;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Double getAfRate() {
		return afRate;
	}

	public void setAfRate(Double afRate) {
		this.afRate = afRate;
	}

	public Double getSfRate() {
		return sfRate;
	}

	public void setSfRate(Double sfRate) {
		this.sfRate = sfRate;
	}

	public Double getCoRate() {
		return coRate;
	}

	public void setCoRate(Double coRate) {
		this.coRate = coRate;
	}

	public Date getFromtime() {
		return fromtime;
	}

	public void setFromtime(Date fromtime) {
		this.fromtime = fromtime;
	}

	public Date getTotime() {
		return totime;
	}

	public void setTotime(Date totime) {
		this.totime = totime;
	}

}
