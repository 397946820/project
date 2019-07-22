package com.it.ocs.task.model;

import java.sql.Timestamp;
import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class AmazonOfferModel extends BaseModel{
	private Long entity_id;
	private String platform;
	private String asin;
	private Long total;
	private Long price;
	private String sold_by;
	private String sold_url;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Long is_delete;
	private Long source_id;
	private Date last_update_date=new Date();
	
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
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getSold_by() {
		return sold_by;
	}
	public void setSold_by(String sold_by) {
		this.sold_by = sold_by;
	}
	public String getSold_url() {
		return sold_url;
	}
	public void setSold_url(String sold_url) {
		this.sold_url = sold_url;
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
	public Long getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(Long is_delete) {
		this.is_delete = is_delete;
	}
	public Date getLast_update_date() {
		last_update_date = new Date();
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	
	
}
