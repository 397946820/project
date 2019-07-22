package com.it.ocs.cal.vo;

import java.io.Serializable;

import com.it.ocs.excel.annotation.ExcelLink;

public class SaleDisCountReportVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 514635858542234611L;
	@ExcelLink(title="状态",index=0)
	private String disFlag;
	@ExcelLink(title="站点",index=1)
	private String site;
	@ExcelLink(title="帐号",index=2)
	private String account;
	@ExcelLink(title="金额",index=3)
	private double price;
	@ExcelLink(title="订单数量",index=4)
	private int orderqty;
	@ExcelLink(title="销售数量",index=5)
	private int saleqty;
	@ExcelLink(title="折扣额",index=6)
	private double deduction;
	@ExcelLink(title="税额",index=7)
	private double taxrate;
	@ExcelLink(title="币种",index=7)
	private String currencycode;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getOrderqty() {
		return orderqty;
	}

	public void setOrderqty(int orderqty) {
		this.orderqty = orderqty;
	}

	public int getSaleqty() {
		return saleqty;
	}

	public void setSaleqty(int saleqty) {
		this.saleqty = saleqty;
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

	public String getDisFlag() {
		return disFlag;
	}

	public void setDisFlag(String disFlag) {
		this.disFlag = disFlag;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
