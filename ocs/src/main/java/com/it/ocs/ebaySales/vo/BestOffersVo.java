package com.it.ocs.ebaySales.vo;

import java.io.Serializable;

import com.it.ocs.ebaySales.model.EBayBestOffersModel;

public class BestOffersVo extends EBayBestOffersModel implements Serializable {
	private String endTime;
	private String prices;
	private String message;
	private String operation;
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		
		this.endTime = endTime;
	}
}
