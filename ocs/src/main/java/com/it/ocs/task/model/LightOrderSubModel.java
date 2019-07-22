package com.it.ocs.task.model;

import com.it.ocs.common.model.BaseModel;

public class LightOrderSubModel extends BaseModel {
	
	private Long parent_id;
	
	private String customer_note;
	
	private String order_id;
	
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public String getCustomer_note() {
		return customer_note;
	}
	public void setCustomer_note(String customer_note) {
		this.customer_note = customer_note;
	}
	
}
