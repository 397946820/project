package com.it.ocs.task.model;

import java.sql.Timestamp;
import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class AmazonSkuHistoryModel extends BaseModel {
	private Long entity_id;
	private String platform;
	private String sku;
	private String asin;
	private String exec_date;
	private String price;
	private String special_price;
	private Long offer_total;
	private String avg_rate;
	private Long rank;
	private Long cat_rank;
	private Long if_outstock;
	private String rank_detail;
	private String reviews_total;
	private Timestamp created_at;
	private Long source_id;
	private Date last_update_date;
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
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getExec_date() {
		return exec_date;
	}
	public void setExec_date(String exec_date) {
		this.exec_date = exec_date;
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
	public Long getOffer_total() {
		return offer_total;
	}
	public void setOffer_total(Long offer_total) {
		this.offer_total = offer_total;
	}
	public String getAvg_rate() {
		return avg_rate;
	}
	public void setAvg_rate(String avg_rate) {
		this.avg_rate = avg_rate;
	}
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public Long getCat_rank() {
		return cat_rank;
	}
	public void setCat_rank(Long cat_rank) {
		this.cat_rank = cat_rank;
	}
	public Long getIf_outstock() {
		return if_outstock;
	}
	public void setIf_outstock(Long if_outstock) {
		this.if_outstock = if_outstock;
	}
	public String getRank_detail() {
		return rank_detail;
	}
	public void setRank_detail(String rank_detail) {
		this.rank_detail = rank_detail;
	}
	public String getReviews_total() {
		return reviews_total;
	}
	public void setReviews_total(String reviews_total) {
		this.reviews_total = reviews_total;
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
