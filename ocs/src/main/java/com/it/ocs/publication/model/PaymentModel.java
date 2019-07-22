package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class PaymentModel extends BaseModel {
	private String paypaiAccount = "";// paypai账户
	private String supportPaypaiInfo = "";// 支持哪些账户信息
	private String description = "";// 描述
	
	private String payAreaName;//名称
	private Long siteNumber;//站点


	public String getPaypaiAccount() {
		return paypaiAccount;
	}

	public void setPaypaiAccount(String paypaiAccount) {
		this.paypaiAccount = paypaiAccount;
	}

	public String getSupportPaypaiInfo() {
		return supportPaypaiInfo;
	}

	public void setSupportPaypaiInfo(String supportPaypaiInfo) {
		this.supportPaypaiInfo = supportPaypaiInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPayAreaName() {
		return payAreaName;
	}

	public void setPayAreaName(String payAreaName) {
		this.payAreaName = payAreaName;
	}

	public Long getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(Long siteNumber) {
		this.siteNumber = siteNumber;
	}

	

	
}
