package com.it.ocs.synchronou.model;

import com.it.ocs.common.model.BaseModel;

public class EBaySiteDetailsModel extends BaseModel {
	
	private String site;
	private Long site_id;
	private Long detail_version;
	private String update_time;
	private String enabled_flag;
	private String url;
	private String abbreviation;
	private String currency;
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Long getSite_id() {
		return site_id;
	}
	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}
	public Long getDetail_version() {
		return detail_version;
	}
	public void setDetail_version(Long detail_version) {
		this.detail_version = detail_version;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getEnabled_flag() {
		return enabled_flag;
	}
	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
