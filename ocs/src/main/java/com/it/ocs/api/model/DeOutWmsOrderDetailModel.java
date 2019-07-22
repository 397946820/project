package com.it.ocs.api.model;

/**
* @ClassName: DeOutWmsOrderDetailModel 
* @Description: 德国仓库出库单实体-明细表
* @author wgc 
* @date 2018年4月9日 下午4:26:51 
*
 */
public class DeOutWmsOrderDetailModel {
	
	/**
	 * 主键
	 */
	private long id;
	
	/**
	 * 出库单主表id
	 */
	private long parentId;
	
	/**
	 * SKU VARCHAR2(256)
	 */
	private String sku;
	
	/**
	 * SKU行号 明细ID VARCHAR2(256)
	 */
	private String itemNumber;
	
	/**
	 * SKU数量
	 */
	private String itemQty;
	
	/**
	 * sku属性
	 */
	private String skuProperty;
	
	private Double price;
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getSkuProperty() {
		return skuProperty;
	}

	public void setSkuProperty(String skuProperty) {
		this.skuProperty = skuProperty;
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
	
	private String item;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	private String actualQty;

	public String getActualQty() {
		return actualQty;
	}

	public void setActualQty(String actualQty) {
		this.actualQty = actualQty;
	}
	
}
