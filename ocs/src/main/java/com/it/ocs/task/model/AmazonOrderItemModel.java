package com.it.ocs.task.model;

import java.sql.Timestamp;

import com.it.ocs.common.model.BaseModel;

public class AmazonOrderItemModel extends BaseAmazonItemModel{
	
	private Long id;
	private Long parent_id;
	private String amazon_item_id;
	private String title;
	private String asin;
	private String sku;
	private Double price;
	private Double qty;
	private Double shipping_amount;
	private Double shipping_discount;
	private Double tax;
	private Double gift_price;
	private String promotion_id;
	private Double promotion_discount;
	private Double shipping_tax;
	private String condition_id;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Long push_status;
	
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public String getAmazon_item_id() {
		return amazon_item_id;
	}
	public void setAmazon_item_id(String amazon_item_id) {
		this.amazon_item_id = amazon_item_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getShipping_amount() {
		return shipping_amount;
	}
	public void setShipping_amount(Double shipping_amount) {
		this.shipping_amount = shipping_amount;
	}
	public Double getShipping_discount() {
		return shipping_discount;
	}
	public void setShipping_discount(Double shipping_discount) {
		this.shipping_discount = shipping_discount;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getGift_price() {
		return gift_price;
	}
	public void setGift_price(Double gift_price) {
		this.gift_price = gift_price;
	}
	public String getPromotion_id() {
		return promotion_id;
	}
	public void setPromotion_id(String promotion_id) {
		this.promotion_id = promotion_id;
	}
	public Double getPromotion_discount() {
		return promotion_discount;
	}
	public void setPromotion_discount(Double promotion_discount) {
		this.promotion_discount = promotion_discount;
	}
	public Double getShipping_tax() {
		return shipping_tax;
	}
	public void setShipping_tax(Double shipping_tax) {
		this.shipping_tax = shipping_tax;
	}
	public String getCondition_id() {
		return condition_id;
	}
	public void setCondition_id(String condition_id) {
		this.condition_id = condition_id;
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
	public Long getPush_status() {
		return push_status;
	}
	public void setPush_status(Long push_status) {
		this.push_status = push_status;
	}
	
	
}
