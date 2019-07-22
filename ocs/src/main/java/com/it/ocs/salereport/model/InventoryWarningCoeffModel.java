package com.it.ocs.salereport.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class InventoryWarningCoeffModel {
	@ExcelLink(title = "系列", index = 0)
	private String field;
	@ExcelLink(title = "平台(EU,US)", index = 1)
	private String platform;
	@ExcelLink(title = "运输方式(AF,CO,SF)", index = 2)
	private String ship_type;
	@ExcelLink(title = "预警系数", index = 3)
	private Double coeff;
}
