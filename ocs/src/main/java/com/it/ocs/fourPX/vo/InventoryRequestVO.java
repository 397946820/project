package com.it.ocs.fourPX.vo;

public class InventoryRequestVO extends Base4PXVO {
	private String[] lstSku;
	private String warehouseCode;
	
	public String[] getLstSku() {
		return lstSku;
	}
	public void setLstSku(String[] lstSku) {
		this.lstSku = lstSku;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
	
}
