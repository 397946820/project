package com.it.ocs.sys.model;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.it.ocs.excel.annotation.ExcelLink;

public class ReturnOrderModel {
	private Long id;

	@ExcelLink(title = "平台", index = 0)
	private String platform;

	@ExcelLink(title = "账号", index = 1)
	private String account;

	@ExcelLink(title = "站点", index = 2)
	private String site;

	@ExcelLink(title = "订单号", index = 3)
	private String orderId;

	@ExcelLink(title = "订单类型", index = 5)
	private String orderType;

	@ExcelLink(title = "原因", index = 4)
	private String cause;

	private String descriptions;

	private String trackingService;

	private String trackingNum;

	private Date deliveryTime;

	private Integer isConsumerPaid;

	private String edaOrderNum;

	private String warehouseNum;

	private Date returnCostTime;

	private String reissuedOrderId;

	private Date reissuedOrderTime;

	private Long approveUser;

	private Date approveTime;

	private String approveDescription;

	@ExcelLink(title = "审核状态", index = 12)
	private String approveResult;

	private Long createBy;

	private Date createDate;

	private Long updateBy;

	private Date updateDate;

	@ExcelLink(title = "创建人", index = 10)
	private String createName;

	private String currencyCode;

	private Integer isReceiving;

	private Integer isReissue;

	private String enabledFlag;

	private Double adjustmentPositive;

	private String deliveryWarehouse;

	private String deliveryAddress;

	private Integer isConfirmOrder;

	private Double shipCost;

	@ExcelLink(title = "运输服务和跟踪号", index = 11)
	private String tarckingService;

	@ExcelLink(title = "SKU", index = 6)
	private String sku;

	@ExcelLink(title = "数量", index = 7)
	private String qty;

	@ExcelLink(title = "金额", index = 8)
	private String returnCost;

	@ExcelLink(title = "问题类型", index = 9)
	private String productCaseType;

	private String useHourDay;

	private List<ReturnOrderItemModel> items = Lists.newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site == null ? null : site.trim();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause == null ? null : cause.trim();
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions == null ? null : descriptions.trim();
	}

	public String getTrackingService() {
		return trackingService;
	}

	public void setTrackingService(String trackingService) {
		this.trackingService = trackingService == null ? null : trackingService.trim();
	}

	public String getTrackingNum() {
		return trackingNum;
	}

	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum == null ? null : trackingNum.trim();
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getIsConsumerPaid() {
		return isConsumerPaid;
	}

	public void setIsConsumerPaid(Integer isConsumerPaid) {
		this.isConsumerPaid = isConsumerPaid;
	}

	public String getEdaOrderNum() {
		return edaOrderNum;
	}

	public void setEdaOrderNum(String edaOrderNum) {
		this.edaOrderNum = edaOrderNum == null ? null : edaOrderNum.trim();
	}

	public String getWarehouseNum() {
		return warehouseNum;
	}

	public void setWarehouseNum(String warehouseNum) {
		this.warehouseNum = warehouseNum == null ? null : warehouseNum.trim();
	}

	public Date getReturnCostTime() {
		return returnCostTime;
	}

	public void setReturnCostTime(Date returnCostTime) {
		this.returnCostTime = returnCostTime;
	}

	public String getReissuedOrderId() {
		return reissuedOrderId;
	}

	public void setReissuedOrderId(String reissuedOrderId) {
		this.reissuedOrderId = reissuedOrderId == null ? null : reissuedOrderId.trim();
	}

	public Date getReissuedOrderTime() {
		return reissuedOrderTime;
	}

	public void setReissuedOrderTime(Date reissuedOrderTime) {
		this.reissuedOrderTime = reissuedOrderTime;
	}

	public Long getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(Long approveUser) {
		this.approveUser = approveUser;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveDescription() {
		return approveDescription;
	}

	public void setApproveDescription(String approveDescription) {
		this.approveDescription = approveDescription == null ? null : approveDescription.trim();
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getIsReceiving() {
		return isReceiving;
	}

	public void setIsReceiving(Integer isReceiving) {
		this.isReceiving = isReceiving;
	}

	public Integer getIsReissue() {
		return isReissue;
	}

	public void setIsReissue(Integer isReissue) {
		this.isReissue = isReissue;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public Double getAdjustmentPositive() {
		return adjustmentPositive;
	}

	public void setAdjustmentPositive(Double adjustmentPositive) {
		this.adjustmentPositive = adjustmentPositive;
	}

	public String getDeliveryWarehouse() {
		return deliveryWarehouse;
	}

	public void setDeliveryWarehouse(String deliveryWarehouse) {
		this.deliveryWarehouse = deliveryWarehouse;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Integer getIsConfirmOrder() {
		return isConfirmOrder;
	}

	public void setIsConfirmOrder(Integer isConfirmOrder) {
		this.isConfirmOrder = isConfirmOrder;
	}

	public List<ReturnOrderItemModel> getItems() {
		return items;
	}

	public void setItems(List<ReturnOrderItemModel> items) {
		this.items = items;
	}

	public Double getShipCost() {
		return shipCost;
	}

	public void setShipCost(Double shipCost) {
		this.shipCost = shipCost;
	}

	public String getTarckingService() {
		return tarckingService;
	}

	public void setTarckingService(String tarckingService) {
		this.tarckingService = tarckingService;
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

	public String getReturnCost() {
		return returnCost;
	}

	public void setReturnCost(String returnCost) {
		this.returnCost = returnCost;
	}

	public String getProductCaseType() {
		return productCaseType;
	}

	public void setProductCaseType(String productCaseType) {
		this.productCaseType = productCaseType;
	}

	public String getUseHourDay() {
		return useHourDay;
	}

	public void setUseHourDay(String useHourDay) {
		this.useHourDay = useHourDay;
	}

	@Override
	public String toString() {
		return "ReturnOrderModel [id=" + id + ", platform=" + platform + ", account=" + account + ", site=" + site
				+ ", orderId=" + orderId + ", orderType=" + orderType + ", cause=" + cause + ", descriptions="
				+ descriptions + ", trackingService=" + trackingService + ", trackingNum=" + trackingNum
				+ ", deliveryTime=" + deliveryTime + ", isConsumerPaid=" + isConsumerPaid + ", edaOrderNum="
				+ edaOrderNum + ", warehouseNum=" + warehouseNum + ", returnCostTime=" + returnCostTime
				+ ", reissuedOrderId=" + reissuedOrderId + ", reissuedOrderTime=" + reissuedOrderTime + ", approveUser="
				+ approveUser + ", approveTime=" + approveTime + ", approveDescription=" + approveDescription
				+ ", approveResult=" + approveResult + ", createBy=" + createBy + ", createDate=" + createDate
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate + ", createName=" + createName
				+ ", currencyCode=" + currencyCode + ", isReceiving=" + isReceiving + ", isReissue=" + isReissue
				+ ", enabledFlag=" + enabledFlag + ", adjustmentPositive=" + adjustmentPositive + ", deliveryWarehouse="
				+ deliveryWarehouse + ", deliveryAddress=" + deliveryAddress + ", isConfirmOrder=" + isConfirmOrder
				+ ", shipCost=" + shipCost + ", tarckingService=" + tarckingService + ", sku=" + sku + ", qty=" + qty
				+ ", returnCost=" + returnCost + ", productCaseType=" + productCaseType + ", useHourDay=" + useHourDay
				+ ", items=" + items + "]";
	}

}