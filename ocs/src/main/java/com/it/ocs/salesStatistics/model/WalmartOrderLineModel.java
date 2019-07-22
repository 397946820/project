package com.it.ocs.salesStatistics.model;

import java.util.Date;

public class WalmartOrderLineModel {
	private Long id;

	private Long parentId;

	private Long walmartOrderId;

	private String purchaseOrderId;

	private String customerOrderId;

	private Long lineNumber;

	private String productName;

	private String sku;

	private Double itemPrice;

	private String itemPriceCurrency;

	private Double shippingPrice;

	private String shippingPriceCurrency;

	private String orderLineQuantityMeas;

	private Integer orderLineQuantityAmount;

	private Date statusDateUtc;

	private String orderLineStatus;

	private String statusQuantityMeas;

	private Integer statusQuantityAmount;

	private Date shipDateTimeUtc;

	private String shipCarrier;

	private String shipMethodCode;

	private String shipTrackingNumber;

	private String shipTrackingUrl;

	private Date createdAt;

	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getWalmartOrderId() {
		return walmartOrderId;
	}

	public void setWalmartOrderId(Long walmartOrderId) {
		this.walmartOrderId = walmartOrderId;
	}

	public String getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId == null ? null : purchaseOrderId.trim();
	}

	public String getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(String customerOrderId) {
		this.customerOrderId = customerOrderId == null ? null : customerOrderId.trim();
	}

	public Long getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemPriceCurrency() {
		return itemPriceCurrency;
	}

	public void setItemPriceCurrency(String itemPriceCurrency) {
		this.itemPriceCurrency = itemPriceCurrency == null ? null : itemPriceCurrency.trim();
	}

	public Double getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(Double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public String getShippingPriceCurrency() {
		return shippingPriceCurrency;
	}

	public void setShippingPriceCurrency(String shippingPriceCurrency) {
		this.shippingPriceCurrency = shippingPriceCurrency == null ? null : shippingPriceCurrency.trim();
	}

	public String getOrderLineQuantityMeas() {
		return orderLineQuantityMeas;
	}

	public void setOrderLineQuantityMeas(String orderLineQuantityMeas) {
		this.orderLineQuantityMeas = orderLineQuantityMeas == null ? null : orderLineQuantityMeas.trim();
	}

	public Integer getOrderLineQuantityAmount() {
		return orderLineQuantityAmount;
	}

	public void setOrderLineQuantityAmount(Integer orderLineQuantityAmount) {
		this.orderLineQuantityAmount = orderLineQuantityAmount;
	}

	public Date getStatusDateUtc() {
		return statusDateUtc;
	}

	public void setStatusDateUtc(Date statusDateUtc) {
		this.statusDateUtc = statusDateUtc;
	}

	public String getOrderLineStatus() {
		return orderLineStatus;
	}

	public void setOrderLineStatus(String orderLineStatus) {
		this.orderLineStatus = orderLineStatus == null ? null : orderLineStatus.trim();
	}

	public String getStatusQuantityMeas() {
		return statusQuantityMeas;
	}

	public void setStatusQuantityMeas(String statusQuantityMeas) {
		this.statusQuantityMeas = statusQuantityMeas == null ? null : statusQuantityMeas.trim();
	}

	public Integer getStatusQuantityAmount() {
		return statusQuantityAmount;
	}

	public void setStatusQuantityAmount(Integer statusQuantityAmount) {
		this.statusQuantityAmount = statusQuantityAmount;
	}

	public Date getShipDateTimeUtc() {
		return shipDateTimeUtc;
	}

	public void setShipDateTimeUtc(Date shipDateTimeUtc) {
		this.shipDateTimeUtc = shipDateTimeUtc;
	}

	public String getShipCarrier() {
		return shipCarrier;
	}

	public void setShipCarrier(String shipCarrier) {
		this.shipCarrier = shipCarrier == null ? null : shipCarrier.trim();
	}

	public String getShipMethodCode() {
		return shipMethodCode;
	}

	public void setShipMethodCode(String shipMethodCode) {
		this.shipMethodCode = shipMethodCode == null ? null : shipMethodCode.trim();
	}

	public String getShipTrackingNumber() {
		return shipTrackingNumber;
	}

	public void setShipTrackingNumber(String shipTrackingNumber) {
		this.shipTrackingNumber = shipTrackingNumber == null ? null : shipTrackingNumber.trim();
	}

	public String getShipTrackingUrl() {
		return shipTrackingUrl;
	}

	public void setShipTrackingUrl(String shipTrackingUrl) {
		this.shipTrackingUrl = shipTrackingUrl == null ? null : shipTrackingUrl.trim();
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
		return "WalmartOrderLineModel [id=" + id + ", parentId=" + parentId + ", walmartOrderId=" + walmartOrderId
				+ ", purchaseOrderId=" + purchaseOrderId + ", customerOrderId=" + customerOrderId + ", lineNumber="
				+ lineNumber + ", productName=" + productName + ", sku=" + sku + ", itemPrice=" + itemPrice
				+ ", itemPriceCurrency=" + itemPriceCurrency + ", shippingPrice=" + shippingPrice
				+ ", shippingPriceCurrency=" + shippingPriceCurrency + ", orderLineQuantityMeas="
				+ orderLineQuantityMeas + ", orderLineQuantityAmount=" + orderLineQuantityAmount + ", statusDateUtc="
				+ statusDateUtc + ", orderLineStatus=" + orderLineStatus + ", statusQuantityMeas=" + statusQuantityMeas
				+ ", statusQuantityAmount=" + statusQuantityAmount + ", shipDateTimeUtc=" + shipDateTimeUtc
				+ ", shipCarrier=" + shipCarrier + ", shipMethodCode=" + shipMethodCode + ", shipTrackingNumber="
				+ shipTrackingNumber + ", shipTrackingUrl=" + shipTrackingUrl + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}