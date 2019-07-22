package com.it.ocs.fourPX.vo;

import java.util.List;

public class DeliveryOrderRequestVO extends Base4PXVO{
	
	private String warehouseCode;
	private String referenceCode;
	private String carrierCode;
	private String insureType;
	private String sellCode;
	private String remoteArea;
	private String description;
	private ConsigneeVO consignee;
	private List<DeliveryOrderItemVO> items;
	private String insureMoney;
	private String platformCode;
	private String fbaLabelCode;
	private String shippingCode;
	private String shippingLabelURL;
	private String invoiceURL;
	private String msdsURL;
	private String shopId;
	private String shopName;
	private String fba_shipment_id;
	private List<DeliveryOrderFbaItemVo> fbaItems;
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getReferenceCode() {
		return referenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getInsureType() {
		return insureType;
	}
	public void setInsureType(String insureType) {
		this.insureType = insureType;
	}
	public String getSellCode() {
		return sellCode;
	}
	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}
	public String getRemoteArea() {
		return remoteArea;
	}
	public void setRemoteArea(String remoteArea) {
		this.remoteArea = remoteArea;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ConsigneeVO getConsignee() {
		return consignee;
	}
	public void setConsignee(ConsigneeVO consignee) {
		this.consignee = consignee;
	}
	public List<DeliveryOrderItemVO> getItems() {
		return items;
	}
	public void setItems(List<DeliveryOrderItemVO> items) {
		this.items = items;
	}
	public String getInsureMoney() {
		return insureMoney;
	}
	public void setInsureMoney(String insureMoney) {
		this.insureMoney = insureMoney;
	}
	public String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
	public String getFbaLabelCode() {
		return fbaLabelCode;
	}
	public void setFbaLabelCode(String fbaLabelCode) {
		this.fbaLabelCode = fbaLabelCode;
	}
	public String getShippingCode() {
		return shippingCode;
	}
	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}
	public String getShippingLabelURL() {
		return shippingLabelURL;
	}
	public void setShippingLabelURL(String shippingLabelURL) {
		this.shippingLabelURL = shippingLabelURL;
	}
	public String getInvoiceURL() {
		return invoiceURL;
	}
	public void setInvoiceURL(String invoiceURL) {
		this.invoiceURL = invoiceURL;
	}
	public String getMsdsURL() {
		return msdsURL;
	}
	public void setMsdsURL(String msdsURL) {
		this.msdsURL = msdsURL;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getFba_shipment_id() {
		return fba_shipment_id;
	}
	public void setFba_shipment_id(String fba_shipment_id) {
		this.fba_shipment_id = fba_shipment_id;
	}
	public List<DeliveryOrderFbaItemVo> getFbaItems() {
		return fbaItems;
	}
	public void setFbaItems(List<DeliveryOrderFbaItemVo> fbaItems) {
		this.fbaItems = fbaItems;
	}
	
	
}
