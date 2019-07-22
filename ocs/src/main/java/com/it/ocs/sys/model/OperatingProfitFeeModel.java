package com.it.ocs.sys.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitFeeModel {
	private Long entityId;

	@ExcelLink(title = "运费-美国外仓", index = 0)
	private Double freightUs;

	@ExcelLink(title = "运费-英国外仓", index = 1)
	private Double freightUk;

	@ExcelLink(title = "运费-德国外仓", index = 2)
	private Double freightDe;

	@ExcelLink(title = "客服-60K", index = 3)
	private Double customerFee1;

	@ExcelLink(title = "客服-通讯费", index = 4)
	private Double customerFee2;

	@ExcelLink(title = "客服-ZENDESK-INC", index = 5)
	private Double customerFee3;

	@ExcelLink(title = "客服-RESELLERRATINGS.COM", index = 6)
	private Double customerFee4;

	@ExcelLink(title = "客服-TrustedShopsGmbH", index = 7)
	private Double customerFee5;

	@ExcelLink(title = "平台费-eBay", index = 8)
	private Double feeEbay;

	@ExcelLink(title = "打包费-Onlinepack", index = 9)
	private Double packingCharge;

	@ExcelLink(title = "清关费", index = 10)
	private Double clearFee;

	@ExcelLink(title = "其他费用", index = 11)
	private Double otherFee;

	private String monthOfYear;

	private Date createdAt;

	private Date updatedAt;

	public OperatingProfitFeeModel(){
		super();
	}
	
	public OperatingProfitFeeModel(String monthOfYear) {
		this.monthOfYear = monthOfYear;
	}
	
	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Double getFreightUs() {
		return freightUs;
	}

	public void setFreightUs(Double freightUs) {
		this.freightUs = freightUs;
	}

	public Double getFreightUk() {
		return freightUk;
	}

	public void setFreightUk(Double freightUk) {
		this.freightUk = freightUk;
	}

	public Double getFreightDe() {
		return freightDe;
	}

	public void setFreightDe(Double freightDe) {
		this.freightDe = freightDe;
	}

	public Double getCustomerFee1() {
		return customerFee1;
	}

	public void setCustomerFee1(Double customerFee1) {
		this.customerFee1 = customerFee1;
	}

	public Double getCustomerFee2() {
		return customerFee2;
	}

	public void setCustomerFee2(Double customerFee2) {
		this.customerFee2 = customerFee2;
	}

	public Double getCustomerFee3() {
		return customerFee3;
	}

	public void setCustomerFee3(Double customerFee3) {
		this.customerFee3 = customerFee3;
	}

	public Double getCustomerFee4() {
		return customerFee4;
	}

	public void setCustomerFee4(Double customerFee4) {
		this.customerFee4 = customerFee4;
	}

	public Double getCustomerFee5() {
		return customerFee5;
	}

	public void setCustomerFee5(Double customerFee5) {
		this.customerFee5 = customerFee5;
	}

	public Double getFeeEbay() {
		return feeEbay;
	}

	public void setFeeEbay(Double feeEbay) {
		this.feeEbay = feeEbay;
	}

	public Double getPackingCharge() {
		return packingCharge;
	}

	public void setPackingCharge(Double packingCharge) {
		this.packingCharge = packingCharge;
	}

	public Double getClearFee() {
		return clearFee;
	}

	public void setClearFee(Double clearFee) {
		this.clearFee = clearFee;
	}

	public Double getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear == null ? null : monthOfYear.trim();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", entityId=").append(entityId);
		sb.append(", freightUs=").append(freightUs);
		sb.append(", freightUk=").append(freightUk);
		sb.append(", freightDe=").append(freightDe);
		sb.append(", customerFee1=").append(customerFee1);
		sb.append(", customerFee2=").append(customerFee2);
		sb.append(", customerFee3=").append(customerFee3);
		sb.append(", customerFee4=").append(customerFee4);
		sb.append(", customerFee5=").append(customerFee5);
		sb.append(", feeEbay=").append(feeEbay);
		sb.append(", packingCharge=").append(packingCharge);
		sb.append(", clearFee=").append(clearFee);
		sb.append(", otherFee=").append(otherFee);
		sb.append(", monthOfYear=").append(monthOfYear);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append("]");
		return sb.toString();
	}
}
