package com.it.ocs.task.model;

import com.it.ocs.common.model.BaseModel;

public class EbayOrderSubModel extends BaseModel {
	
	private Long parent_id;
	
	private String checkout_status;
	
	private String shipping_details;
	
	private String shipping_address;
	
	private String shipping_service_selected;
	
	private String external_transaction;
	
	private String transaction_array;
	
	private String monetary_details;
	
	private String order_id ;
	

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

	public String getCheckout_status() {
		return checkout_status;
	}

	public void setCheckout_status(String checkout_status) {
		this.checkout_status = checkout_status;
	}

	public String getShipping_details() {
		return shipping_details;
	}

	public void setShipping_details(String shipping_details) {
		this.shipping_details = shipping_details;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public String getShipping_service_selected() {
		return shipping_service_selected;
	}

	public void setShipping_service_selected(String shipping_service_selected) {
		this.shipping_service_selected = shipping_service_selected;
	}

	public String getExternal_transaction() {
		return external_transaction;
	}

	public void setExternal_transaction(String external_transaction) {
		this.external_transaction = external_transaction;
	}

	public String getTransaction_array() {
		return transaction_array;
	}

	public void setTransaction_array(String transaction_array) {
		this.transaction_array = transaction_array;
	}

	public String getMonetary_details() {
		return monetary_details;
	}

	public void setMonetary_details(String monetary_details) {
		this.monetary_details = monetary_details;
	}
	
	
	
}
