package com.it.ocs.seller.model;

import com.it.ocs.common.model.BaseModel;

public class EBaySellerDescriptionModel extends BaseModel{

	private String siteId;
	private String description1;
	private String description2;
	private String description3;
	private String description4;
	private String description5;
	private Long carry_count;
	
	private Long sample_count;

	

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getDescription3() {
		return description3;
	}

	public void setDescription3(String description3) {
		this.description3 = description3;
	}

	public String getDescription4() {
		return description4;
	}

	public void setDescription4(String description4) {
		this.description4 = description4;
	}

	public String getDescription5() {
		return description5;
	}

	public void setDescription5(String description5) {
		this.description5 = description5;
	}

	public Long getCarry_count() {
		return carry_count;
	}

	public void setCarry_count(Long carry_count) {
		this.carry_count = carry_count;
	}

	public Long getSample_count() {
		return sample_count;
	}

	public void setSample_count(Long sample_count) {
		this.sample_count = sample_count;
	}
	
	
}
