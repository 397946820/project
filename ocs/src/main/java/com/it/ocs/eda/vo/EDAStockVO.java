package com.it.ocs.eda.vo;

import java.io.Serializable;

public class EDAStockVO implements Serializable{

	private Integer id;
	private String sku;
	private String warehouseId;
	private String warehouseName;
	private Integer qty;
	private Integer totalInventory;
	private Integer forOutboundInventory;
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
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getTotalInventory() {
		return totalInventory;
	}
	public void setTotalInventory(Integer totalInventory) {
		this.totalInventory = totalInventory;
	}
	public Integer getForOutboundInventory() {
		return forOutboundInventory;
	}
	public void setForOutboundInventory(Integer forOutboundInventory) {
		this.forOutboundInventory = forOutboundInventory;
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
