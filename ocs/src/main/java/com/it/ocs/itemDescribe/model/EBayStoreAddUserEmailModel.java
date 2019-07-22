package com.it.ocs.itemDescribe.model;

public class EBayStoreAddUserEmailModel {

	private String signUrl="http://my.ebay.com/ws/eBayISAPI.dll?AcceptSavedSeller&linkname=includenewsletter&sellerid=le.deutschland";
	
	private String instruction="Add my Store to your Favourites and receive my email newsletters about new items and special promotions!";

	private String buttonName="SING UP NOW";
	
	
	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	
}
