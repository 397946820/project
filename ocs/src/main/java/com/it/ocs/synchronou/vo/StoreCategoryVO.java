package com.it.ocs.synchronou.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.it.ocs.synchronou.model.EBayStoreCategoryModel;

public class StoreCategoryVO extends EBayStoreCategoryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8545358728757866683L;
	
	
	private List<StoreCategoryVO> children = new ArrayList<>();

	private String parentName;
	
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<StoreCategoryVO> getChildren() {
		return children;
	}

	public void setChildren(List<StoreCategoryVO> children) {
		this.children = children;
	}
	

}
