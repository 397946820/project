package com.it.ocs.publication.vo;

import java.io.Serializable;
import java.util.Map;

import com.it.ocs.publication.model.TransChooseModel;

public class TransChooseVO extends TransChooseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1184911098520732723L;


	private Map<String, Object> calCulateObj;// 运费计算项

	private Map<String, Object> domestic;// 国内运输

	private Map<String, Object> international;// 国际运输


	public Map<String, Object> getCalCulateObj() {
		return calCulateObj;
	}

	public void setCalCulateObj(Map<String, Object> calCulateObj) {
		this.calCulateObj = calCulateObj;
	}

	public Map<String, Object> getDomestic() {
		return domestic;
	}

	public void setDomestic(Map<String, Object> domestic) {
		this.domestic = domestic;
	}

	public Map<String, Object> getInternational() {
		return international;
	}

	public void setInternational(Map<String, Object> international) {
		this.international = international;
	}

}
