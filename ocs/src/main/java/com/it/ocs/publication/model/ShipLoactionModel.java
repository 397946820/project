package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class ShipLoactionModel extends BaseModel{
	private Integer siteId;
	private String shippingLocation;
	private String description;
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getShippingLocation() {
		return shippingLocation;
	}
	public void setShippingLocation(String shippingLocation) {
		this.shippingLocation = shippingLocation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
