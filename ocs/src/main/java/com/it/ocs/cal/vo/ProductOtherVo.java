package com.it.ocs.cal.vo;

import java.io.Serializable;

import com.it.ocs.cal.model.ProductOtherModel;

public class ProductOtherVo extends ProductOtherModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1407673039455049798L;

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
