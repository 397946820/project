package com.it.ocs.publication.vo;

import java.io.Serializable;
import java.util.Map;

import com.it.ocs.publication.model.BuyerRequireModel;

public class BuyerRequireVO extends BuyerRequireModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2257969514461688979L;
	private Map<String, Object> buyRuleObject;
	public Map<String, Object> getBuyRuleObject() {
		return buyRuleObject;
	}

	public void setBuyRuleObject(Map<String, Object> buyRuleObject) {
		this.buyRuleObject = buyRuleObject;
	}

}
