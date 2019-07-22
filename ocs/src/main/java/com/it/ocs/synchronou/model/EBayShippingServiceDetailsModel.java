package com.it.ocs.synchronou.model;

import com.it.ocs.common.model.BaseModel;

public class EBayShippingServiceDetailsModel extends BaseModel{
	

	private String description;
	private Integer siteId;
	private Integer shippingServiceId;
	private String shippingService;
	private Integer shippingTimeMax;
	private Integer shippingTimeMin;
	private String serviceType;
	private String validForSellingFlow;
	private String weightRequired;
    private String dimensionsRequired;
    private String shippingPackage;
    private String shippingCategory;
    private String updateTime;
    private Integer isUse;
    private String internationalService;
    
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Integer getShippingServiceId() {
		return shippingServiceId;
	}
	public void setShippingServiceId(Integer shippingServiceId) {
		this.shippingServiceId = shippingServiceId;
	}
	public String getShippingService() {
		return shippingService;
	}
	public void setShippingService(String shippingService) {
		this.shippingService = shippingService;
	}
	public Integer getShippingTimeMax() {
		return shippingTimeMax;
	}
	public void setShippingTimeMax(Integer shippingTimeMax) {
		this.shippingTimeMax = shippingTimeMax;
	}
	public Integer getShippingTimeMin() {
		return shippingTimeMin;
	}
	public void setShippingTimeMin(Integer shippingTimeMin) {
		this.shippingTimeMin = shippingTimeMin;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getValidForSellingFlow() {
		return validForSellingFlow;
	}
	public void setValidForSellingFlow(String validForSellingFlow) {
		this.validForSellingFlow = validForSellingFlow;
	}
	public String getWeightRequired() {
		return weightRequired;
	}
	public void setWeightRequired(String weightRequired) {
		this.weightRequired = weightRequired;
	}
	public String getDimensionsRequired() {
		return dimensionsRequired;
	}
	public void setDimensionsRequired(String dimensionsRequired) {
		this.dimensionsRequired = dimensionsRequired;
	}
	public String getShippingPackage() {
		return shippingPackage;
	}
	public void setShippingPackage(String shippingPackage) {
		this.shippingPackage = shippingPackage;
	}
	public String getShippingCategory() {
		return shippingCategory;
	}
	public void setShippingCategory(String shippingCategory) {
		this.shippingCategory = shippingCategory;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	public String getInternationalService() {
		return internationalService;
	}
	public void setInternationalService(String internationalService) {
		this.internationalService = internationalService;
	}
    
    
	
	
}
