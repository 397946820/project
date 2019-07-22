package com.it.ocs.task.model;

import com.it.ocs.common.model.BaseModel;

public class EbayOrderItemModel extends BaseEbayItemModel{
	
	private Long id;
	private String order_id;
	private String transaction_id;
	private String transaction_site_id;
	private String item_id;
	private String item_sku;
	private String item_title;
	private String quantity_purchased;
	private String transaction_price;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getTransaction_site_id() {
		return transaction_site_id;
	}
	public void setTransaction_site_id(String transaction_site_id) {
		this.transaction_site_id = transaction_site_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_sku() {
		return item_sku;
	}
	public void setItem_sku(String item_sku) {
		this.item_sku = item_sku;
	}
	public String getItem_title() {
		return item_title;
	}
	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}
	public String getQuantity_purchased() {
		return quantity_purchased;
	}
	public void setQuantity_purchased(String quantity_purchased) {
		this.quantity_purchased = quantity_purchased;
	}
	public String getTransaction_price() {
		return transaction_price;
	}
	public void setTransaction_price(String transaction_price) {
		this.transaction_price = transaction_price;
	}
	
	
	
}
