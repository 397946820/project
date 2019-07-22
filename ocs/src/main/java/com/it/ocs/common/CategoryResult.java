package com.it.ocs.common;

import java.io.Serializable;

public class CategoryResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -782081421240434295L;
	private String name;
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
