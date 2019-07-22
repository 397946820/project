//package com.it.ocs.synchronou.service.impl;
//
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TimeZone;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.apache.log4j.Logger;
//import org.dom4j.Document;
//import org.dom4j.Element;
//import org.jsoup.Jsoup;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.sdk.call.GetItemCall;
//import com.ebay.sdk.call.ReviseInventoryStatusCall;
//import com.ebay.sdk.call.ReviseItemCall;
//import com.ebay.soap.eBLBaseComponents.AddItemResponseType;
//import com.ebay.soap.eBLBaseComponents.BestOfferDetailsType;
//import com.ebay.soap.eBLBaseComponents.BuyerPaymentMethodCodeType;
//import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
//import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
//import com.ebay.soap.eBLBaseComponents.EndReasonCodeType;
//import com.ebay.soap.eBLBaseComponents.FeeType;
//import com.ebay.soap.eBLBaseComponents.FeesType;
//import com.ebay.soap.eBLBaseComponents.GetMyeBaySellingResponseType;
//import com.ebay.soap.eBLBaseComponents.InventoryFeesType;
//import com.ebay.soap.eBLBaseComponents.InventoryStatusType;
//import com.ebay.soap.eBLBaseComponents.ItemListCustomizationType;
//import com.ebay.soap.eBLBaseComponents.ItemSortTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.ItemType;
//import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.NameValueListArrayType;
//import com.ebay.soap.eBLBaseComponents.NameValueListType;
//import com.ebay.soap.eBLBaseComponents.PaginatedItemArrayType;
//import com.ebay.soap.eBLBaseComponents.PaginationResultType;
//import com.ebay.soap.eBLBaseComponents.PaginationType;
//import com.ebay.soap.eBLBaseComponents.ShippingDetailsType;
//import com.ebay.soap.eBLBaseComponents.SiteCodeType;
//import com.ebay.soap.eBLBaseComponents.VariationType;
//import com.ebay.soap.eBLBaseComponents.VariationsType;
//import com.ebay.soap.eBLBaseComponents.VerifyAddItemResponseType;
//import com.google.common.collect.Lists;
//import com.it.ocs.cal.service.inner.impl.SynchronizationService;
//import com.it.ocs.common.BeanConvertUtil;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.service.BaseService;
//import com.it.ocs.itemDescribe.Base.BaseTemplate;
//import com.it.ocs.itemDescribe.model.EBayInternalContentModel;
//import com.it.ocs.itemDescribe.model.EBayPromoteModel;
//import com.it.ocs.itemDescribe.model.EBayStoreAddUserEmailModel;
//import com.it.ocs.itemDescribe.model.StoreCategoryModel;
//import com.it.ocs.pic.model.PicModel;
//import com.it.ocs.pic.service.IPicService;
//import com.it.ocs.publication.dao.IPublicationInfoDAO;
//import com.it.ocs.publication.model.EBayAdvertTemplatesModel;
//import com.it.ocs.publication.model.PublicationAddItemModel;
//import com.it.ocs.publication.model.PublicationModel;
//import com.it.ocs.publication.model.ShipOptionModel;
//import com.it.ocs.publication.service.external.IEBayAdvertTemplatesService;
//import com.it.ocs.publication.service.external.IPublicationService;
//import com.it.ocs.publication.vo.PublicationVO;
//import com.it.ocs.salesStatistics.utils.TimeTools;
//import com.it.ocs.seller.dao.IEBaySellerDescriptionDao;
//import com.it.ocs.seller.model.EBayItemPromoteModel;
//import com.it.ocs.seller.model.EBaySellerDescriptionModel;
//import com.it.ocs.seller.service.IEBayItemPromoteService;
//import com.it.ocs.synchronou.call.EBayRelistItemCall;
//import com.it.ocs.synchronou.call.EbayAddItemCall;
//import com.it.ocs.synchronou.call.EbayEndItemCall;
//import com.it.ocs.synchronou.call.EbayGetMyeBaySellingCall;
//import com.it.ocs.synchronou.call.EbayVerifyItemCall;
//import com.it.ocs.synchronou.dao.IEBayAccountDao;
//import com.it.ocs.synchronou.dao.IEBayStoreCategoryDao;
//import com.it.ocs.synchronou.mapping.CurrencyCodeTypeEnum;
//import com.it.ocs.synchronou.mapping.EbayMappingToSDK;
//import com.it.ocs.synchronou.mapping.SiteIDEnum;
//import com.it.ocs.synchronou.mapping.SiteItemUrl;
//import com.it.ocs.synchronou.mapping.UserIDToken;
//import com.it.ocs.synchronou.mapping.ZoneMapping;
//import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
//import com.it.ocs.synchronou.model.EbayAccountModel;
//import com.it.ocs.synchronou.service.IEBayItemService;
//import com.it.ocs.synchronou.service.IEBayStoreCategoryService;
//import com.it.ocs.synchronou.service.ITemplateService;
//import com.it.ocs.synchronou.util.ConversionDateUtil;
//import com.it.ocs.synchronou.util.ObjectAndJsonUtil;
//import com.it.ocs.synchronou.util.RequestSDKMessage;
//import com.it.ocs.synchronou.util.ServerUrl;
//import com.it.ocs.synchronou.util.ValidationUtil;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//@Service
//public class EBayItemService extends BaseService implements IEBayItemService {
//	
//	private final static Logger log = Logger.getLogger(EBayItemService.class);
//	
//	@Autowired
//	private IPublicationService publicationService;
//	@Autowired
//	private IPublicationInfoDAO publicationDao;
//	@Autowired
//	private IEBaySellerDescriptionDao sellerDescriptionDao;
//	@Autowired 
//	private IEBayAccountDao accountDao;
//	@Autowired
//    private ITemplateService templateService;
//	@Autowired
//	private IEBayStoreCategoryDao storeCategoryDao;
//	@Autowired
//	private IEBayAdvertTemplatesService advertService;
//	@Autowired
//	private IPicService picService;
//	@Autowired
//	private IEBayStoreCategoryService storeCategoryService;
//	@Autowired
//	private IEBayItemPromoteService eBayItemPromoteService;
//	@Override
//	public OperationResult synchronouVariations(){
//		OperationResult result = new OperationResult();
//		StringBuffer stringBuffer = new StringBuffer();
//		List<EbayAccountModel> ebayAccountModels = accountDao.getAccounts();
//		String serverUrl = "";
//		for (EbayAccountModel ebayAccountModel : ebayAccountModels) {
//			serverUrl = ServerUrl.getServerUrl(ebayAccountModel.getAccount());
//			ApiContext apiContext = BaseEbaySDKService.getApiContext(ebayAccountModel.getToken(), serverUrl);
//			EbayGetMyeBaySellingCall getMyeBaySellingCall = new EbayGetMyeBaySellingCall(apiContext);
//			ItemListCustomizationType activeList = new ItemListCustomizationType();
//			activeList.setSort(ItemSortTypeCodeType.TIME_LEFT);
//			PaginationType paginationType = new PaginationType();
//			paginationType.setPageNumber(1);
//			paginationType.setEntriesPerPage(1);
//			activeList.setPagination(paginationType);
//			getMyeBaySellingCall.setActiveList(activeList);
//			/*getMyeBaySellingCall.setSite(SiteCodeType.US);*/
//			
//				try {
//					getMyeBaySellingCall.getMyeBaySelling();
//				} catch (Exception e1) {
//					log.error("同步失败 ：", e1);
//					
//				} 
//				GetMyeBaySellingResponseType response = getMyeBaySellingCall.getEyeBaySellingResponseType();
//				PaginatedItemArrayType itemArrayType =  response.getActiveList();
//			    PaginationResultType paginationResultType =	itemArrayType.getPaginationResult();
//			    Integer totalPages = paginationResultType.getTotalNumberOfPages();
//				ExecutorService cExecutorService = Executors.newCachedThreadPool();
//
//			    for (int i = 1; i <= totalPages/100+1; i++) {
//			    	int k = i;
//			    	 cExecutorService.execute(new Runnable() {
//						   @Override
//						   public void run() {
//						    	try{
//									
//						    	paginationType.setEntriesPerPage(100);
//						    	paginationType.setPageNumber(k);
//						    	activeList.setPagination(paginationType);
//						    	getMyeBaySellingCall.setActiveList(activeList);
//						    	getMyeBaySellingCall.setDetailLevel(new DetailLevelCodeType[]{DetailLevelCodeType.RETURN_ALL});
//						    	getMyeBaySellingCall.getMyeBaySelling();
//								GetMyeBaySellingResponseType response = getMyeBaySellingCall.getEyeBaySellingResponseType();
//						    	response= getMyeBaySellingCall.getEyeBaySellingResponseType();
//						    	PaginatedItemArrayType itemArrayType =  response.getActiveList();
//						    	itemArrayType = response.getActiveList();
//						    	ItemType[] itemTypes =  itemArrayType.getItemArray().getItem();
//						    	for (ItemType itemType : itemTypes) {
//						    		String itemId = itemType.getItemID();
//									PublicationModel publicationModel = new PublicationModel();
//									synchronized (publicationModel) {
//										publicationModel.setItemId(itemId);
//										publicationModel.setQuantity_available(itemType.getQuantityAvailable());
//										VariationsType variationsTypes = itemType.getVariations();
//										if(!ValidationUtil.isNull(variationsTypes)){
//											VariationType[] variationTypes = variationsTypes.getVariation();
//											List<Map<String, String>> maps = Lists.newArrayList();
//											/*for (VariationType variationType : variationTypes) {
//												Map<String, String> map = new HashMap<>();
//												map.put("SKU", variationType.getSKU());
//												Double startPrice = variationType.getStartPrice().getValue();
//												map.put("StartPrice", startPrice.toString());
//												map.put("Currency", variationType.getStartPrice().getCurrencyID().value());
//												Object quantityO = variationType.getQuantity();
//												if (!ValidationUtil.isNull(quantityO)) {
//													map.put("Quantity", quantityO.toString());
//												}
//												Object quantitySoldO = variationType.getSellingStatus().getQuantitySold();
//												if(!ValidationUtil.isNull(quantitySoldO)){
//													map.put("QuantitySold", quantitySoldO.toString());
//												}
//												
//												map.put("VariationTitle", variationType.getVariationTitle());
//												NameValueListArrayType nameValue = variationType.getVariationSpecifics();
//												NameValueListType[] nameValues = nameValue.getNameValueList();
//												List<Map<String, String>> nameValueMaps = Lists.newArrayList();
//												for (NameValueListType nameValueListType : nameValues) {
//													Map<String, String> nameValueMap = new HashMap<>();
//													nameValueMap.put("Name", nameValueListType.getName());
//													String[] values = nameValueListType.getValue();
//													StringBuffer valueResult = new StringBuffer();
//													for (String string : values) {
//														valueResult.append(string+",");
//													}
//													nameValueMap.put("value", valueResult.toString());
//													nameValueMaps.add(nameValueMap);
//												}
//												String nameValueList = ObjectAndJsonUtil.MapsToJsonArray(nameValueMaps, null);
//												map.put("NameValueList", nameValueList);
//												maps.add(map);
//											}*/
//											//publicationModel.setVariations_quantity_available(ObjectAndJsonUtil.MapsToJsonArray(maps, null));
//											publicationModel.setQuantity_sold(itemType.getSellingStatus().getQuantitySold());
//										  }
//										}
//									publicationDao.updatePublicationVariations(publicationModel);
//									stringBuffer.append(itemId+"同步成功！<br/>");
//						    	}
//								} catch (Exception e) {
//									stringBuffer.append("同步第"+k+"页库存异常\n");
//								}
//						   	}
//						   });
//				}
//			    cExecutorService.shutdown();
//				while(true){
//					if(cExecutorService.isTerminated()){
//												break;
//					}
//					try {
//												Thread.sleep(2000);
//					} catch (InterruptedException e) {
//												log.equals(e);
//					}
//				}	
//			    
//			
//		}
//		result.setDescription(stringBuffer.toString());
//		return result;
//	}
//	@Override
//	public OperationResult endItem(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		String itemId = map.get("itemId").toString();
//		PublicationModel publicationModel2 = pubInfoDAO.selectPublicByItemId(itemId);
//		String ebayAccount = publicationModel2.getEbayAccount();
//		EbayAccountModel ebayAccountModel = accountDao.getAccountByAccount(ebayAccount);
//		String token = ebayAccountModel.getToken();
//		String serverUrl = ServerUrl.getServerUrl(ebayAccount);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		EbayEndItemCall ebayEndItemCall = new EbayEndItemCall(apiContext);
//		String endingReason = map.get("endingReason").toString();
//		ebayEndItemCall.setItemID(itemId);
//		ebayEndItemCall.setEndingReason(EndReasonCodeType.fromValue(endingReason));
//			Calendar endTime;
//			try {
//				endTime = ebayEndItemCall.endItem();
//				String string = RequestSDKMessage.getSDKMessage(ebayEndItemCall, "结束");
//				PublicationModel publicationModel = new PublicationModel();
//				publicationModel.setItemId(itemId);
//				publicationModel.setEnd_publication_date(endTime.getTime());
//				publicationModel.setEnding_reason(endingReason);
//				publicationDao.updatePublictionInfoEndDateByItemId(publicationModel);
//			    result.setDescription(string);
//			} catch (ApiException e) {
//				result.setDescription(RequestSDKMessage.getSDKMessage(ebayEndItemCall, "结束"));
//				result.setError(e.getMessage());
//				log.error("结束产品失败", e);
//			} catch (SdkException e) {
//				result.setDescription(RequestSDKMessage.getSDKMessage(ebayEndItemCall, "结束"));
//				result.setError(e.getMessage());
//				log.error("结束产品失败", e);
//			} catch (Exception e) {
//				result.setDescription("服务器错误！");
//				result.setError(e.getMessage());
//				log.error("结束产品失败", e);
//			}
//		
//		return result;
//	}
//	/**
//	 * 描述：刊登
//	 */
//	@Override
//	public OperationResult publishedItem(Long id) {
//		OperationResult result = new OperationResult();
//		PublicationAddItemModel publicationModel = new PublicationAddItemModel(publicationDao.getById(id));
//        String ebayAccount = publicationModel.getEbayAccount();
//        PublicationModel publicationModel3 = publicationModel.getPub();
//        List<PublicationModel> publicationModels = publicationDao.selectLineExist(publicationModel3);
//        if(publicationModels.size()>0){
//        	result.setDescription("线上已经存在(账号、标题、SKU，三者相同)，不可刊登第二次!");
//        	return result;
//        }
//        EbayAccountModel ebayAccountModel = accountDao.getAccountByAccount(ebayAccount);
//		
//		String serverUrl = ServerUrl.getServerUrl(ebayAccount);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(ebayAccountModel.getToken(), serverUrl);
//		EbayAddItemCall addItemCall = new EbayAddItemCall(apiContext);
//		Long siteID = publicationModel.getSiteId();
//		ItemType itemType = getItemType(publicationModel);
//		SiteCodeType siteCodeType = SiteIDEnum.searchBySiteID(siteID);
//		itemType.setSite(siteCodeType);//站点
//		addItemCall.setSite(siteCodeType);
//		StringBuffer stringBuffer = new StringBuffer();
//		addItemCall.setItem(itemType);
//		try {
//			FeesType feesType = addItemCall.addItem();
//			stringBuffer.append(RequestSDKMessage.getSDKMessage(addItemCall,"发布"));
//			String itemId =itemType.getItemID();
//			String url = SiteItemUrl.getItemUrlBySIDAndIID(ebayAccount, itemId);
//			if(siteID == 77l){
//				url = url.replace(".com", ".de");
//			}
//			stringBuffer.append("<a href=\""+url+"\" target=\"_blank\"><b>ItemID:</b>"+itemId+"</a><br/>");
//			PublicationModel publicationModel2 = publicationModel.getPub();
//			
//			publicationModel2.setEbayProductURL(url);
//			publicationModel2.setItemId(itemId);
//			//结束时间
//			Calendar endTime = addItemCall.getAddItemResponseType().getEndTime();
//			Date endDate = ConversionDateUtil.getDateByCZ(endTime,"GMT+8");
//			Calendar startTime = addItemCall.getAddItemResponseType().getStartTime();
//			//刊登时间
//			Date startDate = ConversionDateUtil.getDateByCZ(startTime,"GMT+8");
//			result.setEndDate(endDate);
//			result.setStartDate(startDate);
//			
//			publicationModel2.setEnd_publication_date(endTime.getTime());
//			Date publicationDate = new Date();
//			publicationModel2.setPublication_date(publicationDate);
//			publicationModel2.setProductCount(itemType.getQuantity().toString());
//			//publicationDao.updatePublicationById(publicationModel2);
//			publicationDao.updateById(publicationModel2);
//		    OperationResult result2 = publicationService.copyItemPublication(id, publicationModel2);
//		    if(!ValidationUtil.isNull(result2)){
//		    	result.setId(result2.getId());
//		    }
//		    FeeType[] fees = feesType.getFee();
//			Map<String, String> feeMap = RequestSDKMessage.getSDKFeeMessage(fees);
//			stringBuffer.append(feeMap.get("totalPrice"));
//			stringBuffer.append(feeMap.get("feeBuffer"));
//			result.setDescription(stringBuffer.toString());
//		} catch (ApiException e) {
//			result.setErrorCode(1);
//			result.setDescription(RequestSDKMessage.getSDKMessage(addItemCall, "发布"));
//			result.setError(e.getMessage());
//			log.error("发布产品失败", e);
//			
//		} catch (SdkException e) {
//			result.setErrorCode(1);
//			result.setDescription(e.getMessage());
//			result.setError(e.getMessage());
//			log.error("发布产品失败", e);
//		} catch (Exception e) {
//			result.setDescription("服务器错误！");
//			result.setErrorCode(1);
//			result.setError(e.getMessage());
//			log.error("发布产品失败", e);
//			
//		}
//		return result;
//	}
//	public Date getDateByCZ(Calendar calendar,String zone){
//		String sourceEnd = ConversionDateUtil.dateToCharFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
//		String endString = TimeTools.timeConvert(sourceEnd, calendar.getTimeZone().getID(), zone);
//		return ConversionDateUtil.charToDate(endString, "yyyy-MM-dd HH:mm:ss");
//	}
//	
//	public List<EBayPromoteModel> getPromotes(PublicationAddItemModel mode,String templateId) {
//		EBayItemPromoteModel itemPromoteModel = publicationDao.getItemPromoteInfoById(templateId);
//		List<String> idList = null;
//		if(null==itemPromoteModel) return new ArrayList<EBayPromoteModel>();
//		if("0".equals(itemPromoteModel.getDataType())){//指定方式
//			String itemIds = itemPromoteModel.getItemId();
//			if(null != itemIds && !StringUtils.isEmpty(itemIds)){
//				String[] ids = itemIds.split(",");
//				idList = Arrays.asList(ids);
//			}
//		}else{//规则方式
//			idList = new ArrayList<>();
//			if(!ValidationUtil.isNull(itemPromoteModel.getRules())){
//				JSONObject json =JSONObject.fromObject(itemPromoteModel.getRules());
//				Map<String,Object> map = (Map<String,Object>)json;
//				//账号指定
//				if("1".equals(map.get("account_check").toString())){
//					map.put("account", map.get("account1"));
//				}else{
//					map.put("account", mode.getEbayAccount());
//				}
//				//站点指定
//				if("1".equals(map.get("site_check").toString())){
//					map.put("siteId", map.get("siteId1"));
//				}else{
//					map.put("siteId", mode.getSiteId().toString());
//				}
//				//刊登类型指定
//				if("0".equals(map.get("publictionType_check").toString())){
//					map.put("publictionType", mode.getPublicationType()+";");
//				}
//				//物品分类指定
//				if("0".equals(map.get("itemType_check").toString())){
//					map.put("itemType", mode.getProductFirstCategoryId());
//				}
//				//商店分类指定
//				if("0".equals(map.get("itemStore_check").toString())){
//					map.put("itemStore", mode.getStoreFirstCategoryId());
//				}
//				
//				List<PublicationVO> itemVOs = eBayItemPromoteService.getItemsByRule(map);
//				for(PublicationVO itemVO : itemVOs){
//					idList.add(itemVO.getItemId());
//				}
//			 }
//		}
//		
//		if(null!= idList&&idList.size()>0){
//			List<EBayPromoteModel> eBayPromoteModels = publicationDao.getProductInfo(idList);
//			for (EBayPromoteModel eBayPromoteModel : eBayPromoteModels) {
//				String url = eBayPromoteModel.getImgUrl();
//				if(url!=null){
//				eBayPromoteModel.setImgUrl(url.replace("http:", "https:"));
//				}
//			}
//			return eBayPromoteModels;
//		}
//		return new ArrayList<EBayPromoteModel>();
//		
//	}
//	
//	@Override
//	public List<EBayPromoteModel> getPromotes(String templateId) {
//		EBayItemPromoteModel itemPromoteModel = publicationDao.getItemPromoteInfoById(templateId);
//		List<String> idList = null;
//		if(null==itemPromoteModel) return new ArrayList<EBayPromoteModel>();
//		if("0".equals(itemPromoteModel.getDataType())){//指定方式
//			String itemIds = itemPromoteModel.getItemId();
//			if(null != itemIds && !StringUtils.isEmpty(itemIds)){
//				String[] ids = itemIds.split(",");
//				idList = Arrays.asList(ids);
//			}
//		}else{//规则方式
//			idList = new ArrayList<>();
//			if(!ValidationUtil.isNull(itemPromoteModel.getRules())){
//				JSONObject json =JSONObject.fromObject(itemPromoteModel.getRules());
//				Map<String,Object> map = (Map<String,Object>)json;
//				map.put("siteId", itemPromoteModel.getSiteId());
//				map.put("account", itemPromoteModel.getAccount());
//				List<PublicationVO> itemVOs = eBayItemPromoteService.getItemsByRule(map);
//				for(PublicationVO itemVO : itemVOs){
//					idList.add(itemVO.getItemId());
//				}
//			 }
//		}
//		
//		if(null!= idList&&idList.size()>0){
//			List<EBayPromoteModel> eBayPromoteModels = publicationDao.getProductInfo(idList);
//			for (EBayPromoteModel eBayPromoteModel : eBayPromoteModels) {
//				String url = eBayPromoteModel.getImgUrl();
//				if(url!=null){
//				eBayPromoteModel.setImgUrl(url.replace("http:", "https:"));
//				}
//			}
//			return eBayPromoteModels;
//		}
//		return new ArrayList<EBayPromoteModel>();
//	}
//	
//	
//	@Override
//	public ItemType getItemType(PublicationAddItemModel publicationAddItemModel) {
//		ItemType itemType = new ItemType();
//		Long siteID = publicationAddItemModel.getSiteId();
//		String user = publicationAddItemModel.getEbayAccount();
//		CurrencyCodeType currency = CurrencyCodeTypeEnum.searchCurrencyBySiteID(siteID);//币种
//		String listingType = publicationAddItemModel.getPublicationType();
//		if (listingType.equals("FixedPriceItem1")) {
//			listingType= "FixedPriceItem";
//		}
//		itemType.setListingType(ListingTypeCodeType.fromValue(listingType));//刊登类型
//
//		itemType.setCurrency(currency);
//		itemType.setSKU(publicationAddItemModel.getSku());//SKU 
//		itemType.setTitle(publicationAddItemModel.getProductTitle());//产品标题
//		itemType.setSubTitle(ValidationUtil.isEmptyToNull(publicationAddItemModel.getProductSubtitle()));//产品子标题
//		itemType.setPrimaryCategory(EbayMappingToSDK.emptyCategoryTypeById(publicationAddItemModel.getProductFirstCategoryId()));//第一分类
//		itemType.setSecondaryCategory(EbayMappingToSDK.emptyCategoryTypeById(publicationAddItemModel.getProductSecondCategoryId()));//第二分类 
//		itemType.setStorefront(EbayMappingToSDK.getStoreFrontType(publicationAddItemModel.getStoreFirstCategoryId(), publicationAddItemModel.getStoreSecondCategoryId()));//店铺分类
//		//-----------物品属性与状况
//		itemType.setVariations(publicationAddItemModel.getVariations());
//	    //UPC、ISBN、EAN
//		itemType.setProductListingDetails(publicationAddItemModel.getProductListingDetail());
//		//物品状况 
//		itemType.setConditionID(publicationAddItemModel.getProductStatus());
//		//物品属性与值
//		itemType.setItemSpecifics(publicationAddItemModel.getNameValueListArrayType());
//		//publicationAddItemModel.
//		itemType.setPictureDetails(publicationAddItemModel.getEbayImages());//eBay图片
//		//eBay物品描述
//		//顶部推广
//		List<EBayPromoteModel> topPromotes = getPromotes(publicationAddItemModel,publicationAddItemModel.getTopPromotionType());
//		//内部推广
//		List<EBayPromoteModel> inPromotes = getPromotes(publicationAddItemModel,publicationAddItemModel.getInPromotionType());
//		Class<?> clazz;
//		try {
//			clazz = Class.forName("com.it.ocs.itemDescribe.template.TemplateC");
//			BaseTemplate baseTemplate = (BaseTemplate) clazz.newInstance();
//			baseTemplate.setTemplateService(templateService);
//			baseTemplate.setTopPromoteModel(topPromotes);
//			Long advertId = publicationAddItemModel.getAdvert_id();
//			EBayAdvertTemplatesModel eBayAdvertTemplatesModel = advertService.selectAdvertById(advertId);
//			//广告图片
//			if(!ValidationUtil.isNull(eBayAdvertTemplatesModel)){
//				Long picId = eBayAdvertTemplatesModel.getPid_id();
//				PicModel picModel = picService.queryById(picId);
//				if(!ValidationUtil.isNull(picModel)){
//				baseTemplate.setAdvertImg("data:image/"+picModel.getImgType()+";base64,"+picModel.getUrl() );
//				}
//				//广告产品
//				if(!ValidationUtil.isNull(eBayAdvertTemplatesModel.getProduct_url())){
//				baseTemplate.setAdvertUrl(eBayAdvertTemplatesModel.getProduct_url());
//				}
//			}else{
//				baseTemplate.setAdvertImg("");
//				baseTemplate.setAdvertUrl("");
//			}
//			/*String realUrl = picModel.getRealUrl();
//			if (ValidationUtil.isNullOrEmpty(realUrl)) {
//				PicVO picVO = picService.getPicRealUrl(picId, user);
//				baseTemplate.setAdvertImg(picVO.getRealUrl());
//			}else{
//				baseTemplate.setAdvertImg(realUrl);
//			}
//			*/
//			//商店连接
//			EBayStoreCategoryModel eBayStoreCategoryModel3 = storeCategoryService.selectStoreUrlByUserId(user);
//			String storeUrl = "";
//			if(ValidationUtil.isNull(eBayStoreCategoryModel3)){
//				storeUrl="http://stores.ebay.com/neonmartlighting";
//			}else{
//				storeUrl=eBayStoreCategoryModel3.getStore_url();
//			}
//			baseTemplate.setStoreUrl(storeUrl);
//			//左边连接
//			  //商店分类
//			 
//			  List<EBayStoreCategoryModel> eBayStoreCategoryModels = storeCategoryDao.selectChildStoreCategorysByCIDAndMID(0L, user);
//
//			  List<StoreCategoryModel> storeCategoryModels = Lists.newArrayList();
//			  
//			  for (EBayStoreCategoryModel eBayStoreCategoryModel2 : eBayStoreCategoryModels) {
//					StoreCategoryModel storeCategoryModel = new StoreCategoryModel();
//					storeCategoryModel.setCategoryId(eBayStoreCategoryModel2.getCategory_id().toString());
//					storeCategoryModel.setCategoryName(eBayStoreCategoryModel2.getName());
//					storeCategoryModel.setStoreUrl(storeUrl);
//					storeCategoryModels.add(storeCategoryModel);
//			  }
//			  baseTemplate.setEBayStoreCategoryModels(storeCategoryModels);
//			  //用户地址和按钮
//			  EBayStoreAddUserEmailModel storeAddUserEmailModel = new EBayStoreAddUserEmailModel();
//			  baseTemplate.setStoreAddUserEmailModel(storeAddUserEmailModel);
//			  EBaySellerDescriptionModel eBaySellerDescriptionModel = sellerDescriptionDao.getById(publicationAddItemModel.getSellerDescription());
//			  // EBaySellerDescriptionModel eBaySellerDescriptionModel = null;
//			  EBayInternalContentModel  eBayInternalContentModel = new EBayInternalContentModel();
//			  if(eBaySellerDescriptionModel==null){
//				  eBaySellerDescriptionModel = new EBaySellerDescriptionModel();
//				  eBayInternalContentModel.setPayment("");
//				  eBayInternalContentModel.setShipment("");
//			      eBayInternalContentModel.setReturns("");
//			      eBayInternalContentModel.setAboutUs("");
//			      eBayInternalContentModel.setFaq("");
//			  }else{
//				  eBayInternalContentModel.setPayment(eBaySellerDescriptionModel.getDescription1());
//				  eBayInternalContentModel.setShipment(eBaySellerDescriptionModel.getDescription2());
//				  eBayInternalContentModel.setReturns(eBaySellerDescriptionModel.getDescription3());
//				  eBayInternalContentModel.setAboutUs(eBaySellerDescriptionModel.getDescription4());
//				  eBayInternalContentModel.setFaq(eBaySellerDescriptionModel.getDescription5());
//				  
//			  }
//			  
//			  eBayInternalContentModel.setAppComment(publicationAddItemModel.getAppComment());
//			  eBayInternalContentModel.setProduceDescription(publicationAddItemModel.getComments());
//			  eBayInternalContentModel.setTitle(publicationAddItemModel.getProductTitle());
//			  eBayInternalContentModel.setBigImgUrl(publicationAddItemModel.getTemplateImages());
//			  baseTemplate.setEBayInternalContentModel(eBayInternalContentModel);
//			  //底部推广
//			  
//			  List<EBayPromoteModel> endPromotes = getPromotes(publicationAddItemModel,publicationAddItemModel.getFooterPromotionType());
//			  baseTemplate.setBottomPromoteModel(endPromotes);
//			  baseTemplate.setSiteId(siteID);
//			  baseTemplate.setInPromoteModels(inPromotes);
//			  String description= baseTemplate.getTemplate();
//			  itemType.setDescription(description);
//		} catch (ClassNotFoundException e) {
//			log.error("获取itemType失败", e);
//		} catch (InstantiationException e) {
//			log.error("获取itemType失败", e);
//		} catch (IllegalAccessException e) {
//			log.error("获取itemType失败", e);
//		}
//
//		//拍卖 
//		itemType.setPrivateListing(Boolean.parseBoolean(publicationAddItemModel.getIndividual()));//不向公众显示买家的名称
//		itemType.setListingDuration(publicationAddItemModel.getPublicationDays());//刊登天数
//
//		itemType.setStartPrice(EbayMappingToSDK.getAmountTypeByStr(publicationAddItemModel.getPrice(), currency));//开始价格（固定价格 ）
//		itemType.setReservePrice(EbayMappingToSDK.getAmountTypeByStr(publicationAddItemModel.getReserverPrice(), currency));//保留价格
//		String quantity = publicationAddItemModel.getProductCount();
//		Integer count = null;
//		if(ValidationUtil.isNullOrEmpty(quantity)){
//			count=null;
//		}else{
//			count=Integer.parseInt(quantity);
//		}
//		itemType.setQuantity(count);//数量
//		//是否同意买家还价
//		BestOfferDetailsType bestOfferDetailsType = new BestOfferDetailsType();
//		bestOfferDetailsType.setBestOfferEnabled(ValidationUtil.getBoolean(publicationAddItemModel.getAcceptBuyerCounter()));
//		itemType.setBestOfferDetails(bestOfferDetailsType);
//		//自动接收大于或自动接收小于
//		itemType.setListingDetails(
//				EbayMappingToSDK.getListingDetailsByMinAndMax(publicationAddItemModel.getAcceptBuyerCounterMin(),
//						publicationAddItemModel.getAcceptBuyerCounterMax(), currency));
//		
//		
//		//付款
//		String paypaiAccount = publicationAddItemModel.getPaypaiAccount();//PayPal账号
//		BuyerPaymentMethodCodeType[] payment = EbayMappingToSDK.getPaymentMethod(publicationAddItemModel.getSupportPaypaiInfo());
//		if(!ValidationUtil.isNullOrEmpty(paypaiAccount)){
//			itemType.setPayPalEmailAddress(paypaiAccount);
//			int size = payment.length;
//			payment[size-1]=BuyerPaymentMethodCodeType.PAY_PAL;
//		}
//		//立即付款
//		itemType.setAutoPay(publicationAddItemModel.getAutoPay());
//		//支付方式
//		itemType.setPaymentMethods(payment);
//		//付款说明 
//		
//		//买家要求
//		itemType.setBuyerRequirementDetails(EbayMappingToSDK.getBuyerRequirement(publicationAddItemModel));
//		//退货政策
//		itemType.setReturnPolicy(EbayMappingToSDK.getReturnPolicyTeyp(publicationAddItemModel));
//		//物品所在地 
//		itemType.setCountry(publicationAddItemModel.getRegion());//国家或地区
//		itemType.setPostalCode(publicationAddItemModel.getPostCode());//邮编 
//		itemType.setLocation(publicationAddItemModel.getProductAddress());//物品所在地
//		//运送选项
//			//国际和国内运送
//		 List<ShipOptionModel> shipOptionModels  = publicationAddItemModel.getShipOptionModels();
//		 if(null == shipOptionModels){
//			 shipOptionModels = publicationService.getPublictonTransById(publicationAddItemModel.getId());
//		 }
//		    
//		 if(shipOptionModels.size()>=1){
//		    	ShippingDetailsType shippingDetailsType = EbayMappingToSDK.getShipping(shipOptionModels,currency,publicationAddItemModel);
//				if(!ValidationUtil.isNull(publicationAddItemModel.getShipLocationOver())){
//					shippingDetailsType.setExcludeShipToLocation(publicationAddItemModel.getShipLocationOver());
//				}
//				itemType.setShippingDetails(shippingDetailsType);
//		 }
//		    
//			//处理时间
//			itemType.setDispatchTimeMax(publicationAddItemModel.getDomesticOptDay());
//			
//			//运送地方
//			itemType.setShipToLocations(publicationAddItemModel.getShipLocationIn());
//			
//	    //广告特色
//		if(!ValidationUtil.isNull(publicationAddItemModel.getFeatureType())){
//			itemType.setListingEnhancement(publicationAddItemModel.getFeatureType());
//		}
//		
//		return itemType;
//	}
//
//
//	
//	/**
//	 * 描述：更改信息
//	 */
//	@Override
//	public OperationResult updateItemType(Long id) {
//		PublicationAddItemModel publicationModel = new PublicationAddItemModel(publicationDao.getLineById(id));
//		return updateLineItem(publicationModel);
//	}
//	
//	@Override
//	public OperationResult updateLineItem(PublicationAddItemModel publicationModel) {
//		String ebayAccount = publicationModel.getEbayAccount();
//		EbayAccountModel ebayAccountModel = accountDao.getAccountByAccount(ebayAccount);
//		String token = ebayAccountModel.getToken();
//		String serverUrl = ServerUrl.getServerUrl(ebayAccount);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		OperationResult result = new OperationResult();
//		StringBuffer stringBuffer = new StringBuffer();
//		ReviseItemCall reviseItemCall = new ReviseItemCall(apiContext);
//		
//		try {	
//			Long siteID = publicationModel.getSiteId();
//			
//			ItemType itemType = getItemType(publicationModel);
//			
//			itemType.setItemID(publicationModel.getItemId().toString());
//			
//			itemType.setSite(SiteIDEnum.searchBySiteID(siteID));//站点
//			
//			reviseItemCall.setSite(SiteIDEnum.searchBySiteID(siteID));
//			
//			reviseItemCall.setItemToBeRevised(itemType);
//			
//			FeesType feesType = reviseItemCall.reviseItem();
//			
//			stringBuffer.append(RequestSDKMessage.getSDKMessage(reviseItemCall, "更新"));
//			
//			String itemId =itemType.getItemID();
//			
//			stringBuffer.append("<b>ItemID:</b>"+itemId+"<br/>");
//			
//			FeeType[] fees = feesType.getFee();
//			Map<String, String> feeMap = RequestSDKMessage.getSDKFeeMessage(fees);
//			stringBuffer.append(feeMap.get("totalPrice"));
//			stringBuffer.append(feeMap.get("feeBuffer"));
//			result.setDescription(stringBuffer.toString());
//			
//		} catch (ApiException e) {
//			result.setDescription(RequestSDKMessage.getSDKMessage(reviseItemCall, "更新"));
//			log.error("ebay在线范本更新失败", e);
//		} catch (SdkException e) {
//			result.setDescription(e.getMessage());
//			log.error("ebay在线范本更新失败-SDK异常", e);
//		} catch (Exception e) {
//			result.setDescription("服务器错误！");
//			log.error("ebay在线范本更新失败", e);
//		}
//		result.setData(reviseItemCall.getResponseObject().getAck().value());
//		return result;
//	}
//	
//	/**
//	 * 描述：检查费用
//	 */
//	@Override
//	public OperationResult verifyAddItem(Long id) {
//		PublicationAddItemModel publicationAddItemModel = new PublicationAddItemModel(publicationDao.getById(id));
//		publicationAddItemModel.getPub().setItemId(null);
//		return verifyItem(publicationAddItemModel);
//	}
//	/**
//	 * 描述：检查线上刊登费用。
//	 */
//	@Override
//	public OperationResult verifyLineItem(Long id) {
//		PublicationAddItemModel publicationAddItemModel = new PublicationAddItemModel(pubInfoDAO.getLineById(id));
//		return verifyItem(publicationAddItemModel);
//	}
//	public OperationResult verifyItem(PublicationAddItemModel publicationModel) {
//		String ebayAccount = publicationModel.getEbayAccount();
//		OperationResult result = new OperationResult();
//		EbayAccountModel ebayAccountModel = accountDao.getAccountByAccount(ebayAccount);
//		if(null==ebayAccountModel){
//			result.setDescription("检查失败！");
//			return result;
//		}
//		String token = ebayAccountModel.getToken();
//		String serverUrl = ServerUrl.getServerUrl(ebayAccount);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		
//		
//		EbayVerifyItemCall ebayVerifyItemCall = new EbayVerifyItemCall(apiContext);
//		Long siteID = publicationModel.getSiteId();
//		ItemType itemType = getItemType(publicationModel);
//		itemType.setSite(SiteIDEnum.searchBySiteID(siteID));//站点
//		ebayVerifyItemCall.setSite(SiteIDEnum.searchBySiteID(siteID));
//		StringBuffer stringBuffer = new StringBuffer();
//		ebayVerifyItemCall.setItem(itemType);
//		try {
//			FeesType feesType = ebayVerifyItemCall.verifyAddItem();
//			stringBuffer.append(RequestSDKMessage.getSDKMessage(ebayVerifyItemCall,"检查费用"));
//		    VerifyAddItemResponseType response = ebayVerifyItemCall.getVerifyAddItemResponseType();
//		    
//		    stringBuffer.append("<b>ItemID:</b>"+response.getItemID()+"<br/>");
//			FeeType[] fees = feesType.getFee();
//			Map<String, String> feeMap = RequestSDKMessage.getSDKFeeMessage(fees);
//			stringBuffer.append(feeMap.get("totalPrice"));
//			stringBuffer.append(feeMap.get("feeBuffer"));
//			result.setDescription(stringBuffer.toString());
//		} catch (ApiException e) {
//			result.setDescription(RequestSDKMessage.getSDKMessage(ebayVerifyItemCall, "检查费用"));
//			result.setError(e.getMessage());
//			log.error("检查费用失败", e);
//			return result;
//		} catch (SdkException e) {
//			result.setDescription(e.getMessage());
//			result.setError(e.getMessage());
//			log.error("检查费用失败", e);
//			return result;
//		} catch (Exception e) {
//			result.setDescription("检查失败！");
//			result.setError(e.getMessage());
//			log.error("检查费用失败", e);
//			return result;
//		}
//		return result;
//	}
//	/**
//	 * 描述：定时刊登
//	 */
//	@Override
//	public OperationResult timingAddItem(PublicationVO publicationVO) {
//		PublicationAddItemModel publicationModel = new PublicationAddItemModel(publicationDao.getById(publicationVO.getId()));
//		String ebayAccount = publicationModel.getEbayAccount();
//		EbayAccountModel ebayAccountModel = accountDao.getAccountByAccount(ebayAccount);
//		String token = ebayAccountModel.getToken();
//		String serverUrl = ServerUrl.getServerUrl(ebayAccount);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		OperationResult result = new OperationResult();
//		EbayAddItemCall addItemCall = new EbayAddItemCall(apiContext);
//		Long siteID = publicationModel.getSiteId();
//		ItemType itemType = getItemType(publicationModel);
//		itemType.setSite(SiteIDEnum.searchBySiteID(siteID));//站点
//		Date date = ConversionDateUtil.charToDate(publicationVO.getTimingDate(), "yyyy-MM-dd HH:mm:ss");
//		String dateString = ConversionDateUtil.dateToCharFormat(date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//		Calendar calendar = ConversionDateUtil.getCalendarByDateAndSiteId(dateString, siteID);
//		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//		itemType.setScheduleTime(calendar);
//		addItemCall.setSite(SiteIDEnum.searchBySiteID(siteID));
//		StringBuffer stringBuffer = new StringBuffer();
//		addItemCall.setItem(itemType);
//		try {
//			FeesType feesType = addItemCall.addItem();
//			stringBuffer.append(RequestSDKMessage.getSDKMessage(addItemCall,"定时发布"));
//			
//			String itemId =itemType.getItemID();
//			stringBuffer.append("<b>ItemID:</b>"+itemId+"<br/>");
//			PublicationModel publicationModel2 = publicationModel.getPub();
//			
//			publicationModel2.setEbayProductURL(SiteItemUrl.getItemUrlBySIDAndIID(ebayAccount, itemId));
//			publicationModel2.setItemId(itemId);
//			Date publictionDate = new Date();
//			publicationModel2.setPublication_date(publictionDate);
//			AddItemResponseType addItemResponseType = addItemCall.getAddItemResponseType();
//			
//			Calendar timingDateCalendar = addItemResponseType.getStartTime();
//			Calendar endDateCalendar = addItemResponseType.getEndTime();
//			String sourceTiming = ConversionDateUtil.dateToCharFormat(timingDateCalendar.getTime(), "yyyy-MM-dd HH:mm:ss");
//			String sourceEnd = ConversionDateUtil.dateToCharFormat(endDateCalendar.getTime(), "yyyy-MM-dd HH:mm:ss");
//			String timingString = TimeTools.timeConvert(sourceTiming, timingDateCalendar.getTimeZone().getID(), "GMT+8");
//			String endString = TimeTools.timeConvert(sourceEnd, endDateCalendar.getTimeZone().getID(), "GMT+8");
//			Date timingDate = ConversionDateUtil.charToDate(timingString, "yyyy-MM-dd HH:mm:ss");
//			Date endDate = ConversionDateUtil.charToDate(endString, "yyyy-MM-dd HH:mm:ss");
//			publicationModel2.setTiming_publication_date(timingDate);
//			publicationModel2.setEnd_publication_date(endDate);
//			//publicationDao.updatePublicationById(publicationModel2);
//			publicationService.copyItemPublication(publicationModel2.getId(), publicationModel2);
//
//			FeeType[] fees = feesType.getFee();
//			Map<String, String> feeMap = RequestSDKMessage.getSDKFeeMessage(fees);
//			stringBuffer.append(feeMap.get("totalPrice"));
//			stringBuffer.append(feeMap.get("feeBuffer"));
//			result.setDescription(stringBuffer.toString());
//		
//		} catch (ApiException e) {
//			result.setDescription(RequestSDKMessage.getSDKMessage(addItemCall, "定时发布"));
//			log.error("定时发布失败", e);
//			
//		} catch (SdkException e) {
//			result.setDescription(e.getMessage());
//			log.error("定时发布失败", e);
//			return result;
//		} catch (Exception e) {
//			result.setDescription("定时发布失败！");
//			log.error("定时发布失败", e);
//			return result;
//			
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult verifyAddItem(Map<String, Object> map) {
//		map.put("state", "0");
//		OperationResult or = publicationService.savePubInfo(map);
//		Object saveResult = or.getData();
//		if(null == saveResult){
//			or = new OperationResult();
//			or.setDescription("数据保存时出现错误，请检查数据！");
//			return or;
//		}
//		PublicationModel pub = (PublicationModel)saveResult;
//		PublicationModel pubInfo = BeanConvertUtil.mapToObject2(map, PublicationModel.class);
//		if(null != map.get("siteId")){
//			pubInfo.setSiteId(Long.parseLong(map.get("siteId").toString()));
//		}
//		pubInfo.setEbayImages(pub.getEbayImages());
//		pubInfo.setTemplateImages(pub.getTemplateImages());
//		PublicationAddItemModel publicationModel = new PublicationAddItemModel(pubInfo);
//		List<ShipOptionModel> ships = new ArrayList<>();
//		List<Map> domesticTrans = pubInfo.getDomesticTrans();
//		List<Map> calCulateTrans = pubInfo.getCalCulateTrans();
//		ships.addAll(getShipOptionModelList(domesticTrans,null));
//		ships.addAll(getShipOptionModelList(calCulateTrans,null));
//		publicationModel.setShipOptionModels(ships);
//		or = verifyItem(publicationModel);
//		or.setData(pubInfo);
//		return or;
//	}
//	
//	/**
//	 * 遍历运输选项数据，放入集合
//	 * @param domesticTrans
//	 * @param id
//	 * @return
//	 */
//	private List<ShipOptionModel> getShipOptionModelList(List<Map> domesticTrans, Long id) {
//		List<ShipOptionModel> list = new ArrayList<>();
//		if(null != domesticTrans){
//			for(Map map: domesticTrans){
//				list.add(formartShipOptionModel(id, map));
//			}
//		}
//		return list;
//	}
//	/**
//	 * 根据对象格式化成运输对象
//	 * @param id
//	 * @param map
//	 * @return
//	 */
//	private ShipOptionModel formartShipOptionModel(Long id, Map map) {
//		ShipOptionModel ship = new ShipOptionModel();
//		ship.setDomesticExtraCost(map.get("domesticExtraCost").toString());
//		if(null != map.get("domesticShipAkHiPr")){
//			ship.setDomesticShipAkHiPr(map.get("domesticShipAkHiPr").toString());
//		}
//		ship.setDomesticShipType(map.get("domesticShipType").toString());
//		ship.setDomesticShipCost(map.get("domesticShipCost").toString());
//		if(null != map.get("domesticShipId")){
//			ship.setDomesticShipId(map.get("domesticShipId").toString());
//		}
//		if(null != map.get("shipLocationIn")){
//			ship.setShipLocationIn(map.get("shipLocationIn").toString());
//		}
//		ship.setTranKind(map.get("tranKind").toString());
//		ship.setTranOrder(map.get("tranOrder").toString());
//		ship.setTempId(id);
//		return ship;
//	}
//	
//	@Override
//	public OperationResult relistPublicationItem(Long id) {
//		PublicationAddItemModel publicationModel = new PublicationAddItemModel(publicationDao.getLineById(id));
//		String ebayAccount = publicationModel.getEbayAccount();
//		EbayAccountModel ebayAccountModel = accountDao.getAccountByAccount(ebayAccount);
//		String token = ebayAccountModel.getToken();
//		String serverUrl =ServerUrl.getServerUrl(ebayAccount);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		OperationResult result = new OperationResult();
//		StringBuffer stringBuffer = new StringBuffer();
//		EBayRelistItemCall relistItemCall = new EBayRelistItemCall(apiContext);
//		
//		try {	
//			
//			Long siteID = publicationModel.getSiteId();
//			//ItemType itemType = getItemType(publicationModel);
//			ItemType itemType = new ItemType();
//			itemType.setRelistLink(Boolean.TRUE);
//			itemType.setItemID(publicationModel.getItemId().toString());
//			
//			itemType.setSite(SiteIDEnum.searchBySiteID(siteID));//站点
//			
//			relistItemCall.setSite(SiteIDEnum.searchBySiteID(siteID));
//			
//			relistItemCall.setItemToBeRelisted(itemType);
//			
//			FeesType feesType = relistItemCall.relistItem();
//			
//			
//			stringBuffer.append(RequestSDKMessage.getSDKMessage(relistItemCall, "重新刊登"));
//			
//			String itemId =itemType.getItemID();
//			
//			stringBuffer.append("<b>ItemID:</b>"+itemId+"<br/>");
//			
//			PublicationModel publicationModel2 = publicationModel.getPub();
//			publicationModel2.setEbayProductURL(SiteItemUrl.getItemUrlBySIDAndIID(ebayAccount, itemId));
//			publicationModel2.setItemId(itemId);
//			Calendar endTime = relistItemCall.getRelistItemResponseType().getEndTime();
//			String sourceEnd = ConversionDateUtil.dateToCharFormat(endTime.getTime(), "yyyy-MM-dd HH:mm:ss");
//			String endString = TimeTools.timeConvert(sourceEnd, endTime.getTimeZone().getID(), "GMT+8");
//			Date endDate = ConversionDateUtil.charToDate(endString, "yyyy-MM-dd HH:mm:ss");
//			Calendar startTime = relistItemCall.getRelistItemResponseType().getStartTime();
//			String sourceStart = ConversionDateUtil.dateToCharFormat(startTime.getTime(), "yyyy-MM-dd HH:mm:ss");
//			String startString = TimeTools.timeConvert(sourceStart, startTime.getTimeZone().getID(), "GMT+8");
//			Date startDate = ConversionDateUtil.charToDate(startString, "yyyy-MM-dd HH:mm:ss");
//			
//			result.setEndDate(endDate);
//			result.setStartDate(startDate);
//			publicationModel2.setEnd_publication_date(endTime.getTime());
//			Date publicationDate = new Date();
//			publicationModel2.setPublication_date(publicationDate);
//			//publicationDao.updatePublicationById(publicationModel2);
//			publicationModel2.setId(publicationDao.getLineId());
//			publicationDao.addLine(publicationModel2);
//			FeeType[] fees = feesType.getFee();
//			Map<String, String> feeMap = RequestSDKMessage.getSDKFeeMessage(fees);
//			stringBuffer.append(feeMap.get("totalPrice"));
//			stringBuffer.append(feeMap.get("feeBuffer"));
//			result.setDescription(stringBuffer.toString());
//		} catch (ApiException e) {
//			result.setDescription(RequestSDKMessage.getSDKMessage(relistItemCall, "重新刊登"));
//			result.setErrorCode(1);
//			log.error("ebay在线范本重新刊登失败", e);
//			result.setError(e.getMessage());
//			return result;
//		} catch (SdkException e) {
//			result.setDescription(e.getMessage());
//			result.setErrorCode(1);
//			log.error("ebay在线范本重新刊登失败-SDK异常", e);
//			result.setError(e.getMessage());
//			return result;
//			
//		} catch (Exception e) {
//			result.setDescription("服务器错误！");
//			result.setErrorCode(1);
//			log.error("ebay在线范本重新刊登失败", e);
//			result.setError(e.getMessage());
//			return result;
//		}
//		return result;
//	}
//	/**
//	 * 修改库存
//	 */
//	@Override
//	public OperationResult reviseInventoryStatus(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		ReviseInventoryStatusCall  reviseInventoryStatusCall = new ReviseInventoryStatusCall();
//		PublicationModel publicationModel2 = new PublicationModel();
//		
//		try {
//			Integer size = map.size();
//			InventoryStatusType[] inventoryStatusTypes = null;
//			PublicationModel publicationModel = new PublicationModel();
//			JSONObject varJson = new JSONObject();
//			String variations = null;
//			Integer quantityAvailable= 0;
//			if (size>3) {
//				Integer count = (size-1)/2;
//				inventoryStatusTypes = new InventoryStatusType[count];
//				JSONArray jsonArray = new JSONArray();
//				for (int i = 0; i <count ; i++) {
//					InventoryStatusType inventoryStatusType = new InventoryStatusType();
//					String skuName = "skuVariation"+i;
//					String quantityName = "quantity"+i;
//					String sku =map.get(skuName).toString();
//					String quantity =map.get(quantityName).toString();
//					inventoryStatusType.setSKU(sku);
//					Integer qut=null;
//					if (!ValidationUtil.isNullOrEmpty(quantity)) {
//						 qut = Integer.parseInt(map.get(quantityName).toString());
//					}
//					
//					String itemId = map.get("itemId").toString();
//					 publicationModel2 = publicationDao.selectPublicByItemId(itemId);
//					 JSONObject jsonObject = JSONObject.fromObject(publicationModel2.getVariations());
//					JSONArray jsonArray2 = jsonObject.getJSONArray("variations");
//					for (Object object : jsonArray2) {
//						JSONObject jsonObject2 = JSONObject.fromObject(object);
//						if(jsonObject2.get("SKU").equals(sku)){
//							jsonObject2.put("Quantity", qut);
//							object= jsonObject2.toString();
//							jsonArray.add(object);
//						}
//						
//					}
//					publicationModel.setItemId(itemId);
//					quantityAvailable=quantityAvailable+qut;
//				    inventoryStatusType.setQuantity(qut);
//					inventoryStatusType.setItemID(itemId);
//					inventoryStatusTypes[i]=inventoryStatusType;
//				}
//				varJson.put("variations", jsonArray.toString());
//				variations=varJson.toString();
//				
//			}else{
//				inventoryStatusTypes = new InventoryStatusType[1];
//				InventoryStatusType inventoryStatusType = new InventoryStatusType();
//				inventoryStatusType.setSKU(map.get("skuVariation").toString());
//				Integer qut = Integer.parseInt(map.get("quantity").toString());
//				quantityAvailable=qut;
//				String itemId = map.get("itemId").toString();
//				publicationModel2 = publicationDao.selectPublicByItemId(itemId);
//				publicationModel.setItemId(itemId);
//				inventoryStatusType.setQuantity(qut);
//				inventoryStatusType.setItemID(itemId);
//				inventoryStatusTypes[0]=inventoryStatusType;
//			}
//			String ebayAccount = publicationModel2.getEbayAccount();
//			
//			EbayAccountModel ebayAccountModel = accountDao.getAccountByAccount(ebayAccount);
//			String token = ebayAccountModel.getToken();
//			String serverUrl = ServerUrl.getServerUrl(ebayAccount);
//			
//			ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//			
//			reviseInventoryStatusCall = new ReviseInventoryStatusCall(apiContext);
//			reviseInventoryStatusCall.setInventoryStatus(inventoryStatusTypes);
//			reviseInventoryStatusCall.reviseInventoryStatus();
//			publicationModel.setQuantity_available(quantityAvailable);
//			publicationModel.setVariations(variations);
//			pubInfoDAO.updateAvailableByItemId(publicationModel);
//			InventoryFeesType[] inventoryFeesType = reviseInventoryStatusCall.getReturnedFees();
//			StringBuffer resultString = new StringBuffer();
//			resultString.append(RequestSDKMessage.getSDKMessage(reviseInventoryStatusCall, "修改库存"));
//			for (InventoryFeesType inventoryFeesType2 : inventoryFeesType) {
//				FeeType[] feesTypes = inventoryFeesType2.getFee();
//				Map<String, String> feeMap = RequestSDKMessage.getSDKFeeMessage(feesTypes);
//				resultString.append(feeMap.get("totalPrice"));
//				resultString.append(feeMap.get("feeBuffer"));
//			}
//			
//			result.setDescription(resultString.toString());
//		}catch (ApiException e) {
//			result.setDescription(RequestSDKMessage.getSDKMessage(reviseInventoryStatusCall, "修改库存"));
//			log.error("修改库存失败", e);
//			return result;
//		}catch (Exception e) {
//			// TODO: handle exception
//			result.setDescription("修改库存失败！");
//			log.error("修改库存失败", e);
//			e.printStackTrace();
//		}
//		return result;
//	}
//	@Override
//	public Document getItemInfoByIds(EbayAccountModel accountModel, PublicationModel publicationModel) {
//		Map<String, String> map = new HashMap<>();
//		map.put("X-EBAY-API-SITEID", publicationModel.getSiteId().toString());
//		map.put("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
//		map.put("X-EBAY-API-CALL-NAME", "GetItem");
//		StringBuffer requestXml = new StringBuffer();
//		requestXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
//		requestXml.append("<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">");
//		requestXml.append("<RequesterCredentials>");
//		requestXml.append("<eBayAuthToken>" + accountModel.getToken() + "</eBayAuthToken>");
//		requestXml.append("</RequesterCredentials>");
//		//  < IncludeItemSpecifics > boolean </ IncludeItemSpecifics >
//		requestXml.append("<IncludeItemSpecifics >true </IncludeItemSpecifics>");
//		requestXml.append("<ErrorLanguage>en_US</ErrorLanguage>");
//		requestXml.append("<WarningLevel>High</WarningLevel>");
//		requestXml.append("<DetailLevel>ReturnAll</DetailLevel>");
//		requestXml.append("<ItemID>" + publicationModel.getItemId() + "</ItemID>");
//		requestXml.append("</GetItemRequest>");
//		BaseHttpsService baseHttpsService = new BaseHttpsService();
//		String serverUrl =null;
//		if(accountModel.getAccount().equals("testuser_yangguanbao")){
//			serverUrl="https://api.sandbox.ebay.com/ws/api.dll";
//		}else{
//			serverUrl="https://api.ebay.com/ws/api.dll";
//		}
//		return baseHttpsService.getPesponseXml(serverUrl, map, requestXml.toString());
//		
//	}
//	@Override
//	public PublicationModel parseItemXml(Document doc) {
//		PublicationModel pubModel = new PublicationModel();
//		String xml = "";
//		Element root = doc.getRootElement();
//		
//		Element item = root.element("Item");
//		Element dHtml = item.element("Description");
//		//
//		pubModel.setItemId(item.elementText("ItemID"));
//		//买家要求
//		Element buyerRequirementDetails = item.element("BuyerRequirementDetails");
//			if(null!=buyerRequirementDetails){
//				//没有PayPal账户
//				pubModel.setNoPaypai(buyerRequirementDetails.elementText("LinkedPayPalAccount"));
//				//主要运送地址在我的运送范围之外
//				pubModel.setOutShipCountry(buyerRequirementDetails.elementTextTrim("ShipToRegistrationCountry"));
//				//曾收到..个弃标个案，在过去。。天
//				Element maximumUnpaidItemStrikesInfo = buyerRequirementDetails.element("MaximumUnpaidItemStrikesInfo");
//			    if(null!=maximumUnpaidItemStrikesInfo){
//			    	String count = maximumUnpaidItemStrikesInfo.elementText("Count");
//			    	String period = maximumUnpaidItemStrikesInfo.elementText("Period");
//			    	pubModel.setBuyerReq1(count+"|"+period);
//			    }
//			  //曾收到。。个违反政策检举，在过去 天 
//			   Element maximumBuyerPolicyViolations = buyerRequirementDetails.element("MaximumBuyerPolicyViolations");
//			   if(null!=maximumBuyerPolicyViolations){
//				   String count = maximumBuyerPolicyViolations.elementText("Count");
//			    	String period = maximumBuyerPolicyViolations.elementText("Period");
//			    	pubModel.setBuyerReq2(count+"|"+period);
//			   }
//			   //信用 指标 等于 或 低于 
//			   pubModel.setBuyerReq3(buyerRequirementDetails.elementTextTrim("MinimumFeedbackScore"));
//			  
//			   //在过去。。。。。。
//			   Element maximumItemRequirements = buyerRequirementDetails.element("MaximumItemRequirements");
//			   if(null!=maximumItemRequirements){
//				   pubModel.setBuyerReq4(maximumItemRequirements.elementTextTrim("MaximumItemCount"));
//				   pubModel.setBuyerReq41(maximumItemRequirements.elementTextTrim("MinimumFeedbackScore"));
//			   }
//			}
//		//自动接收小于    自动接收大于
//		Element listingDetails = item.element("ListingDetails");
//		if(null!=listingDetails){
//			pubModel.setAcceptBuyerCounterMax(listingDetails.elementText("BestOfferAutoAcceptPrice"));
//			pubModel.setAcceptBuyerCounterMin(listingDetails.elementText("MinimumBestOfferPrice"));
//			Element start = listingDetails.element("StartTime");
//			Element end = listingDetails.element("EndTime");
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//			Date startD = ConversionDateUtil.charToDate(start.getTextTrim(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//			calendar.setTime(startD);
//			Calendar calendar2 = Calendar.getInstance();
//			calendar2.setTimeZone(TimeZone.getTimeZone("UTC"));
//			Date startE = ConversionDateUtil.charToDate(end.getTextTrim(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//			calendar2.setTime(startE);
//			pubModel.setPublication_date(ConversionDateUtil.getDateByCZ(calendar, "GMT+8"));
//			pubModel.setEnd_publication_date(ConversionDateUtil.getDateByCZ(calendar2, "GMT+8"));
//		}
//		//站点
//		String site = item.element("Site").getTextTrim();
//		pubModel.setSiteId(SiteIDEnum.searchBySiteName(site.toUpperCase()));
//		//产品url
//		String purl = listingDetails.elementText("ViewItemURL");
//		if("GERMANY".equals(site.toUpperCase())){
//			pubModel.setEbayProductURL(purl.replace(".com", ".de"));
//		}else{
//			pubModel.setEbayProductURL(purl);
//		}
//		
//		//计数器类型
//		String hitCounter = item.element("HitCounter").getTextTrim();
//		pubModel.setCounterType(hitCounter);
//		//是否向公众显示卖家名称
//		String privateListing = item.element("PrivateListing").getTextTrim();
//		pubModel.setIndividual(privateListing);
//		//标题
//		String title = item.element("Title").getTextTrim();
//		pubModel.setProductTitle(title);
//		//SKU
//		Element sku = item.element("SKU");
//		if(!ValidationUtil.isNull(sku)){
//			pubModel.setSku(sku.getTextTrim());
//		}
//		//是否自动付款
//		pubModel.setAutoPay(item.elementTextTrim("AutoPay"));
//		
//		//卖家用户信息
//		Element seller = item.element("Seller");
//			//卖家id
//		    pubModel.setEbayAccount(seller.element("UserID").getTextTrim());
//		    
//		//点击量 
//		Element hitCount = item.element("HitCount");
//		pubModel.setHit_count(Long.parseLong(hitCount.getTextTrim()));
//		//产品销售状态
//		Element sellingStatus =  item.element("SellingStatus");
//			//折扣信息
//		    Element promotionalSaleDetails = sellingStatus.element("PromotionalSaleDetails");
//		    if(!ValidationUtil.isNull(promotionalSaleDetails)){
//		    	String originalPrice = promotionalSaleDetails.elementText("OriginalPrice");
//		    	pubModel.setOriginal_price(originalPrice);
//		    	Element startTime = promotionalSaleDetails.element("StartTime");
//		    	Element endTime = promotionalSaleDetails.element("EndTime");
//		    	Calendar startC = ConversionDateUtil.getCBySTF(startTime.getTextTrim(), "UTC", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//		    	Calendar endC = ConversionDateUtil.getCBySTF(endTime.getTextTrim(), "UTC", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//		    	pubModel.setDiscount_end_date(ConversionDateUtil.getDateByCZ(endC, "GMT+8"));
//		    	pubModel.setDiscount_start_date(ConversionDateUtil.getDateByCZ(startC, "GMT+8"));
//
//		    }
//		    //已售数量
//		    Element quantitySold = sellingStatus.element("QuantitySold");
//		    Integer sold = Integer.parseInt(quantitySold.getTextTrim());
//		    pubModel.setQuantity_sold(sold);
//		//商品第一分类 
//		Element primaryCategory  = item.element("PrimaryCategory");
//		pubModel.setProductFirstCategoryId(primaryCategory.element("CategoryID").getTextTrim());
//		
//		//商品第二分类
//		Element secondaryCategory = item.element("SecondaryCategory");
//		if(!ValidationUtil.isNull(secondaryCategory)){
//			pubModel.setProductSecondCategoryId(secondaryCategory.element("CategoryID").getTextTrim());
//		}
//		//
//		//商店分类
//		Element storefront = item.element("Storefront");
//		if(!ValidationUtil.isNull(storefront)){
//			
//			//商店第一分类
//			Element storeCategoryID = storefront.element("StoreCategoryID");
//			if(!ValidationUtil.isNull(storeCategoryID)){
//				pubModel.setStoreFirstCategoryId(storeCategoryID.getTextTrim());
//			}
//			
//			//商店第二分类 
//			Element StoreCategory2ID = storefront.element("StoreCategory2ID");
//			if(!ValidationUtil.isNull(StoreCategory2ID)){
//				pubModel.setStoreSecondCategoryId(StoreCategory2ID.getTextTrim());
//			}
//			
//		}
//		//ebay图片
//		Element pictureDetails = item.element("PictureDetails");
//		if(!ValidationUtil.isNull(pictureDetails)){
//			String ebayUrl = elementsToStr(pictureDetails.elements("PictureURL"));
//			ebayUrl = ebayUrl.replace("http:", "https:");
//			pubModel.setEbayImages(ebayUrl);
//		}
//		
//		//刊登天数
//		Element listingDuration = item.element("ListingDuration");
//		pubModel.setPublicationDays(listingDuration.getTextTrim());
//		xml = dHtml.getTextTrim();
//		//开始价格 
//		Element startPrice = item.element("StartPrice");
//		pubModel.setPrice(startPrice.getTextTrim());
//		//保留价格
//
//		pubModel.setReserverPrice(item.elementText("ReservePrice"));
//		//数量
//		Integer quantity = Integer.parseInt(item.elementText("Quantity"));
//		quantity=quantity-sold;
//		pubModel.setProductCount(quantity.toString());
//		
//		//是否接受议价
//		Element bestOfferDetails = item.element("BestOfferDetails");
//		if(!ValidationUtil.isNull(bestOfferDetails)){
//				pubModel.setAcceptBuyerCounter(bestOfferDetails.elementText("BestOfferEnabled"));
//		}
//		//UPC
//		Element productDetail = item.element("ProductListingDetails");
//		if(null!=productDetail){
//		pubModel.setProductUPC(productDetail.elementText("UPC"));
//		}
//		if(null!=productDetail){
//			pubModel.setProductEAN(productDetail.elementText("EAN"));
//			}
//		if(null!=productDetail){
//			pubModel.setProductISBN(productDetail.elementText("ISBN"));
//			}
//		//运输选项
//		List<ShipOptionModel> shipOptionModels = Lists.newArrayList();
//		Element shippingDetails = item.element("ShippingDetails");
//		if(shippingDetails!=null){
//			List<Element> shippingOptions = shippingDetails.elements("ShippingServiceOptions");
//			Integer size = shippingOptions.size();
//			if(size>0){
//				for (Element element : shippingOptions) {
//					ShipOptionModel shipOptionModel = new ShipOptionModel();
//					shipOptionModel.setDomesticShipCost(element.elementText("ShippingServiceCost"));
//					shipOptionModel.setTranOrder(element.elementText("ShippingServicePriority"));
//					shipOptionModel.setDomesticShipType(element.elementText("ShippingService"));
//					shipOptionModel.setDomesticExtraCost(element.elementText("ShippingServiceAdditionalCost"));
//					if(element.element("ShippingSurcharge")!=null){
//						shipOptionModel.setDomesticShipAkHiPr(element.elementText("ShippingSurcharge"));
//					}else{
//						shipOptionModel.setDomesticShipAkHiPr("0");
//					}
//					
//					shipOptionModel.setTranKind("0");
//					shipOptionModels.add(shipOptionModel);
//				}
//			}
//			List<Element> internationl = shippingDetails.elements("InternationalShippingServiceOption");
//			Integer internationlSite = internationl.size();
//			if(internationlSite>0){
//				for (Element element : internationl) {
//					ShipOptionModel shipOptionModel = new ShipOptionModel();
//					shipOptionModel.setDomesticShipCost(element.elementText("ShippingServiceCost"));
//					shipOptionModel.setTranOrder(element.elementText("ShippingServicePriority"));
//					shipOptionModel.setDomesticShipType(element.elementText("ShippingService"));
//					shipOptionModel.setDomesticExtraCost(element.elementText("ShippingServiceAdditionalCost"));
//					shipOptionModel.setShipLocationIn(element.elementText("ShipToLocation"));
//					shipOptionModel.setTranKind("1");
//					shipOptionModels.add(shipOptionModel);
//				}
//			}
//		}
//		if(shipOptionModels.size()>0){
//			pubModel.setShipOptionModels(shipOptionModels);
//		}
//		//不运送地区 
//		if(!ValidationUtil.isNull(shippingDetails)){
//			String shipLocationOver = elementsToStr(shippingDetails.elements("ExcludeShipToLocation"));
//			pubModel.setShipLocationOver(shipLocationOver); 
//		}
//	   //物品描述  
//			String itemDescription  = getItemDescription(xml);
//			   pubModel.setComments(itemDescription);
//		
//	   
//		Element itemSpecifics = item.element("ItemSpecifics");
//			//物品属性
//			String productProperties = getItemSpecific(itemSpecifics);
//			
//			if(!ValidationUtil.isNullOrEmpty(productProperties)){
//				JSONArray jsonArray = JSONArray.fromObject(productProperties);
//				
//				for (int j = 0; j < jsonArray.size(); j++) {
//					
//					JSONObject jsonS = JSONObject.fromObject(jsonArray.get(j));
//					if(jsonS.get("MPN")!=null){
//						String json= jsonS.get("MPN").toString();
//						json = json.substring(2, json.length()-2);
//						pubModel.setProduct_mpn(json);
//						jsonArray.remove(j);
//					}else if(jsonS.get("Herstellernummer")!=null){
//						String json= jsonS.get("Herstellernummer").toString();
//						json = json.substring(2, json.length()-2);
//						pubModel.setProduct_mpn(json);
//						jsonArray.remove(j);
//					}
//					if(jsonS.get("Brand")!=null){
//						String json= jsonS.get("Brand").toString();
//						json = json.substring(2, json.length()-2);
//						pubModel.setProduct_brand(json);
//						jsonArray.remove(j);
//					}else if(jsonS.get("Marke")!=null){
//						String json= jsonS.get("Marke").toString();
//						json = json.substring(2, json.length()-2);
//						pubModel.setProduct_brand(json);
//						jsonArray.remove(j);
//					}
//					if(jsonS.get("Ust-IdNr")!=null){
//						String json= jsonS.get("Ust-IdNr").toString();
//						json = json.substring(2, json.length()-2);
//						pubModel.setProduct_ust(json);
//						jsonArray.remove(j);
//					}
//				}
//				productProperties =jsonArray.toString();
//			}
//			pubModel.setProductProperties(productProperties);
//			//退货政策
//			Element returnPolicy = item.element("ReturnPolicy");
//			pubModel.setPolicyType(returnPolicy.elementText("ReturnsAcceptedOption"));
//					if(returnPolicy.element("ReturnsAcceptedOption").getTextTrim().equals("ReturnsAccepted")){
//						pubModel.setReturnType(returnPolicy.elementTextTrim("RefundOption"));
//						pubModel.setReturnDays(returnPolicy.elementTextTrim("ReturnsWithinOption"));
//						pubModel.setAllowDelay(returnPolicy.elementTextTrim("ExtendedHolidayReturns"));
//						pubModel.setFaretakeinhander(returnPolicy.elementTextTrim("ShippingCostPaidByOption"));
//						pubModel.setDepreciationRate(returnPolicy.elementTextTrim("RestockingFeeValueOption"));
//						pubModel.setReturnDescription(returnPolicy.elementTextTrim("Description"));
//			}
//            //多属性	
//			Element variations = item.element("Variations");
//			Element listingType = item.element("ListingType");
//			if(!ValidationUtil.isNull(variations)){
//				pubModel.setPublicationType("FixedPriceItem1");
//				String vs = getVariations(variations);
//				pubModel.setVariations(vs);
//				//System.out.println(vs);
//			}else{
//				pubModel.setPublicationType(listingType.getTextTrim());
//			}
//			Element businessSellerDetails = item.element("BusinessSellerDetails");
//			Element address = null;
//			if(!ValidationUtil.isNull(businessSellerDetails)){
//				address = businessSellerDetails.element("Address");
//			}
//			
//			pubModel.setPostCode(item.elementTextTrim("PostalCode"));
//			//物品所在地 
//			pubModel.setRegion(item.elementTextTrim("Country"));//国家或地区
//			pubModel.setProductAddress(item.elementTextTrim("Location"));//物品所在地
//			pubModel.setPaypaiAccount(item.element("PayPalEmailAddress").getTextTrim());
//				List<Element> paypais =  item.elements("PaymentMethods");
//				if(paypais.size()>1){
//					StringBuffer payPaisStr = new StringBuffer();
//					for (Element element : paypais) {
//						payPaisStr.append(element.toString()+",");
//					}
//					pubModel.setSupportPaypaiInfo(payPaisStr.toString());
//				}
//		return pubModel;
//	}
//	
//
//	public  String mapToJsonStr(Map<String, String> map){
//		StringBuffer result = new StringBuffer();
//		result.append("[");
//		for (Map.Entry<String, String> entry : map.entrySet()) {
//			String key = entry.getKey();
//			String value = entry.getValue();
//			result.append("{\""+key+"\":[");
//			if(value.contains(",")){
//				String[] values =  value.split(",");
//				for (String string : values) {
//					result.append("\""+string+"\",");
//				}
//				result.deleteCharAt(result.length()-1);
//				result.append("]},");
//			}else{
//				result.append("\""+value+"\"]},");
//			}
//			
//		
//			
//		}
//		result.deleteCharAt(result.length()-1);
//		result.append("]");
//		return result.toString();
//	}
//	/**
//	 * 描述：获取物品描述
//	 * @param xml
//	 * @return
//	 */
//	public synchronized String getItemDescription(String xml){
//		xml = xml.replace("&lt;", "<").replace("&gt;", ">");
//		org.jsoup.nodes.Document html = Jsoup.parse(xml);
//		org.jsoup.nodes.Element description = html.getElementById("patemplate_description");
//		if (!ValidationUtil.isNull(description)) {
//			Elements description2 = description.getElementsByClass("patemplatebox_bodyin");
//			return description2.toString();
//		}
//		
//		return null;
//	}
//    /**
//     * 描述：获取物品属性
//     * @param itemSpecifics
//     * @return
//     */
//	public synchronized String getItemSpecific(Element itemSpecifics){
//		List<Element> nameValue = itemSpecifics.elements("NameValueList");
//		Map<String, String> map = new HashMap<>();
//		for (Element element : nameValue) {
//			String name = element.element("Name").getTextTrim();
//			name = name.replace(".", "").replace("\"", "");
//			
//			String value = element.element("Value").getTextTrim();
//			value = value.replace("\"", "");
//			map.put(name, value);
//		}
//		String productProperties = mapToJsonStr(map);
//		productProperties = productProperties.replace(":\"", "\"");
//		return productProperties;
//	}
//	
//	public  String elementsToStr(List<Element> elements){
//		StringBuffer result = new StringBuffer();
//		if(!ValidationUtil.isNull(elements)&&elements.size()>0){
//			for (Element element : elements) {
//				result.append(element.getTextTrim()+",");
//				
//			}
//			result.deleteCharAt(result.length()-1);
//			return result.toString();
//		}
//		return null;
//	}
//	@Override
//	public OperationResult getSiteDateById(Long siteId) {
//		OperationResult result = new OperationResult();
//		Calendar calendar = Calendar.getInstance();
//		String dateStr = ConversionDateUtil.dateToCharFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
//		String zone = ZoneMapping.searchTokenBySiteId(siteId);
//		String date = TimeTools.timeConvert(dateStr, calendar.getTimeZone().getID(), zone);
//		result.setData(date);
//		return result;
//	}
//	@Override
//	public OperationResult getLocalBySiteId(String siteDate, Long siteId) {
//		OperationResult result = new OperationResult();
//		String zone = ZoneMapping.searchTokenBySiteId(siteId);
//		String localDate = TimeTools.timeConvert(siteDate, zone, "GMT+8");
//		result.setData(localDate);
//		return result;
//	}
//    public synchronized String getVariations(Element dec){
//    	StringBuffer result = new StringBuffer();
//    	List<Element> skuInfos = dec.elements("Variation");
//    	result.append("{\"variations\":[");
//    	for (Element element : skuInfos) {
//			result.append("{");
//			result.append("\"SKU\":\""+element.elementTextTrim("SKU")+"\",");
//			result.append("\"StartPrice\":\""+element.elementTextTrim("StartPrice")+"\",");
//			result.append("\"Quantity\":\""+element.elementTextTrim("Quantity")+"\",");
//			Element productListingDetails = element.element("VariationProductListingDetails");
//			if(null!=productListingDetails){
//				String ean = productListingDetails.elementText("EAN");
//				String isbn =productListingDetails.elementText("ISBN");
//				String upc =productListingDetails.elementText("UPC");
//				if(null!=ean){
//					result.append("\"EAN\":\""+ean+"\",");
//				}
//				if(null!=isbn){
//					result.append("\"ISBN\":\""+isbn+"\",");			
//								}
//				if(null!=upc){
//					result.append("\"UPC\":\""+upc+"\",");	
//				}
//			}
//			Element variationSpecifics = element.element("VariationSpecifics");
//			if(null!=variationSpecifics){
//				result.append("\"NameValueList\": [");
//				List<Element> nameValues = variationSpecifics.elements("NameValueList");
//			    for (Element nameValue : nameValues) {
//			    	result.append("{");
//			    	result.append("\"Name\":\""+format(nameValue.elementTextTrim("Name"))+"\",");
//			    	result.append("\"Value\":\""+format(nameValue.elementTextTrim("Value"))+"\"");
//			    	result.append("},");
//			    }
//			    result.deleteCharAt(result.length()-1);
//			    result.append("]");
//			}
//			result.append("},");
//    	}
//    	result.deleteCharAt(result.length()-1);
//    	result.append("],");
//    	List<Element> pictures = dec.elements("Pictures");
//    	if(pictures!=null&&pictures.size()>0){
//    		result.append("\"pictures\": [");
//    		for (Element element : pictures) {
//				String specificName = format(element.elementText("VariationSpecificName"));
//				if(null == specificName || "".equals(specificName)){
//					continue;
//				}
//    			List<Element> variationSpecificPictureSet = element.elements("VariationSpecificPictureSet");
//    			for (Element element2 : variationSpecificPictureSet) {
//					result.append("{\"VariationSpecificName\": \""+specificName+"\",");
//					result.append("\"VariationSpecificValue\":\""+format(element2.elementText("VariationSpecificValue"))+"\",");
//					result.append("\"PictureURL\": \""+element2.elementText("PictureURL")+"\"},");
//    			}
//    		}
//    		if(result.toString().endsWith(",")){
//    			result.deleteCharAt(result.length()-1);
//    		}
//    		result.append("],");
//    	}else{
//    		result.append("\"pictures\": [],");
//    	}
//    	Element variationSpecifics = dec.element("VariationSpecificsSet");
//    	if(null!=variationSpecifics){
//    		result.append("\"variationSpecificsSet\": [");
//    		List<Element> elements = variationSpecifics.elements("NameValueList");
//    	    for (Element element : elements) {
//				result.append("{");
//				result.append("\"Name\":\""+format(element.elementTextTrim("Name"))+"\",");
//				List<Element> values = element.elements("Value");
//				String valueStr = "";
//		    	if(null!=values&&values.size()>0){
//		    		valueStr = elementsToString(values,"Values");
//		    		result.append(valueStr+",");
//		    	}
//		    	result.deleteCharAt(result.length()-1);
//		    	result.append("},");
//			}
//    	    result.deleteCharAt(result.length()-1);
//    	    result.append("],");
//    	}else{
//    		result.append("\"variationSpecificsSet\": [],");
//    	}
//    	result.deleteCharAt(result.length()-1);
//    	result.append("}");
//    	return result.toString();
//    }
//    
//    public  String elementsToString(List<Element> ele,String str){
//    	StringBuffer result = new StringBuffer();
//    	result.append("\""+str+"\": [");
//    	for (Element element : ele) {
//			result.append("\""+format(element.getTextTrim())+"\",");
//		}
//    	result.deleteCharAt(result.length()-1);
//    	result.append("]");
//    	return result.toString();
//    }
//    
//    public static String format(String str){
//    	if(null != str && !"".equals(str) && str.contains("\"")){
//    		str = str.replace("\"", "\\\"");
//    	}
//    	return str;
//    }
//
//}
