package com.it.ocs.publication.service.inner.impl;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.publication.model.AdvertFeatureModel;
import com.it.ocs.publication.model.AuctionModel;
import com.it.ocs.publication.model.BuyerRequireModel;
import com.it.ocs.publication.model.OtherModel;
import com.it.ocs.publication.model.PaymentModel;
import com.it.ocs.publication.model.ProductCommentModel;
import com.it.ocs.publication.model.ProductPlaceModel;
import com.it.ocs.publication.model.PublicationModel;
import com.it.ocs.publication.model.PublicationRelationModel;
import com.it.ocs.publication.model.ReturnPolicyModel;
import com.it.ocs.publication.model.TransChooseModel;
import com.it.ocs.publication.service.inner.IPublicationEditService;
import com.it.ocs.publication.vo.AuctionVO;
import com.it.ocs.publication.vo.BuyerRequireVO;
import com.it.ocs.publication.vo.OtherVO;
import com.it.ocs.publication.vo.ProductCommentVO;
import com.it.ocs.publication.vo.PublicationSaveVO;
import com.it.ocs.publication.vo.PublicationVO;
import com.it.ocs.publication.vo.TransChooseVO;

import net.sf.json.JSONArray;

@Service
public class PublicationEditService extends BaseService implements IPublicationEditService {

	@Override
	public void editPublication(PublicationSaveVO pubInfo) {
		edit(pubInfo);
	}

