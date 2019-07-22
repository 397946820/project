package com.it.ocs.noArriveRegion.model;

import com.it.ocs.common.model.BaseModel;

public class NoArriveRegionModel extends BaseModel {

	private Long siteId;

	private String noArriveRegions;

	private String ico;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getNoArriveRegions() {
		return noArriveRegions;
	}

	public void setNoArriveRegions(String noArriveRegions) {
		this.noArriveRegions = noArriveRegions;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

}
