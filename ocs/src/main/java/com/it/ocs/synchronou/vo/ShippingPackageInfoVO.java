package com.it.ocs.synchronou.vo;

import java.util.Calendar;

public class ShippingPackageInfoVO {


	private String storeID;

	private String shippingTrackingEvent;


	private Calendar scheduledDeliveryTimeMin;

	private Calendar scheduledDeliveryTimeMax;

	private Calendar actualDeliveryTime;

	private Calendar estimatedDeliveryTimeMin;

	private Calendar estimatedDeliveryTimeMax;

	public String getStoreID() {
		return storeID;
	}

	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}

	public String getShippingTrackingEvent() {
		return shippingTrackingEvent;
	}

	public void setShippingTrackingEvent(String shippingTrackingEvent) {
		this.shippingTrackingEvent = shippingTrackingEvent;
	}

	public Calendar getScheduledDeliveryTimeMin() {
		return scheduledDeliveryTimeMin;
	}

	public void setScheduledDeliveryTimeMin(Calendar scheduledDeliveryTimeMin) {
		this.scheduledDeliveryTimeMin = scheduledDeliveryTimeMin;
	}

	public Calendar getScheduledDeliveryTimeMax() {
		return scheduledDeliveryTimeMax;
	}

	public void setScheduledDeliveryTimeMax(Calendar scheduledDeliveryTimeMax) {
		this.scheduledDeliveryTimeMax = scheduledDeliveryTimeMax;
	}

	public Calendar getActualDeliveryTime() {
		return actualDeliveryTime;
	}

	public void setActualDeliveryTime(Calendar actualDeliveryTime) {
		this.actualDeliveryTime = actualDeliveryTime;
	}

	public Calendar getEstimatedDeliveryTimeMin() {
		return estimatedDeliveryTimeMin;
	}

	public void setEstimatedDeliveryTimeMin(Calendar estimatedDeliveryTimeMin) {
		this.estimatedDeliveryTimeMin = estimatedDeliveryTimeMin;
	}

	public Calendar getEstimatedDeliveryTimeMax() {
		return estimatedDeliveryTimeMax;
	}

	public void setEstimatedDeliveryTimeMax(Calendar estimatedDeliveryTimeMax) {
		this.estimatedDeliveryTimeMax = estimatedDeliveryTimeMax;
	}
	
	
}
