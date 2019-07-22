package com.it.ocs.ukinventory.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.excel.annotation.ExcelLink;

public class InventoryFlowUKModel extends BaseModel {
	@ExcelLink(index=0,title="仓库代码")
	private String wmcode;
	@ExcelLink(index=1,title="SKU")
	private String sku;
	@ExcelLink(index=2,title="数量")
	private Integer qty;
	@ExcelLink(index=3,title="流水类型")
	private String bType;
	@ExcelLink(index=4,title="流水产生时间")
	private Date flowCreateAt;// 流水业务时间，真实流水时间，物流负责上传
	@ExcelLink(index=5,title="业务单号")
	private String buOrder;// 业务单号

	public String getBuOrder() {
		return buOrder;
	}

	public void setBuOrder(String buOrder) {
		this.buOrder = buOrder;
	}

	public String getbType() {
		return bType;
	}

	public void setbType(String bType) {
		this.bType = bType;
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

	public Date getFlowCreateAt() {
		return flowCreateAt;
	}

	public void setFlowCreateAt(Date flowCreateAt) {
		this.flowCreateAt = flowCreateAt;
	}

}
