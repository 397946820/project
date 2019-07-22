package com.it.ocs.cal.model;

public class StaticPriceModel {
	
	private String sku;
	
	private String countryId;
	
	private Double length;//长
	
	private Double width;//宽
	
	private Double height;//高
	
	private Double nw;//净重
	
	private Double gw;//毛重
	
	private Integer qtyPackage;//装箱数量
	
	private Double v;//体积
	
	private Double weight;//实重  ------
	
	private Double outsideBoxVolume;//外箱体积
	
	private Double outsideBoxWeight;//外箱重量
	
	private Double exchangeRate;//RMB汇率
	
	private Double taxRebateRate; //退税率
	
	private Double capitalOccupancyRate;//资金占用率 INTEREST_RATE
	
	private Double averageMonth;//平均存储月份
	
	private Double unfulliableRate;//不可用率
	
	private Double returnRate; //退货率
	
	private Double replacementRate;//补发率
	
	private Double dutyRate;//关税税率
	
	private Double calculateFactor;//清关系数 ------
	
	private Double clearPrice;//清关价
	
	private Double erpPrice;//通过ERP的采购成本
	
	private String eroCurrency;//erp采购价货币
	
	private Double price;//参与建议售价运算的采购成本
	
	private boolean isOversize;
	
	/////空运
	/**
	 *  体积重
		FBA费用
		仓租费
		运费
		到岸价
		最终成本
		建议售价
	 */
	
	
}
