package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class LabelMiddleModel extends BaseModel {

	private Long lebelId;
	
	private Long templateId;

	public Long getLebelId() {
		return lebelId;
	}

	public void setLebelId(Long lebelId) {
		this.lebelId = lebelId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	
}
