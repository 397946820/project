package com.it.ocs.synchronou.test;

public class StoreModel {
	private Long id;
	private Long category_id;
	private String name;
	private Long parent_category_id;
	private Long category_order;
	private String child_category;
	
	public void show(){
		
		System.out.println("id: "+id);
		System.out.println("category_id: "+category_id);
		System.out.println("name: "+name);
		System.out.println("parent_category_id: "+parent_category_id);
		System.out.println("category_order: "+category_order);
		System.out.println("child_category: "+child_category);
		System.out.println("-------------------------------------");
	}
	
	public String getChild_category() {
		return child_category;
	}

	public void setChild_category(String child_category) {
		this.child_category = child_category;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParent_category_id() {
		return parent_category_id;
	}
	public void setParent_category_id(Long parent_category_id) {
		this.parent_category_id = parent_category_id;
	}
	public Long getCategory_order() {
		return category_order;
	}
	public void setCategory_order(Long category_order) {
		this.category_order = category_order;
	}
	
}
