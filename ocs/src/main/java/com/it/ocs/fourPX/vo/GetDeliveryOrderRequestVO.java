package com.it.ocs.fourPX.vo;

public class GetDeliveryOrderRequestVO extends Base4PXVO {

	private String orderCode;
	private String referenceCode;
	
	public GetDeliveryOrderRequestVO() {
		
	}
	
	public GetDeliveryOrderRequestVO(String orderCode, String referenceCode) {
		this.orderCode = orderCode;
		this.referenceCode = referenceCode;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getReferenceCode() {
		return referenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	
	
}
