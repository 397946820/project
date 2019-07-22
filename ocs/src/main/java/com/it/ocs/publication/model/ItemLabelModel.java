package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class ItemLabelModel extends BaseModel {
  
	private String labelState; //标签状态 
	
	private String labelName; //标签名称
	 
	private String labelNote; //标签备注
	
	
	public String getLabelNote() {
		return labelNote;
	}

	public void setLabelNote(String labelNote) {
		this.labelNote = labelNote;
	}

	public String getLabelState() {
		return labelState;
	}

	public void setLabelState(String labelState) {
		this.labelState = labelState;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	
}
