package com.it.ocs.paymentManagement.model;

import com.it.ocs.common.model.BaseModel;

public class PayPalModel extends BaseModel {
	private String account;
	private String access_token;
	private String credit_card_number;
	private String credit_card_type;
	private String user_name;
	private String password;
	private String phone_number;
	private String account_type;
	private String country;
	private String client_id;
	private String secret;
	private Long ebay_id;
	private String signature;
	private String email_id;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getCredit_card_number() {
		return credit_card_number;
	}
	public void setCredit_card_number(String credit_card_number) {
		this.credit_card_number = credit_card_number;
	}
	public String getCredit_card_type() {
		return credit_card_type;
	}
	public void setCredit_card_type(String credit_card_type) {
		this.credit_card_type = credit_card_type;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public Long getEbay_id() {
		return ebay_id;
	}
	public void setEbay_id(Long ebay_id) {
		this.ebay_id = ebay_id;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	
	
}
