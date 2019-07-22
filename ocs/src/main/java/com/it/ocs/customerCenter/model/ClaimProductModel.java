package com.it.ocs.customerCenter.model;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.excel.annotation.ExcelLink;

public class ClaimProductModel extends BaseModel {

	private Long id;
	@ExcelLink(title="日期",index=0)
	private String claim_date;
	@ExcelLink(title="售后单号",index=1)
	private String order_id;
	@ExcelLink(title="SKU",index=2)
	private String sku;
	@ExcelLink(title="数量",index=3)
	private Long product_number;
	@ExcelLink(title="售后原因",index=4)
	private String after_reason;
	@ExcelLink(title="处理方式",index=5)
	private String handling;
	@ExcelLink(title="负责人",index=6)
	private String clone;
	private Long create_by;

	@ExcelLink(title="追溯码",index=7)
	private String trace_code;
	
	public String getTrace_code() {
		return trace_code;
	}
	public void setTrace_code(String trace_code) {
		this.trace_code = trace_code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClaim_date() {
		return claim_date;
	}
	public void setClaim_date(String claim_date) {
		this.claim_date = claim_date;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Long getProduct_number() {
		return product_number;
	}
	public void setProduct_number(Long product_number) {
		this.product_number = product_number;
	}
	public String getAfter_reason() {
		return after_reason;
	}
	public void setAfter_reason(String after_reason) {
		this.after_reason = after_reason;
	}
	public String getHandling() {
		return handling;
	}
	public void setHandling(String handling) {
		this.handling = handling;
	}
	public String getClone() {
		return clone;
	}
	public void setClone(String clone) {
		this.clone = clone;
	}
	public Long getCreate_by() {
		return create_by;
	}
	public void setCreate_by(Long create_by) {
		this.create_by = create_by;
	}
	
	
}
