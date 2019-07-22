package com.it.ocs.amazon.model;

import com.it.ocs.excel.annotation.ExcelLink;

public class AmazonMyiUnsuppressedExportModel {

	@ExcelLink(title = "request_created_at", index = -3)
	private String request_created_at;
	
	@ExcelLink(title = "request_updated_at", index = -2)
	private String request_updated_at;

	@ExcelLink(title = "report_id", index = -1)
	private String report_id;

	@ExcelLink(title = "site", index = 0)
	private String site;
	
	@ExcelLink(title = "sku", index = 1)
	private String sku;
	
	@ExcelLink(title = "fnsku", index = 2)
	private String fnsku;
	
	@ExcelLink(title = "asin", index = 3)
	private String asin;
	
	@ExcelLink(title = "product_name", index = 4)
	private String product_name;
	
	@ExcelLink(title = "condition", index = 5)
	private String condition;
	
	@ExcelLink(title = "your_price", index = 6)
	private String your_price;
	
	@ExcelLink(title = "mfn_listing_exists", index = 7)
	private String mfn_listing_exists;
	
	@ExcelLink(title = "mfn_fulfillable_quantity", index = 8)
	private String mfn_fulfillable_quantity;
	
	@ExcelLink(title = "afn_listing_exists", index = 9)
	private String afn_listing_exists;
	
	@ExcelLink(title = "afn_warehouse_quantity", index = 10)
	private String afn_warehouse_quantity;
	
	@ExcelLink(title = "afn_fulfillable_quantity", index = 11)
	private String afn_fulfillable_quantity;
	
	@ExcelLink(title = "afn_unsellable_quantity", index = 12)
	private String afn_unsellable_quantity;
	
	@ExcelLink(title = "afn_reserved_quantity", index = 13)
	private String afn_reserved_quantity;
	
	@ExcelLink(title = "afn_total_quantity", index = 14)
	private String afn_total_quantity;
	
	@ExcelLink(title = "per_unit_volume", index = 15)
	private String per_unit_volume;
	
	@ExcelLink(title = "afn_inbound_working_quantity", index = 16)
	private String afn_inbound_working_quantity;
	
	@ExcelLink(title = "afn_inbound_shipped_quantity", index = 17)
	private String afn_inbound_shipped_quantity;
	
	@ExcelLink(title = "afn_inbound_receiving_quantity", index = 18)
	private String afn_inbound_receiving_quantity;

	@ExcelLink(title = "reserved_fc_transfers", index = 19)
	private String reserved_fc_transfers;
}
