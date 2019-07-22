package com.it.ocs.publication.service.inner.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
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
import com.it.ocs.publication.service.inner.IPublicationAddService;
import com.it.ocs.publication.vo.AuctionVO;
import com.it.ocs.publication.vo.BuyerRequireVO;
import com.it.ocs.publication.vo.OtherVO;
import com.it.ocs.publication.vo.ProductCommentVO;
import com.it.ocs.publication.vo.PublicationSaveVO;
import com.it.ocs.publication.vo.PublicationVO;
import com.it.ocs.publication.vo.TransChooseVO;

import net.sf.json.JSONArray;
@Service
public class PublicationAddService extends BaseService implements IPublicationAddService {
	
	@Override
	public void addPublication(PublicationSaveVO pubInfo) {
		add(pubInfo);
	}
	private void add(PublicationSaveVO pubInfo) {
		PublicationRelationModel relationModel = new PublicationRelationModel();
		addPublicationModel(relationModel, pubInfo);
		addProductCommentModel(relationModel, pubInfo);
		addAuctionModel(relationModel, pubInfo);
		addPaymentModel(relationModel, pubInfo);
		addBuyerRequireModel(relationModel, pubInfo);
		addReturnPolicyModel(relationModel, pubInfo);
		addProductPlaceModel(relationModel, pubInfo);
		addTransChooseModel(relationModel, pubInfo);
		addAdvertFeatureModel(relationModel, pubInfo);
		addOtherModel(relationModel, pubInfo);
		addPubRelationModel(pubInfo,relationModel);
	}
	private void addOtherModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long otherId = otherDAO.getId();
		relationModel.setOtherId(otherId);
		OtherModel otherModel = new OtherModel();
		OtherVO otherVO = pubInfo.getOther();
		BeanUtils.copyProperties(otherVO, otherModel);
		otherModel.setId(otherId);
		insertInit(otherModel);
		if (!MapUtils.isEmpty(otherVO.getSalesTaxInfo())) {
			otherModel.setSalesTax(otherVO.getSalesTaxInfo().toString());
		}
		otherDAO.add(otherModel);
	}
	private void addAdvertFeatureModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long advertFeatureId = advertFeatureDAO.getId();
		relationModel.setAdvertFeatureId(advertFeatureId);
		AdvertFeatureModel advertFeatureModel = new AdvertFeatureModel();
		BeanUtils.copyProperties(pubInfo.getAdvertFeature(), advertFeatureModel);
		advertFeatureModel.setId(advertFeatureId);
		insertInit(advertFeatureModel);
		advertFeatureDAO.add(advertFeatureModel);
	}
	private void addTransChooseModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long transChooseId = transChooseDAO.getId();
		relationModel.setTransChooseId(transChooseId);
		TransChooseModel transChooseModel = new TransChooseModel();
		TransChooseVO transChooseVO = pubInfo.getTransChoose();
		BeanUtils.copyProperties(transChooseVO, transChooseModel);
		transChooseModel.setId(transChooseId);
		insertInit(transChooseModel);
		if (!MapUtils.isEmpty(transChooseVO.getCalCulateObj())) {
			transChooseModel.setCalCulateInfo(transChooseVO.getCalCulateObj().toString());
		}
		if (!MapUtils.isEmpty(transChooseVO.getDomestic())) {
			transChooseModel.setDomesticInfo(transChooseVO.getDomestic().toString());
		}
		if (!MapUtils.isEmpty(transChooseVO.getInternational())) {
			transChooseModel.setInternationalInfo(transChooseVO.getInternational().toString());
		}
		transChooseDAO.add(transChooseModel);
	}
	private void addProductPlaceModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long productPlaceId = productPlaceDAO.getId();
		relationModel.setProductPlaceId(productPlaceId);
		ProductPlaceModel productPlaceModel = new ProductPlaceModel();
		BeanUtils.copyProperties(pubInfo.getProductPlace(), productPlaceModel);
		productPlaceModel.setId(productPlaceId);
		insertInit(productPlaceModel);
		productPlaceDAO.add(productPlaceModel);
	}
	private void addReturnPolicyModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long returnPolicyId = returnPolicyDAO.getId();
		relationModel.setReturnPolicyId(returnPolicyId);
		ReturnPolicyModel returnPolicyModel = new ReturnPolicyModel();
		BeanUtils.copyProperties(pubInfo.getReturnPolicy(), returnPolicyModel);
		insertInit(returnPolicyModel);
		returnPolicyModel.setId(returnPolicyId);
		returnPolicyDAO.add(returnPolicyModel);
	}
	private void addBuyerRequireModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long buyerRequireId = buyerRequireDAO.getId();
		relationModel.setBuyerRequireId(buyerRequireId);
		BuyerRequireVO buyerRequireVO = pubInfo.getBuyerRequire();
		BuyerRequireModel buyerRequireModel = new BuyerRequireModel();
		BeanUtils.copyProperties(buyerRequireVO, buyerRequireModel);
		insertInit(buyerRequireModel);
		buyerRequireModel.setId(buyerRequireId);
		if (!MapUtils.isEmpty(buyerRequireVO.getBuyRuleObject())) {
			buyerRequireModel.setBuyerRule(buyerRequireVO.getBuyRuleObject().toString());
		}
		buyerRequireDAO.add(buyerRequireModel);
	}
	private void addPaymentModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long paymentId = paymentDAO.getId();
		relationModel.setPaymentId(paymentId);
		PaymentModel paymentModel = new PaymentModel();
		BeanUtils.copyProperties(pubInfo.getPayment(), paymentModel);
		insertInit(paymentModel);
		paymentModel.setId(paymentId);
		paymentDAO.add(paymentModel);
	}
	private void addAuctionModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long auctionId = auctionDAO.getId();
		relationModel.setAuctionId(auctionId);
		AuctionVO auctionVO = pubInfo.getAuction();
		AuctionModel auctionModel = new AuctionModel();
		BeanUtils.copyProperties(auctionVO, auctionModel);
		auctionModel.setId(auctionId);
		insertInit(auctionModel);
		if (!MapUtils.isEmpty(auctionVO.getAuctionItemInfo())) {
			auctionModel.setAuctionItem(auctionVO.getAuctionItemInfo().toString());
		}
		if (!MapUtils.isEmpty(auctionVO.getSecondTradingInfo())) {
			auctionModel.setSecondTradInfo(auctionVO.getSecondTradingInfo().toString());
		}
		auctionDAO.add(auctionModel);
	}
	private void addProductCommentModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long productCommentId = productCommentDAO.getId();
		relationModel.setProductCommentId(productCommentId);
		ProductCommentModel productCommentModel = new ProductCommentModel();
		ProductCommentVO productCommentVO = pubInfo.getArticleComment();
		BeanUtils.copyProperties(productCommentVO, productCommentModel);
		productCommentModel.setId(productCommentId);
		insertInit(productCommentModel);
		if (!CollectionUtil.isNullOrEmpty(productCommentVO.getEbayImg())) {
			productCommentModel.setEbayImgs(JSONArray.fromObject(productCommentVO.getEbayImg()).toString());
		}
		if (!CollectionUtil.isNullOrEmpty(productCommentVO.getTemplateImg())) {
			productCommentModel.setTemplateImgs(JSONArray.fromObject(productCommentVO.getTemplateImg()).toString());
		}
		if (!CollectionUtil.isNullOrEmpty(productCommentVO.getComment())) {
			productCommentModel.setComments(JSONArray.fromObject(productCommentVO.getComment()).toString());
		}
		productCommentDAO.add(productCommentModel);
	} 
	private void addPublicationModel(PublicationRelationModel relationModel,PublicationSaveVO pubInfo) {
		Long pubInfoId = pubInfoDAO.getId();
		relationModel.setPublicationId(pubInfoId);
		PublicationModel model = new PublicationModel();
		PublicationVO publicationVO = pubInfo.getBaseInfo();
		BeanUtils.copyProperties(publicationVO, model);
		model.setId(pubInfoId);
		insertInit(model);
		if (!CollectionUtil.isNullOrEmpty(publicationVO.getArticleTitle())) {
			model.setProductTitle(JSONArray.fromObject(publicationVO.getArticleTitle()).toString());
		}
		if (!CollectionUtil.isNullOrEmpty(publicationVO.getBundleProduct())) {
			model.setBundledProduct(JSONArray.fromObject(pubInfo.getBaseInfo().getBundleProduct()).toString());
		}
		pubInfoDAO.add(model);
	}
	private void addPubRelationModel(PublicationSaveVO pubInfo,PublicationRelationModel relationModel) {
		Long id = publicationRelationDAO.getId();
		pubInfo.setId(id);
		relationModel.setId(id);
		insertInit(relationModel);
		//publicationRelationDAO.add(relationModel);
	}

}
