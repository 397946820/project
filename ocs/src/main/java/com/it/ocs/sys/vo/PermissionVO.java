package com.it.ocs.sys.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.sys.model.PermissionModel;

public class PermissionVO extends PermissionModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6974621642770674282L;

	private String projectName;
	private String state;// 控件绑定属性，用来显示数据折叠,可选值closed
	private List<PermissionVO> children = Lists.newArrayList();
	private String parentName;
	private String text;
	private boolean checked = false;
	private Map<String, Object> attributes = Maps.newConcurrentMap();
	
	public PermissionVO(){}
	
	public PermissionVO(Long id,String text) {
		this.setId(id);
		setName(text);
	}
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<PermissionVO> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionVO> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
