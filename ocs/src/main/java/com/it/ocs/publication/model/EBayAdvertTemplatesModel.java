package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class EBayAdvertTemplatesModel extends BaseModel {
	private String name;
	private Long site_id;
	private String ebay_account;
	private Long line_product;
	private String enabled_flag;
	private String url;
	private Long pid_id;
	private String product_url;
	
	private String img_name;
	
	
	public String getImg_name() {
		return img_name;
	}
	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}
	private String product_name;
	
	
	
	
	
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_url() {
		return product_url;
	}
	public void setProduct_url(String product_url) {
		this.product_url = product_url;
	}
	public Long getPid_id() {
		return pid_id;
	}
	public void setPid_id(Long pid_id) {
		this.pid_id = pid_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public Long getSite_id() {
		return site_id;
	}
	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}
	public String getEbay_account() {
		return ebay_account;
	}
	public void setEbay_account(String ebay_account) {
		this.ebay_account = ebay_account;
	}
	public Long getLine_product() {
		return line_product;
	}
	public void setLine_product(Long line_product) {
		this.line_product = line_product;
	}
	public String getEnabled_flag() {
		return enabled_flag;
	}
	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}
	
}
