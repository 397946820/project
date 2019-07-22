package com.it.ocs.task.model;

import java.sql.Timestamp;

import com.it.ocs.common.model.BaseModel;

public class LightOrderModel extends BaseModel{
	
	private Long entity_id;
	private String platform;
	private String order_id;
	private String state;
	private String status;
	private Double discount_amount;
	private Double subtotal;
	private Double grand_total;
	private Double shipping_amount;
	private Double tax_amount;
	private Double total_canceled;
	private Double total_invoiced;
	private Double total_paid;
	private Double total_qty_ordered;
	private Double total_refunded;
	private String gift_message;
	private Double weight;
	private String customer_email;
	private String customer_firstname;
	private String customer_lastname;
	private String customer_middlename;
	private String global_currency_code;
	private String order_currency_code;
	private String remote_ip;
	private String shipping_method;
	private String customer_note;
	private Timestamp light_created_at;
	private Timestamp paid_time;
	private Timestamp light_updated_at;
	private Timestamp created_at;
	private Timestamp updated_at;
	
	public Long getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(Long entity_id) {
		this.entity_id = entity_id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(Double discount_amount) {
		this.discount_amount = discount_amount;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public Double getGrand_total() {
		return grand_total;
	}
	public void setGrand_total(Double grand_total) {
		this.grand_total = grand_total;
	}
	public Double getShipping_amount() {
		return shipping_amount;
	}
	public void setShipping_amount(Double shipping_amount) {
		this.shipping_amount = shipping_amount;
	}
	public Double getTax_amount() {
		return tax_amount;
	}
	public void setTax_amount(Double tax_amount) {
		this.tax_amount = tax_amount;
	}
	public Double getTotal_canceled() {
		return total_canceled;
	}
	public void setTotal_canceled(Double total_canceled) {
		this.total_canceled = total_canceled;
	}
	public Double getTotal_invoiced() {
		return total_invoiced;
	}
	public void setTotal_invoiced(Double total_invoiced) {
		this.total_invoiced = total_invoiced;
	}
	public Double getTotal_paid() {
		return total_paid;
	}
	public void setTotal_paid(Double total_paid) {
		this.total_paid = total_paid;
	}
	public Double getTotal_qty_ordered() {
		return total_qty_ordered;
	}
	public void setTotal_qty_ordered(Double total_qty_ordered) {
		this.total_qty_ordered = total_qty_ordered;
	}
	public Double getTotal_refunded() {
		return total_refunded;
	}
	public void setTotal_refunded(Double total_refunded) {
		this.total_refunded = total_refunded;
	}
	public String getGift_message() {
		return gift_message;
	}
	public void setGift_message(String gift_message) {
		this.gift_message = gift_message;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public String getCustomer_firstname() {
		return customer_firstname;
	}
	public void setCustomer_firstname(String customer_firstname) {
		this.customer_firstname = customer_firstname;
	}
	public String getCustomer_lastname() {
		return customer_lastname;
	}
	public void setCustomer_lastname(String customer_lastname) {
		this.customer_lastname = customer_lastname;
	}
	public String getCustomer_middlename() {
		return customer_middlename;
	}
	public void setCustomer_middlename(String customer_middlename) {
		this.customer_middlename = customer_middlename;
	}
	public String getGlobal_currency_code() {
		return global_currency_code;
	}
	public void setGlobal_currency_code(String global_currency_code) {
		this.global_currency_code = global_currency_code;
	}
	public String getOrder_currency_code() {
		return order_currency_code;
	}
	public void setOrder_currency_code(String order_currency_code) {
		this.order_currency_code = order_currency_code;
	}
	public String getRemote_ip() {
		return remote_ip;
	}
	public void setRemote_ip(String remote_ip) {
		this.remote_ip = remote_ip;
	}
	public String getShipping_method() {
		return shipping_method;
	}
	public void setShipping_method(String shipping_method) {
		this.shipping_method = shipping_method;
	}
	public String getCustomer_note() {
		return customer_note;
	}
	public void setCustomer_note(String customer_note) {
		this.customer_note = customer_note;
	}
	public Timestamp getLight_created_at() {
		return light_created_at;
	}
	public void setLight_created_at(Timestamp light_created_at) {
		this.light_created_at = light_created_at;
	}
	public Timestamp getPaid_time() {
		return paid_time;
	}
	public void setPaid_time(Timestamp paid_time) {
		this.paid_time = paid_time;
	}
	public Timestamp getLight_updated_at() {
		return light_updated_at;
	}
	public void setLight_updated_at(Timestamp light_updated_at) {
		this.light_updated_at = light_updated_at;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	
	
	
}
