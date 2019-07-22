package com.it.ocs.api.model;

import java.util.Date;

public class DeWmsOperateLogModel {
	private Long id;
	private String target;
	private Long objectId;
	private Long operator;
	private String result;
	private String description;
	private String enabledFlag;
	private Date createdAt;
	private Date updatedAt;
	
	public DeWmsOperateLogModel() {
		this.enabledFlag = "1";
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
	
	public DeWmsOperateLogModel(String target, Long objectId, Long operator, String result, String description) {
		this();
		this.target = target;
		this.objectId = objectId;
		this.operator = operator;
		this.result = result;
		this.description = description;
	}
	
	public DeWmsOperateLogModel(Long id, String target, Long objectId, Long operator, String result, String description) {
		this(target, objectId, operator, result, description);
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public Long getOperator() {
		return operator;
	}
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
