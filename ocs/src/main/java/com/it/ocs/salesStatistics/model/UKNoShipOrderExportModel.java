package com.it.ocs.salesStatistics.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class UKNoShipOrderExportModel {

	@ExcelLink(title = "PlatformAccount", index = 0)
	private String platformAccount;
	
	@ExcelLink(title = "Short Order ID", index = 1)
	private String shortOrderId;
	
	@ExcelLink(title = "ItemId", index = 2)
	private String itemId;

	@ExcelLink(title = "TransactionId", index = 3)
	private String transactionId;

	@ExcelLink(title = "SellingSKU", index = 4)
	private String sellingSKU;

	@ExcelLink(title = "QTY", index = 5)
	private String qty;

	@ExcelLink(title = "OrderNo", index = 6)
	private String orderNo;

	@ExcelLink(title = "CustOrderRef", index = 7)
	private String custOrderRef;

	@ExcelLink(title = "OrderDate", index = 8)
	private String orderDate;

	@ExcelLink(title = "StockCode", index = 9)
	private String stockCode;

	@ExcelLink(title = "StockDesc", index = 10)
	private String stockDesc;

	@ExcelLink(title = "OrderQty", index = 11)
	private String orderQty;

	@ExcelLink(title = "Weight", index = 12)
	private String weight;

	@ExcelLink(title = "ShipToNo", index = 13)
	private String shipToNo;

	@ExcelLink(title = "ShipToName", index = 14)
	private String shipToName;

	@ExcelLink(title = "ShipToAddr1", index = 15)
	private String shipToAddr1;

	@ExcelLink(title = "ShipToAddr2", index = 16)
	private String shipToAddr2;

	@ExcelLink(title = "ShipToAddr3", index = 17)
	private String shipToAddr3;

	@ExcelLink(title = "ShipToCounty", index = 18)
	private String shipToCounty;

	@ExcelLink(title = "ShipPC", index = 19)
	private String shipPC;

	@ExcelLink(title = "ShipTel", index = 20)
	private String shipTel;

	@ExcelLink(title = "ShipCC", index = 21)
	private String shipCC;

	@ExcelLink(title = "ShipMethod", index = 22)
	private String shipMethod;

	@ExcelLink(title = "EnhanceCode", index = 23)
	private String enhanceCode;

	@ExcelLink(title = "SpecialInstructions", index = 24)
	private String specialInstructions;

	@ExcelLink(title = "SubDate", index = 25)
	private String subDate;

	@ExcelLink(title = "CoofOrigin", index = 26)
	private String coofOrigin;

	@ExcelLink(title = "Courier", index = 27)
	private String courier;

	@ExcelLink(title = "Format", index = 28)
	private String format;

	@ExcelLink(title = "Carrier", index = 29)
	private String carrier;

	@ExcelLink(title = "Tracking No.", index = 30)
	private String trackingNo;

	public String getPlatformAccount() {
		return platformAccount;
	}

	public void setPlatformAccount(String platformAccount) {
		this.platformAccount = platformAccount;
	}
	
	public String getShortOrderId() {
		return shortOrderId;
	}

	public void setShortOrderId(String shortOrderId) {
		this.shortOrderId = shortOrderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCustOrderRef() {
		return custOrderRef;
	}

	public void setCustOrderRef(String custOrderRef) {
		this.custOrderRef = custOrderRef;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getSellingSKU() {
		return sellingSKU;
	}

	public void setSellingSKU(String sellingSKU) {
		this.sellingSKU = sellingSKU;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockDesc() {
		return stockDesc;
	}

	public void setStockDesc(String stockDesc) {
		this.stockDesc = stockDesc;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getShipToNo() {
		return shipToNo;
	}

	public void setShipToNo(String shipToNo) {
		this.shipToNo = shipToNo;
	}

	public String getShipToName() {
		return shipToName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	public String getShipToAddr1() {
		return shipToAddr1;
	}

	public void setShipToAddr1(String shipToAddr1) {
		this.shipToAddr1 = shipToAddr1;
	}

	public String getShipToAddr2() {
		return shipToAddr2;
	}

	public void setShipToAddr2(String shipToAddr2) {
		this.shipToAddr2 = shipToAddr2;
	}

	public String getShipToAddr3() {
		return shipToAddr3;
	}

	public void setShipToAddr3(String shipToAddr3) {
		this.shipToAddr3 = shipToAddr3;
	}

	public String getShipToCounty() {
		return shipToCounty;
	}

	public void setShipToCounty(String shipToCounty) {
		this.shipToCounty = shipToCounty;
	}

	public String getShipPC() {
		return shipPC;
	}

	public void setShipPC(String shipPC) {
		this.shipPC = shipPC;
	}

	public String getShipTel() {
		return shipTel;
	}

	public void setShipTel(String shipTel) {
		this.shipTel = shipTel;
	}

	public String getShipCC() {
		return shipCC;
	}

	public void setShipCC(String shipCC) {
		this.shipCC = shipCC;
	}

	public String getShipMethod() {
		return shipMethod;
	}

	public void setShipMethod(String shipMethod) {
		this.shipMethod = shipMethod;
	}

	public String getEnhanceCode() {
		return enhanceCode;
	}

	public void setEnhanceCode(String enhanceCode) {
		this.enhanceCode = enhanceCode;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getSubDate() {
		return subDate;
	}

	public void setSubDate(String subDate) {
		this.subDate = subDate;
	}

	public String getCoofOrigin() {
		return coofOrigin;
	}

	public void setCoofOrigin(String coofOrigin) {
		this.coofOrigin = coofOrigin;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

}
