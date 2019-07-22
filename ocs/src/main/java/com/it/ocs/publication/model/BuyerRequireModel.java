package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class BuyerRequireModel extends BaseModel {
	private boolean allowAllBuyer;
	private String buyerRule = "";

	public String getBuyerRule() {
		return buyerRule;
	}

	public void setBuyerRule(String buyerRule) {
		this.buyerRule = buyerRule;
	}

	public boolean isAllowAllBuyer() {
		return allowAllBuyer;
		
	}

	public void setAllowAllBuyer(String allowAllBuyerStr) {
		if("Y".equals(allowAllBuyerStr)){
			allowAllBuyer =  true;
		}else{
			allowAllBuyer =  false;
		}
	}

	

}
