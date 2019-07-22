package com.it.ocs.salesStatistics.vo;

import java.io.Serializable;

import com.it.ocs.salesStatistics.model.LightOrderModel;
/**
 * 退货申请单
 * @author wangqiang
 *
 */
public class ReturnGoodsOrderVo extends LightOrderModel implements Serializable {
	 
	private static final long serialVersionUID = -3174600297773526688L;
	
	private String orderItemId;
	
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	
	/**
	 * 主键识别id
	 */
	private String returnId;
	
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 退货原因
	 */
	private String cause;
	/**
	 * 联系电话
	 */
	private String telephone;
	/**
	 * 联系地址
	 */
	private String address;
	/**
	 * 新订单号
	 */
	private String orderIdNew;
	/**
	 * 入库类型
	 */
	private String sku;
	/**
	 * 快递运单号
	 */
	private String trackingNum;
	
	/**
	 * RMA序列号
	 */
	private String rmaSeq;
	
	
	
	public String getRmaSeq() {
		return rmaSeq;
	}
	public void setRmaSeq(String rmaSeq) {
		this.rmaSeq = rmaSeq;
	}
	public String getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(String trackingNum) {
		this.trackingNum = trackingNum;
	}
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
 
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrderIdNew() {
		return orderIdNew;
	}
	public void setOrderIdNew(String orderIdNew) {
		this.orderIdNew = orderIdNew;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	
	
}
