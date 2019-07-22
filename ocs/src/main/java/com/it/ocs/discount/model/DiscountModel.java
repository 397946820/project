package com.it.ocs.discount.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;

//折扣实体类
public class DiscountModel extends BaseModel {

	// ebay账户
	private String ebayAccount;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 优惠明细
	private String discountDetail;

	public String getEbayAccount() {
		return ebayAccount;
	}

	public void setEbayAccount(String ebayAccount) {
		this.ebayAccount = ebayAccount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDiscountDetail() {
		return discountDetail;
	}

	public void setDiscountDetail(String discountDetail) {
		this.discountDetail = discountDetail;
	}

}