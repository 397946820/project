package com.it.ocs.cal.vo;

import java.io.Serializable;

import com.it.ocs.cal.model.FbaRuleModel;

public class FbaRuleVo extends FbaRuleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1090637922752905240L;

	private String size;

	private String condition;

	private String other;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}
