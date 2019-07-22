package com.it.ocs.task.model;

import java.sql.Timestamp;
import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class AmazonReportsBestsellerModel extends BaseModel {
	private Long entity_id;
	private String platform;
	private String url;
	private String category_name;
	private String category_name_cn;
	private String top_hundred;
	private String top_twenty;
	private String top_ten;
	private String top_five;
	private String brand_distribution;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String url_product;
	private String category_ranking;
	private String average_rate;
	private String reviews_total;
	private String asin;
	private String brand_name;
	private String price;
	private Long bestseller_created;
	private Long bestseller_product_created;
	private Timestamp bestseller_created_at;
	private Timestamp bestseller_product_created_at;
	private String top_asin;
	private String pic;
	private Date last_update_date = new Date();
	private Long source_id;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCategory_name_cn() {
		return category_name_cn;
	}
	public void setCategory_name_cn(String category_name_cn) {
		this.category_name_cn = category_name_cn;
	}
	public String getTop_hundred() {
		return top_hundred;
	}
	public void setTop_hundred(String top_hundred) {
		this.top_hundred = top_hundred;
	}
	public String getTop_twenty() {
		return top_twenty;
	}
	public void setTop_twenty(String top_twenty) {
		this.top_twenty = top_twenty;
	}
	public String getTop_ten() {
		return top_ten;
	}
	public void setTop_ten(String top_ten) {
		this.top_ten = top_ten;
	}
	public String getTop_five() {
		return top_five;
	}
	public void setTop_five(String top_five) {
		this.top_five = top_five;
	}
	public String getBrand_distribution() {
		return brand_distribution;
	}
	public void setBrand_distribution(String brand_distribution) {
		this.brand_distribution = brand_distribution;
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
	public String getUrl_product() {
		return url_product;
	}
	public void setUrl_product(String url_product) {
		this.url_product = url_product;
	}
	public String getCategory_ranking() {
		return category_ranking;
	}
	public void setCategory_ranking(String category_ranking) {
		this.category_ranking = category_ranking;
	}
	public String getAverage_rate() {
		return average_rate;
	}
	public void setAverage_rate(String average_rate) {
		this.average_rate = average_rate;
	}
	public String getReviews_total() {
		return reviews_total;
	}
	public void setReviews_total(String reviews_total) {
		this.reviews_total = reviews_total;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Long getBestseller_created() {
		return bestseller_created;
	}
	public void setBestseller_created(Long bestseller_created) {
		this.bestseller_created = bestseller_created;
	}
	public Long getBestseller_product_created() {
		return bestseller_product_created;
	}
	public void setBestseller_product_created(Long bestseller_product_created) {
		this.bestseller_product_created = bestseller_product_created;
	}
	public Timestamp getBestseller_created_at() {
		return bestseller_created_at;
	}
	public void setBestseller_created_at(Timestamp bestseller_created_at) {
		this.bestseller_created_at = bestseller_created_at;
	}
	public Timestamp getBestseller_product_created_at() {
		return bestseller_product_created_at;
	}
	public void setBestseller_product_created_at(Timestamp bestseller_product_created_at) {
		this.bestseller_product_created_at = bestseller_product_created_at;
	}
	public String getTop_asin() {
		return top_asin;
	}
	public void setTop_asin(String top_asin) {
		this.top_asin = top_asin;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Date getLast_update_date() {
		last_update_date = new Date();
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
	
	
}
