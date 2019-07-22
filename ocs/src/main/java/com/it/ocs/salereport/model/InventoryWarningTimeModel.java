package com.it.ocs.salereport.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class InventoryWarningTimeModel {
	@ExcelLink(title = "仓库代码&运输方式&OVERSIZE（全大写）", index = 0)
	private String scode_ship_oversize;
	@ExcelLink(title = "物流时长", index = 1)
	private Integer ship_time;
	
}
