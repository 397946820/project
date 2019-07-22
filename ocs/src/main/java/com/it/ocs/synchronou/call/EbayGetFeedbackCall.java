//package com.it.ocs.synchronou.call;
//
//import com.ebay.sdk.ApiCall;
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.soap.eBLBaseComponents.CommentTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.FeedbackDetailType;
//import com.ebay.soap.eBLBaseComponents.FeedbackSummaryType;
//import com.ebay.soap.eBLBaseComponents.FeedbackTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.GetFeedbackRequestType;
//import com.ebay.soap.eBLBaseComponents.GetFeedbackResponseType;
//import com.ebay.soap.eBLBaseComponents.PaginationResultType;
//import com.ebay.soap.eBLBaseComponents.PaginationType;
//
//public class EbayGetFeedbackCall extends ApiCall {
//	 private String userID = null;
//	 private String feedbackID = null;
//	 private String itemID = null;
//	 private String transactionID = null;
//	 private CommentTypeCodeType[] commentType = null;
//	 private FeedbackTypeCodeType feedbackType = null;
//	 private PaginationType pagination = null;
//	 private String orderLineItemID = null;
//	 private FeedbackDetailType[] returnedFeedbackDetails = null;
//	 private int grandTotal = 0;
//	 private FeedbackSummaryType feedbackSummary = null;
//	 private int feedbackScore = 0;
//	 private PaginationResultType returnedPaginationResult = null;
//	 private Integer returnedEntriesPerPage = null;
//	 private Integer returnedPageNumber = null;
//	 private GetFeedbackResponseType getFeedbackResponseType;
//
//	
//	 public EbayGetFeedbackCall()
//	 {
//		 }
//
//	
//	 public EbayGetFeedbackCall(ApiContext apiContext)
//	 {
//		 super(apiContext);
//		 }
//
//	
//	 public GetFeedbackResponseType getGetFeedbackResponseType() {
//		return getFeedbackResponseType;
//	}
//
//
//	public void setGetFeedbackResponseType(GetFeedbackResponseType getFeedbackResponseType) {
//		this.getFeedbackResponseType = getFeedbackResponseType;
//	}
//
//
//	public FeedbackDetailType[] getFeedback() throws ApiException, SdkException, Exception
//	 {
//		 GetFeedbackRequestType req = new GetFeedbackRequestType();
//		
//		 if (this.userID == null) {
//			 throw new SdkException("UserID property is not set.");
//			 }
//		 req.setDetailLevel(getDetailLevel());
//		 if (this.userID != null) {
//			 req.setUserID(this.userID);
//		 }
//		 if (this.feedbackID != null) {
//			 req.setFeedbackID(this.feedbackID);
//		 }
//		 if (this.itemID != null) {
//			 req.setItemID(this.itemID);
//		 }
//		 if (this.transactionID != null) {
//			 req.setTransactionID(this.transactionID);
//		 }
//		 if (this.commentType != null) {
//			 req.setCommentType(this.commentType);
//		 }
//		 if (this.feedbackType != null) {
//			 req.setFeedbackType(this.feedbackType);
//		 }
//		 if (this.pagination != null) {
//			 req.setPagination(this.pagination);
//		 }
//		 if (this.orderLineItemID != null) {
//			 req.setOrderLineItemID(this.orderLineItemID);
//			 }
//		 GetFeedbackResponseType resp = (GetFeedbackResponseType) execute(req);
//		 getFeedbackResponseType=resp;
//		 this.returnedFeedbackDetails = ((resp.getFeedbackDetailArray() == null) ? null
//				: resp.getFeedbackDetailArray().getFeedbackDetail());
//		 this.grandTotal = ((resp.getFeedbackDetailItemTotal() == null) ? 0
//				: resp.getFeedbackDetailItemTotal().intValue());
//		 this.feedbackSummary = resp.getFeedbackSummary();
//		 this.feedbackScore = ((resp.getFeedbackScore() == null) ? 0 : resp.getFeedbackScore().intValue());
//		 this.returnedPaginationResult = resp.getPaginationResult();
//		 this.returnedEntriesPerPage = resp.getEntriesPerPage();
//		 this.returnedPageNumber = resp.getPageNumber();
//		 return getReturnedFeedbackDetails();
//		 }
//
//	
//	 public CommentTypeCodeType[] getCommentType()
//	 {
//		 return this.commentType;
//		 }
//
//	
//	 public void setCommentType(CommentTypeCodeType[] commentType)
//	 {
//		 this.commentType = commentType;
//		 }
//
//	
//	 public String getFeedbackID()
//	 {
//		 return this.feedbackID;
//		 }
//
//	
//	 public void setFeedbackID(String feedbackID)
//	 {
//		 this.feedbackID = feedbackID;
//		 }
//
//	
//	 public FeedbackTypeCodeType getFeedbackType()
//	 {
//		 return this.feedbackType;
//		 }
//
//	
//	 public void setFeedbackType(FeedbackTypeCodeType feedbackType)
//	 {
//		 this.feedbackType = feedbackType;
//		 }
//
//	
//	 public String getItemID()
//	 {
//		 return this.itemID;
//		 }
//
//	
//	 public void setItemID(String itemID)
//	 {
//		 this.itemID = itemID;
//		 }
//
//	
//	 public String getOrderLineItemID()
//	 {
//		 return this.orderLineItemID;
//		 }
//
//	
//	 public void setOrderLineItemID(String orderLineItemID)
//	 {
//		 this.orderLineItemID = orderLineItemID;
//		 }
//
//	
//	 public PaginationType getPagination()
//	 {
//		 return this.pagination;
//		 }
//
//	
//	 public void setPagination(PaginationType pagination)
//	 {
//		 this.pagination = pagination;
//		 }
//
//	
//	 public String getTransactionID()
//	 {
//		 return this.transactionID;
//		 }
//
//	
//	 public void setTransactionID(String transactionID)
//	 {
//		 this.transactionID = transactionID;
//		 }
//
//	
//	 public String getUserID()
//	 {
//		 return this.userID;
//		 }
//
//	
//	 public void setUserID(String userID)
//	 {
//		 this.userID = userID;
//		 }
//
//	
//	 public int getFeedbackScore()
//	 {
//		 return this.feedbackScore;
//		 }
//
//	
//	 public FeedbackSummaryType getFeedbackSummary()
//	 {
//		 return this.feedbackSummary;
//		 }
//
//	
//	 public int getGrandTotal()
//	 {
//		 return this.grandTotal;
//		 }
//
//	
//	 public Integer getReturnedEntriesPerPage()
//	 {
//		 return this.returnedEntriesPerPage;
//		 }
//
//	
//	 public FeedbackDetailType[] getReturnedFeedbackDetails()
//	 {
//		 return this.returnedFeedbackDetails;
//		 }
//
//	
//	 public Integer getReturnedPageNumber()
//	 {
//		 return this.returnedPageNumber;
//		 }
//
//	
//	 public PaginationResultType getReturnedPaginationResult()
//	 {
//		 return this.returnedPaginationResult;
//		 }
//}
