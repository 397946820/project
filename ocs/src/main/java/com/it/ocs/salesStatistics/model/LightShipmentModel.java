package com.it.ocs.salesStatistics.model;

import java.util.Date;

public class LightShipmentModel {
	private Long entityId;

	private String orderId;

	private String shipmentIncrementId;

	private String trackingNumber;

	private String trackNumber;

	private String title;

	private String carrier;

	private Date createdAt;

	private Date updatedAt;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getShipmentIncrementId() {
		return shipmentIncrementId;
	}

	public void setShipmentIncrementId(String shipmentIncrementId) {
		this.shipmentIncrementId = shipmentIncrementId == null ? null : shipmentIncrementId.trim();
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber == null ? null : trackingNumber.trim();
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "LightShipmentModel [entityId=" + entityId + ", orderId=" + orderId + ", shipmentIncrementId="
				+ shipmentIncrementId + ", trackingNumber=" + trackingNumber + ", trackNumber=" + trackNumber
				+ ", title=" + title + ", carrier=" + carrier + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}

}