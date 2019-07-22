package com.it.ocs.cal.vo;

import java.io.Serializable;

import com.it.ocs.cal.model.ProductCostModel;

public class ProductCostVo extends ProductCostModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487337185907146469L;

	private String purchasePrice;

	private String purchasePriceRMB;

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getPurchasePriceRMB() {
		return purchasePriceRMB;
	}

	public void setPurchasePriceRMB(String purchasePriceRMB) {
		this.purchasePriceRMB = purchasePriceRMB;
	}

}
