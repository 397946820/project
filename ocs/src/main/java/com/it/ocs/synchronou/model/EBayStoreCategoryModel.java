package com.it.ocs.synchronou.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class EBayStoreCategoryModel extends BaseModel {

	
	private Long category_id;
	
	private Long parent_category_id;
	
	private String enabled_flag;
	
	private Long category_order;
	
	private String child_category;
	
	private Long marketplace_id;
	
	private String user_name;
	
	private String store_name;
	
	private String store_url;
	
	
	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_url() {
		return store_url;
	}

	public void setStore_url(String store_url) {
		this.store_url = store_url;
	}

	public Long getMarketplace_id() {
		return marketplace_id;
	}

	public void setMarketplace_id(Long marketplace_id) {
		this.marketplace_id = marketplace_id;
	}

	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public Long getParent_category_id() {
		return parent_category_id;
	}

	public void setParent_category_id(Long parent_category_id) {
		this.parent_category_id = parent_category_id;
	}

	
	public String getEnabled_flag() {
		return enabled_flag;
	}

	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}

	public Long getCategory_order() {
		return category_order;
	}

	public void setCategory_order(Long category_order) {
		this.category_order = category_order;
	}

	public String getChild_category() {
		return child_category;
	}

	public void setChild_category(String child_category) {
		this.child_category = child_category;
	}
	
	
}
