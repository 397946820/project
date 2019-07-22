package com.it.ocs.pic.vo;

import java.io.Serializable;
import java.util.List;

import com.it.ocs.pic.model.PicCategoryModel;

public class PicCategoryVO extends PicCategoryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -627055626075866711L;

	private String state;// 控件绑定属性，用来显示数据折叠,可选值closed
	private List<PicCategoryVO> children;
	private String parentName;
	private String text;
	
	@Override
	public String getText() {
		return text;
	}
	@Override
	public void setText(String text) {
		this.text = text;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<PicCategoryVO> getChildren() {
		return children;
	}

	public void setChildren(List<PicCategoryVO> children) {
		this.children = children;
	}

}
