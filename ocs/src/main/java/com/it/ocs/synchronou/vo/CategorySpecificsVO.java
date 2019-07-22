package com.it.ocs.synchronou.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.it.ocs.synchronou.model.EBayCategorySpecificsModel;

public class CategorySpecificsVO extends EBayCategorySpecificsModel implements Serializable {
	
	private String value ;
	private List<CategorySpecificsVVO> categorySpecificsVVOs;
	
	public List<CategorySpecificsVVO> getCategorySpecificsVVOs() {
		return categorySpecificsVVOs;
	}

	public void setCategorySpecificsVVOs(List<CategorySpecificsVVO> categorySpecificsVVOs) {
		this.categorySpecificsVVOs = categorySpecificsVVOs;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	
	
}
