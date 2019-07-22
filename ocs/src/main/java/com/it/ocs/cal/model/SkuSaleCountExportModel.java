package com.it.ocs.cal.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class SkuSaleCountExportModel {
	@ExcelLink(title="类目",index=0)
	private String field;
	@ExcelLink(title="亚马逊销售数量",index=1)
    private String amazonQty;
	@ExcelLink(title="亚马逊销售总额($)",index=2)
    private String amazonPrice;
	@ExcelLink(title="类目占比（亚马逊)",index=3)
    private String amazonPEC;
	@ExcelLink(title="Ebay销售数量",index=4)
    private String ebayQty;
	@ExcelLink(title="Ebay销售总额($)",index=5)
    private String ebayPrice;
	@ExcelLink(title="类目占比（Ebay)",index=6)
    private String ebayPEC;
	@ExcelLink(title="官网销售数量",index=7)
    private String lightQty;
	@ExcelLink(title="官网销售总额($)",index=8)
    private String lightPrice;
	@ExcelLink(title="类目占比（官网)",index=9)
    private String lightPEC;
	@ExcelLink(title="合计",index=10)
    private String total;
	@ExcelLink(title="类目占比（全平台)",index=11)
    private String totalPEC;
}
