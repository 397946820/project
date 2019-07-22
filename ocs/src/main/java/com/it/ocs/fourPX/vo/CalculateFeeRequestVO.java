package com.it.ocs.fourPX.vo;

public class CalculateFeeRequestVO extends Base4PXVO {
	
	private String warehouseCode;
	private String areaCode;
	private String weight;
	private String carrierCode;
	private String postCode;
	private String cargoType;
	
	public CalculateFeeRequestVO() {
		
	}
	
	public CalculateFeeRequestVO(String warehouseCode, String areaCode, String weight, String carrierCode, String postCode, String cargoType) {
		this.warehouseCode = warehouseCode;
		this.areaCode = areaCode;
		this.weight = weight;
		this.carrierCode = carrierCode;
		this.postCode = postCode;
		this.cargoType = cargoType;
	}
	
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getCargoType() {
		return cargoType;
	}
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

}
