package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class AuctionModel extends BaseModel {
	private String discount = "";// 折扣
	private boolean individual = false;// 是否不向公众显示买家的名称
	private String publicationDays = "";// 刊登天数
	private String price = "";// 价格
	private String reserverPrice = "";// 保留价
	private String buyoutPrice = "";// 一口价
	private String auctionItem = "";// 拍卖产品信息,批量、数量、批物品数
	private boolean secondTrading = false;// 二次交易机会
	private String secondTradInfo = "";// 二次交易的价格、时间、消息

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public boolean isIndividual() {
		return individual;
	}

	public void setIndividual(boolean individual) {
		this.individual = individual;
	}

	public String getPublicationDays() {
		return publicationDays;
	}

	public void setPublicationDays(String publicationDays) {
		this.publicationDays = publicationDays;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReserverPrice() {
		return reserverPrice;
	}

	public void setReserverPrice(String reserverPrice) {
		this.reserverPrice = reserverPrice;
	}

	public String getBuyoutPrice() {
		return buyoutPrice;
	}

	public void setBuyoutPrice(String buyoutPrice) {
		this.buyoutPrice = buyoutPrice;
	}

	public String getAuctionItem() {
		return auctionItem;
	}

	public void setAuctionItem(String auctionItem) {
		this.auctionItem = auctionItem;
	}

	public boolean isSecondTrading() {
		return secondTrading;
	}

	public void setSecondTrading(boolean secondTrading) {
		this.secondTrading = secondTrading;
	}

	public String getSecondTradInfo() {
		return secondTradInfo;
	}

	public void setSecondTradInfo(String secondTradInfo) {
		this.secondTradInfo = secondTradInfo;
	}

}
