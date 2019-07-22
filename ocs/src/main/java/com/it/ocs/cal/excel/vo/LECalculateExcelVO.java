package com.it.ocs.cal.excel.vo;

import java.util.HashMap;
import java.util.Map;

import com.it.ocs.excel.annotation.ExcelLink;

public class LECalculateExcelVO {

	@ExcelLink(title = "平台", index = 0)
	private String platform;

	@ExcelLink(title = "国家", index = 1)
	private String countryId;

	@ExcelLink(title = "SKU", index = 2)
	private String sku;

	@ExcelLink(title = "运输方式", index = 3)
	private String shippingType;

	@ExcelLink(title = "交易模式", index = 4)
	private String transactionMode;

	@ExcelLink(title = "是否用占用费比", index = 5)
	private String isCostOf;

	@ExcelLink(title = "是否用仓租费", index = 6)
	private String isStorageCharges;

	@ExcelLink(title = "捆绑个数", index = 7)
	private String qty;

	@ExcelLink(title = "利润系数1", index = 8)
	private String profitRateAction1;

	@ExcelLink(title = "建议售价1", index = 9)
	private String finalPrice1;

	@ExcelLink(title = "利润系数2", index = 10)
	private String profitRateAction2;

	@ExcelLink(title = "建议售价2", index = 11)
	private String finalPrice2;

	@ExcelLink(title = "利润系数3", index = 12)
	private String profitRateAction3;

	@ExcelLink(title = "建议售价3", index = 13)
	private String finalPrice3;

	private Double profitRateAction;

	private Double finalPrice;

	public Double getProfitRateAction() {
		return profitRateAction;
	}

	public void setProfitRateAction(Double profitRateAction) {
		this.profitRateAction = profitRateAction;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

	public String getIsCostOf() {
		return isCostOf;
	}

	public void setIsCostOf(String isCostOf) {
		this.isCostOf = isCostOf;
	}

	public String getIsStorageCharges() {
		return isStorageCharges;
	}

	public void setIsStorageCharges(String isStorageCharges) {
		this.isStorageCharges = isStorageCharges;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getProfitRateAction1() {
		return profitRateAction1;
	}

	public void setProfitRateAction1(String profitRateAction1) {
		this.profitRateAction1 = profitRateAction1;
	}

	public String getFinalPrice1() {
		return finalPrice1;
	}

	public void setFinalPrice1(String finalPrice1) {
		this.finalPrice1 = finalPrice1;
	}

	public String getProfitRateAction2() {
		return profitRateAction2;
	}

	public void setProfitRateAction2(String profitRateAction2) {
		this.profitRateAction2 = profitRateAction2;
	}

	public String getFinalPrice2() {
		return finalPrice2;
	}

	public void setFinalPrice2(String finalPrice2) {
		this.finalPrice2 = finalPrice2;
	}

	public String getProfitRateAction3() {
		return profitRateAction3;
	}

	public void setProfitRateAction3(String profitRateAction3) {
		this.profitRateAction3 = profitRateAction3;
	}

	public String getFinalPrice3() {
		return finalPrice3;
	}

	public void setFinalPrice3(String finalPrice3) {
		this.finalPrice3 = finalPrice3;
	}

}
