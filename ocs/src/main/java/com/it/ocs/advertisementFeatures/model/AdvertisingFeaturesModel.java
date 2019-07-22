package com.it.ocs.advertisementFeatures.model;

import com.it.ocs.common.model.BaseModel;

//广告特色实体类
public class AdvertisingFeaturesModel extends BaseModel {

	// 站点
	private Long siteId;
	// 特色属性
	private String featureProperty;
	// 站点图片地址
	private String ico;

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getFeatureProperty() {
		return featureProperty;
	}

	public void setFeatureProperty(String featureProperty) {
		this.featureProperty = featureProperty;
	}

}