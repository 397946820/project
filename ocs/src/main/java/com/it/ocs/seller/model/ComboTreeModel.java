package com.it.ocs.seller.model;

import java.util.ArrayList;
import java.util.List;

public class ComboTreeModel {
	private Long id;
	private String text;
	private String state;
	private String checked;
	private Long parentId;
	private List<ComboTreeModel> children ;
	public ComboTreeModel(){
		checked = "false";
		state = "open";
		children = new ArrayList<ComboTreeModel>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

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
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public List<ComboTreeModel> getChildren() {
		return children;
	}
	public void setChildren(List<ComboTreeModel> children) {
		this.children = children;
	}
	

	public static List<ComboTreeModel> formatData(List<ComboTreeModel> treeDatas,long rootId){
		List<ComboTreeModel> trees = new ArrayList<>();
		for(ComboTreeModel treeData : treeDatas){
			if(rootId == treeData.getParentId()){
				List<ComboTreeModel> children = formatData(treeDatas,treeData.getId());
				if(children.size()==0){
					treeData.setState("open");
				}
				treeData.setChildren(children);
				trees.add(treeData);
			}
		}
		return trees;
	}
}
