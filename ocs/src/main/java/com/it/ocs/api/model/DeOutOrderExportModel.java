package com.it.ocs.api.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class DeOutOrderExportModel {

	@ExcelLink(title = "ID", index = 1)
	private long id;

	@ExcelLink(title = "来源", index = 2)
	private String platform;

	@ExcelLink(title = "Order Ocs Id", index = 3)
	private long order_ocs_id;

	@ExcelLink(title = "出库单类型", index = 4)
	private String order_out_type;
	
	@ExcelLink(title = "跟踪号状态", index = 5)
	private String is_upload;
	
	@ExcelLink(title = "Order Id", index = 6)
	private String order_id;
	
	@ExcelLink(title = "warehouseId", index = 7)
	private String warehouse_id;
	
	@ExcelLink(title = "跟踪号", index = 8)
	private String tracking_number;
	
	@ExcelLink(title = "发货时间", index = 9)
	private Date ship_date;
	
	@ExcelLink(title = "发货人", index = 10)
	private String ship_by;
	
	@ExcelLink(title = "发货仓库代码", index = 11)
	private String store_code;
	
	@ExcelLink(title = "订单创建日期", index = 12)
	private Date ocs_order_create_date;
	
	@ExcelLink(title = "物流承运商", index = 13)
	private String carrier_id;
	
	@ExcelLink(title = "客户名称", index = 14)
	private String customer_name;
	
	@ExcelLink(title = "客户国家", index = 15)
	private String customer_country;
	
	@ExcelLink(title = "客户省份", index = 16)
	private String customer_province;
	
	@ExcelLink(title = "客户城市", index = 17)
	private String customer_city;
	
	@ExcelLink(title = "客户地址1", index = 18)
	private String customer_address;

	@ExcelLink(title = "客户地址2", index = 19)
	private String customer_address_2;
	
	@ExcelLink(title = "客户邮编", index = 20)
	private String customer_zip;
	
	@ExcelLink(title = "客户Email", index = 21)
	private String customer_email;
	
	@ExcelLink(title = "客户电话", index = 22)
	private String customer_phone;
	
	@ExcelLink(title = "出库单状态", index = 23)
	private String is_send_wms;
	
	@ExcelLink(title = "是否异常", index = 24)
	private String is_abnormal;
	
	@ExcelLink(title = "推送WMS日期", index = 25)
	private Date send_date;
	
	@ExcelLink(title = "WMS反馈日期", index = 26)
	private Date feedback_date;

	@ExcelLink(title = "创建日期", index = 27)
	private Date created_date;
	
	@ExcelLink(title = "更新日期", index = 28)
	private Date update_date;

	@ExcelLink(title = "明细ID", index = 29)
	private long detail_id;

	@ExcelLink(title = "SKU", index = 30)
	private String sku;

	@ExcelLink(title = "Item Number", index = 31)
	private String item_number;

	@ExcelLink(title = "SKU数量", index = 32)
	private String item_qty;

	@ExcelLink(title = "货品属性", index = 33)
	private String sku_property;

	@ExcelLink(title = "版本", index = 34)
	private String item;

	@ExcelLink(title = "实际SKU数量", index = 35)
	private String actual_qty;

	@ExcelLink(title = "SKU售价", index = 36)
	private String price;
}
