package com.it.ocs.api.model;

import java.util.Date;

/**
* @ClassName: DeOutWmsOrderMainModel 
* @Description: 德国仓库出库单实体-主表
* @author wgc 
* @date 2018年4月9日 下午4:26:51 
*
 */
public class DeOutWmsOrderMainModel {
	
	/**
	 * 主键ID
	 */
	private long id;
	
	/**
	 * 订单平台来源(light,ebay,walmart等)
	 */
	private String platform;
	
	/**
	 * OCS系统订单ID
	 */
	private long orderOcsId;
	
	/**
	 * 出库订单类型(销售出库、补发出库、换货出库、补发矫正出库等)
	 */
	private String orderOutType;
	
	/**
	 * 是否已上传跟踪号(0:否 1:是)
	 */
	private String isUpload;
	
	/**
	 * 订单号 VARCHAR2(256)
	 */
	private String orderId;
	
	/**
	 * WMS订单号 VARCHAR2(256)
	 */
	private String warehouseId;
	
	/**
	 * 跟踪号 VARCHAR2(256)
	 */
	private String trackingNumber;
	
	/**
	 * 发货时间
	 */
	private Date shipDate;
	
	/**
	 * 操作人
	 */
	private String shipBy;
	
	/**
	 * 发货仓库代码
	 */
	private String storeCode;
	
	/**
	 * ocs订单创建日期
	 */
	private Date ocsOrderCreateDate;
	
	/**
	 * 物流承运商代码(DHL等等)
	 */
	private String carrierId;
	
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 客户国家
	 */
	private String customerCountry;
	
	/**
	 * 客户省份
	 */
	private String customerProvince;
	
	/**
	 * 客户城市
	 */
	private String customerCity;
	
	/**
	 * 客户地址
	 */
	private String customerAddress;
	
	/**
	 * 客户邮编
	 */
	private String customerZip;
	
	/**
	 * 客户email
	 */
	private String customerEmail;
	
	/**
	 * 客户电话
	 */
	private String customerPhone;
	
	/**
	 * 是否已推送WMS(0未推送 1已推送 2已反馈)
	 */
	private String isSendWms;
	
	/**
	 * 是否异常(0非异常 1库存不足)
	 */
	private String isAbnormal;
	
	/**
	 * 推送时间
	 */
	private Date sendDate;
	
	/**
	 * 反馈时间
	 */
	private Date feedbackDate;
	
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public long getOrderOcsId() {
		return orderOcsId;
	}

	public void setOrderOcsId(long orderOcsId) {
		this.orderOcsId = orderOcsId;
	}

	public String getOrderOutType() {
		return orderOutType;
	}

	public void setOrderOutType(String orderOutType) {
		this.orderOutType = orderOutType;
	}
	
	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public String getShipBy() {
		return shipBy;
	}

	public void setShipBy(String shipBy) {
		this.shipBy = shipBy;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Date getOcsOrderCreateDate() {
		return ocsOrderCreateDate;
	}

	public void setOcsOrderCreateDate(Date ocsOrderCreateDate) {
		this.ocsOrderCreateDate = ocsOrderCreateDate;
	}

	public String getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	public String getCustomerProvince() {
		return customerProvince;
	}

	public void setCustomerProvince(String customerProvince) {
		this.customerProvince = customerProvince;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerZip() {
		return customerZip;
	}

	public void setCustomerZip(String customerZip) {
		this.customerZip = customerZip;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getIsSendWms() {
		return isSendWms;
	}

	public void setIsSendWms(String isSendWms) {
		this.isSendWms = isSendWms;
	}

	public String getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}
	
	private String customerAddress2;
	public String getCustomerAddress2() {
		return customerAddress2;
	}

	public void setCustomerAddress2(String customerAddress2) {
		this.customerAddress2 = customerAddress2;
	}
	
}
