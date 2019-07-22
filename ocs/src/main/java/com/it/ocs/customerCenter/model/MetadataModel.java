package com.it.ocs.customerCenter.model;

import com.it.ocs.common.model.BaseModel;

public class MetadataModel extends BaseModel {
	
	private String sku;
	
	private Long catagorieId;
	
	private String catagories;
	
	private String parentCatagories;
	
	private Long parentId;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getCatagorieId() {
		return catagorieId;
	}

	public void setCatagorieId(Long catagorieId) {
		this.catagorieId = catagorieId;
	}

	

	public String getCatagories() {
		return catagories;
	}

	public void setCatagories(String catagories) {
		this.catagories = catagories;
	}

	public String getParentCatagories() {
		return parentCatagories;
	}

	public void setParentCatagories(String parentCatagories) {
		this.parentCatagories = parentCatagories;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
	
}
