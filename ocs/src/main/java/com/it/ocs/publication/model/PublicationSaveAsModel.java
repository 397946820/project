package com.it.ocs.publication.model;

import java.io.Serializable;

import com.it.ocs.common.model.BaseModel;

public class PublicationSaveAsModel extends BaseModel implements Serializable {
	private String title;
	private String siteId;
	private String data;
	private String dataType;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
}
