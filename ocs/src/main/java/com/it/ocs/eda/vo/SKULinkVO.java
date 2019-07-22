package com.it.ocs.eda.vo;

import java.io.Serializable;

public class SKULinkVO implements Serializable{
	private Integer id;
	private String sku;
	private String pSku;
	private String qty;
	private String createDate;
	private String updateDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getpSku() {
		return pSku;
	}
	public void setpSku(String pSku) {
		this.pSku = pSku;
	}

	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
