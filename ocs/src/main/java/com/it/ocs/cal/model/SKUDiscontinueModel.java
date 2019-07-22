package com.it.ocs.cal.model;

public class SKUDiscontinueModel {
	private Integer id;
	private String sku;
	private String platform;
	private String countryId;
	private Integer isDis;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public Integer getIsDis() {
		return isDis;
	}
	public void setIsDis(Integer isDis) {
		this.isDis = isDis;
	}
	
	
}
