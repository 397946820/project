package com.it.ocs.api.vo;

import com.it.ocs.api.model.DeAbnormalReasonModel;

public class AbnormalReasonVO extends DeAbnormalReasonModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 7442901821325289959L;
	
	private String parentOrderId;
	private String parentTypeText;
	private String actionText;
	
	public String getParentOrderId() {
		return parentOrderId;
	}
	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	public String getParentTypeText() {
		return parentTypeText;
	}
	public void setParentTypeText(String parentTypeText) {
		this.parentTypeText = parentTypeText;
	}
	public String getActionText() {
		return actionText;
	}
	public void setActionText(String actionText) {
		this.actionText = actionText;
	}
}
