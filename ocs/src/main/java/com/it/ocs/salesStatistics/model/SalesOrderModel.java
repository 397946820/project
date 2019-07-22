package com.it.ocs.salesStatistics.model;

import java.util.Date;

import com.it.ocs.cal.utils.ExcelRead;

public class SalesOrderModel {
	@ExcelRead(title = "sku")
	private String sku;

	@ExcelRead(title = "Asin")
	private String asin;

	@ExcelRead(title = "账号")
	private String platform;

	@ExcelRead(title = "站点")
	private String station;

	@ExcelRead(title = "币种")
	private String currencycode;

	@ExcelRead(title = "折扣额")
	private double deduction;

	@ExcelRead(title = "税额")
	private double taxrate;

	@ExcelRead(title = "金额")
	private double price;

	@ExcelRead(title = "数量")
	private int count;

	@ExcelRead(title = "单价")
	private double unitprice;

	@ExcelRead(title = "订单状态")
	private String status;

	@ExcelRead(title = "购买时间")
	private Date purchaseat;

	@ExcelRead(title = "发货时间")
	private Date lastestshipdate;

	@ExcelRead(title = "订单更新时间")
	private Date updatedat;

	@ExcelRead(title = "支付时间")
	private Date paidtime;

	@ExcelRead(title = "最新拉取数据时间")
	private Date lastfetchtime;

	@ExcelRead(title = "订单创建时间")
	private Date createdat;

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPurchaseat() {
		return purchaseat;
	}

	public void setPurchaseat(Date purchaseat) {
		this.purchaseat = purchaseat;
	}

	public Date getLastestshipdate() {
		return lastestshipdate;
	}

	public void setLastestshipdate(Date lastestshipdate) {
		this.lastestshipdate = lastestshipdate;
	}

	public Date getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

	public Date getPaidtime() {
		return paidtime;
	}

	public void setPaidtime(Date paidtime) {
		this.paidtime = paidtime;
	}

	public Date getLastfetchtime() {
		return lastfetchtime;
	}

	public void setLastfetchtime(Date lastfetchtime) {
		this.lastfetchtime = lastfetchtime;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

}
