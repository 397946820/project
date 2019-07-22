package com.it.ocs.publication.model;

import org.dom4j.Element;

import com.it.ocs.common.model.BaseModel;

public class ItemDataStructureModel extends BaseModel{
	
	private Long id;
	private String name;
	private Long parentId;
	private String path;
	private String link;
	private String contentXML;
	private Element item;
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getContentXML() {
		return contentXML;
	}
	public void setContentXML(String contentXML) {
		this.contentXML = contentXML;
	}
	public Element getItem() {
		return item;
	}
	public void setItem(Element item) {
		this.item = item;
	}
	
}
