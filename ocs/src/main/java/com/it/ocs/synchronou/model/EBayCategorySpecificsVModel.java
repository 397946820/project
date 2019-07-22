package com.it.ocs.synchronou.model;

import com.it.ocs.common.model.BaseModel;

public class EBayCategorySpecificsVModel extends BaseModel {

	private String value;
	private Long category_id;
	private Long marketplace_id;
	private String value_type;
	private String min_values;
	private String max_values;
	private String variation_specifics;
	private String selectionMode;
	
	
	public String getSelectionMode() {
		return selectionMode;
	}
	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}
	public String getValue_type() {
		return value_type;
	}
	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}
	public String getMin_values() {
		return min_values;
	}
	public void setMin_values(String min_values) {
		this.min_values = min_values;
	}
	public String getMax_values() {
		return max_values;
	}
	public void setMax_values(String max_values) {
		this.max_values = max_values;
	}
	
	public String getVariation_specifics() {
		return variation_specifics;
	}
	public void setVariation_specifics(String variation_specifics) {
		this.variation_specifics = variation_specifics;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
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
	
}
