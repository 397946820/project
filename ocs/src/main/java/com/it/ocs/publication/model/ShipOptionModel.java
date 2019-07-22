package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class ShipOptionModel extends BaseModel{
	private Long tempId;
	private String domesticShipId;//运输方式Id
	private String domesticShipType;//运输方式
	private String domesticShipCost;//运费
	private String domesticExtraCost;//额外每件加收
	private String domesticShipAkHiPr;//AK,HI,PR 额外收费
	private String tranKind ; //0国内，1国际
	private String tranOrder;//第几运输
	private String shipLocationIn;
	public Long getTempId() {
		return tempId;
	}
	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}
	public String getDomesticShipId() {
		return domesticShipId;
	}
	public void setDomesticShipId(String domesticShipId) {
		this.domesticShipId = domesticShipId;
	}
	public String getDomesticShipType() {
		return domesticShipType;
	}
	public void setDomesticShipType(String domesticShipType) {
		this.domesticShipType = domesticShipType;
	}
	public String getDomesticShipCost() {
		return domesticShipCost;
	}
	public void setDomesticShipCost(String domesticShipCost) {
		this.domesticShipCost = domesticShipCost;
	}
	public String getDomesticExtraCost() {
		return domesticExtraCost;
	}
	public void setDomesticExtraCost(String domesticExtraCost) {
		this.domesticExtraCost = domesticExtraCost;
	}
	public String getDomesticShipAkHiPr() {
		return domesticShipAkHiPr;
	}
	public void setDomesticShipAkHiPr(String domesticShipAkHiPr) {
		this.domesticShipAkHiPr = domesticShipAkHiPr;
	}
	public String getTranKind() {
		return tranKind;
	}
	public void setTranKind(String tranKind) {
		this.tranKind = tranKind;
	}
	public String getTranOrder() {
		return tranOrder;
	}
	public void setTranOrder(String tranOrder) {
		this.tranOrder = tranOrder;
	}
	public String getShipLocationIn() {
		return shipLocationIn;
	}
	public void setShipLocationIn(String shipLocationIn) {
		this.shipLocationIn = shipLocationIn;
	}
	
	
}
