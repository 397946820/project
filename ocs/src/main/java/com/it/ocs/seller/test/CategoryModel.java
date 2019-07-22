package com.it.ocs.seller.test;

public class CategoryModel {

	private String leafCategoryTreeNode;
	private String categoryTreeNodeLevel;
	private String parentCategoryTreeNodeHref;
	private String categoryName;
	private String categoryId;
	private Long marketplace_id;
	private String pcategoryId;
	private String childCategoryTreeNodes;

	private Long categorytreeversion;
	
	public  void allToString(){
		
		System.out.println("categoryId:"+categoryId);
		System.out.println("categoryName:"+categoryName);
		System.out.println("leafCategoryTreeNode:"+leafCategoryTreeNode);
		System.out.println("categoryTreeNodeLevel:"+categoryTreeNodeLevel);
		System.out.println("parentCategoryTreeNodeHref:"+parentCategoryTreeNodeHref);
		System.out.println("childCategoryTreeNodes:" + childCategoryTreeNodes);
		System.out.println("marketplace_id:"+marketplace_id);
		System.out.println("pcategoryId:"+pcategoryId);
		System.out.println("categorytreeversion:"+categorytreeversion);
		System.out.println("--------------------------------------");
	};
	
	public Long getCategorytreeversion() {
		return categorytreeversion;
	}

	public void setCategorytreeversion(Long categorytreeversion) {
		this.categorytreeversion = categorytreeversion;
	}

	public String getLeafCategoryTreeNode() {
		return leafCategoryTreeNode;
	}
	public void setLeafCategoryTreeNode(String leafCategoryTreeNode) {
		this.leafCategoryTreeNode = leafCategoryTreeNode;
	}
	public String getCategoryTreeNodeLevel() {
		return categoryTreeNodeLevel;
	}
	public void setCategoryTreeNodeLevel(String categoryTreeNodeLevel) {
		this.categoryTreeNodeLevel = categoryTreeNodeLevel;
	}
	public String getParentCategoryTreeNodeHref() {
		return parentCategoryTreeNodeHref;
	}
	public void setParentCategoryTreeNodeHref(String parentCategoryTreeNodeHref) {
		this.parentCategoryTreeNodeHref = parentCategoryTreeNodeHref;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public Long getMarketplace_id() {
		return marketplace_id;
	}

	public void setMarketplace_id(Long marketplace_id) {
		this.marketplace_id = marketplace_id;
	}

	public String getPcategoryId() {
		return pcategoryId;
	}
	public void setPcategoryId(String pcategoryId) {
		this.pcategoryId = pcategoryId;
	}
	public String getChildCategoryTreeNodes() {
		return childCategoryTreeNodes;
	}
	public void setChildCategoryTreeNodes(String childCategoryTreeNodes) {
		this.childCategoryTreeNodes = childCategoryTreeNodes;
	}
}
