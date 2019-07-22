package com.it.ocs.api.model;

/**
* @ClassName: DeInWmsOrderDetailModel 
* @Description: 德国仓库入库单实体-明细表
* @author wgc 
* @date 2018年4月10日 上午11:00:47 
*
 */
public class DeInWmsOrderDetailModel {
	
	/**
	 * 主键ID
	 */
	private long id;
	
	/**
	 * 入库单主表ID
	 */
	private long parentId;
	
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
	
	/**
	 * 不良原因
	 */
	private String badReason;

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
