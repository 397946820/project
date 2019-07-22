package com.it.ocs.cal.model;

import java.util.Date;

public class ImportModel {

	private Long entityId;

	private Date createdAt;

	private Date updatedAt;

	private String cstarttime;

	private String cendtime;

	private String ustarttime;

	private String uendtime;
	
	private Long updatedBy;
	
	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
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

	public String getCstarttime() {
		return cstarttime;
	}

	public void setCstarttime(String cstarttime) {
		this.cstarttime = cstarttime;
	}

	public String getCendtime() {
		return cendtime;
	}

	public void setCendtime(String cendtime) {
		this.cendtime = cendtime;
	}

	public String getUstarttime() {
		return ustarttime;
	}

	public void setUstarttime(String ustarttime) {
		this.ustarttime = ustarttime;
	}

	public String getUendtime() {
		return uendtime;
	}

	public void setUendtime(String uendtime) {
		this.uendtime = uendtime;
	}

}
