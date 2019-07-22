package com.it.ocs.ebaySales.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class EBayBestOffersModel extends BaseModel {
	private Long id;
	private String item_id;
	private String best_offer_id;
	private Date expiration_time;
	private String best_offer_code_type;
	private String startus;
	private Integer quantity;
	private Double price;
	private String currency;
	private String email;
	private String user_id;
	private Integer feed_back_score;
	private Date registration_date;
	private String state_or_province;
	private String country_name;
	private String postal_code;
	private String is_deleted;
	private String siteid;
	private Double buyit_now_price;
	private String shipping_address;
	private String buyer_message;
	private String seller_message;
	private String enabled_flag;
	private String titel;
	private String image;
	private String productUrl;
	private String sku;
	
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getEnabled_flag() {
		return enabled_flag;
	}
	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}
	private Date update_date = new Date();
	
	
	public Date getUpdate_date() {
		update_date = new Date();
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getSeller_message() {
		return seller_message;
	}
	public void setSeller_message(String seller_message) {
		this.seller_message = seller_message;
	}
	public String getShipping_address() {
		return shipping_address;
	}
	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}
	public String getBuyer_message() {
		return buyer_message;
	}
	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getBest_offer_id() {
		return best_offer_id;
	}
	public void setBest_offer_id(String best_offer_id) {
		this.best_offer_id = best_offer_id;
	}
	
	public Date getExpiration_time() {
		return expiration_time;
	}
	public void setExpiration_time(Date expiration_time) {
		this.expiration_time = expiration_time;
	}
	public String getBest_offer_code_type() {
		return best_offer_code_type;
	}
	public void setBest_offer_code_type(String best_offer_code_type) {
		this.best_offer_code_type = best_offer_code_type;
	}
	public String getStartus() {
		return startus;
	}
	public void setStartus(String startus) {
		this.startus = startus;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public Integer getFeed_back_score() {
		return feed_back_score;
	}
	public void setFeed_back_score(Integer feed_back_score) {
		this.feed_back_score = feed_back_score;
	}
	public Date getRegistration_date() {
		return registration_date;
	}
	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}
	public String getState_or_province() {
		return state_or_province;
	}
	public void setState_or_province(String state_or_province) {
		this.state_or_province = state_or_province;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getSiteid() {
		return siteid;
	}
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	public Double getBuyit_now_price() {
		return buyit_now_price;
	}
	public void setBuyit_now_price(Double buyit_now_price) {
		this.buyit_now_price = buyit_now_price;
	}
	
	
}
