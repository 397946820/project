package com.it.ocs.cal.vo;

import java.io.Serializable;

import com.it.ocs.cal.model.ProductEntityModel;

public class ProductUserVO extends ProductEntityModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4643790904014975567L;
	private String productIds;
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

}
