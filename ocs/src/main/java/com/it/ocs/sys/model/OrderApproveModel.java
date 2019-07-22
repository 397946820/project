package com.it.ocs.sys.model;

import java.io.Serializable;

import com.it.ocs.excel.annotation.ExcelLink;

public class OrderApproveModel implements Serializable {
	private Integer id;
	@ExcelLink(title="平台",index=0)
	private String platform;
	@ExcelLink(title="账号",index=1)
	private String account;
	@ExcelLink(title="站点",index=2)
	private String site;
	@ExcelLink(title="订单ID",index=3)
	private String orderId;
	@ExcelLink(title="类型",index=4)
	private String orderType;
	@ExcelLink(title="原因",index=5)
	private String cause;
	@ExcelLink(title="退款金额",index=11)
	private String returnCost;
	public String getReturnCost() {
		return returnCost;
	}

	public void setReturnCost(String returnCost) {
		this.returnCost = returnCost;
	}

	private String descriptions;
	private String isConsumerPaid;
	private String trankingService;
	private String trankingNum;
	private String edaOrderNum;
	private String warehouseNum;
	private String returnCostTime;
	private String reissuedOrderId;
	private String ressuedOrderTime;
	private String approveUser;
	@ExcelLink(title="审批时间",index=10)
	private String approveTime;
	@ExcelLink(title="审批理由",index=9)
	private String approveDescription;
	@ExcelLink(title="审批结果",index=8)
	private String approveResult;
	@ExcelLink(title="申请人",index=6)
	private String createBy;
	@ExcelLink(title="申请时间",index=7)
	private String createDate;
	private String currencyCode;
	private String adjustmentPositive;
	private String deliveryWarehouse;
	private String deliveryAddress;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
		this.cause = cause;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getIsConsumerPaid() {
		return isConsumerPaid;
	}

	public void setIsConsumerPaid(String isConsumerPaid) {
		this.isConsumerPaid = isConsumerPaid;
	}

	public String getTrankingService() {
		return trankingService;
	}

	public void setTrankingService(String trankingService) {
		this.trankingService = trankingService;
	}

	public String getTrankingNum() {
		return trankingNum;
	}

	public void setTrankingNum(String trankingNum) {
		this.trankingNum = trankingNum;
	}

	public String getEdaOrderNum() {
		return edaOrderNum;
	}

	public void setEdaOrderNum(String edaOrderNum) {
		this.edaOrderNum = edaOrderNum;
	}

	public String getWarehouseNum() {
		return warehouseNum;
	}

	public void setWarehouseNum(String warehouseNum) {
		this.warehouseNum = warehouseNum;
	}

	public String getReturnCostTime() {
		return returnCostTime;
	}

	public void setReturnCostTime(String returnCostTime) {
		this.returnCostTime = returnCostTime;
	}

	public String getReissuedOrderId() {
		return reissuedOrderId;
	}

	public void setReissuedOrderId(String reissuedOrderId) {
		this.reissuedOrderId = reissuedOrderId;
	}

	public String getRessuedOrderTime() {
		return ressuedOrderTime;
	}

	public void setRessuedOrderTime(String ressuedOrderTime) {
		this.ressuedOrderTime = ressuedOrderTime;
	}

	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveDescription() {
		return approveDescription;
	}

	public void setApproveDescription(String approveDescription) {
		this.approveDescription = approveDescription;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getAdjustmentPositive() {
		return adjustmentPositive;
	}

	public void setAdjustmentPositive(String adjustmentPositive) {
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

}
