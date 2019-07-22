package com.it.ocs.pic.model;

import com.it.ocs.common.model.BaseModel;

public class PicCategoryModel extends BaseModel {
	private Long pid;
	private String ebayRelationInfo;
	
	private String text;
	
	

	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		if (pid == null) {
			this.pid = 0L;
		} else {
			this.pid = pid;
		}
	}

	public String getEbayRelationInfo() {
		return ebayRelationInfo;
	}

	public void setEbayRelationInfo(String ebayRelationInfo) {
		this.ebayRelationInfo = ebayRelationInfo;
	}

}
