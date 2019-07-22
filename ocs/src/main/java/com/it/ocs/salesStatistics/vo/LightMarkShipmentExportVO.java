package com.it.ocs.salesStatistics.vo;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightMarkShipmentExportVO implements java.io.Serializable {
	
	private static final long serialVersionUID = 8596987883784552651L;

	private Long id;
	
	@ExcelLink(title = "动作轨迹", index = 10)
	private String actions;
	
	private String platform;
	
	@ExcelLink(title = "账号", index = 20)
	private String account;
	
	private Long marker;
	
	@ExcelLink(title = "订单ID", index = 30)
	private String order_id;
	
	@ExcelLink(title = "明细ID", index = 40)
	private String item_id;
	
	@ExcelLink(title = "运输号", index = 50)
	private String tracking_number_01;
	
	@ExcelLink(title = "运输服务", index = 60)
	private String carrier_01;
	
	
	@ExcelLink(title = "上传状态", index = 70)
	private String tn_upload_status;
	
	@ExcelLink(title = "成功上传时间", index = 80)
	private Date shipped_at;
	
	@ExcelLink(title = "异常说明", index = 110)
	private String cause;
	
	@ExcelLink(title = "数据导入时间", index = 100)
	private Date created_at;
	
	@ExcelLink(title = "跟踪号导入时间", index = 90)
	private Date tn_init_at;
	
	private String markertext;

	public LightMarkShipmentExportVO() {}
	
	public String getMarkertext() {
		return markertext;
	}

	public void setMarkertext(String markertext) {
		this.markertext = markertext;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getMarker() {
		return marker;
	}

	public void setMarker(Long marker) {
		this.marker = marker;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getTracking_number_01() {
		return tracking_number_01;
	}

	public void setTracking_number_01(String tracking_number_01) {
		this.tracking_number_01 = tracking_number_01;
	}

	public String getCarrier_01() {
		return carrier_01;
	}

	public void setCarrier_01(String carrier_01) {
		this.carrier_01 = carrier_01;
	}

	public String getTn_upload_status() {
		return tn_upload_status;
	}

	public void setTn_upload_status(String tn_upload_status) {
		this.tn_upload_status = tn_upload_status;
	}

	public Date getShipped_at() {
		return shipped_at;
	}

	public void setShipped_at(Date shipped_at) {
		this.shipped_at = shipped_at;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getTn_init_at() {
		return tn_init_at;
	}

	public void setTn_init_at(Date tn_init_at) {
		this.tn_init_at = tn_init_at;
	}
}
