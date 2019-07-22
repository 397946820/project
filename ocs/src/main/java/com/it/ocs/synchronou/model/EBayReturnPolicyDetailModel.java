package com.it.ocs.synchronou.model;

import com.it.ocs.common.model.BaseModel;

public class EBayReturnPolicyDetailModel extends BaseModel {
	
	private String refund;	//退货方式
	private String returns_within;	//退货天数
	private String description;	//退货详情
	private String shippingcost_paidby;	//退货运费负担角色
	private String restocking_feevalue;//折旧费
	private String returns_accepted;	//退货政策
	private Long site_id;	//站点ID
	public String getRefund() {
		return refund;
	}
	public void setRefund(String refund) {
		this.refund = refund;
	}
	public String getReturns_within() {
		return returns_within;
	}
	public void setReturns_within(String returns_within) {
		this.returns_within = returns_within;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getShippingcost_paidby() {
		return shippingcost_paidby;
	}
	public void setShippingcost_paidby(String shippingcost_paidby) {
		this.shippingcost_paidby = shippingcost_paidby;
	}
	public String getRestocking_feevalue() {
		return restocking_feevalue;
	}
	public void setRestocking_feevalue(String restocking_feevalue) {
		this.restocking_feevalue = restocking_feevalue;
	}
	public String getReturns_accepted() {
		return returns_accepted;
	}
	public void setReturns_accepted(String returns_accepted) {
		this.returns_accepted = returns_accepted;
	}
	public Long getSite_id() {
		return site_id;
	}
	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}
	
	
	
	
	
	
}
