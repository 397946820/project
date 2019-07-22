package com.it.ocs.cal.vo;

import java.io.Serializable;

import com.it.ocs.cal.model.ProductEntityModel;

public class ProductEntityVo extends ProductEntityModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9128337707168391288L;
	
	//接收产品其他信息
	private String others;

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

}
