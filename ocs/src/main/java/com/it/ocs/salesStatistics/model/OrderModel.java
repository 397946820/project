package com.it.ocs.salesStatistics.model;

import com.it.ocs.cal.model.ImportModel;
import com.it.ocs.cal.utils.ModelProp;

public class OrderModel extends ImportModel {

	@ModelProp(name = "amazon-order-id", colIndex = 0, nullable = false)
	private String orderId;

	@ModelProp(name = "merchant-order-id", colIndex = 1, nullable = false)
	private String copyOrderId;

	@ModelProp(name = "purchase-date", colIndex = 2, nullable = false)
	private String purchasedate;

	@ModelProp(name = "last-updated-date", colIndex = 3, nullable = false)
	private String updatedat;

	@ModelProp(name = "order-status", colIndex = 4, nullable = false)
	private String status;

	@ModelProp(name = "fulfillment-channel", colIndex = 5, nullable = false)
	private String channel;

	@ModelProp(name = "sales-channel", colIndex = 6, nullable = false)
	private String station;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCopyOrderId() {
		return copyOrderId;
	}

	public void setCopyOrderId(String copyOrderId) {
		this.copyOrderId = copyOrderId;
	}

	public String getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(String purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(String updatedat) {
		this.updatedat = updatedat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

}
