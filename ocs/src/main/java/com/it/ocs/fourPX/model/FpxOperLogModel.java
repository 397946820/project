package com.it.ocs.fourPX.model;

import java.util.Date;

public class FpxOperLogModel {
	
	private Long id;
	private String target;
	private Long objectid;
	private Long operator;
	private String result;
	private String request;
	private String response;
	private Date createdat;
	
	public FpxOperLogModel() {
		
	}
	
	public FpxOperLogModel(Long id, String target, Long objectid, Long operator, String result, String request, String response) {
		this.id = id;
		this.target = target;
		this.objectid = objectid;
		this.operator = operator;
		this.result = result;
		this.request = request;
		this.response = response;
		this.createdat = new Date();
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
	public Long getObjectid() {
		return objectid;
	}
	public void setObjectid(Long objectid) {
		this.objectid = objectid;
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
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Date getCreatedat() {
		return createdat;
	}
	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}
}
