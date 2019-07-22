package com.it.ocs.site.model;

import com.it.ocs.common.model.BaseModel;

public class SiteModel extends BaseModel {
	private String ebaySiteRelation;
	private String ico;

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getEbaySiteRelation() {
		return ebaySiteRelation;
	}

	public void setEbaySiteRelation(String ebaySiteRelation) {
		this.ebaySiteRelation = ebaySiteRelation;
	}

}
