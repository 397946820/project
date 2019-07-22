package com.it.ocs.paymentManagement.model;

import com.it.ocs.common.model.BaseModel;

public class PayPalRefundModel extends BaseModel {

	
	private Double refundAmount;    //金额
	
	private String currency;   //币种
	
	private String transaction; //交易号
	
	private String payPalAccount;//PayPal账号

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getPayPalAccount() {
		return payPalAccount;
	}

	public void setPayPalAccount(String payPalAccount) {
		this.payPalAccount = payPalAccount;
	}
	
	
}
