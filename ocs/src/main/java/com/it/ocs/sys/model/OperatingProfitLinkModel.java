package com.it.ocs.sys.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitLinkModel {
	private Long entityId;

	@ExcelLink(title = "平台", index = 0)
	private String platform;

	@ExcelLink(title = "类目", index = 1)
	private String category;

	@ExcelLink(title = "US", index = 2)
	private String us;

	@ExcelLink(title = "GB", index = 3)
	private String gb;

	@ExcelLink(title = "DE", index = 4)
	private String de;

	@ExcelLink(title = "FR", index = 5)
	private String fr;

	@ExcelLink(title = "ES", index = 6)
	private String es;

	@ExcelLink(title = "IT", index = 7)
	private String it;

	@ExcelLink(title = "JP", index = 8)
	private String jp;

	@ExcelLink(title = "CA", index = 9)
	private String ca;

	private String country;

	private String nick;

	private String monthOfYear;

	private Date createdAt;

	private Date updatedAt;

	public OperatingProfitLinkModel() {
		super();
	}

	public OperatingProfitLinkModel(String platform, String category, String monthOfYear) {
		this.platform = platform;
		this.category = category;
		this.monthOfYear = monthOfYear;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", entityId=").append(entityId);
		sb.append(", platform=").append(platform);
		sb.append(", category=").append(category);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append("]");
		return sb.toString();
	}

	public String getUs() {
		return us;
	}

	public void setUs(String us) {
		this.us = us;
	}

	public String getGb() {
		return gb;
	}

	public void setGb(String gb) {
		this.gb = gb;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getFr() {
		return fr;
	}

	public void setFr(String fr) {
		this.fr = fr;
	}

	public String getEs() {
		return es;
	}

	public void setEs(String es) {
		this.es = es;
	}

	public String getIt() {
		return it;
	}

	public void setIt(String it) {
		this.it = it;
	}

	public String getJp() {
		return jp;
	}

	public void setJp(String jp) {
		this.jp = jp;
	}

	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

}
