package com.it.ocs.seller.model;

import com.it.ocs.common.model.BaseModel;

public class EBayBuyerRequiredModel extends BaseModel {
	private String site;
	private String allowAllBuyer;
	private String buyerRule;
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getAllowAllBuyer() {
		return allowAllBuyer;
	}
	public void setAllowAllBuyer(String allowAllBuyer) {
		this.allowAllBuyer = allowAllBuyer;
	}
	public String getBuyerRule() {
		return buyerRule;
	}
	public void setBuyerRule(String buyerRule) {
		this.buyerRule = buyerRule;
	}
	
	
}
