package com.it.ocs.salereport.model;

import java.util.Calendar;

public class PlanBaseModel {
	private String ship_type;
	private String  scode_ship_oversize; //仓库代码&运输方式&oversize
	private Integer opt_time = 0; //缓冲时长
	private Integer ship_time = 0;//物流时长
	private Integer listing_time = 0 ;//上架时长
	private Integer ship_cycle_time = 0;//物流周期
	private Integer purchase_cycle_time = 50 ; //采购周期
	private Integer cycle_time ;//供应链总时长
	
	
	public String getShip_type() {
		return ship_type;
	}
	public void setShip_type(String ship_type) {
		this.ship_type = ship_type;
	}
	public String getScode_ship_oversize() {
		return scode_ship_oversize;
	}
	public void setScode_ship_oversize(String scode_ship_oversize) {
		this.scode_ship_oversize = scode_ship_oversize;
	}
	public Integer getOpt_time() {
		if(null == this.ship_type || "".equals(this.ship_type)){
			opt_time = 0;
		}else{
			if("SF".equals(this.ship_type.toUpperCase())){
				opt_time = 6;
			}else{
				opt_time = 3;
			}
		}
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
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH)+1;
		switch(month){
		case 10 :
			listing_time = 6;
			break;
		case 11 :
			listing_time = 8;
			break;
		case 12 :
			listing_time = 8;
			break;
		default:
			listing_time = 4;
		}
		return listing_time;
	}
	
	public void setListing_time(Integer listing_time) {
		this.listing_time = listing_time;
	}
	public Integer getShip_cycle_time() {
		ship_cycle_time = this.getListing_time() + this.getShip_time();
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
		
		cycle_time = this.getListing_time() + this.ship_time+ this.getOpt_time()+this.purchase_cycle_time;
		return cycle_time;
	}
	public void setCycle_time(Integer cycle_time) {
		this.cycle_time = cycle_time;
	}
	
	
}
