package com.it.ocs.salereport.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class InventoryWarningSetModel {
	@ExcelLink(title = "SKU", index = 0)
	private String sku;
	@ExcelLink(title = "仓库代码（全大写）", index = 1)
	private String scode;
	@ExcelLink(title = "运输方式（AF,SF,CO）", index = 2)
	private String ship_type;
}
