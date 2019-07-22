package com.it.ocs.sys.vo;

import java.io.Serializable;
import java.util.List;

import com.it.ocs.sys.model.ItemDataModel;

public class EasyUITreeVO extends ItemDataModel implements Serializable{
	private String text;
	private String state;
	private List<EasyUITreeVO> children;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<EasyUITreeVO> getChildren() {
		return children;
	}
	public void setChildren(List<EasyUITreeVO> children) {
		this.children = children;
	}
	
	
}
