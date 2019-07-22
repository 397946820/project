package com.it.ocs.cal.model;

import com.it.ocs.cal.utils.ExcelRead;

public class TaximeterModel extends ImportModel {

	@ExcelRead(title = "发货日期")
	private String deliveryTime;

	@ExcelRead(title = "到货日期")
	private String arrivalTime;

	@ExcelRead(title = "SKU")
	private String sku;

	@ExcelRead(title = "摘要")
	private String description;

	@ExcelRead(title = "发货/到货批次")
	private String batch;

	@ExcelRead(title = "运输方式")
	private String shippingtype;

	@ExcelRead(title = "国家")
	private String country;

	@ExcelRead(title = "发货数量")
	private Long qty;

	@ExcelRead(title = "采购单价")
	private String price;

	@ExcelRead(title = "采购加权单价")
	private Double weightedprice;

	@ExcelRead(title = "最终成本AF")
	private String af;

	@ExcelRead(title = "最终成本SF")
	private String sf;

	@ExcelRead(title = "最终成本CO")
	private String co;

	@ExcelRead(title = "实际销量")
	private Long actualSales;

	@ExcelRead(title = "当日结存")
	private Long balance;

	@ExcelRead(title = "当日在途")
	private Long onpassage;

	@ExcelRead(title = "库存金额")
	private Double amount;

	@ExcelRead(title = "实际库存")
	private Long balanceamount;

	@ExcelRead(title = "ERP库存")
	private Long repertory;

	@ExcelRead(title = "结存库存单价")
	private Double balanceprice;

	@ExcelRead(title = "15%利润")
	private Double oprofit;

	@ExcelRead(title = "22%利润")
	private Double tprofit;

	// 采购币种
	private String currencyCode;

	// 采购成本
	private Double unitPrice;

	// 用户的id
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getShippingtype() {
		return shippingtype;
	}

	public void setShippingtype(String shippingtype) {
		this.shippingtype = shippingtype;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Double getWeightedprice() {
		return weightedprice;
	}

	public void setWeightedprice(Double weightedprice) {
		this.weightedprice = weightedprice;
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

	public Long getActualSales() {
		return actualSales;
	}

	public void setActualSales(Long actualSales) {
		this.actualSales = actualSales;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Long getOnpassage() {
		return onpassage;
	}

	public void setOnpassage(Long onpassage) {
		this.onpassage = onpassage;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getBalanceamount() {
		return balanceamount;
	}

	public void setBalanceamount(Long balanceamount) {
		this.balanceamount = balanceamount;
	}

	public Long getRepertory() {
		return repertory;
	}

	public void setRepertory(Long repertory) {
		this.repertory = repertory;
	}

	public Double getBalanceprice() {
		return balanceprice;
	}

	public void setBalanceprice(Double balanceprice) {
		this.balanceprice = balanceprice;
	}

	public Double getOprofit() {
		return oprofit;
	}

	public void setOprofit(Double oprofit) {
		this.oprofit = oprofit;
	}

	public Double getTprofit() {
		return tprofit;
	}

	public void setTprofit(Double tprofit) {
		this.tprofit = tprofit;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

}