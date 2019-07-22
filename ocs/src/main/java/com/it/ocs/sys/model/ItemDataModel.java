package com.it.ocs.sys.model;

import com.it.ocs.common.model.BaseModel;

public class ItemDataModel extends BaseModel{
	private Long parentId;
	private String name;
	private String displayName;
	private String link;
	private String linkView;
	private String formatSql;

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLinkView() {
		return linkView;
	}
	public void setLinkView(String linkView) {
		this.linkView = linkView;
	}
	public String getFormatSql() {
		return formatSql;
	}
	public void setFormatSql(String formatSql) {
		this.formatSql = formatSql;
	}
	
	
}
