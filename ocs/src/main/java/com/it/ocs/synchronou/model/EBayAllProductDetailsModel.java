package com.it.ocs.synchronou.model;

import com.it.ocs.common.model.BaseModel;

public class EBayAllProductDetailsModel extends BaseModel{

	private String country;
	private String currency;
	private String item_id;
	private String start_price;
	private String start_time;
	private String end_time;
	private String view_item_url;
	private String listing_duration;
	private String listing_type;
	private String location;
	private String title;
	private String shippingtype;
	private String site;
	private String picture_url;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getStart_price() {
		return start_price;
	}
	public void setStart_price(String start_price) {
		this.start_price = start_price;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getView_item_url() {
		return view_item_url;
	}
	public void setView_item_url(String view_item_url) {
		this.view_item_url = view_item_url;
	}
	public String getListing_duration() {
		return listing_duration;
	}
	public void setListing_duration(String listing_duration) {
		this.listing_duration = listing_duration;
	}
	public String getListing_type() {
		return listing_type;
	}
	public void setListing_type(String listing_type) {
		this.listing_type = listing_type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShippingtype() {
		return shippingtype;
	}
	public void setShippingtype(String shippingtype) {
		this.shippingtype = shippingtype;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	
}
