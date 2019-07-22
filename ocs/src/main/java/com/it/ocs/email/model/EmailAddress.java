package com.it.ocs.email.model;

public class EmailAddress {
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 显示名称
	 */
	private String viewName;
	
	public EmailAddress(String email,String viewName){
		this.email = email;
		this.viewName = viewName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	
	
}
