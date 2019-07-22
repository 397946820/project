package com.it.ocs.sys.excel.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitViewFeeExportModel {

	@ExcelLink(title = "运费(分摊)", index = 0)
	private Double freight;

	@ExcelLink(title = "运费(导入)", index = 1)
	private Double freight_;

	@ExcelLink(title = "差异", index = 2)
	private Double difference1;

	@ExcelLink(title = "广告费-google(分摊)", index = 3)
	private Double advertisingFeeGoogle;

	@ExcelLink(title = "广告费-google(导入)", index = 4)
	private Double advertisingFeeGoogle_;

	@ExcelLink(title = "差异", index = 5)
	private Double difference2;

	@ExcelLink(title = "广告费-BING(分摊)", index = 6)
	private Double advertisingFeeBing;

	@ExcelLink(title = "广告费-BING(导入)", index = 7)
	private Double advertisingFeeBing_;

	@ExcelLink(title = "差异", index = 8)
	private Double difference3;

	@ExcelLink(title = "客服-60K(分摊)", index = 9)
	private Double customerFee1;

	@ExcelLink(title = "客服-60K(导入)", index = 10)
	private Double customerFee1_;

	@ExcelLink(title = "差异", index = 11)
	private Double difference4;

	@ExcelLink(title = "客服-通讯费(分摊)", index = 12)
	private Double customerFee2;

	@ExcelLink(title = "客服-通讯费(导入)", index = 13)
	private Double customerFee2_;

	@ExcelLink(title = "差异", index = 14)
	private Double difference5;

	@ExcelLink(title = "客服-ZENDESK-INC(分摊)", index = 15)
	private Double customerFee3;

	@ExcelLink(title = "客服-ZENDESK-INC(导入)", index = 16)
	private Double customerFee3_;

	@ExcelLink(title = "差异", index = 17)
	private Double difference6;

	@ExcelLink(title = "客服-RESELLERRATINGS.COM(分摊)", index = 18)
	private Double customerFee4;

	@ExcelLink(title = "客服-RESELLERRATINGS.COM(导入)", index = 19)
	private Double customerFee4_;

	@ExcelLink(title = "差异", index = 20)
	private Double difference7;

	@ExcelLink(title = "客服-TrustedShopsGmbH(分摊)", index = 21)
	private Double customerFee5;

	@ExcelLink(title = "客服-TrustedShopsGmbH(导入)", index = 22)
	private Double customerFee5_;

	@ExcelLink(title = "差异", index = 23)
	private Double difference8;

	@ExcelLink(title = "平台费-eBay(分摊)", index = 24)
	private Double feeEbay;

	@ExcelLink(title = "平台费-eBay(导入)", index = 25)
	private Double feeEbay_;

	@ExcelLink(title = "差异", index = 26)
	private Double difference9;

	@ExcelLink(title = "清关费(分摊)", index = 27)
	private Double clearFee;

	@ExcelLink(title = "清关费(导入)", index = 28)
	private Double clearFee_;

	@ExcelLink(title = "差异", index = 29)
	private Double difference10;

	@ExcelLink(title = "打包费-Onlinepack(分摊)", index = 30)
	private Double packingCharge;

	@ExcelLink(title = "打包费-Onlinepack(导入)", index = 31)
	private Double packingCharge_;

	@ExcelLink(title = "差异", index = 32)
	private Double difference11;

	@ExcelLink(title = "推广费(分摊)", index = 33)
	private Double promotionFee;

	@ExcelLink(title = "推广费(导入)", index = 34)
	private Double promotionFee_;

	@ExcelLink(title = "差异", index = 35)
	private Double difference12;

	@ExcelLink(title = "亚马逊FEE(分摊)", index = 36)
	private Double feeAmazon;

	@ExcelLink(title = "亚马逊FEE(导入)", index = 37)
	private Double feeAmazon_;

	@ExcelLink(title = "差异", index = 38)
	private Double difference13;

	@ExcelLink(title = "工资(分摊)", index = 39)
	private Double salary;

	@ExcelLink(title = "工资(导入)", index = 40)
	private Double salary_;

	@ExcelLink(title = "差异", index = 41)
	private Double difference14;

	@ExcelLink(title = "年月", index = 42)
	private String monthOfYear;

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Double getFreight_() {
		return freight_;
	}

	public void setFreight_(Double freight_) {
		this.freight_ = freight_;
	}

	public Double getDifference1() {
		return difference1;
	}

	public void setDifference1(Double difference1) {
		this.difference1 = difference1;
	}

	public Double getAdvertisingFeeGoogle() {
		return advertisingFeeGoogle;
	}

	public void setAdvertisingFeeGoogle(Double advertisingFeeGoogle) {
		this.advertisingFeeGoogle = advertisingFeeGoogle;
	}

	public Double getAdvertisingFeeGoogle_() {
		return advertisingFeeGoogle_;
	}

	public void setAdvertisingFeeGoogle_(Double advertisingFeeGoogle_) {
		this.advertisingFeeGoogle_ = advertisingFeeGoogle_;
	}

	public Double getDifference2() {
		return difference2;
	}

	public void setDifference2(Double difference2) {
		this.difference2 = difference2;
	}

	public Double getAdvertisingFeeBing() {
		return advertisingFeeBing;
	}

	public void setAdvertisingFeeBing(Double advertisingFeeBing) {
		this.advertisingFeeBing = advertisingFeeBing;
	}

	public Double getAdvertisingFeeBing_() {
		return advertisingFeeBing_;
	}

	public void setAdvertisingFeeBing_(Double advertisingFeeBing_) {
		this.advertisingFeeBing_ = advertisingFeeBing_;
	}

	public Double getDifference3() {
		return difference3;
	}

	public void setDifference3(Double difference3) {
		this.difference3 = difference3;
	}

	public Double getCustomerFee1() {
		return customerFee1;
	}

	public void setCustomerFee1(Double customerFee1) {
		this.customerFee1 = customerFee1;
	}

	public Double getCustomerFee1_() {
		return customerFee1_;
	}

	public void setCustomerFee1_(Double customerFee1_) {
		this.customerFee1_ = customerFee1_;
	}

	public Double getDifference4() {
		return difference4;
	}

	public void setDifference4(Double difference4) {
		this.difference4 = difference4;
	}

	public Double getCustomerFee2() {
		return customerFee2;
	}

	public void setCustomerFee2(Double customerFee2) {
		this.customerFee2 = customerFee2;
	}

	public Double getCustomerFee2_() {
		return customerFee2_;
	}

	public void setCustomerFee2_(Double customerFee2_) {
		this.customerFee2_ = customerFee2_;
	}

	public Double getDifference5() {
		return difference5;
	}

	public void setDifference5(Double difference5) {
		this.difference5 = difference5;
	}

	public Double getCustomerFee3() {
		return customerFee3;
	}

	public void setCustomerFee3(Double customerFee3) {
		this.customerFee3 = customerFee3;
	}

	public Double getCustomerFee3_() {
		return customerFee3_;
	}

	public void setCustomerFee3_(Double customerFee3_) {
		this.customerFee3_ = customerFee3_;
	}

	public Double getDifference6() {
		return difference6;
	}

	public void setDifference6(Double difference6) {
		this.difference6 = difference6;
	}

	public Double getCustomerFee4() {
		return customerFee4;
	}

	public void setCustomerFee4(Double customerFee4) {
		this.customerFee4 = customerFee4;
	}

	public Double getCustomerFee4_() {
		return customerFee4_;
	}

	public void setCustomerFee4_(Double customerFee4_) {
		this.customerFee4_ = customerFee4_;
	}

	public Double getDifference7() {
		return difference7;
	}

	public void setDifference7(Double difference7) {
		this.difference7 = difference7;
	}

	public Double getCustomerFee5() {
		return customerFee5;
	}

	public void setCustomerFee5(Double customerFee5) {
		this.customerFee5 = customerFee5;
	}

	public Double getCustomerFee5_() {
		return customerFee5_;
	}

	public void setCustomerFee5_(Double customerFee5_) {
		this.customerFee5_ = customerFee5_;
	}

	public Double getDifference8() {
		return difference8;
	}

	public void setDifference8(Double difference8) {
		this.difference8 = difference8;
	}

	public Double getFeeEbay() {
		return feeEbay;
	}

	public void setFeeEbay(Double feeEbay) {
		this.feeEbay = feeEbay;
	}

	public Double getFeeEbay_() {
		return feeEbay_;
	}

	public void setFeeEbay_(Double feeEbay_) {
		this.feeEbay_ = feeEbay_;
	}

	public Double getDifference9() {
		return difference9;
	}

	public void setDifference9(Double difference9) {
		this.difference9 = difference9;
	}

	public Double getClearFee() {
		return clearFee;
	}

	public void setClearFee(Double clearFee) {
		this.clearFee = clearFee;
	}

	public Double getClearFee_() {
		return clearFee_;
	}

	public void setClearFee_(Double clearFee_) {
		this.clearFee_ = clearFee_;
	}

	public Double getDifference10() {
		return difference10;
	}

	public void setDifference10(Double difference10) {
		this.difference10 = difference10;
	}

	public Double getPackingCharge() {
		return packingCharge;
	}

	public void setPackingCharge(Double packingCharge) {
		this.packingCharge = packingCharge;
	}

	public Double getPackingCharge_() {
		return packingCharge_;
	}

	public void setPackingCharge_(Double packingCharge_) {
		this.packingCharge_ = packingCharge_;
	}

	public Double getDifference11() {
		return difference11;
	}

	public void setDifference11(Double difference11) {
		this.difference11 = difference11;
	}

	public Double getPromotionFee() {
		return promotionFee;
	}

	public void setPromotionFee(Double promotionFee) {
		this.promotionFee = promotionFee;
	}

	public Double getPromotionFee_() {
		return promotionFee_;
	}

	public void setPromotionFee_(Double promotionFee_) {
		this.promotionFee_ = promotionFee_;
	}

	public Double getDifference12() {
		return difference12;
	}

	public void setDifference12(Double difference12) {
		this.difference12 = difference12;
	}

	public Double getFeeAmazon() {
		return feeAmazon;
	}

	public void setFeeAmazon(Double feeAmazon) {
		this.feeAmazon = feeAmazon;
	}

	public Double getFeeAmazon_() {
		return feeAmazon_;
	}

	public void setFeeAmazon_(Double feeAmazon_) {
		this.feeAmazon_ = feeAmazon_;
	}

	public Double getDifference13() {
		return difference13;
	}

	public void setDifference13(Double difference13) {
		this.difference13 = difference13;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Double getSalary_() {
		return salary_;
	}

	public void setSalary_(Double salary_) {
		this.salary_ = salary_;
	}

	public Double getDifference14() {
		return difference14;
	}

	public void setDifference14(Double difference14) {
		this.difference14 = difference14;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

}
