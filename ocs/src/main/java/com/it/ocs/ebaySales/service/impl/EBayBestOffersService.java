//package com.it.ocs.ebaySales.service.impl;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
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
//import com.ebay.sdk.call.GetBestOffersCall;
//import com.ebay.sdk.call.RespondToBestOfferCall;
//import com.ebay.soap.eBLBaseComponents.AddressType;
//import com.ebay.soap.eBLBaseComponents.AmountType;
//import com.ebay.soap.eBLBaseComponents.BestOfferActionCodeType;
//import com.ebay.soap.eBLBaseComponents.BestOfferArrayType;
//import com.ebay.soap.eBLBaseComponents.BestOfferStatusCodeType;
//import com.ebay.soap.eBLBaseComponents.BestOfferType;
//import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
//import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
//import com.ebay.soap.eBLBaseComponents.ItemBestOffersArrayType;
//import com.ebay.soap.eBLBaseComponents.ItemBestOffersType;
//import com.ebay.soap.eBLBaseComponents.ItemType;
//import com.ebay.soap.eBLBaseComponents.PaginationResultType;
//import com.ebay.soap.eBLBaseComponents.PaginationType;
//import com.ebay.soap.eBLBaseComponents.UserType;
//import com.google.common.collect.Lists;
//import com.it.ocs.common.BeanConvertUtil;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.RequestParam;
//import com.it.ocs.common.ResponseResult;
//import com.it.ocs.common.service.BaseService;
//import com.it.ocs.common.support.IAction;
//import com.it.ocs.common.util.CollectionUtil;
//import com.it.ocs.ebaySales.dao.IEBayBestOffersDao;
//import com.it.ocs.ebaySales.model.EBayBestOffersModel;
//import com.it.ocs.ebaySales.model.ParseBaseOfferInfoXMLModel;
//import com.it.ocs.ebaySales.model.ParseBaseOffersXMLModel;
//import com.it.ocs.ebaySales.service.IEBayBestOffersService;
//import com.it.ocs.ebaySales.vo.BestOffersVo;
//import com.it.ocs.publication.dao.IPublicationInfoDAO;
//import com.it.ocs.publication.model.PublicationModel;
//import com.it.ocs.publication.model.ShipOptionModel;
//import com.it.ocs.publication.service.external.IPublicationService;
//import com.it.ocs.publication.vo.PublicationVO;
//import com.it.ocs.salesStatistics.utils.TimeTools;
//import com.it.ocs.synchronou.mapping.CurrencyCodeTypeEnum;
//import com.it.ocs.synchronou.mapping.EbayMappingToSDK;
//import com.it.ocs.synchronou.mapping.SiteIDEnum;
//import com.it.ocs.synchronou.model.EbayAccountModel;
//import com.it.ocs.synchronou.model.RequestModel;
//import com.it.ocs.synchronou.model.TemplateModel;
//import com.it.ocs.synchronou.service.IEbayAccountService;
//import com.it.ocs.synchronou.service.impl.BaseEbaySDKService;
//import com.it.ocs.synchronou.service.impl.BaseHttpsService;
//import com.it.ocs.synchronou.service.impl.TemplateService;
//import com.it.ocs.synchronou.util.ConversionDateUtil;
//import com.it.ocs.synchronou.util.RequestSDKMessage;
//import com.it.ocs.synchronou.util.ServerUrl;
//import com.it.ocs.synchronou.util.ValidationUtil;
//import com.it.ocs.task.core.TaskExecutorUtil;
//import com.it.ocs.task.core.TaskRunnable;
//@Service
//public class EBayBestOffersService extends BaseService implements IEBayBestOffersService {
//    private static final Logger logger = Logger.getLogger(EBayBestOffersService.class);
//	@Autowired
//	private IEBayBestOffersDao eBayOffersDao;
//	@Autowired
//	private IPublicationInfoDAO publicationInfoDao;
//	@Autowired
//	private IPublicationService publicationService;
//	@Autowired
//	private IEbayAccountService ebayAccountService;
//	
//	@Autowired
//	private TemplateService templateService;
//
//	@Autowired
//	private BaseHttpsService baseHttpService;
//
//	
//	/**
//	 * 同步议价信息（自定义方式不使用eBay SDK）
//	 */
//	public void syncBestOffers(){
//		List<EbayAccountModel> ebayAccountModels = ebayAccountService.getAccounts();
//		for(EbayAccountModel account : ebayAccountModels){
//			account.setSiteId("201");
//			//account.setAccount("uk.nm");
//			//account.setToken("AgAAAA**AQAAAA**aAAAAA**3vplWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGl4SpD5eFpwydj6x9nY+seQ**Xq8DAA**AAMAAA**iydAm7Kq16duV/Musk4kXJRQmu77ZJqV7oQ+dPkGGilARK6NLpQuu9x+kS+2j7CcOVtGZfPg4jla4w+JwBnrr1vVtzQeV7/VwKuynHuEK4nqhWPi/JZhZ6zSNM+NbbIbI/a3TgvFkP5fyS2YSIALc6OCXH46jiQ77l+Qf7q2AkR8feOgTjlXRybuvDPXEk0MjfuAxb+wryC4JqiRkd6Y7gPHUAllXUBqI8JqL0RlD+Ewr3Hvsl6S892wnqSzlEkmrZm9YcO5hOco8xoMUDovZfXMAzcl56WlE+yZ6ghlJXTmX6BLXmj8VXSWGguG62ICT2waID1eM+aazxoRFgvqhua+tPKTJysql2TDrj/Rh0kT7O4VS+dVxrfHpzeovv+9O1DYyhSrOJfmi/gzvxAqTQt9hqpSe04omCXT4I04IUk+ZKPt0fYmFxH3T2bkscBUyXsxdbakRVWYGCr7jA4iegCSXUMbj4KQ1BRuVVRvAX2a63qcIfMWu9nB9IC4PdSogI3dccd1N6VmLPW/AlQSsGoPTtM2t4L8X6Gu1oitB6mkhvBWQ3CYfz8AjuV1CT2P/VJnSDplkqrYxxN7JeZL1/xeIETcBJ3KxWbhFEta4ERue9EYmBZtHCJ3BEmURV65kPpVfp8AZSGAGiXzOFGT2aYPLKUOtVe/qs9zte2GYSCdmvZ0tXIpZmMDY+zwSxClZ0WP+itTKzkXnrJSuhOGPCo3yITjpj7uO1r/0uJKOLwZ2MmIKH+yIhB3dYDg56MI");
//			createThreadToSyncBestOffersByAccount(account,null);
//			createThreadToBestOfferUpdateActive(account);
//			//break;
//		}
//	}
//	
//	private void createThreadToBestOfferUpdateActive(EbayAccountModel account) {
//		TaskExecutorUtil.threadRun(new TaskRunnable() {
//
//			@Override
//			protected void runTask() {
//				// 获取pending状态更新数据
//				List<Map<String,Object>> activeOffers = eBayOffersDao.getActiveOfferByAccount(account.getAccount());
//				Map<String,String> xmlNameValue = new HashMap<String,String>();
//				TemplateModel template = templateService.getTemplateContent("GetBestOfferInfo", "ebay"); 
//				RequestModel request = new RequestModel(template,account,xmlNameValue);
//				Document doc =  null;
//				for(Map<String,Object> offer : activeOffers){
//					xmlNameValue.put("itemId", offer.get("ITEMID").toString());
//					xmlNameValue.put("bestOfferId", offer.get("BESTOFFERID").toString());
//					request.setParam(xmlNameValue);
//					doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//					ParseBaseOfferInfoXMLModel parse = new ParseBaseOfferInfoXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//					for(Map<String,Object> map : parse.getResult()){
//						map.put("account", account.getAccount());
//						eBayOffersDao.updateOffer(map);
//					}
//				}
//				
//			}
//			
//		});
//		
//	}
//	
//	/**
//	 * 同步议价信息（自定义方式不使用eBay SDK）
//	 */
//	public OperationResult syncBestOffersByHand(){
//		List<EbayAccountModel> ebayAccountModels = ebayAccountService.getAccounts();
//		//同步方式，使主线程等待子线程执行完毕
//		final CountDownLatch countDownLatch=new CountDownLatch(ebayAccountModels.size());
//		for(EbayAccountModel account : ebayAccountModels){
//			account.setSiteId("201");
//			createThreadToSyncBestOffersByAccount(account,countDownLatch);
//		}
//		try {
//			countDownLatch.await();
//		} catch (InterruptedException e) {
//			logger.error("---同步议价主线程等待子线程结束失败---");
//		}
//		OperationResult or = new OperationResult();
//		or.setData("success");
//		return or;
//	}
//	
//	
//	private void createThreadToSyncBestOffersByAccount(EbayAccountModel account,CountDownLatch countDownLatch) {
//		TaskExecutorUtil.threadRun(new TaskRunnable() {
//			@Override
//			protected void runTask() {
//				Map<String,String> xmlNameValue = new HashMap<String,String>();
//				xmlNameValue.put("pageNumber", "1");
//				TemplateModel template = templateService.getTemplateContent("GetBestOffers", "ebay"); 
//				RequestModel request = new RequestModel(template,account,xmlNameValue);
//				Document doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//				if(null == doc){
//					return;
//				}
//				ParseBaseOffersXMLModel parse = new ParseBaseOffersXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//				addAndUpdateBestOffer(parse.getResult(),account);
//				for(int i= 2;i<=parse.getTotalPages();i++){
//					xmlNameValue.put("pageNumber", String.valueOf(i));
//					request.setParam(xmlNameValue);
//					doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//					parse = new ParseBaseOffersXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//					addAndUpdateBestOffer(parse.getResult(),account);
//				}
//				//兼容同步方式
//				if(null != countDownLatch){
//					countDownLatch.countDown();
//				}
//			}
//
//			private void addAndUpdateBestOffer(List<Map<String,Object>> list, EbayAccountModel account) {
//				for(Map<String,Object> offer : list){
//					offer.put("account", account.getAccount());
//					if(eBayOffersDao.exist(offer) > 0){
//						eBayOffersDao.updateOffer(offer);
//					}else{
//						eBayOffersDao.addOffer(offer);
//					}
//				}
//				
//			}
//		});
//		
//	}
//
//
//
//	@Override
//	public OperationResult synchronousBestOffers() {
//		return syncBestOffersByHand();
//		/*OperationResult result = new OperationResult();
//		List<EbayAccountModel> ebayAccountModels = ebayAccountService.getAccounts();
//		
//		for(EbayAccountModel ebayAccountModel : ebayAccountModels){
//			ExecutorService cExecutorService = Executors.newCachedThreadPool();
//			String sellerId =ebayAccountModel.getAccount();
//			List<EBayBestOffersModel> eBayBestOffersModels2 = eBayOffersDao.selectBestOfferPendingByUserId(sellerId);
//			for (EBayBestOffersModel eBayBestOffersModel : eBayBestOffersModels2) {
//				synchronousBestOffer(eBayBestOffersModel.getItem_id(), null, eBayBestOffersModel.getSiteid(), eBayBestOffersModel.getBest_offer_id());
//			}
//			List<PublicationModel> publicationModels = publicationInfoDao.selectPuiItemIdByCounter(sellerId);
//			for (PublicationModel publicationModel : publicationModels) {
//			cExecutorService.execute(new Runnable() {
//			@Override
//			public void run() {
//				EbayAccountModel inteEbayAccountModel = new EbayAccountModel();
//				inteEbayAccountModel =ebayAccountModel;
//				String url = ServerUrl.getServerUrl(inteEbayAccountModel.getAccount());
//				PublicationModel intePublicationModel = publicationModel;
//			    ApiContext apiContext = BaseEbaySDKService.getApiContext(inteEbayAccountModel.getToken(), url);
//			    GetBestOffersCall getBestOffersCall = new GetBestOffersCall(apiContext);
//			    getBestOffersCall.setBestOfferStatus(BestOfferStatusCodeType.ACTIVE);
//			    PaginationType paginationType = new PaginationType();
//			    paginationType.setEntriesPerPage(200);
//			    paginationType.setPageNumber(1);
//			    DetailLevelCodeType[] detailLevelCodeTypes = new DetailLevelCodeType[1];
//			    detailLevelCodeTypes[0] = DetailLevelCodeType.RETURN_ALL;
//			    getBestOffersCall.setDetailLevel(detailLevelCodeTypes);
//			    getBestOffersCall.setPagination(paginationType);
//			    getBestOffersCall.setItemID(intePublicationModel.getItemId());
//			    	try {
//						getBestOffersCall.getBestOffers();
//						PaginationResultType paginationResultType =getBestOffersCall.getReturnedPaginationResult();
//						if(paginationResultType!=null){
//							Integer buyerPage = paginationResultType.getTotalNumberOfPages();
//							for (int i = 1; i < buyerPage; i++) {
//								paginationType.setPageNumber(i);
//							 	getBestOffersCall.setPagination(paginationType);
//							 	getBestOffersCall.getBestOffers();
//							 	insertBestOffer(getBestOffersCall, sellerId);
//							}
//						}else{
//							insertBestOffer(getBestOffersCall, sellerId);
//						}
//			    	} catch (ApiException e) {
//			    		String message = RequestSDKMessage.getSDKMessage(getBestOffersCall, "同步议价");
//						result.setDescription(message);
//						logger.error("同步议价失败"+ e.getMessage());
//					} catch (SdkException e) {
//						String message = RequestSDKMessage.getSDKMessage(getBestOffersCall, "同步议价");
//						result.setDescription(message);
//						logger.error("同步议价失败"+ e.getMessage());
//					} catch (Exception e) {
//						result.setDescription("同步议价失败！");
//						logger.error("同步议价失败"+ e.getMessage());
//					}
//		    	
//		    	
//				}
//				});
//			}
//
//		}
//		//}
//		result.setDescription("同步成功！");
//		return result;*/
//	}
//	public synchronized OperationResult insertBestOffer(GetBestOffersCall getBestOffersCall,String sellerId){
//		OperationResult result = new OperationResult();
//		ItemBestOffersArrayType itemBestOffersArrayType = getBestOffersCall.getReturnedItemBestOffersArray();
//		List<EBayBestOffersModel> eBayBestOffersModels = Lists.newArrayList();
//		List<EBayBestOffersModel> updateBestOffersModls = Lists.newArrayList();
//		if(itemBestOffersArrayType!=null){
//			 	ItemBestOffersType[] itemBestOffersTypes = itemBestOffersArrayType.getItemBestOffers();
//				for (ItemBestOffersType itemBestOffersType : itemBestOffersTypes) {
//					ItemType itemType = itemBestOffersType.getItem();
//					BestOfferArrayType bestOfferArrayType =itemBestOffersType.getBestOfferArray();
//					AmountType buyItNowPrice = itemType.getBuyItNowPrice();
//					Double nowPrice = buyItNowPrice.getValue();
//					String currency = buyItNowPrice.getCurrencyID().value();
//					BestOfferType[] bestOfferTypes= bestOfferArrayType.getBestOffer();
//					for (BestOfferType bestOfferType : bestOfferTypes) {
//						EBayBestOffersModel eBayBestOffersModel = bestOfferToModel(bestOfferType);
//						eBayBestOffersModel.setSiteid(sellerId);
//						eBayBestOffersModel.setItem_id(itemType.getItemID());
//						eBayBestOffersModel.setBuyit_now_price(nowPrice);
//						eBayBestOffersModel.setCurrency(currency);
//						eBayBestOffersModel.setTitel(itemType.getTitle());
//						EBayBestOffersModel bestOffersModel = eBayOffersDao.interiorSelectBestOfferByBestId(eBayBestOffersModel.getBest_offer_id());
//						if(ValidationUtil.isNull(bestOffersModel)){
//							eBayBestOffersModels.add(eBayBestOffersModel);
//						}else{
//							updateBestOffersModls.add(eBayBestOffersModel);
//						}
//						
//					}
//			   }
//		}else{
//			BestOfferType[] bestOfferTypes=getBestOffersCall.getReturnedBestOffers();
//			for (BestOfferType bestOfferType : bestOfferTypes) {
//				EBayBestOffersModel eBayBestOffersModel = bestOfferToModel(bestOfferType);
//				eBayBestOffersModel.setSiteid(sellerId);
//				eBayBestOffersModel.setItem_id(getBestOffersCall.getItemID());
//				ItemType itemType = getBestOffersCall.getReturnedItem();
//				AmountType buyItNowPrice = itemType.getBuyItNowPrice();
//				Double nowPrice = buyItNowPrice.getValue();
//				String currency = buyItNowPrice.getCurrencyID().value();
//				eBayBestOffersModel.setBuyit_now_price(nowPrice);
//				eBayBestOffersModel.setCurrency(currency);
//				eBayBestOffersModel.setTitel(itemType.getTitle());
//				EBayBestOffersModel bestOffersModel = eBayOffersDao.interiorSelectBestOfferByBestId(eBayBestOffersModel.getBest_offer_id());
//				if(ValidationUtil.isNull(bestOffersModel)){
//					eBayBestOffersModels.add(eBayBestOffersModel);
//				}else{
//					updateBestOffersModls.add(eBayBestOffersModel);
//				}
//			}
//		}
//		if(eBayBestOffersModels.size()>0){
//			insertEBayBestOffers(eBayBestOffersModels);
//		}
//		if(updateBestOffersModls.size()>0){
//			updateEBayBestOffers(updateBestOffersModls);
//		}
//		String message = RequestSDKMessage.getSDKMessage(getBestOffersCall, "议价信息");
//		result.setDescription(message);
//		return result;
//	}
//	public synchronized EBayBestOffersModel bestOfferToModel(BestOfferType bestOfferType){
//		EBayBestOffersModel eBayBestOffersModel = new EBayBestOffersModel();
//		eBayBestOffersModel.setBest_offer_id(bestOfferType.getBestOfferID());
//		Calendar expirationTime = bestOfferType.getExpirationTime();
//		String sourceExpiration = ConversionDateUtil.dateToCharFormat(expirationTime.getTime(), "yyyy-MM-dd HH:mm:ss");
//		String expirationString = TimeTools.timeConvert(sourceExpiration, expirationTime.getTimeZone().getID(), "GMT+8");
//		Date expirationDate = ConversionDateUtil.charToDate(expirationString, "yyyy-MM-dd HH:mm:ss");
//		eBayBestOffersModel.setExpiration_time(expirationDate);
//		eBayBestOffersModel.setBest_offer_code_type(bestOfferType.getBestOfferCodeType().value());
//		eBayBestOffersModel.setStartus(bestOfferType.getStatus().value());
//		eBayBestOffersModel.setQuantity(bestOfferType.getQuantity());
//		eBayBestOffersModel.setPrice(bestOfferType.getPrice().getValue());
//		eBayBestOffersModel.setBuyer_message(bestOfferType.getBuyerMessage());
//		UserType userType = bestOfferType.getBuyer();
//		eBayBestOffersModel.setEmail(userType.getEmail());
//		eBayBestOffersModel.setFeed_back_score(userType.getFeedbackScore());
//		Calendar registration = userType.getRegistrationDate();
//	    String sourceRegistration = ConversionDateUtil.dateToCharFormat(registration.getTime(), "yyyy-MM-dd HH:mm:ss");
//	    String registrationString = TimeTools.timeConvert(sourceRegistration, registration.getTimeZone().getID(), "GMT+8");
//	    Date registrationDate = ConversionDateUtil.charToDate(registrationString, "yyyy-MM-dd HH:mm:ss");
//	    
//	    eBayBestOffersModel.setRegistration_date(registrationDate);
//	    
//	    eBayBestOffersModel.setUser_id(userType.getUserID());
//		AddressType addressType = userType.getShippingAddress();
//		    
//		eBayBestOffersModel.setState_or_province(addressType.getStateOrProvince());
//		eBayBestOffersModel.setCountry_name(addressType.getCountryName());
//		eBayBestOffersModel.setPostal_code(addressType.getPostalCode());
//		eBayBestOffersModel.setSeller_message(bestOfferType.getSellerMessage());
//		
//		return eBayBestOffersModel;
//	}
//	@Override
//	public OperationResult synchronousBestOffer(String itemId, String siteName,String sellerId,String bestOfferId) {
//		OperationResult result = new OperationResult();
//		EbayAccountModel ebayAccountModel = ebayAccountService.getAccountModelByName(sellerId);
//		String token = ebayAccountModel.getToken();
//		String serverUrl =ServerUrl.getServerUrl(sellerId);
//		 
//	    ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		GetBestOffersCall getBestOffersCall = new GetBestOffersCall(apiContext);
//		
//	    getBestOffersCall.setBestOfferStatus(BestOfferStatusCodeType.ALL);
//	    PaginationType paginationType = new PaginationType();
//	    paginationType.setEntriesPerPage(200);
//	    paginationType.setPageNumber(1);
//	    DetailLevelCodeType[] detailLevelCodeTypes = new DetailLevelCodeType[1];
//	    detailLevelCodeTypes[0] = DetailLevelCodeType.RETURN_ALL;
//	    getBestOffersCall.setDetailLevel(detailLevelCodeTypes);
//	    getBestOffersCall.setPagination(paginationType);
//	    getBestOffersCall.setItemID(itemId);
//	    	if (!ValidationUtil.isNull(bestOfferId)) {
//	    		getBestOffersCall.setBestOfferID(bestOfferId);
//			}
//	    	try {
//	    		
//				getBestOffersCall.getBestOffers();
//				BestOfferType[] bestOfferTypes = getBestOffersCall.getReturnedBestOffers();
//				ItemBestOffersArrayType itemBestOffersArrayType = getBestOffersCall.getReturnedItemBestOffersArray();
//				ItemType itemType = getBestOffersCall.getReturnedItem();
//				AmountType buyItNowPrice = itemType.getBuyItNowPrice();
//				Double nowPrice = buyItNowPrice.getValue();
//				String currency = buyItNowPrice.getCurrencyID().value();
//				List<EBayBestOffersModel> eBayBestOffersModels = Lists.newArrayList();
//				List<EBayBestOffersModel> updateBestOffersModls = Lists.newArrayList();
//				for (BestOfferType bestOfferType : bestOfferTypes) {
//					
//					EBayBestOffersModel eBayBestOffersModel = new EBayBestOffersModel();
//					eBayBestOffersModel.setSiteid(sellerId);
//					eBayBestOffersModel.setItem_id(itemId);
//					
//					eBayBestOffersModel.setBest_offer_id(bestOfferType.getBestOfferID());
//					Calendar expirationTime = bestOfferType.getExpirationTime();
//					String sourceExpiration = ConversionDateUtil.dateToCharFormat(expirationTime.getTime(), "yyyy-MM-dd HH:mm:ss");
//					String expirationString = TimeTools.timeConvert(sourceExpiration, expirationTime.getTimeZone().getID(), "GMT+8");
//					Date expirationDate = ConversionDateUtil.charToDate(expirationString, "yyyy-MM-dd HH:mm:ss");
//					eBayBestOffersModel.setExpiration_time(expirationDate);
//					eBayBestOffersModel.setBest_offer_code_type(bestOfferType.getBestOfferCodeType().value());
//					eBayBestOffersModel.setStartus(bestOfferType.getStatus().value());
//					
//					eBayBestOffersModel.setQuantity(bestOfferType.getQuantity());
//					
//					eBayBestOffersModel.setPrice(bestOfferType.getPrice().getValue());
//					
//					eBayBestOffersModel.setBuyit_now_price(nowPrice);
//					eBayBestOffersModel.setCurrency(currency);
//					eBayBestOffersModel.setBuyer_message(bestOfferType.getBuyerMessage());
//					UserType userType = bestOfferType.getBuyer();
//					
//					eBayBestOffersModel.setEmail(userType.getEmail());
//					
//					eBayBestOffersModel.setFeed_back_score(userType.getFeedbackScore());
//				    
//					Calendar registration = userType.getRegistrationDate();
//				    String sourceRegistration = ConversionDateUtil.dateToCharFormat(registration.getTime(), "yyyy-MM-dd HH:mm:ss");
//				    String registrationString = TimeTools.timeConvert(sourceRegistration, registration.getTimeZone().getID(), "GMT+8");
//				    Date registrationDate = ConversionDateUtil.charToDate(registrationString, "yyyy-MM-dd HH:mm:ss");
//				    
//				    eBayBestOffersModel.setRegistration_date(registrationDate);
//				    
//				    eBayBestOffersModel.setUser_id(userType.getUserID());
//					 AddressType addressType = userType.getShippingAddress();
//					    
//					eBayBestOffersModel.setState_or_province(addressType.getStateOrProvince());
//					eBayBestOffersModel.setCountry_name(addressType.getCountryName());
//					eBayBestOffersModel.setPostal_code(addressType.getPostalCode());
//					eBayBestOffersModel.setSeller_message(bestOfferType.getSellerMessage());
//					eBayBestOffersModel.setTitel(itemType.getTitle());
//					EBayBestOffersModel bestOffersModel = eBayOffersDao.interiorSelectBestOfferByBestId(eBayBestOffersModel.getBest_offer_id());
//					if(ValidationUtil.isNull(bestOffersModel)){
//						eBayBestOffersModels.add(eBayBestOffersModel);
//					}else{
//						updateBestOffersModls.add(eBayBestOffersModel);
//					}
//					
//				}
//				if(eBayBestOffersModels.size()>0){
//					insertEBayBestOffers(eBayBestOffersModels);
//				}
//				if(updateBestOffersModls.size()>0){
//					updateEBayBestOffers(updateBestOffersModls);
//				}
//				result.setDescription("同步成功！");
//	    	} catch (ApiException e) {
//	    		String message = RequestSDKMessage.getSDKMessage(getBestOffersCall, "同步议价");
//				result.setDescription(message);
//				result.setError("0");
//				logger.error("同步议价失败--"+e.getMessage());
//			} catch (SdkException e) {
//				String message = RequestSDKMessage.getSDKMessage(getBestOffersCall, "同步议价");
//				result.setError("0");
//				result.setDescription(message);
//				logger.error("同步议价失败", e);
//			} catch (Exception e) {
//				String message = RequestSDKMessage.getSDKMessage(getBestOffersCall, "同步议价");
//				result.setDescription(message);
//				result.setError("0");
//				logger.error("同步议价失败", e);
//			}
//		
//		return result;
//	}
//	@Override
//	public OperationResult insertEBayBestOffers(List<EBayBestOffersModel> eBayBestOffersModels) {
//		OperationResult result = new OperationResult();
//		try {
//			eBayOffersDao.insertEBayBestOffers(eBayBestOffersModels);
//			result.setDescription("添加成功！");
//		} catch (Exception e) {
//			result.setDescription("添加失败！");
//			logger.error("议价添加", e);
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult updateEBayBestOffers(List<EBayBestOffersModel> eBayBestOffersModels) {
//		OperationResult result = new OperationResult();
//		try {
//			eBayOffersDao.updateEBayBestOffers(eBayBestOffersModels);
//			result.setDescription("更改成功！");
//			
//		} catch (Exception e) {
//			result.setDescription("更改失败！");
//			logger.error("议价更改",e);
//		}
//		return result;
//	}
//
//	@Override
//	public ResponseResult<PublicationVO> selectBestOfferItems(RequestParam param) {
//		ResponseResult<PublicationVO> result = new ResponseResult<>();
//		PublicationVO publicationVO = BeanConvertUtil.mapToObject(param.getParam(), PublicationVO.class);
//		List<PublicationModel> publicationModels = publicationInfoDao.selectBestOfferItems(param.getStartRow(), param.getEndRow());
//		List<PublicationVO> publicationVOs = Lists.newArrayList();
//		Integer total = publicationInfoDao.getBestOfferTotal();
//		convertList(publicationModels, publicationVOs);
//		for (PublicationVO publicationVO2 : publicationVOs) {
//			Date endDate = publicationVO2.getEnd_publication_date();
//			publicationVO2.setEndDate(ConversionDateUtil.dateToCharFormat(endDate, "yyyy-MM-dd hh:MM:ss"));
//			Long siteId = publicationVO2.getSiteId();
//			String  currency = CurrencyCodeTypeEnum.searchCurrencyBySiteID(siteId).value();
//			publicationVO2.setSiteName(SiteIDEnum.searchBySiteID(siteId).value());
//			List<ShipOptionModel> shipOptionModels = pubInfoDAO.getPublictonTransById(publicationVO2.getId().toString());
//			Double inlandCost = new Double(0);
//			Double inlandExtraCost = new Double(0);
//			Double internationCost = new Double(0);
//			Double internationExtraCost = new Double(0);
//			for (ShipOptionModel shipOptionModel : shipOptionModels) {
//				String tranKind = shipOptionModel.getTranKind();
//				if(tranKind.equals("0")){
//					String domesticShipCost = shipOptionModel.getDomesticShipCost();
//					if(!ValidationUtil.isNullOrEmpty(domesticShipCost)){
//						inlandCost = inlandCost+Double.parseDouble(domesticShipCost);
//					}
//					String domesticExtraCost = shipOptionModel.getDomesticExtraCost();
//					if(!ValidationUtil.isNullOrEmpty(domesticExtraCost)){
//						inlandExtraCost = inlandExtraCost+ Double.parseDouble(domesticExtraCost);
//					}
//				}else if (tranKind.equals("1")) {
//					String domesticShipCost = shipOptionModel.getDomesticShipCost();
//					if(!ValidationUtil.isNullOrEmpty(domesticShipCost)){
//						internationCost = internationCost+Double.parseDouble(domesticShipCost);
//					}
//					String domesticExtraCost = shipOptionModel.getDomesticExtraCost();
//					if(!ValidationUtil.isNullOrEmpty(domesticExtraCost)){
//						internationExtraCost = internationExtraCost+ Double.parseDouble(domesticExtraCost);
//					}
//				}
//			}
//			publicationVO2.setInland(inlandCost+"/"+inlandExtraCost+" "+currency);
//			publicationVO2.setInternation(internationCost+"/"+internationExtraCost+" "+currency);
//			
//		}
//		result.setRows(publicationVOs);
//		result.setTotal(total);
//		return result;
//	}
//	@Override
//	public ResponseResult<BestOffersVo> selectEBayBestOffersByItemId(Map<String, Object> map) {
//		ResponseResult<BestOffersVo> result = new ResponseResult<>();
//		List<EBayBestOffersModel> eBayBestOffersModels = eBayOffersDao.selectEBayBestOffersByItemId(map);
//		Integer total = eBayBestOffersModels.size();
//		List<BestOffersVo> bestOffersVos = Lists.newArrayList();
//		convertList2(eBayBestOffersModels, bestOffersVos);
//		for (BestOffersVo bestOffersVo : bestOffersVos) {
//			String entDate = ConversionDateUtil.dateToCharFormat(bestOffersVo.getExpiration_time(), "yyyy-MM-dd hh:MM:ss");
//			bestOffersVo.setEndTime(entDate);
//		    Double price = bestOffersVo.getPrice();
//		    Double newPrice = bestOffersVo.getBuyit_now_price();
//		    bestOffersVo.setPrices(newPrice+"/"+price +" "+bestOffersVo.getCurrency());
//		    String bestOfferCodeType = bestOffersVo.getBest_offer_code_type();
//		    if(bestOfferCodeType.equalsIgnoreCase("BuyerBestOffer")){
//		    	bestOffersVo.setMessage(bestOffersVo.getBuyer_message());
//		    }else if(bestOfferCodeType.equalsIgnoreCase("SellerCounterOffer")){
//		    	bestOffersVo.setMessage(bestOffersVo.getSeller_message());
//		    }else{
//		    	bestOffersVo.setMessage(bestOffersVo.getSeller_message());
//		    }		
//		}
//		result.setRows(bestOffersVos);
//		result.setTotal(total);
//		return result;
//	}
//	private void convertList(List<PublicationModel> source, final List<PublicationVO> target) {
//		CollectionUtil.each(source, new IAction<PublicationModel>() {
//			@Override
//			public void excute(PublicationModel obj) {
//				PublicationVO publicationVO = new PublicationVO();
//				BeanUtils.copyProperties(obj, publicationVO);
//				target.add(publicationVO);
//			}
//		});
//	}
//	private void convertList2(List<EBayBestOffersModel> source, final List<BestOffersVo> target) {
//		CollectionUtil.each(source, new IAction<EBayBestOffersModel>() {
//			@Override
//			public void excute(EBayBestOffersModel obj) {
//				BestOffersVo bestOffersVo = new BestOffersVo();
//				BeanUtils.copyProperties(obj, bestOffersVo);
//				target.add(bestOffersVo);
//			}
//		});
//	}
//
//	@Override
//	public OperationResult bestOfferMain(Map<String, Object> map) {
//		String seller_id = map.get("seller_id").toString();
//		EbayAccountModel ebayAccountModel = ebayAccountService.getAccountByAccount(seller_id);
//		String token = ebayAccountModel.getToken();
//		String serverUrl = ServerUrl.getServerUrl(seller_id);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		OperationResult result = new OperationResult();
//		RespondToBestOfferCall respondToBestOfferCall = new RespondToBestOfferCall(apiContext);
//		respondToBestOfferCall.setItemID(map.get("none_item_id").toString());
//		String[] bestOfferId = new String[1];
//		String bestId = map.get("none_best_id").toString();
//		bestOfferId[0] = bestId;
//		respondToBestOfferCall.setBestOfferIDs(bestOfferId);
//		String sellerMessage =map.get("sellerResponse").toString();
//		respondToBestOfferCall.setSellerResponse(sellerMessage);
//		try {
//			String operateType = map.get("operate_Type").toString();
//			respondToBestOfferCall.setBestOfferAction(BestOfferActionCodeType.fromValue(operateType));
//			if(operateType.equalsIgnoreCase("Counter")){
//				String quantity = map.get("none_quantity").toString();
//				respondToBestOfferCall.setCounterOfferQuantity(Integer.parseInt(quantity));
//				AmountType price =  EbayMappingToSDK.getAmountTypeByStr(map.get("counterOfferPrice").toString(), CurrencyCodeType.fromValue(map.get("none_currency").toString()));
//				respondToBestOfferCall.setCounterOfferPrice(price);
//			}
//			respondToBestOfferCall.respondToBestOffer();
//			String message = RequestSDKMessage.getSDKMessage(respondToBestOfferCall, "议价");
//			EBayBestOffersModel eBayBestOffersModel = new EBayBestOffersModel();
//			eBayBestOffersModel.setBest_offer_id(bestId);
//			String startuVal = "";
//			if(operateType.equalsIgnoreCase("accept")){
//				startuVal = "Accepted";
//			}else if(operateType.equalsIgnoreCase("decline")){
//				startuVal = "Declined";
//			}else if(operateType.equalsIgnoreCase("counter")){
//				startuVal = "Countered";
//			}
//			eBayBestOffersModel.setStartus(startuVal);
//			eBayBestOffersModel.setSeller_message(sellerMessage);
//			eBayOffersDao.updateEbayBestOfferByBest(eBayBestOffersModel);
///*			EBayBestOffersModel eBayBestOffersModel2 = new EBayBestOffersModel();
//			eBayBestOffersModel2.setBest_offer_id(bestId);
//			eBayBestOffersModel2.setStartus(startuVal);
//			eBayBestOffersModel2.setSeller_message(sellerMessage);
//			eBayOffersDao.updateEbayBestOfferByBest(eBayBestOffersModel2);*/
//			result.setDescription(message);
//		} catch (Exception e) {
//			String message = RequestSDKMessage.getSDKMessage(respondToBestOfferCall, "议价");
//			result.setDescription(message);
//			result.setError("0");
//			logger.error("议价失败", e);
//		}
//		return result;
//	}
//
//	@Override
//	public EBayBestOffersModel interiorSelectBestOfferByBestId(String bestOfferId) {
//		return eBayOffersDao.interiorSelectBestOfferByBestId(bestOfferId);
//	}
//
//	@Override
//	public OperationResult updateEnabledFlagByBestId(EBayBestOffersModel eBayBestOffersModel) {
//		OperationResult result = new OperationResult();
//		try {
//			eBayOffersDao.updateEnabledFlagByBestId(eBayBestOffersModel);
//			result.setDescription("忽略成功！");
//		} catch (Exception e) {
//			result.setDescription("忽略失败！");
//			e.printStackTrace();
//			return result;
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult restoreBestOfferByBestId(EBayBestOffersModel eBayBestOffersModel) {
//		eBayBestOffersModel.setEnabled_flag("Y");
//		OperationResult result =updateEnabledFlagByBestId(eBayBestOffersModel);
//		result.setDescription("还原成功！");
//		return result;
//	}
//
//	@Override
//	public OperationResult deleteBestOfferByBestId(EBayBestOffersModel eBayBestOffersModel) {
//		eBayBestOffersModel.setEnabled_flag("N");
//		OperationResult result = updateEnabledFlagByBestId(eBayBestOffersModel);
//		result.setDescription("忽略成功！");
//		return result;
//	}
//	@Override
//	public ResponseResult<BestOffersVo> selectGroupbyBestOffers(RequestParam param) {
//		ResponseResult<BestOffersVo> result = new ResponseResult<>();
//		Map<String, Object> map =param.getParam();
//		map.put("sort", param.getOrder());
//		List<EBayBestOffersModel> eBayBestOffersModels = eBayOffersDao.selectGroupbyBestOffers(map,param.getStartRow(), param.getEndRow());
//		Integer total = eBayOffersDao.getTotal(map);
//		List<BestOffersVo> bestOffersVos = Lists.newArrayList();
//		convertList2(eBayBestOffersModels, bestOffersVos);
//		for (BestOffersVo bestOffersVo : bestOffersVos) {
//			String entDate = ConversionDateUtil.dateToCharFormat(bestOffersVo.getExpiration_time(), "yyyy-MM-dd hh:MM:ss");
//			bestOffersVo.setEndTime(entDate);
//			String bestOfferCodeType =bestOffersVo.getBest_offer_code_type();
//			/*if(bestOfferCodeType.equalsIgnoreCase("BuyerBestOffer")){
//		    	bestOffersVo.setMessage(bestOffersVo.getBuyer_message());
//		    }else if(bestOfferCodeType.equalsIgnoreCase("SellerCounterOffer")){
//		    	bestOffersVo.setMessage(bestOffersVo.getSeller_message());
//		    }else{
//		    	bestOffersVo.setMessage(bestOffersVo.getSeller_message());
//		    }	*/
//			if(bestOfferCodeType.toUpperCase().indexOf("BUYER")> -1){
//		    	bestOffersVo.setMessage(bestOffersVo.getBuyer_message());
//		    }else if(bestOfferCodeType.toUpperCase().indexOf("SELLER")> -1){
//		    	bestOffersVo.setMessage(bestOffersVo.getSeller_message());
//		    }else{
//		    	bestOffersVo.setMessage(bestOffersVo.getBuyer_message());
//		    }	
//		}
//		result.setRows(bestOffersVos);
//		result.setTotal(total);
//		return result;
//	}
//	@Override
//	public ResponseResult<BestOffersVo> selectRecycleEBayBestOffersByItemId(String itemId) {
//		ResponseResult<BestOffersVo> result = new ResponseResult<>();
//		List<EBayBestOffersModel> eBayBestOffersModels = eBayOffersDao.selectRecycleEBayBestOffersByItemId(itemId);
//		Integer total = eBayBestOffersModels.size();
//		List<BestOffersVo> bestOffersVos = Lists.newArrayList();
//		convertList2(eBayBestOffersModels, bestOffersVos);
//		for (BestOffersVo bestOffersVo : bestOffersVos) {
//			String entDate = ConversionDateUtil.dateToCharFormat(bestOffersVo.getExpiration_time(), "yyyy-MM-dd hh:MM:ss");
//			bestOffersVo.setEndTime(entDate);
//		    Double price = bestOffersVo.getPrice();
//		    Double newPrice = bestOffersVo.getBuyit_now_price();
//		    bestOffersVo.setPrices(newPrice+"/"+price +" "+bestOffersVo.getCurrency());
//		    String bestOfferCodeType = bestOffersVo.getBest_offer_code_type();
//		    if(bestOfferCodeType.equalsIgnoreCase("BuyerBestOffer")){
//		    	bestOffersVo.setMessage(bestOffersVo.getBuyer_message());
//		    }else if(bestOfferCodeType.equalsIgnoreCase("SellerCounterOffer")){
//		    	bestOffersVo.setMessage(bestOffersVo.getSeller_message());
//		    }else{
//		    	bestOffersVo.setMessage(bestOffersVo.getSeller_message());
//		    }		
//		}
//		result.setRows(bestOffersVos);
//		result.setTotal(total);
//		return result;
//	}
//	
//
//	
//}
