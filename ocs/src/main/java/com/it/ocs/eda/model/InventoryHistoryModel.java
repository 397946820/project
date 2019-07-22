package com.it.ocs.eda.model;

import java.io.Serializable;

import com.it.ocs.excel.annotation.ExcelLink;

import net.sf.json.JSONObject;

public class InventoryHistoryModel implements Serializable {
	@ExcelLink(title="ID",index=0)
	private Integer Id;
	
	@ExcelLink(title="Record type",index=1)
	private String recordType;
	
	@ExcelLink(title="SKU",index=2)
	private String sku;
	
	@ExcelLink(title="Product Name",index=3)
	private String productName;
	
	@ExcelLink(title="Bill Num",index=4)
	private String billNum;
	
	@ExcelLink(title="QTY",index=5)
	private Integer qty;
	
	@ExcelLink(title="Change Date",index=6)
	private String changeDate;
	
	@ExcelLink(title="Order ID",index=7)
	private String orderId;
	
	@ExcelLink(title="EDA Order Create Date",index=8)
	private String edaOrderCreateDate;
	
	@ExcelLink(title="Warehouse",index=9)
	private Integer warehouseId;
	
	@ExcelLink(title="Synchronous Date",index=10)
	private String syncDate;
	
	public static InventoryHistoryModel createInventoryHistoryModel(JSONObject json){
		InventoryHistoryModel ih = new InventoryHistoryModel();
		ih.setInventoryHistoryModel(json);
		return ih;
	}
	
	public void setInventoryHistoryModel(JSONObject json){
		this.recordType = json.getString("recordType");
		this.sku = json.getString("sku");
		this.productName = json.getString("productName");
		this.billNum = json.getString("billNum");
		this.qty = json.getInt("qty");
		this.changeDate = json.getString("changeDate");
		this.warehouseId = json.getInt("warehouseId");
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getRecordType() {
		if("null".equals(recordType)){
			return "";
		}
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBillNum() {
		if("null".equals(billNum)){
			return "";
		}
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getSyncDate() {
		return syncDate;
	}
	public void setSyncDate(String syncDate) {
		this.syncDate = syncDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEdaOrderCreateDate() {
		return edaOrderCreateDate;
	}

	public void setEdaOrderCreateDate(String edaOrderCreateDate) {
		this.edaOrderCreateDate = edaOrderCreateDate;
	}
	
	
}
