package com.it.ocs.ukinventory.vo;

import java.io.Serializable;

import com.it.ocs.ukinventory.model.InventoryUKModel;

public class InventoryUKVO extends InventoryUKModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7338610254685705572L;

	private String inventoryUploadDate;

	public String getInventoryUploadDate() {
		return inventoryUploadDate;
	}

	public void setInventoryUploadDate(String inventoryUploadDate) {
		this.inventoryUploadDate = inventoryUploadDate;
	}

}
