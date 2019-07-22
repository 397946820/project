package com.it.ocs.ukinventory.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.excel.annotation.ExcelLink;

public class InventoryUKModel extends BaseModel {
	@ExcelLink(index = 0, title = "仓库代码")
	private String wmcode;
	@ExcelLink(index = 1, title = "SKU")
	private String sku;
	@ExcelLink(index = 2, title = "数量")
	private Integer qty;
	@ExcelLink(index = 3, title = "库存导入时间")
	private Date uploadDate;

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getWmcode() {
		return wmcode;
	}

	public void setWmcode(String wmcode) {
		this.wmcode = wmcode;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

}
