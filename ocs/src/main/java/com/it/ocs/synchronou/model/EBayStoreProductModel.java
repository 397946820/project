package com.it.ocs.synchronou.model;

import java.sql.Timestamp;

import com.it.ocs.common.model.BaseModel;

public class EBayStoreProductModel extends BaseModel {
	private String item_id;
	private String global_id;
	private String enabled_flag;
	private Long create_by;
	private Timestamp creation_date ;
	private Long last_update_by;
	private Timestamp last_update_date = new Timestamp(System.currentTimeMillis());
	private String item_search_url;
	private String store_name;
	
	
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getItem_search_url() {
		return item_search_url;
	}
	public void setItem_search_url(String item_search_url) {
		this.item_search_url = item_search_url;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getGlobal_id() {
		return global_id;
	}
	public void setGlobal_id(String global_id) {
		this.global_id = global_id;
	}
	public String getEnabled_flag() {
		return enabled_flag;
	}
	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}
	public Long getCreate_by() {
		return create_by;
	}
	public void setCreate_by(Long create_by) {
		this.create_by = create_by;
	}
	public Timestamp getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Timestamp creation_date) {
		this.creation_date = creation_date;
	}
	public Long getLast_update_by() {
		return last_update_by;
	}
	public void setLast_update_by(Long last_update_by) {
		this.last_update_by = last_update_by;
	}
	public Timestamp getLast_update_date() {
		return last_update_date;
	}
	public void setLast_update_date(Timestamp last_update_date) {
		this.last_update_date = last_update_date;
	}
	
	
}
