package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class PublicationRelationModel extends BaseModel {
	private Long publicationId;// 基本信息id
	private Long productCommentId;// 物品描述id
	private Long auctionId;// 拍卖id
	private Long paymentId;// 付款方式id
	private Long buyerRequireId;// 买家要求id
	private Long returnPolicyId;// 退货政策id
	private Long productPlaceId;// 物品所在地id
	private Long transChooseId;// 运输选项id
	private Long advertFeatureId;// 广告特色id
	private Long otherId;// 其他id

	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}

	public Long getProductCommentId() {
		return productCommentId;
	}

	public void setProductCommentId(Long productCommentId) {
		this.productCommentId = productCommentId;
	}

	public Long getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(Long auctionId) {
		this.auctionId = auctionId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getBuyerRequireId() {
		return buyerRequireId;
	}

	public void setBuyerRequireId(Long buyerRequireId) {
		this.buyerRequireId = buyerRequireId;
	}

	public Long getReturnPolicyId() {
		return returnPolicyId;
	}

	public void setReturnPolicyId(Long returnPolicyId) {
		this.returnPolicyId = returnPolicyId;
	}

	public Long getProductPlaceId() {
		return productPlaceId;
	}

	public void setProductPlaceId(Long productPlaceId) {
		this.productPlaceId = productPlaceId;
	}

	public Long getTransChooseId() {
		return transChooseId;
	}

	public void setTransChooseId(Long transChooseId) {
		this.transChooseId = transChooseId;
	}

	public Long getAdvertFeatureId() {
		return advertFeatureId;
	}

	public void setAdvertFeatureId(Long advertFeatureId) {
		this.advertFeatureId = advertFeatureId;
	}

	public Long getOtherId() {
		return otherId;
	}

	public void setOtherId(Long otherId) {
		this.otherId = otherId;
	}

}
