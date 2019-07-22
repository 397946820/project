package com.it.ocs.salereport.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class InventoryWarningExportModel {
	@ExcelLink(title = "SSKU", index = 0)
	private String  sku;
	@ExcelLink(title = "SKU&仓库代码", index = 1)
	private String  sku_and_scode;
	@ExcelLink(title = "增加日期", index = 2)
	private String  sku_add_time;
	@ExcelLink(title = "仓库代码", index = 3)
	private String  scode;
	@ExcelLink(title = "是否有到货记录", index = 4)
	private String  has_sale;
	@ExcelLink(title = "运输方式", index = 5)
	private String  ship_type;
	@ExcelLink(title = "产品等级", index = 6)
	private String  product_level;
	@ExcelLink(title = "系列", index = 7)
	private String  field;//系列
	@ExcelLink(title = "平台", index = 8)
	private String  platform ;
	@ExcelLink(title = "尺寸标准", index = 9)
	private String  oversize ;
	@ExcelLink(title = "仓库代码&运输方式&oversize", index = 10)
	private String  scode_ship_oversize; //仓库代码&运输方式&oversize
	@ExcelLink(title = "缓冲时长", index = 11)
	private Integer opt_time; //缓冲时长
	@ExcelLink(title = "物流时长", index = 12)
	private Integer ship_time;//物流时长
	@ExcelLink(title = "上架时长", index = 13)
	private Integer listing_time;//上架时长
	@ExcelLink(title = "物流周期", index = 14)
	private Integer ship_cycle_time;//物流周期
	@ExcelLink(title = "采购周期", index = 15)
	private Integer purchase_cycle_time; //采购周期
	@ExcelLink(title = "供应链总时长", index = 16)
	private Integer cycle_time;//供应链总时长
	@ExcelLink(title = "系列预警系数", index = 17)
	private Double coeff; // 系列预警系数
	@ExcelLink(title = "类目运营系数", index = 18)
	private Double coeff1;//品类运营系数
	@ExcelLink(title = "安全库存量扣除国外库存", index = 19)
	private Integer save_ity;//安全库存量
	@ExcelLink(title = "可销售天数", index = 20)
	private Integer sale_day;//可销售天数
	@ExcelLink(title = "最大日均销量", index = 21)
	private Double qty_one_day;//最近3天、7天或30天未断货的日均销量（取大值
	@ExcelLink(title = "最近3天销量", index = 22)
	private Integer avg_3_day;
	@ExcelLink(title = "最近7天销量", index = 23)
	private Integer avg_7_day;
	@ExcelLink(title = "最近30天销量", index = 24)
	private Integer avg_30_day;
	@ExcelLink(title = "是否续卖", index = 25)
	private String  is_dis ;//discontinue
	@ExcelLink(title = "海外库存", index = 26)
	private Integer outer_stock;
	@ExcelLink(title = "Quantity", index = 27)
	private Integer quantity;
	@ExcelLink(title = "Inbound", index = 28)
	private Integer inbound;
	@ExcelLink(title = "receiving", index = 29)
	private Integer receiving;
	@ExcelLink(title = "reserved", index = 30)
	private Integer reserved;
	@ExcelLink(title = "reserved能卖", index = 31)
	private Integer reserved_sale;
	@ExcelLink(title = "Mon-Days Sales", index = 32)
	private Integer month_days_sales;
	@ExcelLink(title = "Avg. Days Sales", index = 33)
	private Integer avg_days_sales;
	@ExcelLink(title = "本月断货天数", index = 34)
	private Integer bad_day;
	@ExcelLink(title = "是否断货", index = 35)
	private String is_bad ;//是否断货
	@ExcelLink(title = "半年累计销量", index = 36)
	private Integer total_sales ;
	@ExcelLink(title = "上月断货天数", index = 37)
	private Integer last_month_bad;
}
