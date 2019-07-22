package com.it.ocs.publication.vo;

import java.io.Serializable;
import java.util.Map;

import com.it.ocs.publication.model.AuctionModel;

public class AuctionVO extends AuctionModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6436450657987864443L;

	private Map<String, Object> auctionItemInfo;
	private Map<String, Object> secondTradingInfo;

	public Map<String, Object> getAuctionItemInfo() {
		return auctionItemInfo;
	}

	public void setAuctionItemInfo(Map<String, Object> auctionItemInfo) {
		this.auctionItemInfo = auctionItemInfo;
	}

	public Map<String, Object> getSecondTradingInfo() {
		return secondTradingInfo;
	}

	public void setSecondTradingInfo(Map<String, Object> secondTradingInfo) {
		this.secondTradingInfo = secondTradingInfo;
	}

}
