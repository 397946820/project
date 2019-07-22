package com.it.ocs.salereport.model;

public class SkuInventoryModel {
	private String  sku;
	private String  scode;
	
	private Integer outer_stock ;//海外总库存
	private Integer quantity ;//在库库存
	private Integer inbound  ;//在途库存
	private Integer receiving ;//国外仓库到达亚马逊库存
	private Integer reserved ;//占用库存
	private Integer reserved_sale ;//占用库存可用部分
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public Integer getOuter_stock() {
		return outer_stock;
	}
	public void setOuter_stock(Integer outer_stock) {
		this.outer_stock = outer_stock;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getInbound() {
		return inbound;
	}
	public void setInbound(Integer inbound) {
		this.inbound = inbound;
	}
	public Integer getReceiving() {
		return receiving;
	}
	public void setReceiving(Integer receiving) {
		this.receiving = receiving;
	}
	public Integer getReserved() {
		return reserved;
	}
	public void setReserved(Integer reserved) {
		this.reserved = reserved;
	}
	public Integer getReserved_sale() {
		return reserved_sale;
	}
	public void setReserved_sale(Integer reserved_sale) {
		this.reserved_sale = reserved_sale;
	}
	
	
}
