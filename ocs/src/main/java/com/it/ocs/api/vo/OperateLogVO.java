package com.it.ocs.api.vo;

import com.it.ocs.api.model.DeWmsOperateLogModel;

public class OperateLogVO extends DeWmsOperateLogModel implements java.io.Serializable {

	private static final long serialVersionUID = -2101379878685762242L;

	private String orderId;
	private String orderOcsId;
	private String operatorText;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderOcsId() {
		return orderOcsId;
	}
	public void setOrderOcsId(String orderOcsId) {
		this.orderOcsId = orderOcsId;
	}
	public String getOperatorText() {
		return operatorText;
	}
	public void setOperatorText(String operatorText) {
		this.operatorText = operatorText;
	}
}
