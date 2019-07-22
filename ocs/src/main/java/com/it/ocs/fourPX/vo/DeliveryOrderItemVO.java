package com.it.ocs.fourPX.vo;

public class DeliveryOrderItemVO extends Base4PXVO{
	private String sku;
	private String quantity;
	private String skuLabelCode;
	private String skuValue;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
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
