package com.it.ocs.cal.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class ProductInfoAvgMonthImportModel {
	@ExcelLink(title="SKU",index=0)
	private String sku;
	@ExcelLink(title="国家",index=1)
	private String country;
	@ExcelLink(title="平均存储月份",index=2)
	private String avgMonth;;
}
