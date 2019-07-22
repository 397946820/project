package com.it.ocs.compare.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class AmazonInputEXModel {

	private Long id;
	
	@ExcelLink(title = "date/time", index = 0)
	private String date_time;
	
	@ExcelLink(title = "settlement id", index = 1)
	private String settlement_id;
	
	@ExcelLink(title = "type", index = 2)
	private String type;
	
	@ExcelLink(title="order id",index=3)
	private String order_id;
	
	@ExcelLink(title="sku",index=4)
	private String sku;
	
	@ExcelLink(title="description",index=5)
	private String description;
	
	@ExcelLink(title="quantity",index=6)
	private String quantity;

	@ExcelLink(title="marketplace",index=7)
	private String marketplace;
	
	@ExcelLink(title="fulfillment",index=8)
	private String fulfillment;
	
	@ExcelLink(title="order city",index=9)
	private String order_city;
	
	@ExcelLink(title="order state",index=10)
	private String order_state;
	
	@ExcelLink(title="order postal",index=11)
	private String order_postal;
	
	@ExcelLink(title="product sales",index=12)
	private String product_sales;
	
	@ExcelLink(title="shipping credits",index=13)
	private String shipping_credits;
	
	@ExcelLink(title="gift wrap credits",index=14)
	private String gift_wrap_credits;
	
	@ExcelLink(title="promotional rebates",index=15)
	private String promotional_rebates;
	
	@ExcelLink(title="sales tax collected",index=16)
	private String sales_tax_collected;
	
	@ExcelLink(title="selling fees",index=17)
	private String selling_fees;
	
	@ExcelLink(title="fba fees",index=18)
	private String fba_fees;
	
	@ExcelLink(title="other transaction fees",index=19)
	private String other_transaction_fees;
	
	@ExcelLink(title="other",index=20)
	private String other;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	public String getSettlement_id() {
		return settlement_id;
	}

	public void setSettlement_id(String settlement_id) {
		this.settlement_id = settlement_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMarketplace() {
		return marketplace;
	}

	public void setMarketplace(String marketplace) {
		this.marketplace = marketplace;
	}

	public String getFulfillment() {
		return fulfillment;
	}

	public void setFulfillment(String fulfillment) {
		this.fulfillment = fulfillment;
	}

	public String getOrder_city() {
		return order_city;
	}

	public void setOrder_city(String order_city) {
		this.order_city = order_city;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	public String getOrder_postal() {
		return order_postal;
	}

	public void setOrder_postal(String order_postal) {
		this.order_postal = order_postal;
	}

	public String getProduct_sales() {
		return product_sales;
	}

	public void setProduct_sales(String product_sales) {
		this.product_sales = product_sales;
	}

	public String getShipping_credits() {
		return shipping_credits;
	}

	public void setShipping_credits(String shipping_credits) {
		this.shipping_credits = shipping_credits;
	}

	public String getGift_wrap_credits() {
		return gift_wrap_credits;
	}

	public void setGift_wrap_credits(String gift_wrap_credits) {
		this.gift_wrap_credits = gift_wrap_credits;
	}

	public String getPromotional_rebates() {
		return promotional_rebates;
	}

	public void setPromotional_rebates(String promotional_rebates) {
		this.promotional_rebates = promotional_rebates;
	}

	public String getSales_tax_collected() {
		return sales_tax_collected;
	}

	public void setSales_tax_collected(String sales_tax_collected) {
		this.sales_tax_collected = sales_tax_collected;
	}

	public String getSelling_fees() {
		return selling_fees;
	}

	public void setSelling_fees(String selling_fees) {
		this.selling_fees = selling_fees;
	}

	public String getFba_fees() {
		return fba_fees;
	}

	public void setFba_fees(String fba_fees) {
		this.fba_fees = fba_fees;
	}

	public String getOther_transaction_fees() {
		return other_transaction_fees;
	}

	public void setOther_transaction_fees(String other_transaction_fees) {
		this.other_transaction_fees = other_transaction_fees;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@ExcelLink(title="total",index=21)
	private String total;
	
	

	
	
}
