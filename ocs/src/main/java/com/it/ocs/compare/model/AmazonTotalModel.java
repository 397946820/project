package com.it.ocs.compare.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class AmazonTotalModel {

	@ExcelLink(title = "excel中的总金额", index = 0)
	private Double excelTotalPrice;
	@ExcelLink(title = "数据库中的总金额", index = 1)
	private Double dbTotalPrice;
	@ExcelLink(title = "数据库中不存在的总金额", index = 2)
	private Double dbNotExistTotalPrice;
	@ExcelLink(title = "excel中不存在的总金额", index = 3)
	private Double excelNotExistTotalPrice;
	@ExcelLink(title = "价格不同数据的差异数据", index = 4)
	private String diffPriceTotalPrice;
	
	
	public String getDiffPriceTotalPrice() {
		return diffPriceTotalPrice;
	}
	public void setDiffPriceTotalPrice(String diffPriceTotalPrice) {
		this.diffPriceTotalPrice = diffPriceTotalPrice;
	}
	public Double getExcelTotalPrice() {
		return excelTotalPrice;
	}
	public void setExcelTotalPrice(Double excelTotalPrice) {
		this.excelTotalPrice = excelTotalPrice;
	}
	public Double getDbTotalPrice() {
		return dbTotalPrice;
	}
	public void setDbTotalPrice(Double dbTotalPrice) {
		this.dbTotalPrice = dbTotalPrice;
	}
	public Double getDbNotExistTotalPrice() {
		return dbNotExistTotalPrice;
	}
	public void setDbNotExistTotalPrice(Double dbNotExistTotalPrice) {
		this.dbNotExistTotalPrice = dbNotExistTotalPrice;
	}
	public Double getExcelNotExistTotalPrice() {
		return excelNotExistTotalPrice;
	}
	public void setExcelNotExistTotalPrice(Double excelNotExistTotalPrice) {
		this.excelNotExistTotalPrice = excelNotExistTotalPrice;
	}
	
	
}
