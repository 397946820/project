package com.it.ocs.vc.model;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.excel.annotation.ExcelLink;

public class AmazonVcModel extends BaseModel {
	@ExcelLink(title = "Shipping Date", index = 0)
	private String shippingDate;// 发货时间
	@ExcelLink(title = "Delivery From", index = 1)
	private String deliveryFrom;// 发货国家
	@ExcelLink(title = "PO", index = 2)
	private String po;// 订单号
	@ExcelLink(title = "Vendor", index = 3)
	private String vendor;// 发货帐号代码
	@ExcelLink(title = "Address Code", index = 4)
	private String addressCode;// 地址代码
	@ExcelLink(title = "ShipToName", index = 5)
	private String shipToName;// 收件公司名称
	@ExcelLink(title = "ShipToAddr1", index = 6)
	private String shipToAddr1;// 地址一
	@ExcelLink(title = "ShipToAddr2", index = 7)
	private String shipToAddr2;// 地址二
	@ExcelLink(title = "ShipToAddr3", index = 8)
	private String shipToAddr3;// 地址三
	@ExcelLink(title = "ShipToCountry", index = 9)
	private String shipToCountry;// 城市名
	@ExcelLink(title = "ShipPC", index = 10)
	private String shipPC;// 邮编
	@ExcelLink(title = "Model Number", index = 11)
	private String modelNumber;// 产品型号，与公司SSKU号相同
	@ExcelLink(title = "ASIN", index = 12)
	private String asin;// ASIN码
	@ExcelLink(title = "SKU", index = 13)
	private String sku;
	@ExcelLink(title = "Title", index = 14)
	private String title;// 货品名称及描述
	@ExcelLink(title = "Deliver From", index = 15)
	private String deliverFrom;// 接收快递送开始时间
	@ExcelLink(title = "Deliver To", index = 16)
	private String deliverTo;// 接收快递送结束时间
	@ExcelLink(title = "Submitted Quantity", index = 17)
	private String submittedQuantity;// 提交订单数量(亚马逊)
	@ExcelLink(title = "Accepted Quantity", index = 18)
	private String acceptedQuantity;// 接收数量(计划部)
	@ExcelLink(title = "Unit Cost", index = 19)
	private String unitCost;// 单价
	@ExcelLink(title = "Total Cost", index = 20)
	private String totalCost;// 总价
	@ExcelLink(title = "The mode of transportation", index = 21)
	private String shipMethod;// 运输方式
	@ExcelLink(title = "Service Type", index = 22)
	private String serviceType;// 服务类型
	@ExcelLink(title = "Tracking NO", index = 23)
	private String trackingNO;// 货物追踪号
	@ExcelLink(title = "Actual Delivery Quantity", index = 24)
	private String actualDeliveryQuantity;
	@ExcelLink(title = "Actual Quantity Received", index = 25)
	private String actualQuantityReceived;// 实际收货数量
	@ExcelLink(title = "Receive Date", index = 26)
	private String receiveDate;// 收货时间
	@ExcelLink(title = "Delivery To", index = 27)
	private String deliveryTo;// 目的国
	@ExcelLink(title = "Carton", index = 28)
	private String carton;// 件数
	@ExcelLink(title = "Net Weight", index = 29)
	private String netWeight;// 净重
	@ExcelLink(title = "Gross Weight", index = 30)
	private String grossWeight;// 毛重
	@ExcelLink(title = "Platform", index = 31)
	private String platform; //平台：DE | ES 
	private Long ocsOrderId;

	public Long getOcsOrderId() {
		return ocsOrderId;
	}

	public void setOcsOrderId(Long ocsOrderId) {
		this.ocsOrderId = ocsOrderId;
	}

	public String getActualDeliveryQuantity() {
		return actualDeliveryQuantity;
	}

	public void setActualDeliveryQuantity(String actualDeliveryQuantity) {
		this.actualDeliveryQuantity = actualDeliveryQuantity;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
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

	public String getShipToCountry() {
		return shipToCountry;
	}

	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}

	public String getShipPC() {
		return shipPC;
	}

	public void setShipPC(String shipPC) {
		this.shipPC = shipPC;
	}

	public String getShipMethod() {
		return shipMethod;
	}

	public void setShipMethod(String shipMethod) {
		this.shipMethod = shipMethod;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getDeliveryFrom() {
		return deliveryFrom;
	}

	public void setDeliveryFrom(String deliveryFrom) {
		this.deliveryFrom = deliveryFrom;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDeliverFrom() {
		return deliverFrom;
	}

	public void setDeliverFrom(String deliverFrom) {
		this.deliverFrom = deliverFrom;
	}

	public String getDeliverTo() {
		return deliverTo;
	}

	public void setDeliverTo(String deliverTo) {
		this.deliverTo = deliverTo;
	}

	public String getSubmittedQuantity() {
		return submittedQuantity;
	}

	public void setSubmittedQuantity(String submittedQuantity) {
		this.submittedQuantity = submittedQuantity;
	}

	public String getAcceptedQuantity() {
		return acceptedQuantity;
	}

	public void setAcceptedQuantity(String acceptedQuantity) {
		this.acceptedQuantity = acceptedQuantity;
	}

	public String getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getTrackingNO() {
		return trackingNO;
	}

	public void setTrackingNO(String trackingNO) {
		this.trackingNO = trackingNO;
	}

	public String getActualQuantityReceived() {
		return actualQuantityReceived;
	}

	public void setActualQuantityReceived(String actualQuantityReceived) {
		this.actualQuantityReceived = actualQuantityReceived;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getDeliveryTo() {
		return deliveryTo;
	}

	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}

	public String getCarton() {
		return carton;
	}

	public void setCarton(String carton) {
		this.carton = carton;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
