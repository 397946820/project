package com.it.ocs.salesStatistics.model;

import java.util.Date;

public class AmazonOrderItemModel {
	private Long id;

	private Long parentId;

	private String amazonItemId;

	private String title;

	private String asin;

	private String sku;

	private Double price;

	private Long qty;

	private Double shippingAmount;

	private Double shippingDiscount;

	private Double tax;

	private Double giftPrice;

	private String promotionId;

	private Double promotionDiscount;

	private Double shippingTax;

	private String conditionId;

	private Date createdAt;

	private Date updatedAt;

	private Integer pushStatus;

	private String url;

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

	public String getAmazonItemId() {
		return amazonItemId;
	}

	public void setAmazonItemId(String amazonItemId) {
		this.amazonItemId = amazonItemId == null ? null : amazonItemId.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin == null ? null : asin.trim();
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Double getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(Double shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public Double getShippingDiscount() {
		return shippingDiscount;
	}

	public void setShippingDiscount(Double shippingDiscount) {
		this.shippingDiscount = shippingDiscount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(Double giftPrice) {
		this.giftPrice = giftPrice;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId == null ? null : promotionId.trim();
	}

	public Double getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(Double promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public Double getShippingTax() {
		return shippingTax;
	}

	public void setShippingTax(Double shippingTax) {
		this.shippingTax = shippingTax;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId == null ? null : conditionId.trim();
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

	public Integer getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "AmazonOrderItemModel [id=" + id + ", parentId=" + parentId + ", amazonItemId=" + amazonItemId
				+ ", title=" + title + ", asin=" + asin + ", sku=" + sku + ", price=" + price + ", qty=" + qty
				+ ", shippingAmount=" + shippingAmount + ", shippingDiscount=" + shippingDiscount + ", tax=" + tax
				+ ", giftPrice=" + giftPrice + ", promotionId=" + promotionId + ", promotionDiscount="
				+ promotionDiscount + ", shippingTax=" + shippingTax + ", conditionId=" + conditionId + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", pushStatus=" + pushStatus + ", url=" + url + "]";
	}

}