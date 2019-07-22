package com.it.ocs.synchronou.vo;

import java.io.Serializable;

import com.it.ocs.synchronou.model.EBayCategoryModel;

public class CategoryVO extends EBayCategoryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7074638152983145881L;
	
	private String authorization;

	private String state;
	
	
	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getAuthorization() {
		return authorization;
	}



	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


}
