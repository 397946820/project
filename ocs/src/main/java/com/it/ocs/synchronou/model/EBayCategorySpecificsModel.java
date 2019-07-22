package com.it.ocs.synchronou.model;

import com.it.ocs.common.model.BaseModel;

public class EBayCategorySpecificsModel extends BaseModel {

	private Long category_id;
	private Long marketplace_id;
	private String validation_rules;
	private String helptext;
	private String helpurl;
	public Long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}
	public Long getMarketplace_id() {
		return marketplace_id;
	}
	public void setMarketplace_id(Long marketplace_id) {
		this.marketplace_id = marketplace_id;
	}
	public String getValidation_rules() {
		return validation_rules;
	}
	public void setValidation_rules(String validation_rules) {
		this.validation_rules = validation_rules;
	}
	public String getHelptext() {
		return helptext;
	}
	public void setHelptext(String helptext) {
		this.helptext = helptext;
	}
	public String getHelpurl() {
		return helpurl;
	}
	public void setHelpurl(String helpurl) {
		this.helpurl = helpurl;
	}
	
}
