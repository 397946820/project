package com.it.ocs.salereport.model;

import java.io.Serializable;

public class InventoryWarningModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id ;
	private String  sku;
	private String  sku_and_scode;
	private String  sku_add_time;
	private String  scode;
	private String  has_sale;
	private String  ship_type;
	private String  product_level;
	private String  field;//系列
	private String  platform ;
	private String  oversize ;
	private String  scode_ship_oversize; //仓库代码&运输方式&oversize
	private Integer opt_time = 0; //缓冲时长
	private Integer ship_time = 0;//物流时长
	private Integer listing_time = 0 ;//上架时长
	private Integer ship_cycle_time = 0;//物流周期
	private Integer purchase_cycle_time =50 ; //采购周期
	private Integer cycle_time =0;//供应链总时长
	private Double coeff=1.0; // 系列预警系数
	private Double coeff1 = 1.0;//品类运营系数
	private Integer save_ity =0;//安全库存量
	private Integer sale_day =0;//可销售天数
	private Double qty_one_day =0d;//最近3天、7天或30天未断货的日均销量（取大值
	private Integer avg_3_day = 0 ;
	private Integer avg_7_day = 0;
	private Integer avg_30_day =0 ;
	private String  is_dis ;//discontinue
	private Integer outer_stock =0;
	private Integer quantity =0;
	private Integer inbound  =0;
	private Integer receiving =0;
	private Integer reserved =0;
	private Integer reserved_sale=0 ;
	private Integer month_days_sales=0 ;
	private Integer avg_days_sales =0;
	private Integer bad_day = 0;
	private String is_bad ;//是否断货
	private Integer total_sales =0 ;
	private Integer last_month_bad =0;
	private String createDate;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSku_and_scode() {
		return sku_and_scode;
	}
	public void setSku_and_scode(String sku_and_scode) {
		this.sku_and_scode = sku_and_scode;
	}
	public String getSku_add_time() {
		return sku_add_time;
	}
	public void setSku_add_time(String sku_add_time) {
		this.sku_add_time = sku_add_time;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getHas_sale() {
		return has_sale;
	}
	public void setHas_sale(String has_sale) {
		this.has_sale = has_sale;
	}
	public String getShip_type() {
		return ship_type;
	}
	public void setShip_type(String ship_type) {
		this.ship_type = ship_type;
	}
	public String getProduct_level() {
		return product_level;
	}
	public void setProduct_level(String product_level) {
		this.product_level = product_level;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getOversize() {
		return oversize;
	}
	public void setOversize(String oversize) {
		this.oversize = oversize;
	}
	public String getScode_ship_oversize() {
		return scode_ship_oversize;
	}
	public void setScode_ship_oversize(String scode_ship_oversize) {
		this.scode_ship_oversize = scode_ship_oversize;
	}
	public Integer getOpt_time() {
		return opt_time;
	}
	public void setOpt_time(Integer opt_time) {
		this.opt_time = opt_time;
	}
	public Integer getShip_time() {
		return ship_time;
	}
	public void setShip_time(Integer ship_time) {
		this.ship_time = ship_time;
	}
	public Integer getListing_time() {
		return listing_time;
	}
	public void setListing_time(Integer listing_time) {
		this.listing_time = listing_time;
	}
	public Integer getShip_cycle_time() {
		return ship_cycle_time;
	}
	public void setShip_cycle_time(Integer ship_cycle_time) {
		this.ship_cycle_time = ship_cycle_time;
	}
	public Integer getPurchase_cycle_time() {
		return purchase_cycle_time;
	}
	public void setPurchase_cycle_time(Integer purchase_cycle_time) {
		this.purchase_cycle_time = purchase_cycle_time;
	}
	public Integer getCycle_time() {
		return cycle_time;
	}
	public void setCycle_time(Integer cycle_time) {
		this.cycle_time = cycle_time;
	}
	
	public Double getCoeff() {
		return null == coeff?0:coeff;
	}
	public void setCoeff(Double coeff) {
		this.coeff = coeff;
	}
	public Double getCoeff1() {
		return coeff1;
	}
	public void setCoeff1(Double coeff1) {
		this.coeff1 = coeff1;
	}
	public Double getQty_one_day() {
		return qty_one_day;
	}
	public void setQty_one_day(Double qty_one_day) {
		this.qty_one_day = qty_one_day;
	}
	public Integer getSave_ity() {
		return save_ity;
	}
	public void setSave_ity(Integer save_ity) {
		this.save_ity = save_ity;
	}
	public Integer getSale_day() {
		return sale_day;
	}
	public void setSale_day(Integer sale_day) {
		this.sale_day = sale_day;
	}
	
	public Integer getAvg_3_day() {
		return avg_3_day;
	}
	public void setAvg_3_day(Integer avg_3_day) {
		this.avg_3_day = avg_3_day;
	}
	public Integer getAvg_7_day() {
		return avg_7_day;
	}
	public void setAvg_7_day(Integer avg_7_day) {
		this.avg_7_day = avg_7_day;
	}
	public Integer getAvg_30_day() {
		return avg_30_day;
	}
	public void setAvg_30_day(Integer avg_30_day) {
		this.avg_30_day = avg_30_day;
	}
	public String getIs_dis() {
		return is_dis;
	}
	public void setIs_dis(String is_dis) {
		this.is_dis = is_dis;
	}
	public Integer getOuter_stock() {
		return outer_stock;
	}
	public void setOuter_stock(Integer outer_stock) {
		this.outer_stock = outer_stock;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getInbound() {
		return inbound;
	}
	public void setInbound(Integer inbound) {
		this.inbound = inbound;
	}
	public Integer getReceiving() {
		return receiving;
	}
	public void setReceiving(Integer receiving) {
		this.receiving = receiving;
	}
	public Integer getReserved() {
		return reserved;
	}
	public void setReserved(Integer reserved) {
		this.reserved = reserved;
	}
	public Integer getReserved_sale() {
		return reserved_sale;
	}
	public void setReserved_sale(Integer reserved_sale) {
		this.reserved_sale = reserved_sale;
	}
	public Integer getMonth_days_sales() {
		return month_days_sales;
	}
	public void setMonth_days_sales(Integer month_days_sales) {
		this.month_days_sales = month_days_sales;
	}
	public Integer getAvg_days_sales() {
		return avg_days_sales;
	}
	public void setAvg_days_sales(Integer avg_days_sales) {
		this.avg_days_sales = avg_days_sales;
	}
	public Integer getBad_day() {
		return bad_day;
	}
	public void setBad_day(Integer bad_day) {
		this.bad_day = bad_day;
	}
	public String getIs_bad() {
		return is_bad;
	}
	public void setIs_bad(String is_bad) {
		this.is_bad = is_bad;
	}
	public Integer getTotal_sales() {
		return total_sales;
	}
	public void setTotal_sales(Integer total_sales) {
		this.total_sales = total_sales;
	}
	public Integer getLast_month_bad() {
		return last_month_bad;
	}
	public void setLast_month_bad(Integer last_month_bad) {
		this.last_month_bad = last_month_bad;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
    
     
}
