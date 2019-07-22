package com.it.ocs.itemDescribe.model;

public class EBayInternalContentModel {
	
	private String title;
	private String[] smallImgUrl;
	private String[] bigImgUrl;
	private String produceDescription;
	private String payment;
	private String returns;
	private String aboutUs;
	private String faq;
	private String shipment;
	private String appComment;
	
	
	public String getAppComment() {
		return appComment;
	}
	public void setAppComment(String appComment) {
		this.appComment = appComment;
	}
	public String getShipment() {
		return shipment;
	}
	public void setShipment(String shipment) {
		this.shipment = shipment;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String[] getSmallImgUrl() {
		return smallImgUrl;
	}
	public void setSmallImgUrl(String[] smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}
	public String[] getBigImgUrl() {
		return bigImgUrl;
	}
	public void setBigImgUrl(String[] bigImgUrl) {
		this.bigImgUrl = bigImgUrl;
	}
	public String getProduceDescription() {
		return produceDescription;
	}
	public void setProduceDescription(String produceDescription) {
		this.produceDescription = produceDescription;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getReturns() {
		return returns;
	}
	public void setReturns(String returns) {
		this.returns = returns;
	}
	public String getAboutUs() {
		return aboutUs;
	}
	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}
	public String getFaq() {
		return faq;
	}
	public void setFaq(String faq) {
		this.faq = faq;
	}
	
	
}
