//package com.it.ocs.synchronou.call;
//
//import com.ebay.sdk.ApiCall;
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.soap.eBLBaseComponents.GetMyeBaySellingRequestType;
//import com.ebay.soap.eBLBaseComponents.GetMyeBaySellingResponseType;
//import com.ebay.soap.eBLBaseComponents.ItemListCustomizationType;
//import com.ebay.soap.eBLBaseComponents.MyeBaySellingSummaryType;
//import com.ebay.soap.eBLBaseComponents.PaginatedItemArrayType;
//import com.ebay.soap.eBLBaseComponents.PaginatedOrderTransactionArrayType;
//import com.ebay.soap.eBLBaseComponents.SellingSummaryType;
//
//public class EbayGetMyeBaySellingCall extends ApiCall {
//
//	 private ItemListCustomizationType scheduledList = null;
//	 private ItemListCustomizationType activeList = null;
//	 private ItemListCustomizationType soldList = null;
//	 private ItemListCustomizationType unsoldList = null;
//	 private ItemListCustomizationType bidList = null;
//	 private ItemListCustomizationType deletedFromSoldList = null;
//	 private ItemListCustomizationType deletedFromUnsoldList = null;
//	 private ItemListCustomizationType sellingSummary = null;
//	 private Boolean hideVariations = null;
//	 private GetMyeBaySellingRequestType myeBaySellingRequest = null;
//	 private SellingSummaryType returnedSellingSummary = null;
//	 private PaginatedItemArrayType returnedScheduledList = null;
//	 private PaginatedItemArrayType returnedActiveList = null;
//	 private PaginatedOrderTransactionArrayType returnedSoldList = null;
//	 private PaginatedItemArrayType returnedUnsoldList = null;
//	 private MyeBaySellingSummaryType returnedSummary = null;
//	 private PaginatedItemArrayType returnedBidList = null;
//	 private PaginatedOrderTransactionArrayType returnedDeletedFromSoldList = null;
//	 private PaginatedItemArrayType returnedDeletedFromUnsoldList = null;
//	 private GetMyeBaySellingResponseType eyeBaySellingResponseType;
//	
//	 
//
//
//	public GetMyeBaySellingResponseType getEyeBaySellingResponseType() {
//		return eyeBaySellingResponseType;
//	}
//
//
//	public void setEyeBaySellingResponseType(GetMyeBaySellingResponseType eyeBaySellingResponseType) {
//		this.eyeBaySellingResponseType = eyeBaySellingResponseType;
//	}
//
//
//	public EbayGetMyeBaySellingCall()
//	 {
//		 }
//
//	
//	 public EbayGetMyeBaySellingCall(ApiContext apiContext)
//	 {
//		 super(apiContext);
//		 }
//
//	
//	 public void getMyeBaySelling() throws ApiException, SdkException, Exception
//	 {
//		 GetMyeBaySellingRequestType req;
//		 //GetMyeBaySellingRequestType req;
//		 if (this.myeBaySellingRequest != null)
//		 {
//			 req = this.myeBaySellingRequest;
//			 } else {
//			 req = new GetMyeBaySellingRequestType();
//			 if (this.scheduledList != null) {
//				 req.setScheduledList(this.scheduledList);
//			 }
//			 if (this.activeList != null) {
//				 req.setActiveList(this.activeList);
//			 }
//			 if (this.soldList != null) {
//				 req.setSoldList(this.soldList);
//			 }
//			 if (this.unsoldList != null) {
//				 req.setUnsoldList(this.unsoldList);
//			 }
//			 if (this.bidList != null) {
//				 req.setBidList(this.bidList);
//			 }
//			 if (this.deletedFromSoldList != null) {
//				 req.setDeletedFromSoldList(this.deletedFromSoldList);
//			 }
//			 if (this.deletedFromUnsoldList != null) {
//				 req.setDeletedFromUnsoldList(this.deletedFromUnsoldList);
//			 }
//			 if (this.sellingSummary != null) {
//				 req.setSellingSummary(this.sellingSummary);
//			 }
//			 if (this.hideVariations != null) {
//				 req.setHideVariations(this.hideVariations);
//				 }
//			 }
//		
//		 GetMyeBaySellingResponseType resp = (GetMyeBaySellingResponseType) execute(req);
//		 eyeBaySellingResponseType=resp;
//		 this.returnedSellingSummary = resp.getSellingSummary();
//		 this.returnedScheduledList = resp.getScheduledList();
//		 this.returnedActiveList = resp.getActiveList();
//		 this.returnedSoldList = resp.getSoldList();
//		 this.returnedUnsoldList = resp.getUnsoldList();
//		 this.returnedSummary = resp.getSummary();
//		 this.returnedBidList = resp.getBidList();
//		 this.returnedDeletedFromSoldList = resp.getDeletedFromSoldList();
//		 this.returnedDeletedFromUnsoldList = resp.getDeletedFromUnsoldList();
//		 }
//
//	
//	 public GetMyeBaySellingRequestType getMyeBaySellingRequest()
//	 {
//		 return this.myeBaySellingRequest;
//		 }
//
//	
//	 public void setMyeBaySellingRequest(GetMyeBaySellingRequestType myeBaySellingRequest)
//	 {
//		 this.myeBaySellingRequest = myeBaySellingRequest;
//		 }
//
//	
//	 public ItemListCustomizationType getActiveList()
//	 {
//		 return this.activeList;
//		 }
//
//	
//	 public void setActiveList(ItemListCustomizationType activeList)
//	 {
//		 this.activeList = activeList;
//		 }
//
//	
//	 public ItemListCustomizationType getBidList()
//	 {
//		 return this.bidList;
//		 }
//
//	
//	 public void setBidList(ItemListCustomizationType bidList)
//	 {
//		 this.bidList = bidList;
//		 }
//
//	
//	 public ItemListCustomizationType getDeletedFromSoldList()
//	 {
//		 return this.deletedFromSoldList;
//		 }
//
//	
//	 public void setDeletedFromSoldList(ItemListCustomizationType deletedFromSoldList)
//	 {
//		 this.deletedFromSoldList = deletedFromSoldList;
//		 }
//
//	
//	 public ItemListCustomizationType getDeletedFromUnsoldList()
//	 {
//		 return this.deletedFromUnsoldList;
//		 }
//
//	
//	 public void setDeletedFromUnsoldList(ItemListCustomizationType deletedFromUnsoldList)
//	 {
//		 this.deletedFromUnsoldList = deletedFromUnsoldList;
//		 }
//
//	
//	 public Boolean getHideVariations()
//	 {
//		 return this.hideVariations;
//		 }
//
//	
//	 public void setHideVariations(Boolean hideVariations)
//	 {
//		 this.hideVariations = hideVariations;
//		 }
//
//	
//	 public ItemListCustomizationType getScheduledList()
//	 {
//		 return this.scheduledList;
//		 }
//
//	
//	 public void setScheduledList(ItemListCustomizationType scheduledList)
//	 {
//		 this.scheduledList = scheduledList;
//		 }
//
//	
//	 public ItemListCustomizationType getSellingSummary()
//	 {
//		 return this.sellingSummary;
//		 }
//
//	
//	 public void setSellingSummary(ItemListCustomizationType sellingSummary)
//	 {
//		 this.sellingSummary = sellingSummary;
//		 }
//
//	
//	 public ItemListCustomizationType getSoldList()
//	 {
//		 return this.soldList;
//		 }
//
//	
//	 public void setSoldList(ItemListCustomizationType soldList)
//	 {
//		 this.soldList = soldList;
//		 }
//
//	
//	 public ItemListCustomizationType getUnsoldList()
//	 {
//		 return this.unsoldList;
//		 }
//
//	
//	 public void setUnsoldList(ItemListCustomizationType unsoldList)
//	 {
//		 this.unsoldList = unsoldList;
//		 }
//
//	
//	 public GetMyeBaySellingResponseType getReturnedMyeBaySellingResponse()
//	 {
//		 return ((GetMyeBaySellingResponseType) getResponseObject());
//		 }
//
//	
//	 public PaginatedItemArrayType getReturnedActiveList()
//	 {
//		 return this.returnedActiveList;
//		 }
//
//	
//	 public PaginatedItemArrayType getReturnedBidList()
//	 {
//		 return this.returnedBidList;
//		 }
//
//	
//	 public PaginatedOrderTransactionArrayType getReturnedDeletedFromSoldList()
//	 {
//		 return this.returnedDeletedFromSoldList;
//		 }
//
//	
//	 public PaginatedItemArrayType getReturnedDeletedFromUnsoldList()
//	 {
//		 return this.returnedDeletedFromUnsoldList;
//		 }
//
//	
//	 public PaginatedItemArrayType getReturnedScheduledList()
//	 {
//		 return this.returnedScheduledList;
//		 }
//
//	
//	 public SellingSummaryType getReturnedSellingSummary()
//	 {
//		 return this.returnedSellingSummary;
//		 }
//
//	
//	 public PaginatedOrderTransactionArrayType getReturnedSoldList()
//	 {
//		 return this.returnedSoldList;
//		 }
//
//	
//	 public MyeBaySellingSummaryType getReturnedSummary()
//	 {
//		 return this.returnedSummary;
//		 }
//
//	
//	 public PaginatedItemArrayType getReturnedUnsoldList()
//	 {
//		 return this.returnedUnsoldList;
//		 }
//}
