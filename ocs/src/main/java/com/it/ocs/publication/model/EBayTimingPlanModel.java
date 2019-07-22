package com.it.ocs.publication.model;

import java.util.Date;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;

import com.it.ocs.common.model.BaseModel;

public class EBayTimingPlanModel extends BaseModel {
	

	private Integer template_id;
	private String template_name;
	private Integer site_id;
	private Date first_date;
	private Date first_site_date;
	private Integer create_by;
	private Date create_date;
	private Integer update_by;
	private Date update_date;
	private String enabled_flag;
	private Integer is_success;
	private Date real_publication_date;
	private Date real_end_date;
	private String publication_info;
	private String sku ;
	private String productTitle;//主标题
	private String publicationType;//刊登类型
	private String ebayAccount;
	private String ebayImages;
	private String error_info;
	private String itemId;
	private String ebayProductURL;
	private Long new_template_id;
	
	
	
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getEbayProductURL() {
		return ebayProductURL;
	}
	public void setEbayProductURL(String ebayProductURL) {
		this.ebayProductURL = ebayProductURL;
	}
	public Long getNew_template_id() {
		return new_template_id;
	}
	public void setNew_template_id(Long new_template_id) {
		this.new_template_id = new_template_id;
	}
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getPublicationType() {
		return publicationType;
	}
	public void setPublicationType(String publicationType) {
		this.publicationType = publicationType;
	}
	public String getEbayAccount() {
		return ebayAccount;
	}
	public void setEbayAccount(String ebayAccount) {
		this.ebayAccount = ebayAccount;
	}
	public String getEbayImages() {
		return ebayImages;
	}
	public void setEbayImages(String ebayImages) {
		this.ebayImages = ebayImages;
	}
	public Integer getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Integer template_id) {
		this.template_id = template_id;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public Integer getSite_id() {
		return site_id;
	}
	public void setSite_id(Integer site_id) {
		this.site_id = site_id;
	}
	public Date getFirst_date() {
		return first_date;
	}
	public void setFirst_date(Date first_date) {
		this.first_date = first_date;
	}
	public Date getFirst_site_date() {
		return first_site_date;
	}
	public void setFirst_site_date(Date first_site_date) {
		this.first_site_date = first_site_date;
	}
	public Integer getCreate_by() {
		return create_by;
	}
	public void setCreate_by(Integer create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Integer getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(Integer update_by) {
		this.update_by = update_by;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getEnabled_flag() {
		return enabled_flag;
	}
	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}
	public Integer getIs_success() {
		return is_success;
	}
	public void setIs_success(Integer is_success) {
		this.is_success = is_success;
	}
	public Date getReal_publication_date() {
		return real_publication_date;
	}
	public void setReal_publication_date(Date real_publication_date) {
		this.real_publication_date = real_publication_date;
	}
	public Date getReal_end_date() {
		return real_end_date;
	}
	public void setReal_end_date(Date real_end_date) {
		this.real_end_date = real_end_date;
	}
	public String getPublication_info() {
		return publication_info;
	}
	public void setPublication_info(String publication_info) {
		this.publication_info = publication_info;
	}
	
	

}
