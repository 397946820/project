package com.it.ocs.synchronou.vo;

/**
 * 
 * @author yangguanbao
 * 描述：买家要求
 */
public class BuyerRequirementDetailsVO {
	
	
	private Boolean shipToRegistrationCountry;

	
	private Boolean zeroFeedbackScore;

	
	private Integer minimumFeedbackScore;

	
	private Boolean linkedPayPalAccount;


	public Boolean getShipToRegistrationCountry() {
		return shipToRegistrationCountry;
	}


	public void setShipToRegistrationCountry(Boolean shipToRegistrationCountry) {
		this.shipToRegistrationCountry = shipToRegistrationCountry;
	}


	public Boolean getZeroFeedbackScore() {
		return zeroFeedbackScore;
	}


	public void setZeroFeedbackScore(Boolean zeroFeedbackScore) {
		this.zeroFeedbackScore = zeroFeedbackScore;
	}


	public Integer getMinimumFeedbackScore() {
		return minimumFeedbackScore;
	}


	public void setMinimumFeedbackScore(Integer minimumFeedbackScore) {
		this.minimumFeedbackScore = minimumFeedbackScore;
	}


	public Boolean getLinkedPayPalAccount() {
		return linkedPayPalAccount;
	}


	public void setLinkedPayPalAccount(Boolean linkedPayPalAccount) {
		this.linkedPayPalAccount = linkedPayPalAccount;
	}

	

	
}
