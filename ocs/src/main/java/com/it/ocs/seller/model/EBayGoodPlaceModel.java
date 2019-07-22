package com.it.ocs.seller.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class EBayGoodPlaceModel extends BaseModel{

	private String address;
	private String region;
	private String post_code;
	private String enabled_flag;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public String getEnabled_flag() {
		return enabled_flag;
	}
	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}
	


}
