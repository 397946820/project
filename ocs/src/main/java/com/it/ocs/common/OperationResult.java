package com.it.ocs.common;

import java.io.Serializable;
import java.util.Date;

public class OperationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2161953090232792309L;

	private int errorCode = 0;// 0表示正常
	private String description = "success";
	private Object data;
	
	private Date endDate;
	private Date startDate;
	private String error;
	private Long id;
	
	public OperationResult() {}
	
	public OperationResult(int errorCode, String description, Object data, String error) {
		this.errorCode = errorCode;
		this.description = description;
		this.data = data;
		this.error = error;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}
    
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
