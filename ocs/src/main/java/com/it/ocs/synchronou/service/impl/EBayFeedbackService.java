//package com.it.ocs.synchronou.service.impl;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.sdk.call.GetUserPreferencesCall;
//import com.ebay.sdk.call.LeaveFeedbackCall;
//import com.ebay.sdk.call.RespondToFeedbackCall;
//import com.ebay.soap.eBLBaseComponents.CommentTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
//import com.ebay.soap.eBLBaseComponents.FeedbackDetailType;
//import com.ebay.soap.eBLBaseComponents.FeedbackResponseCodeType;
//import com.ebay.soap.eBLBaseComponents.FeedbackTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.PaginationResultType;
//import com.ebay.soap.eBLBaseComponents.PaginationType;
//import com.google.common.collect.Lists;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.RequestParam;
//import com.it.ocs.common.ResponseResult;
//import com.it.ocs.common.service.BaseService;
//import com.it.ocs.common.support.IAction;
//import com.it.ocs.common.util.CollectionUtil;
//import com.it.ocs.synchronou.call.EbayGetFeedbackCall;
//import com.it.ocs.synchronou.dao.IEBayFeedbackDao;
//import com.it.ocs.synchronou.mapping.UserIDToken;
//import com.it.ocs.synchronou.model.EBayFeedbackModel;
//import com.it.ocs.synchronou.model.EbayAccountModel;
//import com.it.ocs.synchronou.service.IEBayFeedbackService;
//import com.it.ocs.synchronou.service.IEbayAccountService;
//import com.it.ocs.synchronou.util.RequestSDKMessage;
//import com.it.ocs.synchronou.util.ServerUrl;
//import com.it.ocs.synchronou.util.ValidationUtil;
//import com.it.ocs.synchronou.vo.FeedbackVO;
//
//@Service
//public class EBayFeedbackService extends BaseService implements IEBayFeedbackService {
//    private static Logger log = Logger.getLogger(EBayFeedbackService.class);
//	@Autowired
//	private IEBayFeedbackDao feedbackDao;
//	@Autowired
//	private IEbayAccountService ebayAccountService;
//	
//	@Override
//	public OperationResult note(FeedbackVO feedbackVO) {
//		OperationResult result = new OperationResult();
//		try {
//			EBayFeedbackModel eBayFeedbackModel = feedbackVO;
//			feedbackDao.updateFeedbackById(eBayFeedbackModel);
//			result.setDescription("修改成功！");
//		} catch (Exception e) {
//			// TODO: handle exception
//			result.setDescription("修改失败");
//			log.error("修改失败",e);
//			return result;
//		}
//		return result;
//	}
//	@Override
//	public OperationResult comments(FeedbackVO feedbackVO) {
//		String sellerId = feedbackVO.getSeller_id();
//		EbayAccountModel ebayAccountModel = ebayAccountService.getAccountByAccount(sellerId);
//		String token = ebayAccountModel.getToken();
//		String serverUrl = ServerUrl.getServerUrl(sellerId);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		LeaveFeedbackCall leaveFeedbackCall = new LeaveFeedbackCall(apiContext);
//		OperationResult result = new OperationResult();
//		try {
//			leaveFeedbackCall.setOrderLineItemID(feedbackVO.getOrder_line_item_id());
//			leaveFeedbackCall.setTargetUser(feedbackVO.getBuyer_id());
//			FeedbackDetailType feedbackDetail = new FeedbackDetailType();
//			feedbackDetail.setCommentText(feedbackVO.getResponseText());
//			feedbackDetail.setCommentType(CommentTypeCodeType.POSITIVE);
//			feedbackDetail.setItemID(feedbackVO.getItem_id());
//			leaveFeedbackCall.setFeedbackDetail(feedbackDetail);
//			leaveFeedbackCall.leaveFeedback();
//			String message = RequestSDKMessage.getSDKMessage(leaveFeedbackCall, "评价");
//			result.setDescription(message);
//		} catch (Exception e) {
//			result.setDescription(e.getMessage());
//			log.error("comments失败", e);
//		}
//		
//		return result;
//	}
//	@Override
//	public OperationResult replyFollowUp(FeedbackVO feedbackVO) {
//		String sellerId = feedbackVO.getSeller_id();
//		EbayAccountModel ebayAccountModel = ebayAccountService.getAccountByAccount(sellerId);
//		String token = ebayAccountModel.getToken();
//		String serverUrl =ServerUrl.getServerUrl(sellerId);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		RespondToFeedbackCall respondToFeedbackCall = new RespondToFeedbackCall(apiContext);
//		respondToFeedbackCall.setFeedbackID(feedbackVO.getReceived_feedback_id());
//		respondToFeedbackCall.setTargetUserID(feedbackVO.getBuyer_id());
//		String responseType = feedbackVO.getResponse_type();
//		String responseText = feedbackVO.getResponseText();
//		respondToFeedbackCall.setOrderLineItemID(feedbackVO.getOrder_line_item_id());
//		respondToFeedbackCall.setResponseType(FeedbackResponseCodeType.fromValue(responseType));
//		respondToFeedbackCall.setResponseText(responseText);
//		OperationResult result = new OperationResult();
//		
//		try {
//			respondToFeedbackCall.respondToFeedback();
//			String message = RequestSDKMessage.getSDKMessage(respondToFeedbackCall, "回复/追加");
//			EBayFeedbackModel eBayFeedbackModel = feedbackVO;
//			if(responseType.equals("Reply")){
//				eBayFeedbackModel.setReply_text(responseText);
//			}else{
//				eBayFeedbackModel.setFollow_up_text(responseText);
//			}
//			updateFeedbackById(eBayFeedbackModel);
//			result.setDescription(message);
//		} catch (ApiException e) {
//			String message = RequestSDKMessage.getSDKMessage(respondToFeedbackCall, "回复/追加");
//			result.setDescription(message);
//			log.error("replyFollowUp失败", e);
//		} catch (SdkException e) {
//			result.setDescription("操作失败！");
//			log.error("replyFollowUp失败", e);
//		} catch (Exception e) {
//			result.setDescription("操作失败！");
//			log.error("replyFollowUp失败", e);
//			return result;
//		}
//		return result;
//	}
//	@Override
//	public OperationResult feedbackSynchronous() {
//		OperationResult result = new OperationResult();
//		List<EbayAccountModel> ebayAccountModels = ebayAccountService.getAccounts();
//		ExecutorService cExecutorService = Executors.newCachedThreadPool();
//		for (EbayAccountModel ebayAccountModel : ebayAccountModels) {
//			cExecutorService.execute(new Runnable() {
//				   @Override
//				   public void run() {
//					    String seller_id = ebayAccountModel.getAccount();
//					    Date buyerDate = feedbackDao.selectMaxLeftCommentTime(seller_id); 
//					    Date date = feedbackDao.selectMaxReceivedCommentTime(seller_id);
//					    String token = ebayAccountModel.getToken();
//					    String serverUrl = null;
//						serverUrl = ServerUrl.getServerUrl(seller_id);
//						ApiContext apiContext  = BaseEbaySDKService.getApiContext(token, serverUrl);
//						EbayGetFeedbackCall getFeedbackCall = new EbayGetFeedbackCall(apiContext);
//						DetailLevelCodeType[] detailLevelCodeTypes = new DetailLevelCodeType[1];
//						detailLevelCodeTypes[0]= DetailLevelCodeType.RETURN_ALL;
//						getFeedbackCall.setDetailLevel(detailLevelCodeTypes);
//						PaginationType paginationType = new PaginationType();
//						paginationType.setEntriesPerPage(200);
//						paginationType.setPageNumber(1);
//						getFeedbackCall.setPagination(paginationType);
//						getFeedbackCall.setFeedbackType(FeedbackTypeCodeType.FEEDBACK_LEFT);
//						getFeedbackCall.setUserID(seller_id);
//				
//				try {
//					getFeedbackCall.getFeedback();
//					PaginationResultType buyerPaginationResultType = getFeedbackCall.getReturnedPaginationResult();
//					Integer buyerPage = buyerPaginationResultType.getTotalNumberOfPages();
//					for (int i = 1; i <= buyerPage; i++) {
//						paginationType.setPageNumber(i);
//						getFeedbackCall.setPagination(paginationType);
//						FeedbackDetailType[] feedbackDetailTypes = getFeedbackCall.getFeedback();
//						List<EBayFeedbackModel> buyerEBayFeedbackModels = Lists.newArrayList();
//						List<EBayFeedbackModel> updateBuyerEBayFeedbackModels = Lists.newArrayList();
//						
//						for (FeedbackDetailType feedbackDetailType : feedbackDetailTypes) {
//								EBayFeedbackModel eBayFeedbackModel = getFeedbackLeft(feedbackDetailType);
//								Date buyerCommentTime = feedbackDetailType.getCommentTime().getTime();
//						        eBayFeedbackModel.setSeller_id(seller_id);
//						        if(buyerDate!=null&&buyerDate.compareTo(buyerCommentTime)>0){
//						        	i=buyerPage+1;
//						        }
//						        buyerEBayFeedbackModels.add(eBayFeedbackModel);
//						}
//						for (EBayFeedbackModel eBayFeedbackModel : buyerEBayFeedbackModels) {
//							EBayFeedbackModel eBayFeedbackModel2 = new EBayFeedbackModel();
//							eBayFeedbackModel2.setOrder_line_item_id(eBayFeedbackModel.getOrder_line_item_id());
//							if(selectEBayFeedbackModelByFeedbackId(eBayFeedbackModel2)!=null){
//								updateBuyerEBayFeedbackModels.add(eBayFeedbackModel);
//							}
//						}
//						buyerEBayFeedbackModels.removeAll(updateBuyerEBayFeedbackModels);
//						if(buyerEBayFeedbackModels.size()>0){
//							insertFeedbacks(buyerEBayFeedbackModels);
//						}
//						if(updateBuyerEBayFeedbackModels.size()>0){
//							updateEBayFeedbackModels(updateBuyerEBayFeedbackModels);
//						}
//					}
//					getFeedbackCall.setFeedbackType(FeedbackTypeCodeType.FEEDBACK_RECEIVED);
//					getFeedbackCall.getFeedback();
//					PaginationResultType sellerPaginationResultType = getFeedbackCall.getReturnedPaginationResult();
//					Integer sellerPage = sellerPaginationResultType.getTotalNumberOfPages();
//					for (int i = 0; i <= sellerPage; i++) {
//						paginationType.setPageNumber(i);
//						getFeedbackCall.setPagination(paginationType);
//						FeedbackDetailType[] feedbackDetailTypes = getFeedbackCall.getFeedback();
//						List<EBayFeedbackModel> sellerEBayFeedbackModels = Lists.newArrayList();
//						List<EBayFeedbackModel> sellerUpdateEBayFeedbackModels = Lists.newArrayList();
//						for (FeedbackDetailType feedbackDetailType : feedbackDetailTypes) {
//							EBayFeedbackModel feedbackModel = getFeedbackReceived(feedbackDetailType);
//							Date sellerDate = feedbackDetailType.getCommentTime().getTime();
//							feedbackModel.setSeller_id(seller_id);
//							if(!ValidationUtil.isNull(date)&&date.compareTo(sellerDate)>0){
//								i=sellerPage+1;
//							}
//							sellerEBayFeedbackModels.add(feedbackModel);
//						}
//						for (EBayFeedbackModel eBayFeedbackModel : sellerEBayFeedbackModels) {
//							EBayFeedbackModel eBayFeedbackModel2 = new EBayFeedbackModel();
//							eBayFeedbackModel2.setOrder_line_item_id(eBayFeedbackModel.getOrder_line_item_id());
//							if(selectEBayFeedbackModelByFeedbackId(eBayFeedbackModel2)!=null){
//								sellerUpdateEBayFeedbackModels.add(eBayFeedbackModel);
//							}
//						}
//						sellerEBayFeedbackModels.removeAll(sellerUpdateEBayFeedbackModels);
//						if(sellerEBayFeedbackModels.size()>0){
//						insertFeedbacks(sellerEBayFeedbackModels);
//						}
//						if(sellerUpdateEBayFeedbackModels.size()>0){
//							updateEBayFeedbackModels(sellerUpdateEBayFeedbackModels);
//						}
//						
//					}
//					StringBuffer stringBuffer = new StringBuffer();
//					stringBuffer.append(RequestSDKMessage.getSDKMessage(getFeedbackCall, "同步评价"));
//					result.setDescription(stringBuffer.toString());
//				} catch (ApiException e) {
//				    log.error("feedbackSynchronous", e);
//					result.setDescription(RequestSDKMessage.getSDKMessage(getFeedbackCall, "同步评价"));
//				} catch (SdkException e) {
//					 log.error("feedbackSynchronous", e);
//					result.setDescription(RequestSDKMessage.getSDKMessage(getFeedbackCall, "同步议价"));
//				} catch (Exception e) {
//					result.setDescription("操作失败！");
//					log.error("feedbackSynchronous", e);
//				}
//			}
//		  });   
//		}
//		cExecutorService.shutdown();
//		while(true){
//			if(cExecutorService.isTerminated()){
//										break;
//			}
//			try {
//										Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				log.equals(e);
//			}
//		}
//		return result;
//	}
//	@Override
//	public OperationResult insertFeedbacks(List<EBayFeedbackModel> eBayFeedbackModels) {
//		OperationResult result = new OperationResult();
//		try {
//			feedbackDao.insertFeedbacks(eBayFeedbackModels);
//			result.setDescription("添加成功！");
//		} catch (Exception e) {
//			result.setDescription("添加失败！");
//			log.error("添加feedback",e);
//			return result;
//		}
//		return result;
//	}
//
//	@Override
//	public EBayFeedbackModel selectEBayFeedbackModelByFeedbackId(EBayFeedbackModel eBayFeedbackModel) {
//		return feedbackDao.selectEBayFeedbackModel(eBayFeedbackModel);
//	}
//
//	@Override
//	public OperationResult updateEBayFeedbackModels(List<EBayFeedbackModel> eBayFeedbackModels) {
//		OperationResult result = new OperationResult();
//		try {
//			feedbackDao.updateEBayFeedbackModels(eBayFeedbackModels);
//			result.setDescription("更改成功！");
//		} catch (Exception e) {
//			result.setDescription("更改失败！");
//			log.error("更改失败", e);
//			return result;
//		}
//		return result;
//	}
//
//	@Override
//	public ResponseResult<FeedbackVO> list(RequestParam param) {
//		ResponseResult<FeedbackVO> result = new ResponseResult<>();
//		List<FeedbackVO> feedbackVOs = Lists.newArrayList();
//		Map<String, Object> map = param.getParam();
//		List<EBayFeedbackModel> eBayFeedbackModels = feedbackDao.selectFeedbackModelsByPage(map, param.getStartRow(), param.getEndRow());
//		convertList(eBayFeedbackModels, feedbackVOs);
//		int count = feedbackDao.getTotal(map);
//		result.setRows(feedbackVOs);
//		result.setTotal(count);
//		return result;
//	}
//	private void convertList(List<EBayFeedbackModel> source, final List<FeedbackVO> target) {
//		CollectionUtil.each(source, new IAction<EBayFeedbackModel>() {
//			@Override
//			public void excute(EBayFeedbackModel obj) {
//				FeedbackVO feedbackVO = new FeedbackVO();
//				BeanUtils.copyProperties(obj, feedbackVO);
//				target.add(feedbackVO);
//			}
//		});
//	}
//	@Override
//	public OperationResult updateFeedbackById(EBayFeedbackModel eBayFeedbackModel) {
//		OperationResult result = new OperationResult();
//		try {
//			feedbackDao.updateFeedbackById(eBayFeedbackModel);
//			result.setDescription("更改成功!");
//		} catch (Exception e) {
//			// TODO: handle exception
//			result.setDescription("更改失败!");
//			log.error("更改失败", e);
//			return result;
//		}
//		return result;
//	}
//	@Override
//	public OperationResult userSynchronous() {
//		OperationResult result = new OperationResult();
//		String accountModel = "le.deutschland";
//		EbayAccountModel ebayAccountModel = ebayAccountService.getAccountByAccount(accountModel);
//		String serverUrl = ServerUrl.getServerUrl(accountModel);
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(ebayAccountModel.getToken(), serverUrl);
//		GetUserPreferencesCall getUserPreferences = new GetUserPreferencesCall(apiContext);
//		
//		return null;
//	}
//	
//	public EBayFeedbackModel getFeedbackReceived(FeedbackDetailType feedbackDetailType){
//		EBayFeedbackModel eBayFeedbackModel = new EBayFeedbackModel();
//		synchronized (feedbackDetailType){
//			Date sellerDate = feedbackDetailType.getCommentTime().getTime();
//			eBayFeedbackModel.setReceived_comment_text(feedbackDetailType.getCommentText());
//			eBayFeedbackModel.setReceived_comment_time(sellerDate);
//			eBayFeedbackModel.setReceivedt_comment_type(feedbackDetailType.getCommentType().value());
//			eBayFeedbackModel.setItem_title(feedbackDetailType.getItemTitle());
//			eBayFeedbackModel.setBuyer_id(feedbackDetailType.getCommentingUser());
//			eBayFeedbackModel.setItem_id(feedbackDetailType.getItemID());
//			eBayFeedbackModel.setTransaction_id(feedbackDetailType.getTransactionID());
//			eBayFeedbackModel.setOrder_line_item_id(feedbackDetailType.getOrderLineItemID());
//			eBayFeedbackModel.setReceived_feed_back_response(feedbackDetailType.getFeedbackResponse());
//			eBayFeedbackModel.setReceived_feedback_id(feedbackDetailType.getFeedbackID());
//			if(!ValidationUtil.isNull(feedbackDetailType.getItemPrice())){
//				eBayFeedbackModel.setItem_price(feedbackDetailType.getItemPrice().getValue());
//				eBayFeedbackModel.setCurrency_id(feedbackDetailType.getItemPrice().getCurrencyID().value());
//			}
//		}
//		return eBayFeedbackModel;
//	}
//
//	public EBayFeedbackModel getFeedbackLeft(FeedbackDetailType feedbackDetailType){
//		EBayFeedbackModel eBayFeedbackModel = new EBayFeedbackModel();
//		synchronized (feedbackDetailType){
//			Date buyerCommentTime = feedbackDetailType.getCommentTime().getTime();
//			eBayFeedbackModel.setBuyer_id(feedbackDetailType.getCommentingUser());
//			eBayFeedbackModel.setCommenting_user_score(feedbackDetailType.getCommentingUserScore());
//			eBayFeedbackModel.setLeft_comment_text(feedbackDetailType.getCommentText());
//			eBayFeedbackModel.setLeft_comment_time(buyerCommentTime);
//			if(!ValidationUtil.isNull(feedbackDetailType.getCommentType())){
//				 eBayFeedbackModel.setLeft_comment_type(feedbackDetailType.getCommentType().value());
//			}
//			eBayFeedbackModel.setLeft_feed_back_response(feedbackDetailType.getFeedbackResponse());
//	        eBayFeedbackModel.setItem_id(feedbackDetailType.getItemID());
//	        eBayFeedbackModel.setLeft_feedback_id(feedbackDetailType.getFeedbackID());
//	        eBayFeedbackModel.setTransaction_id(feedbackDetailType.getTransactionID());
//	        eBayFeedbackModel.setOrder_line_item_id(feedbackDetailType.getOrderLineItemID());
//	        eBayFeedbackModel.setItem_title(feedbackDetailType.getItemTitle());
//	        if(!ValidationUtil.isNull(feedbackDetailType.getItemPrice())){
//	        
//			eBayFeedbackModel.setItem_price(feedbackDetailType.getItemPrice().getValue());
//			eBayFeedbackModel.setCurrency_id(feedbackDetailType.getItemPrice().getCurrencyID().value());
//	        }
//		}
//		return eBayFeedbackModel;
//	}
//}
