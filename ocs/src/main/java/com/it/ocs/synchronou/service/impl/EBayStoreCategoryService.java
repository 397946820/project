//package com.it.ocs.synchronou.service.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.dom4j.Document;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.sdk.call.GetStoreCall;
//import com.ebay.soap.eBLBaseComponents.SiteCodeType;
//import com.ebay.soap.eBLBaseComponents.StoreCustomCategoryArrayType;
//import com.ebay.soap.eBLBaseComponents.StoreCustomCategoryType;
//import com.ebay.soap.eBLBaseComponents.StoreType;
//import com.ebay.soap.eBLBaseComponents.StorefrontType;
//import com.ebay.soap.eBLBaseComponents.WarningLevelCodeType;
//import com.google.common.collect.Lists;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.ResponseResult;
//import com.it.ocs.common.service.BaseService;
//import com.it.ocs.common.support.IAction;
//import com.it.ocs.common.support.IFunction;
//import com.it.ocs.common.util.CollectionUtil;
//import com.it.ocs.synchronou.dao.IEBayAccountDao;
//import com.it.ocs.synchronou.dao.IEBayStoreCategoryDao;
//import com.it.ocs.synchronou.mapping.SiteIDEnum;
//import com.it.ocs.synchronou.mapping.UserIDToken;
//import com.it.ocs.synchronou.model.EBayCategoryModel;
//import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
//import com.it.ocs.synchronou.model.EbayAccountModel;
//import com.it.ocs.synchronou.service.IEBayStoreCategoryService;
//import com.it.ocs.synchronou.util.ServerUrl;
//import com.it.ocs.synchronou.util.StoreCategoryHttps;
//import com.it.ocs.synchronou.vo.CategoryVO;
//import com.it.ocs.synchronou.vo.StoreCategoryVO;
//
//@Service
//public class EBayStoreCategoryService extends BaseService implements IEBayStoreCategoryService {
//	private static final Logger log = Logger.getLogger(EBayStoreCategoryService.class);
//	@Autowired 
//	private IEBayStoreCategoryDao storeCategoryDao;
//	@Autowired
//	private IEBayAccountDao accountDao;
//	@Override
//	public OperationResult addOrEdit(StoreCategoryVO storeCategoryVO) {
//		OperationResult result = new OperationResult();
//		if(storeCategoryVO.getCategory_id()==null){
//			result = insertStoreCategory(storeCategoryVO);
//		}else{
//			result = updateStoreCategory(storeCategoryVO);
//		}
//		return result;
//	}
//	@Override
//	public OperationResult insertStoreCategory(StoreCategoryVO storeCategoryVO) {
//		OperationResult result = new OperationResult();
//		try{
//			if (storeCategoryVO.getParent_category_id()==null) {
//				storeCategoryVO.setParent_category_id(0L);
//			}
//			EBayStoreCategoryModel eBayStoreCategoryModel = storeCategoryVO;
//			storeCategoryDao.add(eBayStoreCategoryModel);
//		}catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("insert error");
//			e.printStackTrace();
//			log.error(e);
//		}
//		return result;
//	}
//	@Override
//	public OperationResult updateStoreCategory(StoreCategoryVO storeCategoryVO) {
//		OperationResult result = new OperationResult();
//		try{
//			EBayStoreCategoryModel eBayStoreCategoryModel = storeCategoryVO;
//			storeCategoryDao.update(eBayStoreCategoryModel);
//		}catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("update error");
//			e.printStackTrace();
//			log.error(e);
//		}
//		return result;
//	}
//	
//	@Override
//	public List<StoreCategoryVO> selectStoreCategorys() {
//		EBayStoreCategoryModel eBayStoreCategoryModel = new EBayStoreCategoryModel();
//		eBayStoreCategoryModel.setMarketplace_id(0L);
//		List<EBayStoreCategoryModel> eBayStoreCategoryModels = storeCategoryDao.query(eBayStoreCategoryModel);
//		List<StoreCategoryVO> storeCategoryVOs = CollectionUtil.beansConvert(eBayStoreCategoryModels, StoreCategoryVO.class);
//		setParentName(storeCategoryVOs);
//		List<StoreCategoryVO> parents = CollectionUtil.searchList(storeCategoryVOs, new IFunction<StoreCategoryVO, Boolean>() {
//			@Override
//			public Boolean excute(StoreCategoryVO obj) {
//				return null==obj.getParent_category_id()||obj.getParent_category_id()==0;
//			}
//		});
//		CollectionUtil.each(parents, new IAction<StoreCategoryVO>() {
//			@Override
//			public void excute(StoreCategoryVO obj) {
//				setChildren(obj, storeCategoryVOs);
//			}
//		});
//		return parents;
//	}
//	private void setChildren(StoreCategoryVO parent, List<StoreCategoryVO>  storeCategoryVOs){
//		List<StoreCategoryVO> childrens = CollectionUtil.searchList(storeCategoryVOs, new IFunction<StoreCategoryVO, Boolean>() {
//			@Override
//			public Boolean excute(StoreCategoryVO obj) {
//				return null != obj.getParent_category_id() && obj.getParent_category_id().equals(parent.getCategory_id());
//			}
//		});
//		CollectionUtil.each(childrens, new IAction<StoreCategoryVO>() {
//			@Override
//			public void excute(StoreCategoryVO obj) {
//				setChildren(obj, storeCategoryVOs);
//			}
//		});
//		if(!CollectionUtil.isNullOrEmpty(childrens)){
//			parent.setChildren(childrens);
//		}
//	}
//	private void setParentName(List<StoreCategoryVO> storeCategoryVOs) {
//		CollectionUtil.each(storeCategoryVOs, new IAction<StoreCategoryVO>() {
//			@Override
//			public void excute(StoreCategoryVO storeCategoryVO) {
//				if (null != storeCategoryVO.getParent_category_id() && storeCategoryVO.getParent_category_id() != 0) {
//					StoreCategoryVO parentVO = CollectionUtil.search(storeCategoryVOs, new IFunction<StoreCategoryVO, Boolean>() {
//						@Override
//						public Boolean excute(StoreCategoryVO obj) {
//							return storeCategoryVO.getParent_category_id() == obj.getCategory_id() || storeCategoryVO.getParent_category_id().equals(obj.getCategory_id());
//						}
//					});
//					if (null != parentVO) {
//						storeCategoryVO.setParentName(parentVO.getName());
//					}
//				}
//			}
//		});
//	}
//	@Override
//	public OperationResult insertStoreCategorys() {
//		storeCategoryDao.deleteStoreCategoryAll();
//		OperationResult result = new OperationResult();
//		List<EbayAccountModel> ebayAccountModels = accountDao.getAccounts();
//		String serverUrl = "";
//		for (EbayAccountModel ebayAccountModel : ebayAccountModels) {
//			serverUrl = ServerUrl.getServerUrl(ebayAccountModel.getAccount());
//			ApiContext apiContext = BaseEbaySDKService.getApiContext(ebayAccountModel.getToken(), serverUrl);
//			GetStoreCall getStoreCall = new GetStoreCall(apiContext);
//			getStoreCall.setLevelLimit(3);
//			getStoreCall.setWarningLevel(WarningLevelCodeType.HIGH);
//			Long siteId = SiteIDEnum.searchSiteIdByUserId(ebayAccountModel.getAccount());
//			getStoreCall.setSite(SiteIDEnum.searchBySiteID(siteId));
//				try {
//					getStoreCall.getStore();
//					List<EBayStoreCategoryModel> eBayStoreCategoryModels = getEBayStoreCategorys(getStoreCall,ebayAccountModel.getAccount());
//					if(eBayStoreCategoryModels.size()>0){
//						storeCategoryDao.insertStoreCategoryList(eBayStoreCategoryModels);
//					}
//				} catch (Exception e) {
//					result.setDescription("同步失败！");
//					e.printStackTrace();
//				}
//		}
//		result.setDescription("同步成功！");
//		return result;
//	}
//	public List<EBayStoreCategoryModel> getEBayStoreCategorys(GetStoreCall getStoreCall,String user){
//		List<EBayStoreCategoryModel> eBayStoreCategoryModels = Lists.newArrayList();
//		StoreType storeType = getStoreCall.getReturnedStoreType();
//		String storeName = storeType.getName();
//		String storeUrl = storeType.getURL();
//		Long siteId = SiteIDEnum.searchSiteIdByUserId(user);
//		Long parentId= 0L;
//		StoreCustomCategoryArrayType storeCategoryArrayType = storeType.getCustomCategories();
//		StoreCustomCategoryType[] storeCategorys =storeCategoryArrayType.getCustomCategory();
//		for (StoreCustomCategoryType storeCustomCategoryType : storeCategorys) {
//			StoreCustomCategoryType[] childCategorys = storeCustomCategoryType.getChildCategory();
//	    	EBayStoreCategoryModel eBayStoreCategoryModels3 = new EBayStoreCategoryModel();
//	    	eBayStoreCategoryModels3.setParent_category_id(parentId);
//			for (StoreCustomCategoryType childCategory1 : childCategorys) {
//				eBayStoreCategoryModels3.setChild_category("true");
//		    	parentId = storeCustomCategoryType.getCategoryID();
//		    	StoreCustomCategoryType[] childCategorys2 = childCategory1.getChildCategory();
//		    	EBayStoreCategoryModel eBayStoreCategoryModels2 = new EBayStoreCategoryModel();
//		    	for (StoreCustomCategoryType childCategory2 : childCategorys2) {
//		    		eBayStoreCategoryModels2.setChild_category("true");
//		    		parentId=childCategory1.getCategoryID();
//		    		EBayStoreCategoryModel eBayStoreCategoryModel = new EBayStoreCategoryModel();
//		    		eBayStoreCategoryModel.setParent_category_id(parentId);
//		    		eBayStoreCategoryModel.setStore_name(storeName);
//		    		eBayStoreCategoryModel.setStore_url(storeUrl);
//		    		eBayStoreCategoryModel.setUser_name(user);
//		    		eBayStoreCategoryModel.setMarketplace_id(siteId);
//		    		eBayStoreCategoryModel.setName(childCategory2.getName());
//		    		eBayStoreCategoryModel.setCategory_id(childCategory2.getCategoryID());
//		    		Integer order = childCategory2.getOrder();
//		    		if(order!=null){
//		    		eBayStoreCategoryModel.setCategory_order(Long.parseLong(childCategory2.getOrder().toString()));
//		    		}
//		    		eBayStoreCategoryModels.add(eBayStoreCategoryModel);
//		    	}
//		    	eBayStoreCategoryModels2.setParent_category_id(parentId);
//		    	eBayStoreCategoryModels2.setStore_name(storeName);
//		    	eBayStoreCategoryModels2.setUser_name(user);
//		    	eBayStoreCategoryModels2.setStore_url(storeUrl);
//		    	eBayStoreCategoryModels2.setName(childCategory1.getName());
//		    	eBayStoreCategoryModels2.setMarketplace_id(siteId);
//		    	eBayStoreCategoryModels2.setCategory_id(childCategory1.getCategoryID());
//	    		Integer order = childCategory1.getOrder();
//	    		if(order!=null){
//	    			eBayStoreCategoryModels2.setCategory_order(Long.parseLong(order.toString()));
//	    		}
//	    		eBayStoreCategoryModels.add(eBayStoreCategoryModels2);
//			}
//			eBayStoreCategoryModels3.setStore_name(storeName);
//			eBayStoreCategoryModels3.setUser_name(user);
//			eBayStoreCategoryModels3.setStore_url(storeUrl);
//			eBayStoreCategoryModels3.setName(storeCustomCategoryType.getName());
//			eBayStoreCategoryModels3.setCategory_id(storeCustomCategoryType.getCategoryID());
//    		Integer order = storeCustomCategoryType.getOrder();
//    		if(order!=null){
//    			eBayStoreCategoryModels3.setCategory_order(Long.parseLong(order.toString()));
//    		}
//    		eBayStoreCategoryModels3.setMarketplace_id(siteId);
//    		eBayStoreCategoryModels.add(eBayStoreCategoryModels3);
//		    parentId=0L;
//		}
//		return eBayStoreCategoryModels;
//	}
//	@Override
//	public List<StoreCategoryVO> selectChildStoreCategorysByCIDAndMID(Long categoryId,String userId) {
//		List<EBayStoreCategoryModel> eBayStoreCategoryModels = new ArrayList<>();
//		if(categoryId==null){
//			eBayStoreCategoryModels = storeCategoryDao.selectChildStoreCategorysByCIDAndMID(0L,userId);
//		}else{
//			eBayStoreCategoryModels = storeCategoryDao.selectChildStoreCategorysByCIDAndMID(categoryId,userId);
//		}
//		List<StoreCategoryVO> storeCategoryVOs = new ArrayList<>();
//		convertList(eBayStoreCategoryModels, storeCategoryVOs);
//		return storeCategoryVOs;
//	}
//	@Override
//	public List<StoreCategoryVO> selectStoreCategorysByMarketplaceId(Long marketplaceId) {
//		List<EBayStoreCategoryModel> eBayStoreCategoryModels = storeCategoryDao.selectStoreCategorysByMarketplaceId(marketplaceId);
//		List<StoreCategoryVO> storeCategoryVOs = new ArrayList<>();
//		convertList(eBayStoreCategoryModels, storeCategoryVOs);
//		return storeCategoryVOs;
//	}
//	@Override
//	public EBayStoreCategoryModel selectStoreCategoryByCIDAndMID(Long categoryId,String userId) {
//		return storeCategoryDao.selectStoreCategoryByCIDAndMID(categoryId,userId);
//	}
//	@Override
//	public EBayStoreCategoryModel selectStoreCategoryByPIDAndMIDAndName(Long parentCategoryId, Long marketplaceId,
//			String category_name) {
//		return storeCategoryDao.selectStoreCategoryByPIDAndMIDAndName(parentCategoryId, marketplaceId, category_name);
//	}
//	
//	private void convertList(List<EBayStoreCategoryModel> source, final List<StoreCategoryVO> target) {
//			CollectionUtil.each(source, new IAction<EBayStoreCategoryModel>() {
//				@Override
//				public void excute(EBayStoreCategoryModel obj) {
//					StoreCategoryVO storeCategoryVO = new StoreCategoryVO();
//					BeanUtils.copyProperties(obj, storeCategoryVO);
//					target.add(storeCategoryVO);
//				}
//			});
//		}
//	@Override
//	public OperationResult synchronouStoreCategory(Long marketplace_id, String user_name) {
//		OperationResult result = new OperationResult();
//		try {
//			StoreCategoryHttps storeCategoryHttps = new StoreCategoryHttps();
//			Map<String, String> map = new HashMap<>();
//			String token = UserIDToken.searchTokenByUserID(user_name);
//			map.put("X-EBAY-API-SITEID", marketplace_id.toString());
//			map.put("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
//			map.put("X-EBAY-API-CALL-NAME", "GetStore");
//			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
//	                +" <GetStoreRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
//					 +"<RequesterCredentials>"
//					 +"<eBayAuthToken>"
//					 + token
//					 + "</eBayAuthToken>"
//					 +" </RequesterCredentials>"
//					 +"<ErrorLanguage>en_US</ErrorLanguage>"
//					 +"<WarningLevel>High</WarningLevel>"
//					 +"<LevelLimit>3</LevelLimit>"
//					 +"</GetStoreRequest>";
//			Document document = storeCategoryHttps.getPesponseXml("https://api.ebay.com/ws/api.dll", map, xml);
//			List<EBayStoreCategoryModel> eBayStoreCategoryModels =storeCategoryHttps.xmlToModel(document);
//			for (EBayStoreCategoryModel eBayStoreCategoryModel : eBayStoreCategoryModels) {
//				eBayStoreCategoryModel.setUser_name(user_name);
//				eBayStoreCategoryModel.setMarketplace_id(marketplace_id);
//			}
//			//storeCategoryDao.insertStoreCategoryList(eBayStoreCategoryModels);
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("同步失败！");
//			e.printStackTrace();
//			log.error(e);
//		}
//		return result;
//	}
//	@Override
//	public OperationResult insertStoreCategoryList(List<EBayStoreCategoryModel> eBayStoreCategoryModels) {
//		OperationResult result = new OperationResult();
//		try {
//			storeCategoryDao.insertStoreCategoryList(eBayStoreCategoryModels);
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("insert error!");
//			e.printStackTrace();
//			log.error(e);
//		}
//		return result;
//	}
//	@Override
//	public OperationResult updateStoreCategoryList(List<EBayStoreCategoryModel> eBayStoreCategoryModels) {
//		OperationResult result = new OperationResult();
//		try {
//			storeCategoryDao.updateStoreCategoryList(eBayStoreCategoryModels);
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("update error!");
//			e.printStackTrace();
//			log.error(e);
//		}
//		return result;
//	}
//	@Override
//	public List<EBayStoreCategoryModel> internalSelectStoreCategorys(EBayStoreCategoryModel eBayStoreCategoryModel) {
//		return null;
//	}
//	@Override
//	public EBayStoreCategoryModel selectStoreUrlByUserId(String userId) {
//		return storeCategoryDao.selectStoreUrlByUserId(userId);
//	}
//
//}
