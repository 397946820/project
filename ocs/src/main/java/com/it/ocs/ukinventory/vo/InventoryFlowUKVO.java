package com.it.ocs.ukinventory.vo;

import java.io.Serializable;

import com.it.ocs.ukinventory.model.InventoryFlowUKModel;

public class InventoryFlowUKVO extends InventoryFlowUKModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7410955045754908910L;
	private String flowStarTime;
	private String flowEndTime;
	private String uploadStarTime;
	private String uploadEndTime;

	public String getFlowStarTime() {
		return flowStarTime;
	}

	public void setFlowStarTime(String flowStarTime) {
		this.flowStarTime = flowStarTime;
	}

	public String getFlowEndTime() {
		return flowEndTime;
	}

	public void setFlowEndTime(String flowEndTime) {
		this.flowEndTime = flowEndTime;
	}

	public String getUploadStarTime() {
		return uploadStarTime;
	}

	public void setUploadStarTime(String uploadStarTime) {
		this.uploadStarTime = uploadStarTime;
	}

	public String getUploadEndTime() {
		return uploadEndTime;
	}

	public void setUploadEndTime(String uploadEndTime) {
		this.uploadEndTime = uploadEndTime;
	}

}
