//package com.it.ocs.publication.service.inner.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.google.common.collect.Lists;
//import com.it.ocs.itemDescribe.Base.BaseTemplate;
//import com.it.ocs.itemDescribe.css.TemplateACss;
//import com.it.ocs.itemDescribe.model.EBayInternalContentModel;
//import com.it.ocs.itemDescribe.model.EBayPromoteModel;
//import com.it.ocs.itemDescribe.model.EBayStoreAddUserEmailModel;
//import com.it.ocs.itemDescribe.model.StoreCategoryModel;
//import com.it.ocs.itemDescribe.template.TemplateA;
//import com.it.ocs.pic.model.PicModel;
//import com.it.ocs.pic.service.IPicService;
//import com.it.ocs.pic.vo.PicVO;
//import com.it.ocs.publication.model.EBayAdvertTemplatesModel;
//import com.it.ocs.publication.model.PublicationAddItemModel;
//import com.it.ocs.publication.model.PublicationModel;
//import com.it.ocs.publication.service.external.IEBayAdvertTemplatesService;
//import com.it.ocs.publication.service.external.IPublicationService;
//import com.it.ocs.publication.service.inner.IPublicationViewService;
//import com.it.ocs.seller.dao.IEBaySellerDescriptionDao;
//import com.it.ocs.seller.model.EBaySellerDescriptionModel;
//import com.it.ocs.synchronou.dao.IEBayStoreCategoryDao;
//import com.it.ocs.synchronou.mapping.SiteIDToStore;
//import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
//import com.it.ocs.synchronou.service.IEBayItemService;
//import com.it.ocs.synchronou.service.IEBayStoreCategoryService;
//import com.it.ocs.synchronou.service.ITemplateService;
//import com.it.ocs.synchronou.util.ValidationUtil;
//
//@Service
//public class PublicationViewService implements IPublicationViewService {
//
//	@Autowired
//	public IEBayItemService eBayItemService;
//	@Autowired
//	private IEBaySellerDescriptionDao sellerDescriptionDao;
//	@Autowired
//	private IEBayStoreCategoryDao storeCategoryDao;
//	@Autowired
//	private ITemplateService templateService;
//	@Autowired
//	private IEBayStoreCategoryService storeCategoryService;
//	@Autowired
//	private IEBayAdvertTemplatesService advertService;
//	@Autowired
//	private IPicService picService;
//	@Autowired
//	private IPublicationService publicationService;
//
//	@Override
//	public String toViewString(PublicationModel pubInfo) {
//		PublicationAddItemModel pub = new PublicationAddItemModel(pubInfo);
//		return modelToString(pub);
//	}
//
//	private String modelToString(PublicationAddItemModel publicationAddItemModel) {
//		Long siteID = publicationAddItemModel.getSiteId();
//		String user = publicationAddItemModel.getEbayAccount();
//		String result = new String();
//		
//		//顶部推广 
//		List<EBayPromoteModel> topPromotes = eBayItemService.getPromotes(publicationAddItemModel,publicationAddItemModel.getTopPromotionType());
//		
//		//中间推广
//		List<EBayPromoteModel> inPromotes = eBayItemService.getPromotes(publicationAddItemModel,publicationAddItemModel.getInPromotionType());
//
//		
//		// 用户地址和按钮
//		EBayStoreAddUserEmailModel storeAddUserEmailModel = new EBayStoreAddUserEmailModel();
//		// 内部推广
//		//List<EBayPromoteModel> internalPromoteModels = Lists.newArrayList();
//       // 卖家描述和内部图片
//		EBaySellerDescriptionModel eBaySellerDescriptionModel = sellerDescriptionDao.getById(publicationAddItemModel.getSellerDescription());
//		EBayInternalContentModel eBayInternalContentModel = new EBayInternalContentModel();
//		if (eBaySellerDescriptionModel == null) {
//			eBaySellerDescriptionModel = new EBaySellerDescriptionModel();
//			eBayInternalContentModel.setPayment("");
//		    eBayInternalContentModel.setShipment("");
//		    eBayInternalContentModel.setReturns("");
//		    eBayInternalContentModel.setAboutUs("");
//		    eBayInternalContentModel.setFaq("");
//		} else {
//			eBayInternalContentModel.setPayment(eBaySellerDescriptionModel.getDescription1());
//			eBayInternalContentModel.setShipment(eBaySellerDescriptionModel.getDescription2());
//			eBayInternalContentModel.setReturns(eBaySellerDescriptionModel.getDescription3());
//			eBayInternalContentModel.setAboutUs(eBaySellerDescriptionModel.getDescription4());
//			eBayInternalContentModel.setFaq(eBaySellerDescriptionModel.getDescription5());
//
//		}
//		eBayInternalContentModel.setProduceDescription(publicationAddItemModel.getComments());
//		eBayInternalContentModel.setTitle(publicationAddItemModel.getProductTitle());
//		eBayInternalContentModel.setBigImgUrl(publicationAddItemModel.getTemplateImages());
//		// 底部推广
//		List<EBayPromoteModel> endPromote = eBayItemService.getPromotes(publicationAddItemModel,publicationAddItemModel.getFooterPromotionType());
//
//		Class<?> clazz;
//		try {
//			
//			clazz = Class.forName("com.it.ocs.itemDescribe.template.TemplateC");
//			BaseTemplate baseTemplate = (BaseTemplate) clazz.newInstance();
//			baseTemplate.setTemplateService(templateService);
//			baseTemplate.setTopPromoteModel(topPromotes);
//			baseTemplate.setInPromoteModels(inPromotes);
//			baseTemplate.setSiteId(siteID);
//			Long advertId = publicationAddItemModel.getAdvert_id();
//			if(!ValidationUtil.isNullOrEmpty(advertId)){
//				EBayAdvertTemplatesModel eBayAdvertTemplatesModel = advertService.selectAdvertById(advertId);
//				//广告图片
//				Long picId = eBayAdvertTemplatesModel.getPid_id();
//				PicModel picModel = picService.queryById(picId);
//				String realUrl = picModel.getRealUrl();
//				if (ValidationUtil.isNullOrEmpty(realUrl)) {
//					PicVO picVO = picService.getPicRealUrl(picModel, user);
//					baseTemplate.setAdvertImg(picVO.getRealUrl());
//				}else{
//					baseTemplate.setAdvertImg(realUrl);
//				}
//				//广告产品
//				PublicationModel publicationModel = publicationService.selectPubById(eBayAdvertTemplatesModel.getLine_product());
//				if(publicationModel!=null){
//				baseTemplate.setAdvertUrl(publicationModel.getEbayProductURL());
//				}else{
//					baseTemplate.setAdvertUrl("");
//				}
//			}
//			
//			
//			
//			//商店连接
//			EBayStoreCategoryModel eBayStoreCategoryModel3 = storeCategoryService.selectStoreUrlByUserId(user);
//			String storeUrl = "";
//			if(ValidationUtil.isNull(eBayStoreCategoryModel3)){
//				storeUrl="http://stores.ebay.com/neonmartlighting";
//			}else{
//				storeUrl=eBayStoreCategoryModel3.getStore_url();
//			}
//			baseTemplate.setStoreUrl(storeUrl);
//			
//			// 左边连接
//			// 商店分类
//			
//			List<EBayStoreCategoryModel> eBayStoreCategoryModels = storeCategoryDao.selectChildStoreCategorysByCIDAndMID(0L, user);
//
//			List<StoreCategoryModel> storeCategoryModels = Lists.newArrayList();
//
//			for (EBayStoreCategoryModel eBayStoreCategoryModel2 : eBayStoreCategoryModels) {
//				StoreCategoryModel storeCategoryModel = new StoreCategoryModel();
//				storeCategoryModel.setCategoryId(eBayStoreCategoryModel2.getCategory_id().toString());
//				storeCategoryModel.setCategoryName(eBayStoreCategoryModel2.getName());
//				storeCategoryModel.setStoreUrl(storeUrl);
//				storeCategoryModels.add(storeCategoryModel);
//			}
//			baseTemplate.setEBayStoreCategoryModels(storeCategoryModels);
//			  //用户地址和按钮
//			  baseTemplate.setStoreAddUserEmailModel(storeAddUserEmailModel);
//			  //内部推广
//			// List<EBayPromoteModel> internalPromoteModels = Lists.newArrayList();
//			 baseTemplate.setEBayInternalContentModel(eBayInternalContentModel);
//			  //底部推广
//			  baseTemplate.setBottomPromoteModel(endPromote);
//			  result= baseTemplate.getTemplate();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return result;
//	}
//}
