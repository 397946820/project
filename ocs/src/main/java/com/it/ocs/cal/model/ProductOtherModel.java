package com.it.ocs.cal.model;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class ProductOtherModel extends ImportModel {

	private Long parentId;

	@ExcelRead(title = "SKU")
	@ModelProp(name = "SKU", colIndex = 0, nullable = false)
	private String sku;

	private String username;

	@ExcelRead(title = "国家")
	@ModelProp(name = "国家", colIndex = 1, nullable = false)
	private String countryId;

	@ExcelRead(title = "产品分类")
	@ModelProp(name = "产品分类", colIndex = 2, nullable = false)
	private String category;

	@ExcelRead(title = "库存周转率")
	@ModelProp(name = "库存周转率", colIndex = 3, nullable = false)
	private Double turnoverRate;

	@ExcelRead(title = "订单数量")
	@ModelProp(name = "订单数量", colIndex = 4, nullable = false)
	private Double qtyOrdered;

	@ExcelRead(title = "平均存储月份")
	@ModelProp(name = "平均存储月份", colIndex = 5, nullable = false)
	private Double averageMonth;

	@ExcelRead(title = "不可用率")
	@ModelProp(name = "不可用率", colIndex = 6, nullable = false)
	private Double unfulliableRate;

	@ExcelRead(title = "补发比率")
	@ModelProp(name = "补发比率", colIndex = 7, nullable = false)
	private Double replacementRate;

	@ExcelRead(title = "清关价")
	@ModelProp(name = "清关价", colIndex = 8, nullable = false)
	private Double clearPrice;

	@ExcelRead(title = "亚马逊FBA费用")
	@ModelProp(name = "亚马逊FBA费用", colIndex = 9, nullable = false)
	private Double amzFba;

	@ExcelRead(title = "EFN费用")
	@ModelProp(name = "EFN费用", colIndex = 10, nullable = false)
	private Double efnFee;

	@ExcelRead(title = "关税税率")
	@ModelProp(name = "关税税率", colIndex = 11, nullable = false)
	private Double dutyRate;
	
	@ExcelRead(title = "退税率")
	@ModelProp(name = "退税率", colIndex = 12, nullable = false)
	private Double returnRate;

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId == null ? null : countryId.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	public Double getTurnoverRate() {
		return turnoverRate;
	}

	public void setTurnoverRate(Double turnoverRate) {
		this.turnoverRate = turnoverRate;
	}

	public Double getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(Double qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public Double getAverageMonth() {
		return averageMonth;
	}

	public void setAverageMonth(Double averageMonth) {
		this.averageMonth = averageMonth;
	}

	public Double getUnfulliableRate() {
		return unfulliableRate;
	}

	public void setUnfulliableRate(Double unfulliableRate) {
		this.unfulliableRate = unfulliableRate;
	}

	public Double getReplacementRate() {
		return replacementRate;
	}

	public void setReplacementRate(Double replacementRate) {
		this.replacementRate = replacementRate;
	}

	public Double getAmzFba() {
		return amzFba;
	}

	public void setAmzFba(Double amzFba) {
		this.amzFba = amzFba;
	}

	public Double getEfnFee() {
		return efnFee;
	}

	public void setEfnFee(Double efnFee) {
		this.efnFee = efnFee;
	}

	public Double getDutyRate() {
		return dutyRate;
	}

	public void setDutyRate(Double dutyRate) {
		this.dutyRate = dutyRate;
	}

	public void setClearPrice(Double clearPrice) {
		this.clearPrice = clearPrice;
	}

	public Double getClearPrice() {
		return clearPrice;
	}

	public Double getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(Double returnRate) {
		this.returnRate = returnRate;
	}
	
}