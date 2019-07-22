package com.it.ocs.publication.vo;

import java.io.Serializable;

public class PublicationSaveVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3493839140094678953L;
	private Long id;
	private PublicationVO baseInfo;// 一般信息
	private ProductCommentVO articleComment;// 物品描述
	private AuctionVO auction;// 拍卖
	private PaymentVO payment;// 付款
	private BuyerRequireVO buyerRequire;// 买家要求
	private ReturnPolicyVO returnPolicy;// 退货政策
	private ProductPlaceVO productPlace;// 物品所在地
	private TransChooseVO transChoose;// 运输选项
	private AdvertFeatureVO advertFeature;// 广告特色
	private OtherVO other;// 其他

	public ProductCommentVO getArticleComment() {
		return articleComment;
	}

	public void setArticleComment(ProductCommentVO articleComment) {
		this.articleComment = articleComment;
	}

	public AuctionVO getAuction() {
		return auction;
	}

	public void setAuction(AuctionVO auction) {
		this.auction = auction;
	}

	public PaymentVO getPayment() {
		return payment;
	}

	public void setPayment(PaymentVO payment) {
		this.payment = payment;
	}

	public BuyerRequireVO getBuyerRequire() {
		return buyerRequire;
	}

	public void setBuyerRequire(BuyerRequireVO buyerRequire) {
		this.buyerRequire = buyerRequire;
	}

	public ReturnPolicyVO getReturnPolicy() {
		return returnPolicy;
	}

	public void setReturnPolicy(ReturnPolicyVO returnPolicy) {
		this.returnPolicy = returnPolicy;
	}

	public ProductPlaceVO getProductPlace() {
		return productPlace;
	}

	public void setProductPlace(ProductPlaceVO productPlace) {
		this.productPlace = productPlace;
	}

	public TransChooseVO getTransChoose() {
		return transChoose;
	}

	public void setTransChoose(TransChooseVO transChoose) {
		this.transChoose = transChoose;
	}

	public AdvertFeatureVO getAdvertFeature() {
		return advertFeature;
	}

	public void setAdvertFeature(AdvertFeatureVO advertFeature) {
		this.advertFeature = advertFeature;
	}

	public OtherVO getOther() {
		return other;
	}

	public void setOther(OtherVO other) {
		this.other = other;
	}

	public PublicationVO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(PublicationVO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
