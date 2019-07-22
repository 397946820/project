package com.it.ocs.amazon.model;

import java.util.List;

public class ReportRequestListModel {
	private Integer id;
	private String reportGetId;
	private String platform;
	private String filePathName;
	private String site;
	private Integer isParse;
	private String startDate;
	private String endDate;
	private Integer isGetData;
	private List<ReportRequestListModel> children;
	private String reportType;
	private String generateMode; // 枚举值: 'sys_auto' - 系统自动生成
	
	public String getGenerateMode() {
		return generateMode;
	}
	public void setGenerateMode(String generateMode) {
		this.generateMode = generateMode;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReportGetId() {
		return reportGetId;
	}
	public void setReportGetId(String reportGetId) {
		this.reportGetId = reportGetId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getFilePathName() {
		return filePathName;
	}
	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Integer getIsParse() {
		return isParse;
	}
	public void setIsParse(Integer isParse) {
		this.isParse = isParse;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getIsGetData() {
		return isGetData;
	}
	public void setIsGetData(Integer isGetData) {
		this.isGetData = isGetData;
	}
	public List<ReportRequestListModel> getChildren() {
		return children;
	}
	public void setChildren(List<ReportRequestListModel> children) {
		this.children = children;
	}
	public boolean hasChild(){
		if(null == children||children.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	
}
