package com.it.ocs.fourPX.vo;

import com.it.ocs.fourPX.model.FpxAbnormalModel;

public class FpxAbnormalVO extends FpxAbnormalModel implements java.io.Serializable {

	private static final long serialVersionUID = 4839644701900426349L;
	
	private String parentOrderId;

	public String getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

}
