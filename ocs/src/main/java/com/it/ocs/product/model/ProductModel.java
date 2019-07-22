package com.it.ocs.product.model;

import com.it.ocs.common.model.BaseModel;

public class ProductModel extends BaseModel {

	private String picture;

	private String sku;

	private String category;

	private Long stock;

	private String stockWarning;

	private Double weight;

	private String sourceArea;
	
	private String updateDate;
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture == null ? null : picture.trim();
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getStockWarning() {
		return stockWarning;
	}

	public void setStockWarning(String stockWarning) {
		this.stockWarning = stockWarning == null ? null : stockWarning.trim();
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getSourceArea() {
		return sourceArea;
	}

	public void setSourceArea(String sourceArea) {
		this.sourceArea = sourceArea == null ? null : sourceArea.trim();
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}