package com.it.ocs.api.model;

import java.util.Date;

public class DeAbnormalReasonModel {
	private Long id;
	private Long parentId;
	private String parentType;
	private String action;
	private String reason;
	private String enabledFlag;
	private Date createdAt;
	private Date updatedAt;
	
	public DeAbnormalReasonModel() {
	}
	
	public DeAbnormalReasonModel(Long id, Long parentId, String parentType, String action, String reason) {
		this.id = id;
		this.parentId = parentId;
		this.parentType = parentType;
		this.action = action;
		this.reason = reason;
		this.enabledFlag = "1";
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentType() {
		return parentType;
	}
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
