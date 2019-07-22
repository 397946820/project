package com.it.ocs.cal.model;

import java.util.Date;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;
/**
 * @author yecaiqing
 */
public class CalculateModel extends ImportModel {
	private Long entityId;

	@ExcelRead(title = "国家")
	@ModelProp(name = "国家", colIndex = 0, nullable = false)
	private String countryId;

	@ExcelRead(title = "SKU")
	@ModelProp(name = "SKU", colIndex = 1, nullable = false)
	private String sku;

	@ExcelRead(title = "运输方式")
	@ModelProp(name = "运输方式", colIndex = 2, nullable = false)
	private String shippingType;

	@ExcelRead(title = "捆绑个数")
	@ModelProp(name = "捆绑个数", colIndex = 3, nullable = false)
	private String qty;

	private Double profitRateAction;

	private Double finalPrice;

	@ExcelRead(title = "利润系数1")
	@ModelProp(name = "利润系数1", colIndex = 4, nullable = false)
	private Double profitRateAction1;

	@ExcelRead(title = "建议售价1")
	@ModelProp(name = "建议售价1", colIndex = 5, nullable = false)
	private Double finalPrice1;

	@ExcelRead(title = "利润系数2")
	@ModelProp(name = "利润系数2", colIndex = 6, nullable = false)
	private Double profitRateAction2;

	@ExcelRead(title = "建议售价2")
	@ModelProp(name = "建议售价2", colIndex = 7, nullable = false)
	private Double finalPrice2;

	@ExcelRead(title = "利润系数3")
	@ModelProp(name = "利润系数3", colIndex = 8, nullable = false)
	private Double profitRateAction3;

	@ExcelRead(title = "建议售价3")
	@ModelProp(name = "建议售价3", colIndex = 9, nullable = false)
	private Double finalPrice3;

	private Long userId;

	private String batchNumber;

	private Date createdAt;

	private Date updatedAt;

	@Override
	public Long getEntityId() {
		return entityId;
	}

	@Override
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId == null ? null : countryId.trim();
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType == null ? null : shippingType.trim();
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

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

	public Double getProfitRateAction1() {
		return profitRateAction1;
	}

	public void setProfitRateAction1(Double profitRateAction1) {
		this.profitRateAction1 = profitRateAction1;
	}

	public Double getFinalPrice1() {
		return finalPrice1;
	}

	public void setFinalPrice1(Double finalPrice1) {
		this.finalPrice1 = finalPrice1;
	}

	public Double getProfitRateAction2() {
		return profitRateAction2;
	}

	public void setProfitRateAction2(Double profitRateAction2) {
		this.profitRateAction2 = profitRateAction2;
	}

	public Double getFinalPrice2() {
		return finalPrice2;
	}

	public void setFinalPrice2(Double finalPrice2) {
		this.finalPrice2 = finalPrice2;
	}

	public Double getProfitRateAction3() {
		return profitRateAction3;
	}

	public void setProfitRateAction3(Double profitRateAction3) {
		this.profitRateAction3 = profitRateAction3;
	}

	public Double getFinalPrice3() {
		return finalPrice3;
	}

	public void setFinalPrice3(Double finalPrice3) {
		this.finalPrice3 = finalPrice3;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber == null ? null : batchNumber.trim();
	}

	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}