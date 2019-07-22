package com.it.ocs.api.vo;

import com.it.ocs.api.model.DeInWmsOrderMainModel;

public class InOrderVO extends DeInWmsOrderMainModel implements java.io.Serializable {

	private static final long serialVersionUID = 4769455904278148559L;
	
	private Long orderOcsId;

	/**
	 * 主键ID
	 */
	private Long detailId;
	
	/**
	 * 入库SKU
	 */
	private String sku;
	
	/**
	 * SKU数量
	 */
	private String qty;
	
	/**
	 * 包装代码(如果没有,WMS默认包装)
	 */
	private String packageCode;
	
	/**
	 * 单位(如果没有,WMS默认产品最小单位)
	 */
	private String unit;
	
	/**
	 * 货品属性
	 */
	private String skuProperty;
	
	/**
	 * 退货原因
	 */
	private String returnReason;
	
	/**
	 * 入库单行号
	 */
	private String itemNumber;
	
	/**
	 * 退货客户名称
	 */
	private String customerName;
	
	/**
	 * 客户联系电话
	 */
	private String mobile;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 产品图片快照
	 */
	private String picUrl;

	
	public Long getOrderOcsId() {
		return orderOcsId;
	}

	public void setOrderOcsId(Long orderOcsId) {
		this.orderOcsId = orderOcsId;
	}
	
	/**
	 * 不良原因
	 */
	private String badReason;
	
	private String actualQty;
	
	private String item;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSkuProperty() {
		return skuProperty;
	}

	public void setSkuProperty(String skuProperty) {
		this.skuProperty = skuProperty;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getBadReason() {
		return badReason;
	}

	public void setBadReason(String badReason) {
		this.badReason = badReason;
	}

	public String getActualQty() {
		return actualQty;
	}

	public void setActualQty(String actualQty) {
		this.actualQty = actualQty;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
