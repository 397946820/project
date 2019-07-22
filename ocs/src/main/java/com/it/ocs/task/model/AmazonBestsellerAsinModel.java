package com.it.ocs.task.model;

import java.sql.Timestamp;
import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class AmazonBestsellerAsinModel extends BaseModel{

	private Long entity_id;
	private Long bestseller_id;
	private String platform;
	private String category_name;
	private String asin;
	private String sku;
	private Long own;
	private String url;
	private String brand;
	private String price;
	private String avg_rate;
	private String reviews_total;
	private Long rank;
	private String title;
	private String images;
	private String pici;
	private Timestamp update_date;
	private String sort_info;
	private Timestamp date;
	private Long source_id;
	private Date last_update_date=new Date();
	
	
	
	public Date getLast_update_date() {
		last_update_date=new Date();
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	public Long getSource_id() {
		return source_id;
	}
	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public Long getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(Long entity_id) {
		this.entity_id = entity_id;
	}
	public Long getBestseller_id() {
		return bestseller_id;
	}
	public void setBestseller_id(Long bestseller_id) {
		this.bestseller_id = bestseller_id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
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
	public Long getOwn() {
		return own;
	}
	public void setOwn(Long own) {
		this.own = own;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAvg_rate() {
		return avg_rate;
	}
	public void setAvg_rate(String avg_rate) {
		this.avg_rate = avg_rate;
	}
	public String getReviews_total() {
		return reviews_total;
	}
	public void setReviews_total(String reviews_total) {
		this.reviews_total = reviews_total;
	}
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getPici() {
		return pici;
	}
	public void setPici(String pici) {
		this.pici = pici;
	}
	public Timestamp getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}
	public String getSort_info() {
		return sort_info;
	}
	public void setSort_info(String sort_info) {
		this.sort_info = sort_info;
	}
	
}
