package com.it.ocs.discount.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.it.ocs.discount.model.DiscountModel;

public class DiscountVo extends DiscountModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4409270220278355601L;

	private String validity;

	private String start;

	private String end;

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
