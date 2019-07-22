package com.it.ocs.fourPX.model;

import java.util.Date;

public class FpxAbnormalModel {
	
	private Long id;
	private Long parentId;
	private String parentType;
	private String action;
	private String reason;
	private Date createdat;
	
	public FpxAbnormalModel() {
		
	}
	
	public FpxAbnormalModel(Long id, Long parentId, String parentType, String action, String reason) {
		this.id = id;
		this.parentId = parentId;
		this.parentType = parentType;
		this.action = action;
		this.reason = reason;
		this.createdat = new Date();
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
	public Date getCreatedat() {
		return createdat;
	}
	public void setCreatedat(Date createdAt) {
		this.createdat = createdAt;
	}

}
