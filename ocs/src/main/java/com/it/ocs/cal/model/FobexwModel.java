package com.it.ocs.cal.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class FobexwModel {

	@ExcelLink(title = "SKU", index = 0)
	private String sku_;

	@ExcelLink(title = "数量", index = 1)
	private Long qty_;

	@ExcelLink(title = "是否退税", index = 2)
	private String refundDuty;

	@ExcelLink(title = "利润系数", index = 3)
	private Double profitRateAction;

	@ExcelLink(title = "清关手续费", index = 4)
	private Double totalClearPrice;

	@ExcelLink(title = "物流费", index = 5)
	private Double totalLogisticFee;

	@ExcelLink(title = "总售价", index = 6)
	private Double price;

	@ExcelLink(title = "单个售价", index = 7)
	private Double unitPrice;

	public String getSku_() {
		return sku_;
	}

	public void setSku_(String sku_) {
		this.sku_ = sku_;
	}

	public Long getQty_() {
		return qty_;
	}

	public void setQty_(Long qty_) {
		this.qty_ = qty_;
	}

	public String getRefundDuty() {
		return refundDuty;
	}

	public void setRefundDuty(String refundDuty) {
		this.refundDuty = refundDuty;
	}

	public Double getProfitRateAction() {
		return profitRateAction;
	}

	public void setProfitRateAction(Double profitRateAction) {
		this.profitRateAction = profitRateAction;
	}

	public Double getTotalClearPrice() {
		return totalClearPrice;
	}

	public void setTotalClearPrice(Double totalClearPrice) {
		this.totalClearPrice = totalClearPrice;
	}

	public Double getTotalLogisticFee() {
		return totalLogisticFee;
	}

	public void setTotalLogisticFee(Double totalLogisticFee) {
		this.totalLogisticFee = totalLogisticFee;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "FobexwModel [sku_=" + sku_ + ", qty_=" + qty_ + ", refundDuty=" + refundDuty + ", profitRateAction="
				+ profitRateAction + ", totalClearPrice=" + totalClearPrice + ", totalLogisticFee=" + totalLogisticFee
				+ ", price=" + price + ", unitPrice=" + unitPrice + "]";
	}

}
