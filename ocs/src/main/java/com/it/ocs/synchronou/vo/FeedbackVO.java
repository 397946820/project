package com.it.ocs.synchronou.vo;

import java.io.Serializable;

import com.it.ocs.synchronou.model.EBayFeedbackModel;

public class FeedbackVO extends EBayFeedbackModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String responseText;
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	

}
