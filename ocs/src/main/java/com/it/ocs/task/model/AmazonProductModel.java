package com.it.ocs.task.model;

import java.sql.Timestamp;
import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class AmazonProductModel extends BaseModel {
	private Long entity_id;
	private Long reviews_total;
	private String asin;
	private Long amazon_stock;
	private String average_rate;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String product_url;
	private String sku;
	private Long has_new;
	private Long status;
	private String country;
	private String price;
	private String special_price;
	private String image;
	private Long offer_total;
	private String star_all;
	private String bullet_points;
	private Long has_update;
	private Long important_level;
	private Long if_continue;
	private Long type;
	private String demand_score;
	private Long if_invite;
	private Long invite_count;
	private Date last_update_date;
	private Long source_id;
	private String description;
	private String details;
	private String short_description;
	private String category;
	private String name;
	
	public Long getSource_id() {
		return source_id;
	}
	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}
	public Long getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(Long entity_id) {
		this.entity_id = entity_id;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getReviews_total() {
		return reviews_total;
	}
	public void setReviews_total(Long reviews_total) {
		this.reviews_total = reviews_total;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public Long getAmazon_stock() {
		return amazon_stock;
	}
	public void setAmazon_stock(Long amazon_stock) {
		this.amazon_stock = amazon_stock;
	}
	public String getAverage_rate() {
		return average_rate;
	}
	public void setAverage_rate(String average_rate) {
		this.average_rate = average_rate;
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
	public String getProduct_url() {
		return product_url;
	}
	public void setProduct_url(String product_url) {
		this.product_url = product_url;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Long getHas_new() {
		return has_new;
	}
	public void setHas_new(Long has_new) {
		this.has_new = has_new;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSpecial_price() {
		return special_price;
	}
	public void setSpecial_price(String special_price) {
		this.special_price = special_price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getOffer_total() {
		return offer_total;
	}
	public void setOffer_total(Long offer_total) {
		this.offer_total = offer_total;
	}
	public String getStar_all() {
		return star_all;
	}
	public void setStar_all(String star_all) {
		this.star_all = star_all;
	}
	public String getBullet_points() {
		return bullet_points;
	}
	public void setBullet_points(String bullet_points) {
		this.bullet_points = bullet_points;
	}
	public String getShort_description() {
		return short_description;
	}
	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Long getHas_update() {
		return has_update;
	}
	public void setHas_update(Long has_update) {
		this.has_update = has_update;
	}
	public Long getImportant_level() {
		return important_level;
	}
	public void setImportant_level(Long important_level) {
		this.important_level = important_level;
	}
	public Long getIf_continue() {
		return if_continue;
	}
	public void setIf_continue(Long if_continue) {
		this.if_continue = if_continue;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getDemand_score() {
		return demand_score;
	}
	public void setDemand_score(String demand_score) {
		this.demand_score = demand_score;
	}
	public Long getIf_invite() {
		return if_invite;
	}
	public void setIf_invite(Long if_invite) {
		this.if_invite = if_invite;
	}
	public Long getInvite_count() {
		return invite_count;
	}
	public void setInvite_count(Long invite_count) {
		this.invite_count = invite_count;
	}
	public Date getLast_update_date() {
		last_update_date= new Date();
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	
}
