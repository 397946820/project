package com.it.ocs.customerCenter.model;

import com.it.ocs.common.model.BaseModel;

public class BaseComboboxModel extends BaseModel {

	private Long id;
	
	private String country;
	
	private String result;
	
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
}
