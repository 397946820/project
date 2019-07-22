package com.it.ocs.mongo.model;

import java.util.Date;

import com.it.ocs.mongo.annotation.MongoTableName;

@MongoTableName(name = "AMAZON_ORDER_ITEM")
public class AmazonOrderItemModel {
	private Long dataId;
	private Long parentId;
	private String amazonItemId;
	private String title;
	private String asin;
	private String sku;
	private double price;
	private int qty;
	private double shippingAmount;
	private double shippingDiscount;
	private double tax;
	private double giftPrice;
	private String promotionId;
	private double promotionDiscount;
	private double shippingTax;
	private String conditionId;
	private Date createdAt;
	private Date updatedAt;
	private int pushStatus;
	private Long sourceId;

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
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
		this.amazonItemId = amazonItemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(double shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public double getShippingDiscount() {
		return shippingDiscount;
	}

	public void setShippingDiscount(double shippingDiscount) {
		this.shippingDiscount = shippingDiscount;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(double giftPrice) {
		this.giftPrice = giftPrice;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public double getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(double promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public double getShippingTax() {
		return shippingTax;
	}

	public void setShippingTax(double shippingTax) {
		this.shippingTax = shippingTax;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
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

	public int getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(int pushStatus) {
		this.pushStatus = pushStatus;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

}
