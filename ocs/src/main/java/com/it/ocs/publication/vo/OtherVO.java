package com.it.ocs.publication.vo;

import java.io.Serializable;
import java.util.Map;

import com.it.ocs.publication.model.OtherModel;

public class OtherVO extends OtherModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5422388780387216280L;
	private Map<String, Object> salesTaxInfo;
	

	public Map<String, Object> getSalesTaxInfo() {
		return salesTaxInfo;
	}

	public void setSalesTaxInfo(Map<String, Object> salesTaxInfo) {
		this.salesTaxInfo = salesTaxInfo;
	}

}
