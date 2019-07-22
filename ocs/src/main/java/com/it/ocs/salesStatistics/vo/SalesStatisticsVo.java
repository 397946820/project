package com.it.ocs.salesStatistics.vo;

import java.io.Serializable;
import java.util.Date;

import com.it.ocs.salesStatistics.model.SalesStatisticsModel;

public class SalesStatisticsVo extends SalesStatisticsModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8402023982292367024L;
	// 来源
	private String source = "";
	// 开始时间,接收前台传递的值
	private String starttime;
	// 结束时间,接收前台传递的值
	private String endtime;

	// 转化date的值
	private Date begintime;
	private Date stoptime;
	// 哪个时间
	private String whichTime;
	// 时间段
	private String timeQuantum;
	// 用来区分是汇总行还是普通行
	private boolean isFooter = false;

	private String details;

	private String currencySymbol;

	// 区分到底显示哪两个数据
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getStoptime() {
		return stoptime;
	}

	public void setStoptime(Date stoptime) {
		this.stoptime = stoptime;
	}

	public String getWhichTime() {
		return whichTime;
	}

	public void setWhichTime(String whichTime) {
		this.whichTime = whichTime;
	}

	public String getTimeQuantum() {
		return timeQuantum;
	}

	public void setTimeQuantum(String timeQuantum) {
		this.timeQuantum = timeQuantum;
	}

	public boolean isFooter() {
		return isFooter;
	}

	public void setFooter(boolean isFooter) {
		this.isFooter = isFooter;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

}
