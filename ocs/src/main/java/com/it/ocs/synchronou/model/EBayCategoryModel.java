package com.it.ocs.synchronou.model;




import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class EBayCategoryModel extends BaseModel {

	
	private Long category_id;
	
	private Long marketplace_id;
	
	private Long parent_category_id;
	
	private String leaf_category_tree_node;
	
	private Long category_tree_node_level;
	
	private String parent_category_tree_node_href;
	
	private Long categorytreeversion;
	
	private String child_category_tree_nodes;
	
	private  Date last_update_date = new Date();
	
	
	
	public Date getLast_update_date() {
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	public String getChild_category_tree_nodes() {
		return child_category_tree_nodes;
	}
	public void setChild_category_tree_nodes(String child_category_tree_nodes) {
		this.child_category_tree_nodes = child_category_tree_nodes;
	}
	public Long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}
	public Long getMarketplace_id() {
		return marketplace_id;
	}
	public void setMarketplace_id(Long marketplace_id) {
		this.marketplace_id = marketplace_id;
	}
	public Long getParent_category_id() {
		return parent_category_id;
	}
	public void setParent_category_id(Long parent_category_id) {
		this.parent_category_id = parent_category_id;
	}
	public String getLeaf_category_tree_node() {
		return leaf_category_tree_node;
	}
	public void setLeaf_category_tree_node(String leaf_category_tree_node) {
		this.leaf_category_tree_node = leaf_category_tree_node;
	}
	public Long getCategory_tree_node_level() {
		return category_tree_node_level;
	}
	public void setCategory_tree_node_level(Long category_tree_node_level) {
		this.category_tree_node_level = category_tree_node_level;
	}
	public String getParent_category_tree_node_href() {
		return parent_category_tree_node_href;
	}
	public void setParent_category_tree_node_href(String parent_category_tree_node_href) {
		this.parent_category_tree_node_href = parent_category_tree_node_href;
	}
	public Long getCategorytreeversion() {
		return categorytreeversion;
	}
	public void setCategorytreeversion(Long categorytreeversion) {
		this.categorytreeversion = categorytreeversion;
	}
	
}
