package com.it.ocs.transportSetting.model;

public class InternationalRuleModel {

	// 运输名称
	private String ctype;
	// 运费
	private Double freight;
	// 是否免费
	private String isFree;
	// 额外加收
	private Double added;
	// 全球
	private String global;
	// 国家或者地区
	private String country;

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public Double getAdded() {
		return added;
	}

	public void setAdded(Double added) {
		this.added = added;
	}

	public String getGlobal() {
		return global;
	}

	public void setGlobal(String global) {
		this.global = global;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
