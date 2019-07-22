package com.it.ocs.api.vo;

import java.util.Date;

import com.it.ocs.api.model.DeOutWmsOrderMainModel;

/**
 * @author zhouyancheng
 *
 */
public class OutOrderVO extends DeOutWmsOrderMainModel implements java.io.Serializable {
	private static final long serialVersionUID = 7624660503246357138L;
	
	private Date createdDate;
	private Date updateDate;
	private long detailId;
	private String sku;
	private String itemNumber;
	private String itemQty;
	private String skuProperty;
	private String item;
	private String actualQty;
	private Double price;

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public long getDetailId() {
		return detailId;
	}
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getItemQty() {
		return itemQty;
	}
	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}
	public String getSkuProperty() {
		return skuProperty;
	}
	public void setSkuProperty(String skuProperty) {
		this.skuProperty = skuProperty;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getActualQty() {
		return actualQty;
	}
	public void setActualQty(String actualQty) {
		this.actualQty = actualQty;
	}
}
