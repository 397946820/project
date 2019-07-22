package com.it.ocs.cal.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class ProductInfoImportModel {
	@ExcelLink(title="SKU",index=0)
	private String sku;
	@ExcelLink(title="国家",index=1)
	private String country;
	@ExcelLink(title="产品分类",index=2)
	private String category;
	@ExcelLink(title="不可用率",index=3)
	private String unavailability;//不可用率
	@ExcelLink(title="退货率",index=4)
	private String returnRate;//退货率
	@ExcelLink(title="补发率",index=5)
	private String rechargeRate;//补发率
	@ExcelLink(title="资金占用率",index=6)
	private String capitalOccupancyRate;//资金占用率
	
	//private String isPEU;
}
