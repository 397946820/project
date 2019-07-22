package com.it.ocs.compare.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightDBModel {
	@ExcelLink(title = "increment_id", index = 0)
	private String orderId;
	@ExcelLink(title = "subtotal", index = 1)
	private Double subTotal;
	@ExcelLink(title = "grand_total", index = 2)
	private Double grandTotal;
	@ExcelLink(title = "total_paid", index = 3)
	private Double totalPaid;
	@ExcelLink(title = "total_refunded", index = 4)
	private Double totalRefunded;
	@ExcelLink(title = "state", index = 5)
	private String state;
	@ExcelLink(title = "status", index = 6)
	private String status;
	@ExcelLink(title = "shipping_amount", index = 7)
	private Double shippingAmount;
	@ExcelLink(title = "tax_amount", index = 8)
	private Double taxAmount;
	@ExcelLink(title = "discount_amount", index = 9)
	private Double discountAmount;
	@ExcelLink(title = "order_currency_code", index = 10)
	private String orderCurrencyCode;
	@ExcelLink(title = "created_at", index = 11)
	private Date lightCreateAt;
	@ExcelLink(title = "Shipped At", index = 12)
	private Date shipAt;
	@ExcelLink(title = "shipping_method", index = 13)
	private String shippingMethod;
	@ExcelLink(title = "shipping_description", index = 14)
	private String shippingDescription;
	@ExcelLink(title = "SKU", index = 15)
	private String sku;
	@ExcelLink(title = "product_name", index = 16)
	private String productName;
	@ExcelLink(title = "qty_ordered", index = 17)
	private Double qtyOrdered;
	@ExcelLink(title = "price", index = 18)
	private Double price;
	@ExcelLink(title = "row_total", index = 19)
	private Double rowTotal;

	public Date getShipAt() {
		return shipAt;
	}

	public void setShipAt(Date shipAt) {
		this.shipAt = shipAt;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Double getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(Double totalPaid) {
		this.totalPaid = totalPaid;
	}

	public Double getTotalRefunded() {
		return totalRefunded;
	}

	public void setTotalRefunded(Double totalRefunded) {
		this.totalRefunded = totalRefunded;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(Double shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getOrderCurrencyCode() {
		return orderCurrencyCode;
	}

	public void setOrderCurrencyCode(String orderCurrencyCode) {
		this.orderCurrencyCode = orderCurrencyCode;
	}

	public Date getLightCreateAt() {
		return lightCreateAt;
	}

	public void setLightCreateAt(Date lightCreateAt) {
		this.lightCreateAt = lightCreateAt;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getShippingDescription() {
		return shippingDescription;
	}

	public void setShippingDescription(String shippingDescription) {
		this.shippingDescription = shippingDescription;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(Double qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(Double rowTotal) {
		this.rowTotal = rowTotal;
	}

}
