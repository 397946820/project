package com.it.ocs.cal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class ProductEntityModel extends ImportModel {

	@ExcelRead(title = "SKU")
	@ModelProp(name = "SKU", colIndex = 0, nullable = false)
	private String sku;

	private String username;

	@ExcelRead(title = "国家")
	@ModelProp(name = "国家", colIndex = 1, nullable = false)
	private String countryId;

	@ExcelRead(title = "长(m)")
	@ModelProp(name = "长(m)", colIndex = 2, nullable = false)
	private Double length;

	@ExcelRead(title = "宽(m)")
	@ModelProp(name = "宽(m)", colIndex = 3, nullable = false)
	private Double width;

	@ExcelRead(title = "高(m)")
	@ModelProp(name = "高(m)", colIndex = 4, nullable = false)
	private Double height;

	@ExcelRead(title = "净重(kg)")
	@ModelProp(name = "净重(kg)", colIndex = 5, nullable = false)
	private Double netWeight;

	@ExcelRead(title = "毛重(kg)")
	@ModelProp(name = "毛重(kg)", colIndex = 6, nullable = false)
	private Double grossWeight;

	@ExcelRead(title = "装箱数量")
	@ModelProp(name = "装箱数量", colIndex = 7, nullable = false)
	private Double packingQty;

	@ExcelRead(title = "外箱体积")
	@ModelProp(name = "外箱体积", colIndex = 8, nullable = false)
	private Double outerVolume;

	@ExcelRead(title = "实际外箱体积")
	@ModelProp(name = "实际外箱体积", colIndex = 9, nullable = false)
	private Double actualOuterVolume = 0d;

	@ExcelRead(title = "外箱重量")
	@ModelProp(name = "外箱重量", colIndex = 10, nullable = false)
	private Double outerWeight;

	@ExcelRead(title = "实际外箱重量")
	@ModelProp(name = "实际外箱重量", colIndex = 11, nullable = false)
	private Double actualOuterWeight = 0d;

	@ExcelRead(title = "关税税率")
	@ModelProp(name = "关税税率", colIndex = 12, nullable = false)
	private String dutyRate;

	@ExcelRead(title = "成本")
	@ModelProp(name = "成本", colIndex = 13, nullable = false)
	private Double price;

	@ExcelRead(title = "清关价")
	@ModelProp(name = "清关价", colIndex = 14, nullable = false)
	private String clearPrice;

	@ExcelRead(title = "货币")
	@ModelProp(name = "货币", colIndex = 15, nullable = false)
	private String currency;

	@ExcelRead(title = "是否多一个包")
	@ModelProp(name = "是否多一个包", colIndex = 16, nullable = false)
	private String isMultiOne;

	@ExcelRead(title = "是否PEU")
	@ModelProp(name = "是否PEU", colIndex = 17, nullable = false)
	private String isPeu;

	@ExcelRead(title = "产品状态")
	@ModelProp(name = "产品状态", colIndex = 18, nullable = false)
	private String status;

	private Long userId;

	private String vSku;

	@ExcelRead(title = "产品分类")
	@ModelProp(name = "产品分类", colIndex = 19, nullable = false)
	private String category;

	@ExcelRead(title = "库存周转率")
	@ModelProp(name = "库存周转率", colIndex = 20, nullable = false)
	private String turnoverRate;

	@ExcelRead(title = "订单数量")
	@ModelProp(name = "订单数量", colIndex = 21, nullable = false)
	private String qtyOrdered;

	@ExcelRead(title = "平均存储月份")
	@ModelProp(name = "平均存储月份", colIndex = 22, nullable = false)
	private String averageMonth;

	@ExcelRead(title = "不可用率")
	@ModelProp(name = "不可用率", colIndex = 23, nullable = false)
	private String unfulliableRate;

	@ExcelRead(title = "补发比率")
	@ModelProp(name = "补发比率", colIndex = 24, nullable = false)
	private String replacementRate;

	@ExcelRead(title = "退税率")
	@ModelProp(name = "退税率", colIndex = 25, nullable = false)
	private Double taxRebateRate;

	@ExcelRead(title = "利率")
	@ModelProp(name = "利率", colIndex = 26, nullable = false)
	private Double interestRate;

	@ExcelRead(title = "亚马逊FBA费用")
	@ModelProp(name = "亚马逊FBA费用", colIndex = 27, nullable = false)
	private String amzFba;

	@ExcelRead(title = "EFN费用")
	@ModelProp(name = "EFN费用", colIndex = 28, nullable = false)
	private String efnFee;
	
	@ExcelRead(title = "退货率")
	@ModelProp(name = "退货率", colIndex = 29, nullable = false)
	private String returnRate;

	private List<ProductOtherModel> productOtherModels = new ArrayList<>();

	public List<ProductOtherModel> getProductOtherModels() {
		if (isEmpty()) {
			for (int i = 0; i < 9; i++) {
				ProductOtherModel otherModel = new ProductOtherModel();
				otherModel.setCountryId(this.getCountryIds()[i]);
				otherModel.setCategory(this.getCategorys()[i]);
				otherModel.setTurnoverRate(new Double(this.getTurnoverRates()[i]));
				otherModel.setQtyOrdered(new Double(this.getQtyOrdereds()[i]));
				otherModel.setAverageMonth(new Double(this.getAverageMonths()[i]));
				otherModel.setUnfulliableRate(new Double(this.getUnfulliableRates()[i]));
				otherModel.setReplacementRate(new Double(this.getReplacementRates()[i]));
				otherModel.setAmzFba(new Double(this.getAmzFbas()[i]));
				otherModel.setEfnFee(new Double(this.getEfnFees()[i]));
				if (this.getClearPrices().length == 9) {
					otherModel.setClearPrice(new Double(this.getClearPrices()[i]));
				}
				otherModel.setDutyRate(new Double(this.getDutyRates()[i]));
				otherModel.setCreatedAt(new Date());
				otherModel.setUpdatedAt(new Date());
				productOtherModels.add(otherModel);
			}
		}
		return productOtherModels;
	}

	public void setProductOtherModels(List<ProductOtherModel> productOtherModels) {
		this.productOtherModels = productOtherModels;
	}

	public Double getTaxRebateRate() {
		return taxRebateRate;
	}

	public void setTaxRebateRate(Double taxRebateRate) {
		this.taxRebateRate = taxRebateRate;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getPackingQty() {
		return packingQty;
	}

	public void setPackingQty(Double packingQty) {
		this.packingQty = packingQty;
	}

	public Double getOuterVolume() {
		return outerVolume;
	}

	public void setOuterVolume(Double outerVolume) {
		this.outerVolume = outerVolume;
	}

	public Double getOuterWeight() {
		return outerWeight;
	}

	public void setOuterWeight(Double outerWeight) {
		this.outerWeight = outerWeight;
	}

	public Double getActualOuterVolume() {
		return actualOuterVolume;
	}

	public void setActualOuterVolume(Double actualOuterVolume) {
		this.actualOuterVolume = actualOuterVolume;
	}

	public Double getActualOuterWeight() {
		return actualOuterWeight;
	}

	public void setActualOuterWeight(Double actualOuterWeight) {
		this.actualOuterWeight = actualOuterWeight;
	}

	public String getIsMultiOne() {
		return isMultiOne;
	}

	public void setIsMultiOne(String isMultiOne) {
		this.isMultiOne = isMultiOne;
	}

	public String getIsPeu() {
		return isPeu;
	}

	public void setIsPeu(String isPeu) {
		this.isPeu = isPeu;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getvSku() {
		return vSku;
	}

	public void setvSku(String vSku) {
		this.vSku = vSku;
	}

	public String[] getCountryIds() {
		return formateData(this.countryId);
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String[] getCategorys() {
		return formateData(this.category);
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String[] getTurnoverRates() {
		return formateData(this.turnoverRate);
	}

	public void setTurnoverRate(String turnoverRate) {
		this.turnoverRate = turnoverRate;
	}

	public String[] getQtyOrdereds() {
		return formateData(this.qtyOrdered);
	}

	public void setQtyOrdered(String qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public String[] getAverageMonths() {
		return formateData(this.averageMonth);
	}

	public void setAverageMonth(String averageMonth) {
		this.averageMonth = averageMonth;
	}

	public String[] getUnfulliableRates() {
		return formateData(this.unfulliableRate);
	}

	public void setUnfulliableRate(String unfulliableRate) {
		this.unfulliableRate = unfulliableRate;
	}

	public String[] getReplacementRates() {
		return formateData(this.replacementRate);
	}

	public void setReplacementRate(String replacementRate) {
		this.replacementRate = replacementRate;
	}

	public String getClearPrice() {
		return clearPrice;
	}

	public String[] getClearPrices() {
		return formateData(this.clearPrice);
	}

	public void setClearPrice(String clearPrice) {
		this.clearPrice = clearPrice;
	}

	public String getAmzFba() {
		return amzFba;
	}

	public void setAmzFba(String amzFba) {
		this.amzFba = amzFba;
	}

	public String getEfnFee() {
		return efnFee;
	}

	public void setEfnFee(String efnFee) {
		this.efnFee = efnFee;
	}

	public void setDutyRate(String dutyRate) {
		this.dutyRate = dutyRate;
	}

	public String getCountryId() {
		return countryId;
	}

	public String getCategory() {
		return category;
	}

	public String getTurnoverRate() {
		return turnoverRate;
	}

	public String getQtyOrdered() {
		return qtyOrdered;
	}

	public String getAverageMonth() {
		return averageMonth;
	}

	public String getUnfulliableRate() {
		return unfulliableRate;
	}

	public String getReplacementRate() {
		return replacementRate;
	}

	public String getDutyRate() {
		return dutyRate;
	}
	
	public String getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(String returnRate) {
		this.returnRate = returnRate;
	}

	public String[] getAmzFbas() {
		return formateData(this.amzFba);
	}

	public String[] getEfnFees() {
		return formateData(this.efnFee);
	}

	public String[] getDutyRates() {
		return formateData(this.dutyRate);
	}

	private String[] formateData(String data) {
		if (null != data) {
			return data.split(",");
		}
		return new String[] {};
	}

	private Boolean isEmpty() {
		if (this.getClearPrices().length == 0) {
			return this.getCountryIds().length == 9 && this.getCategorys().length == 9
					&& this.getTurnoverRates().length == 9 && this.getQtyOrdereds().length == 9
					&& this.getAverageMonths().length == 9 && this.getUnfulliableRates().length == 9
					&& this.getReplacementRates().length == 9 && this.getDutyRates().length == 9
					&& this.getAmzFbas().length == 9 && this.getEfnFees().length == 9;
		} else {
			return this.getCountryIds().length == 9 && this.getCategorys().length == 9
					&& this.getTurnoverRates().length == 9 && this.getQtyOrdereds().length == 9
					&& this.getAverageMonths().length == 9 && this.getUnfulliableRates().length == 9
					&& this.getReplacementRates().length == 9 && this.getDutyRates().length == 9
					&& this.getClearPrices().length == 9 && this.getAmzFbas().length == 9
					&& this.getEfnFees().length == 9;
		}
	}
}