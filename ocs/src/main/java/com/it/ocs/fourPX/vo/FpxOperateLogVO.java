package com.it.ocs.fourPX.vo;

import com.it.ocs.fourPX.model.FpxOperLogModel;

public class FpxOperateLogVO extends FpxOperLogModel implements java.io.Serializable {

	private static final long serialVersionUID = -6722693210006403444L;

	private String orderId;
	private String operatorText;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOperatorText() {
		return operatorText;
	}
	public void setOperatorText(String operatorText) {
		this.operatorText = operatorText;
	}
	
}
