package com.it.ocs.compare.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class LightExcelModel {
	@ExcelLink(title = "increment_id", index = 0)
	private String incrementId;
	@ExcelLink(title = "subtotal", index = 1)
	private Double subtotal;
	@ExcelLink(title = "grand_total", index = 2)
	private Double grandtotal;
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
	private String createdAt;
	@ExcelLink(title = "Shipped At", index = 12)
	private String shippedAt;
	@ExcelLink(title = "shipping_method", index = 13)
	private String shippingMethod;
	@ExcelLink(title = "shipping_description", index = 14)
	private String shippingDescription;
	@ExcelLink(title = "SKU", index = 15)
	private String sku;
	@ExcelLink(title = "product_name", index = 16)
	private String productName;
	@ExcelLink(title = "qty_ordered", index = 17)
	private Double qty_ordered;
	@ExcelLink(title = "price", index = 18)
	private Double price;
	@ExcelLink(title = "row_total", index = 19)
	private Double rowTotal;
	@ExcelLink(title = "pay_method", index = 20)
	private String payMethod;
	@ExcelLink(title = "last_trans_id", index = 21)
	private String lastTransId;
	@ExcelLink(title = "dbPrice", index = 22)
	private Double dbPrice;
	@ExcelLink(title = "dbQtyOrdered", index = 23)
	private Double dbQtyOrdered;
	@ExcelLink(title = "dbRowTotal", index = 24)
	private Double dbRowTotal;

	public Double getDbRowTotal() {
		return dbRowTotal;
	}

	public void setDbRowTotal(Double dbRowTotal) {
		this.dbRowTotal = dbRowTotal;
	}

	public Double getDbQtyOrdered() {
		return dbQtyOrdered;
	}

	public void setDbQtyOrdered(Double dbQtyOrdered) {
		this.dbQtyOrdered = dbQtyOrdered;
	}

	public Double getDbPrice() {
		return dbPrice;
	}

	public void setDbPrice(Double dbPrice) {
		this.dbPrice = dbPrice;
	}

	public String getIncrementId() {
		return incrementId;
	}

	public void setIncrementId(String incrementId) {
		this.incrementId = incrementId;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(Double grandtotal) {
		this.grandtotal = grandtotal;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getShippedAt() {
		return shippedAt;
	}

	public void setShippedAt(String shippedAt) {
		this.shippedAt = shippedAt;
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

	public Double getQty_ordered() {
		return qty_ordered;
	}

	public void setQty_ordered(Double qty_ordered) {
		this.qty_ordered = qty_ordered;
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

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getLastTransId() {
		return lastTransId;
	}

	public void setLastTransId(String lastTransId) {
		this.lastTransId = lastTransId;
	}

}
