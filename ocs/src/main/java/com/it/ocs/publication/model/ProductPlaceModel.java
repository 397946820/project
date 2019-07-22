package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class ProductPlaceModel extends BaseModel {
	private String productAddress = "";// 物品所在地
	private String region = "";// 国家或地区
	private String postCode = "";// 邮编

	public String getProductAddress() {
		return productAddress;
	}

	public void setProductAddress(String productAddress) {
		this.productAddress = productAddress;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}
