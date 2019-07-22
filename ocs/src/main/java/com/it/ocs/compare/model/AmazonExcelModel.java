package com.it.ocs.compare.model;

import java.io.Serializable;
import java.util.Date;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.excel.annotation.ExcelLink;

public class AmazonExcelModel extends BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2446762087753732645L;
	@ExcelLink(title = "amazon-order-id", index = 0)
	private String orderId;
	@ExcelLink(title = "sales-channel", index = 6)
	private String channel;
	@ExcelLink(title = "quantity", index = 14)
	private int quantity;
	@ExcelLink(title = "sku", index = 11)
	private String sku;
	@ExcelLink(title = "purchase-date", index = 2)
	private Date purchaseDate;
	@ExcelLink(title = "last-updated-date", index = 3)
	private Date lastUpdatedDate;
	@ExcelLink(title = "item-price", index = 16)
	private double itemPrice;
	@ExcelLink(title = "order-status", index = 4)
	private String orderStatus;
	@ExcelLink(title = "currency", index = 8)
	private String currencyCode;
	@ExcelLink(title = "item-tax", index = 9)
	private double itemTax;
	@ExcelLink(title = "shipping-price", index = 10)
	private double shippingPrice;
	@ExcelLink(title = "shipping-tax", index = 11)
	private double shippingTax;
	@ExcelLink(title = "gift-wrap-price", index = 12)
	private double giftWrapPrice;
	@ExcelLink(title = "gift-wrap-tax", index = 13)
	private double giftWrapTax;
	@ExcelLink(title = "item-promotion-discount", index = 14)
	private double itemPromotionDiscount;
	@ExcelLink(title = "ship-promotion-discount", index = 15)
	private double shipPromotionDiscount;
	@ExcelLink(title = "ship-city", index = 16)
	private String shipCity;// 运输城市
	@ExcelLink(title = "ship-state", index = 17)
	private String shipState;
	@ExcelLink(title = "ship-postal-code", index = 18)
	private String shipPostalCode;// 邮编
	@ExcelLink(title = "ship-country", index = 19)
	private String shipCountry;
	@ExcelLink(title = "promotion-ids", index = 20)
	private String promotionIds;
	@ExcelLink(title = "is-business-order", index = 21)
	private String businessOrder;
	@ExcelLink(title = "purchase-order-number", index = 22)
	private String purchaseOrderNumber;
	@ExcelLink(title = "price-designation", index = 23)
	private String priceDesignation;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getItemTax() {
		return itemTax;
	}

	public void setItemTax(double itemTax) {
		this.itemTax = itemTax;
	}

	public double getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public double getShippingTax() {
		return shippingTax;
	}

	public void setShippingTax(double shippingTax) {
		this.shippingTax = shippingTax;
	}

	public double getGiftWrapPrice() {
		return giftWrapPrice;
	}

	public void setGiftWrapPrice(double giftWrapPrice) {
		this.giftWrapPrice = giftWrapPrice;
	}

	public double getGiftWrapTax() {
		return giftWrapTax;
	}

	public void setGiftWrapTax(double giftWrapTax) {
		this.giftWrapTax = giftWrapTax;
	}

	public double getItemPromotionDiscount() {
		return itemPromotionDiscount;
	}

	public void setItemPromotionDiscount(double itemPromotionDiscount) {
		this.itemPromotionDiscount = itemPromotionDiscount;
	}

	public double getShipPromotionDiscount() {
		return shipPromotionDiscount;
	}

	public void setShipPromotionDiscount(double shipPromotionDiscount) {
		this.shipPromotionDiscount = shipPromotionDiscount;
	}

	public String getShipCity() {
		return shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipState() {
		return shipState;
	}

	public void setShipState(String shipState) {
		this.shipState = shipState;
	}

	public String getShipPostalCode() {
		return shipPostalCode;
	}

	public void setShipPostalCode(String shipPostalCode) {
		this.shipPostalCode = shipPostalCode;
	}

	public String getShipCountry() {
		return shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

	public String getPromotionIds() {
		return promotionIds;
	}

	public void setPromotionIds(String promotionIds) {
		this.promotionIds = promotionIds;
	}

	public String getBusinessOrder() {
		return businessOrder;
	}

	public void setBusinessOrder(String businessOrder) {
		this.businessOrder = businessOrder;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getPriceDesignation() {
		return priceDesignation;
	}

	public void setPriceDesignation(String priceDesignation) {
		this.priceDesignation = priceDesignation;
	}

}
