package com.it.ocs.seller.vo;

import java.io.Serializable;

import com.it.ocs.seller.model.EBayBuyerRequiredModel;

import net.sf.json.JSONObject;

public class BuyerRequiredVO extends EBayBuyerRequiredModel implements Serializable {

	private static final long serialVersionUID = 3982364958596074067L;
	
	
	private JSONObject jsonObject;
	
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	
}
