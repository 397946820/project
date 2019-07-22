package com.it.ocs.api.model;

import java.util.Date;

/**
* @ClassName: DeInWmsOrderMainModel 
* @Description: 德国仓库出库单实体-主表
* @author wgc 
* @date 2018年4月10日 上午11:01:18 
*
 */
public class DeInWmsOrderMainModel {
	/**
	 * 货主代码(业务主体)
	 */
	private String ownerCode;
	
	/**
	 * 仓库代码(库存组织)
	 */
	private String storeCode;
	
	/**
	 * WMS入库单号
	 */
	private String warehouseId;
	
	/**
	 * OMS入库单号
	 */
	private long id;
	
	/**
	 * 入库类型(退货入库等)
	 */
	private String orderType;
	
	/**
	 * 订单来源(官网、eBay、亚马逊等)
	 */
	private String platform;
	
	/**
	 * 账号(例如ebay的德国账号)
	 */
	private String account;
	
	/**
	 * 制单日期(创建日期)
	 */
	private Date createDate;
	
	/**
	 * 操作人
	 */
	private String createBy;
	
	private Date updateDate;
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 原始销售单号
	 */
	private String orderId;
	
	/**
	 * 原始运单号
	 */
	private String trackingNumber;
	
	/**
	 * 新运单号
	 */
	private String newTrackingNumber;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 退货申请唯一标志(来源退货表)
	 */
	private String rma;
	
	/**
	 * 待认领(0为非任领单 1待认领 2已认领)
	 */
	private String waitClaim;
	
	/**
	 * 是否已推送WMS(0未推送 1已推送 2已反馈)
	 */
	private String isSendWms;
	
	/**
	 * 退货退款表的主键ID
	 */
	private Long returnOrderId;

	/**
	 * 推送时间
	 */
	private Date sendDate;
	
	/**
	 * 反馈时间
	 */
	private Date feedbackDate;
	
	private Long claimId;
	
	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

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

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getNewTrackingNumber() {
		return newTrackingNumber;
	}

	public void setNewTrackingNumber(String newTrackingNumber) {
		this.newTrackingNumber = newTrackingNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRma() {
		return rma;
	}

	public void setRma(String rma) {
		this.rma = rma;
	}

	public String getWaitClaim() {
		return waitClaim;
	}

	public void setWaitClaim(String waitClaim) {
		this.waitClaim = waitClaim;
	}

	public String getIsSendWms() {
		return isSendWms;
	}

	public void setIsSendWms(String isSendWms) {
		this.isSendWms = isSendWms;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getReturnOrderId() {
		return returnOrderId;
	}

	public void setReturnOrderId(Long returnOrderId) {
		this.returnOrderId = returnOrderId;
	}
	
}
