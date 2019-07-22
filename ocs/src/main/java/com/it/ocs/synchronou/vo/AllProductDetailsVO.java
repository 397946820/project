package com.it.ocs.synchronou.vo;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.it.ocs.synchronou.model.EBayAllProductDetailsModel;

@Repository
public class AllProductDetailsVO extends EBayAllProductDetailsModel implements Serializable {
	
	private String us_product;
	private String de_product;
	private String uk_product;
	public String getUs_product() {
		return us_product;
	}
	public void setUs_product(String us_product) {
		this.us_product = us_product;
	}
	public String getDe_product() {
		return de_product;
	}
	public void setDe_product(String de_product) {
		this.de_product = de_product;
	}
	public String getUk_product() {
		return uk_product;
	}
	public void setUk_product(String uk_product) {
		this.uk_product = uk_product;
	}
	
}
