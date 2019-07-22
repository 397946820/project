package com.it.ocs.task.model;

import java.sql.Timestamp;

import com.it.ocs.common.model.BaseModel;

public class EbayOrderModel extends BaseModel{

	
	private Long id;
	private String account;
	private String order_id;
	private Double record_number;
	private String order_status;
	private String adjustment_amount;
	private String amount_paid;
	private String amount_saved;
	private Timestamp created_time;
	private String payment_methods;
	private String seller_email;
	private String sub_total;
	private String total;
	private String buyer_user_id;
	private Timestamp paid_time;
	private Timestamp shipped_time;
	private Long integrated_merchant_credit_card_enabled;
	private String eias_token;
	private String payment_hold_status;
	private Long is_multi_leg_shipping;
	private String seller_user_id;
	private String seller_eias_token;
	private String cancel_status;
	private String extended_order_id;
	private Long contains_ebay_plus_transaction;
	private String checkout_status;
	private String shipping_details;
	private String shipping_address;
	private String shipping_service_selected;
	private String external_transaction;
	private String transaction_array;
	private String monetary_details;
	private Timestamp last_modified_time;
	private Timestamp last_fetch_time;
	private Long has_pushed;
	private String item_id;
	
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Double getRecord_number() {
		return record_number;
	}
	public void setRecord_number(Double record_number) {
		this.record_number = record_number;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getAdjustment_amount() {
		return adjustment_amount;
	}
	public void setAdjustment_amount(String adjustment_amount) {
		this.adjustment_amount = adjustment_amount;
	}
	public String getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(String amount_paid) {
		this.amount_paid = amount_paid;
	}
	public String getAmount_saved() {
		return amount_saved;
	}
	public void setAmount_saved(String amount_saved) {
		this.amount_saved = amount_saved;
	}
	public Timestamp getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}
	public String getPayment_methods() {
		return payment_methods;
	}
	public void setPayment_methods(String payment_methods) {
		this.payment_methods = payment_methods;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getSub_total() {
		return sub_total;
	}
	public void setSub_total(String sub_total) {
		this.sub_total = sub_total;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getBuyer_user_id() {
		return buyer_user_id;
	}
	public void setBuyer_user_id(String buyer_user_id) {
		this.buyer_user_id = buyer_user_id;
	}
	public Timestamp getPaid_time() {
		return paid_time;
	}
	public void setPaid_time(Timestamp paid_time) {
		this.paid_time = paid_time;
	}
	public Timestamp getShipped_time() {
		return shipped_time;
	}
	public void setShipped_time(Timestamp shipped_time) {
		this.shipped_time = shipped_time;
	}
	public Long getIntegrated_merchant_credit_card_enabled() {
		return integrated_merchant_credit_card_enabled;
	}
	public void setIntegrated_merchant_credit_card_enabled(Long integrated_merchant_credit_card_enabled) {
		this.integrated_merchant_credit_card_enabled = integrated_merchant_credit_card_enabled;
	}
	public String getEias_token() {
		return eias_token;
	}
	public void setEias_token(String eias_token) {
		this.eias_token = eias_token;
	}
	public String getPayment_hold_status() {
		return payment_hold_status;
	}
	public void setPayment_hold_status(String payment_hold_status) {
		this.payment_hold_status = payment_hold_status;
	}
	public Long getIs_multi_leg_shipping() {
		return is_multi_leg_shipping;
	}
	public void setIs_multi_leg_shipping(Long is_multi_leg_shipping) {
		this.is_multi_leg_shipping = is_multi_leg_shipping;
	}
	public String getSeller_user_id() {
		return seller_user_id;
	}
	public void setSeller_user_id(String seller_user_id) {
		this.seller_user_id = seller_user_id;
	}
	public String getSeller_eias_token() {
		return seller_eias_token;
	}
	public void setSeller_eias_token(String seller_eias_token) {
		this.seller_eias_token = seller_eias_token;
	}
	public String getCancel_status() {
		return cancel_status;
	}
	public void setCancel_status(String cancel_status) {
		this.cancel_status = cancel_status;
	}
	public String getExtended_order_id() {
		return extended_order_id;
	}
	public void setExtended_order_id(String extended_order_id) {
		this.extended_order_id = extended_order_id;
	}
	public Long getContains_ebay_plus_transaction() {
		return contains_ebay_plus_transaction;
	}
	public void setContains_ebay_plus_transaction(Long contains_ebay_plus_transaction) {
		this.contains_ebay_plus_transaction = contains_ebay_plus_transaction;
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
	public Timestamp getLast_modified_time() {
		return last_modified_time;
	}
	public void setLast_modified_time(Timestamp last_modified_time) {
		this.last_modified_time = last_modified_time;
	}
	public Timestamp getLast_fetch_time() {
		return last_fetch_time;
	}
	public void setLast_fetch_time(Timestamp last_fetch_time) {
		this.last_fetch_time = last_fetch_time;
	}
	public Long getHas_pushed() {
		return has_pushed;
	}
	public void setHas_pushed(Long has_pushed) {
		this.has_pushed = has_pushed;
	}
	
	
	
}
