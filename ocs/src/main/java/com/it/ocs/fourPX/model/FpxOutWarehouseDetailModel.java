package com.it.ocs.fourPX.model;

public class FpxOutWarehouseDetailModel {
	
	private Long id;
	private Long parentId;
	private String sku;
	private Integer quantity;
	private String skuLabelCode;
	private String skuValue;
	
	public FpxOutWarehouseDetailModel() {
		
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSkuLabelCode() {
		return skuLabelCode;
	}
	public void setSkuLabelCode(String skuLabelCode) {
		this.skuLabelCode = skuLabelCode;
	}
	public String getSkuValue() {
		return skuValue;
	}
	public void setSkuValue(String skuValue) {
		this.skuValue = skuValue;
	}

}