	private void edit(PublicationSaveVO pubInfo) {
		/*PublicationRelationModel relationModel = publicationRelationDAO.getById(pubInfo.getId());
		editPublicationModel(relationModel, pubInfo);
		editProductCommentModel(relationModel, pubInfo);
		editAuctionModel(relationModel, pubInfo);
		editPaymentModel(relationModel, pubInfo);
		editBuyerRequireModel(relationModel, pubInfo);
		editReturnPolicyModel(relationModel, pubInfo);
		editProductPlaceModel(relationModel, pubInfo);
		editTransChooseModel(relationModel, pubInfo);
		editAdvertFeatureModel(relationModel, pubInfo);
		editOtherModel(relationModel, pubInfo);
		editPubRelationModel(pubInfo,relationModel);*/
	}
	private void editOtherModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		OtherModel otherModel = otherDAO.getById(relationModel.getOtherId());
		OtherVO otherVO = pubInfo.getOther();
		BeanUtils.copyProperties(otherVO, otherModel,new String[]{"id"});
		if (!MapUtils.isEmpty(otherVO.getSalesTaxInfo())) {
			otherModel.setSalesTax(otherVO.getSalesTaxInfo().toString());
		}
		updateInit(otherModel);
		otherDAO.update(otherModel);
	}
	private void editAdvertFeatureModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		AdvertFeatureModel advertFeatureModel = advertFeatureDAO.getById(relationModel.getAdvertFeatureId());
		BeanUtils.copyProperties(pubInfo.getAdvertFeature(), advertFeatureModel,new String[]{"id"});
		updateInit(advertFeatureModel);
		advertFeatureDAO.update(advertFeatureModel);
	}
	private void editTransChooseModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		TransChooseModel transChooseModel = transChooseDAO.getById(relationModel.getTransChooseId());
		TransChooseVO transChooseVO = pubInfo.getTransChoose();
		BeanUtils.copyProperties(transChooseVO, transChooseModel,new String[]{"id"});
		if (!MapUtils.isEmpty(transChooseVO.getCalCulateObj())) {
			transChooseModel.setCalCulateInfo(transChooseVO.getCalCulateObj().toString());
		}
		if (!MapUtils.isEmpty(transChooseVO.getDomestic())) {
			transChooseModel.setDomesticInfo(transChooseVO.getDomestic().toString());
		}
		if (!MapUtils.isEmpty(transChooseVO.getInternational())) {
			transChooseModel.setInternationalInfo(transChooseVO.getInternational().toString());
		}
		updateInit(transChooseModel);
		transChooseDAO.update(transChooseModel);
	}
	private void editProductPlaceModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		ProductPlaceModel productPlaceModel = productPlaceDAO.getById(relationModel.getProductPlaceId());
		BeanUtils.copyProperties(pubInfo.getProductPlace(), productPlaceModel,new String[]{"id"});
		updateInit(productPlaceModel);
		productPlaceDAO.update(productPlaceModel);
	}
	private void editReturnPolicyModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		ReturnPolicyModel returnPolicyModel = returnPolicyDAO.getById(relationModel.getReturnPolicyId());
		BeanUtils.copyProperties(pubInfo.getReturnPolicy(), returnPolicyModel,new String[]{"id"});
		updateInit(returnPolicyModel);
		returnPolicyDAO.update(returnPolicyModel);
	}
	private void editBuyerRequireModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		BuyerRequireVO buyerRequireVO = pubInfo.getBuyerRequire();
		BuyerRequireModel buyerRequireModel = buyerRequireDAO.getById(relationModel.getBuyerRequireId());
		BeanUtils.copyProperties(buyerRequireVO, buyerRequireModel,new String[]{"id"});
		if (!MapUtils.isEmpty(buyerRequireVO.getBuyRuleObject())) {
			buyerRequireModel.setBuyerRule(buyerRequireVO.getBuyRuleObject().toString());
		}
		updateInit(buyerRequireModel);
		buyerRequireDAO.update(buyerRequireModel);
	}
	private void editPaymentModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		PaymentModel paymentModel = paymentDAO.getById(relationModel.getPaymentId());
		BeanUtils.copyProperties(pubInfo.getPayment(), paymentModel,new String[]{"id"});
		updateInit(paymentModel);
		paymentDAO.update(paymentModel);
	}
	private void editAuctionModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		AuctionVO auctionVO = pubInfo.getAuction();
		AuctionModel auctionModel = auctionDAO.getById(relationModel.getAuctionId());
		BeanUtils.copyProperties(auctionVO, auctionModel,new String[]{"id"});
		if (!MapUtils.isEmpty(auctionVO.getAuctionItemInfo())) {
			auctionModel.setAuctionItem(auctionVO.getAuctionItemInfo().toString());
		}
		if (!MapUtils.isEmpty(auctionVO.getSecondTradingInfo())) {
			auctionModel.setSecondTradInfo(auctionVO.getSecondTradingInfo().toString());
		}
		updateInit(auctionModel);
		auctionDAO.update(auctionModel);
	}
	private void editProductCommentModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		ProductCommentModel productCommentModel = productCommentDAO.getById(relationModel.getProductCommentId());
		ProductCommentVO productCommentVO = pubInfo.getArticleComment();
		BeanUtils.copyProperties(productCommentVO, productCommentModel,new String[]{"id"});
		if (!CollectionUtil.isNullOrEmpty(productCommentVO.getEbayImg())) {
			productCommentModel.setEbayImgs(JSONArray.fromObject(productCommentVO.getEbayImg()).toString());
		}
		if (!CollectionUtil.isNullOrEmpty(productCommentVO.getTemplateImg())) {
			productCommentModel.setTemplateImgs(JSONArray.fromObject(productCommentVO.getTemplateImg()).toString());
		}
		if (!CollectionUtil.isNullOrEmpty(productCommentVO.getComment())) {
			productCommentModel.setComments(JSONArray.fromObject(productCommentVO.getComment()).toString());
		}
		updateInit(productCommentModel);
		productCommentDAO.update(productCommentModel);
	} 
	private void editPublicationModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		PublicationModel model = pubInfoDAO.getById(relationModel.getPublicationId());
		PublicationVO publicationVO = pubInfo.getBaseInfo();
		BeanUtils.copyProperties(publicationVO, model,new String[]{"id"});
		if (!CollectionUtil.isNullOrEmpty(publicationVO.getArticleTitle())) {
			model.setProductTitle(JSONArray.fromObject(publicationVO.getArticleTitle()).toString());
		}
		if (!CollectionUtil.isNullOrEmpty(publicationVO.getBundleProduct())) {
			model.setBundledProduct(JSONArray.fromObject(pubInfo.getBaseInfo().getBundleProduct()).toString());
		}
		updateInit(model);
		pubInfoDAO.update(model);
	}
	private void editPubRelationModel(PublicationSaveVO pubInfo,PublicationRelationModel relationModel) {
		updateInit(relationModel);
		//publicationRelationDAO.update(relationModel);
	}
}
