package com.it.ocs.cal.vo;

import java.io.Serializable;

import com.it.ocs.excel.annotation.ExcelLink;

public class SaleCatCountReportVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1430045244976378112L;
	@ExcelLink(title="分类",index=0)
	private String productType;// 产品分类
	@ExcelLink(title="帐号",index=1)
	private String account;// 帐号
	@ExcelLink(title="站点",index=2)
	private String site;// 站点
	@ExcelLink(title="金额",index=3)
	private double price;// 金额
	@ExcelLink(title="订单数量",index=4)
	private int orderQty;// 卖的订单数量
	@ExcelLink(title="销售数量",index=5)
	private int saleQty;// 卖的产品数量
	@ExcelLink(title="折扣额",index=6)
	private double deduction;// 折扣额
	@ExcelLink(title="税额",index=7)
	private double taxrate;// 税额
	@ExcelLink(title="币种",index=8)
	private String currencycode;// 币种

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public int getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(int saleQty) {
		this.saleQty = saleQty;
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

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

}
