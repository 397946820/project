package com.it.ocs.synchronou.model;

import java.io.Serializable;

import com.it.ocs.common.model.BaseModel;

public class WalmartAccountModel extends BaseModel implements Serializable{
	private String account;
	private String url;
	private String nextUrl;
	private String token;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNextUrl() {
		return nextUrl;
	}
	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
