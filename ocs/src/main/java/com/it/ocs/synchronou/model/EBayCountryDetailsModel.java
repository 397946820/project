package com.it.ocs.synchronou.model;

import com.it.ocs.common.model.BaseModel;

public class EBayCountryDetailsModel extends BaseModel{

	private String country;
	private String description;
	private Long detail_version;
	private String update_time;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	
}
