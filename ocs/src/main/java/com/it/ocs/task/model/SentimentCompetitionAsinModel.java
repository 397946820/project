package com.it.ocs.task.model;

import java.sql.Timestamp;
import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class SentimentCompetitionAsinModel extends BaseModel {
	private Long entity_id;
	private Long product_id;
	private String asin;
	private String price;
	private String brand;
	private Long reviews;
	private String rate;
	private String images;
	private String title;
	private String category_name;
	private String cat_rank;
	private String min_cat_rank;
	private String listing;
	private String deal_type;
	private String deal_price;
	private String pici;
	private Timestamp created_at;
	private Long source_id;
	private Date last_update_date;
	public Long getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(Long entity_id) {
		this.entity_id = entity_id;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Long getReviews() {
		return reviews;
	}
	public void setReviews(Long reviews) {
		this.reviews = reviews;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCat_rank() {
		return cat_rank;
	}
	public void setCat_rank(String cat_rank) {
		this.cat_rank = cat_rank;
	}
	public String getMin_cat_rank() {
		return min_cat_rank;
	}
	public void setMin_cat_rank(String min_cat_rank) {
		this.min_cat_rank = min_cat_rank;
	}
	public String getListing() {
		return listing;
	}
	public void setListing(String listing) {
		this.listing = listing;
	}
	public String getDeal_type() {
		return deal_type;
	}
	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}
	public String getDeal_price() {
		return deal_price;
	}
	public void setDeal_price(String deal_price) {
		this.deal_price = deal_price;
	}
	public String getPici() {
		return pici;
	}
	public void setPici(String pici) {
		this.pici = pici;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Long getSource_id() {
		return source_id;
	}
	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}
	public Date getLast_update_date() {
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	
}
