package com.it.ocs.salesStatistics.model;

import java.util.Date;

public class LightOrderItemModel {
	private Long entityId;

	private Long parentId;

	private Double weight;

	private String sku;

	private String name;

	private Long freeShipping;

	private Long isQtyDecimal;

	private Long noDiscount;

	private Long qtyCanceled;

	private Long qtyInvoiced;

	private Long qtyOrdered;

	private Long qtyRefunded;

	private Long qtyShipped;

	private Double price;

	private Double basePrice;

	private Double originalPrice;

	private Double taxPercent;

	private Double taxAmount;

	private Double taxInvoiced;

	private Double discountPercent;

	private Double discountAmount;

	private Double amountRefunded;

	private Double rowTotal;

	private Long giftMessageId;

	private String postcode;

	private String region;

	private String city;

	private String street;

	private Long giftMessage;

	private Date lightCreatedAt;

	private Date lightUpdatedAt;

	private Long pushStatus;

	private Date createdAt;

	private Date updatedAt;

	private Long sourceId;

	private Double lightItemId;

	private String shippingFirstname;

	private String shippingMiddlename;

	private String shippingLastname;

	private String telephone;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Long getFreeShipping() {
		return freeShipping;
	}

	public void setFreeShipping(Long freeShipping) {
		this.freeShipping = freeShipping;
	}

	public Long getIsQtyDecimal() {
		return isQtyDecimal;
	}

	public void setIsQtyDecimal(Long isQtyDecimal) {
		this.isQtyDecimal = isQtyDecimal;
	}

	public Long getNoDiscount() {
		return noDiscount;
	}

	public void setNoDiscount(Long noDiscount) {
		this.noDiscount = noDiscount;
	}

	public Long getQtyCanceled() {
		return qtyCanceled;
	}

	public void setQtyCanceled(Long qtyCanceled) {
		this.qtyCanceled = qtyCanceled;
	}

	public Long getQtyInvoiced() {
		return qtyInvoiced;
	}

	public void setQtyInvoiced(Long qtyInvoiced) {
		this.qtyInvoiced = qtyInvoiced;
	}

	public Long getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(Long qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public Long getQtyRefunded() {
		return qtyRefunded;
	}

	public void setQtyRefunded(Long qtyRefunded) {
		this.qtyRefunded = qtyRefunded;
	}

	public Long getQtyShipped() {
		return qtyShipped;
	}

	public void setQtyShipped(Long qtyShipped) {
		this.qtyShipped = qtyShipped;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(Double taxPercent) {
		this.taxPercent = taxPercent;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getTaxInvoiced() {
		return taxInvoiced;
	}

	public void setTaxInvoiced(Double taxInvoiced) {
		this.taxInvoiced = taxInvoiced;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getAmountRefunded() {
		return amountRefunded;
	}

	public void setAmountRefunded(Double amountRefunded) {
		this.amountRefunded = amountRefunded;
	}

	public Double getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(Double rowTotal) {
		this.rowTotal = rowTotal;
	}

	public Long getGiftMessageId() {
		return giftMessageId;
	}

	public void setGiftMessageId(Long giftMessageId) {
		this.giftMessageId = giftMessageId;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode == null ? null : postcode.trim();
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region == null ? null : region.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street == null ? null : street.trim();
	}

	public Long getGiftMessage() {
		return giftMessage;
	}

	public void setGiftMessage(Long giftMessage) {
		this.giftMessage = giftMessage;
	}

	public Date getLightCreatedAt() {
		return lightCreatedAt;
	}

	public void setLightCreatedAt(Date lightCreatedAt) {
		this.lightCreatedAt = lightCreatedAt;
	}

	public Date getLightUpdatedAt() {
		return lightUpdatedAt;
	}

	public void setLightUpdatedAt(Date lightUpdatedAt) {
		this.lightUpdatedAt = lightUpdatedAt;
	}

	public Long getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(Long pushStatus) {
		this.pushStatus = pushStatus;
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

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Double getLightItemId() {
		return lightItemId;
	}

	public void setLightItemId(Double lightItemId) {
		this.lightItemId = lightItemId;
	}

	public String getShippingFirstname() {
		return shippingFirstname;
	}

	public void setShippingFirstname(String shippingFirstname) {
		this.shippingFirstname = shippingFirstname == null ? null : shippingFirstname.trim();
	}

	public String getShippingMiddlename() {
		return shippingMiddlename;
	}

	public void setShippingMiddlename(String shippingMiddlename) {
		this.shippingMiddlename = shippingMiddlename == null ? null : shippingMiddlename.trim();
	}

	public String getShippingLastname() {
		return shippingLastname;
	}

	public void setShippingLastname(String shippingLastname) {
		this.shippingLastname = shippingLastname == null ? null : shippingLastname.trim();
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

}