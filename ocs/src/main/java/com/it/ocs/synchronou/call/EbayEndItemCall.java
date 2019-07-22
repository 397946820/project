//package com.it.ocs.synchronou.call;
//
//import java.util.Calendar;
//
//import com.ebay.sdk.ApiCall;
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.soap.eBLBaseComponents.EndItemRequestType;
//import com.ebay.soap.eBLBaseComponents.EndItemResponseType;
//import com.ebay.soap.eBLBaseComponents.EndReasonCodeType;
//
//public class EbayEndItemCall extends ApiCall {
//	 private String itemID = null;
//	 private EndReasonCodeType endingReason = null;
//	 private String sellerInventoryID = null;
//	 private Calendar returnedEndTime = null;
//	 private EndItemResponseType endItemResponseType = null;
//	 
//	
//	 public void setReturnedEndTime(Calendar returnedEndTime) {
//		this.returnedEndTime = returnedEndTime;
//	}
//
//
//	public EndItemResponseType getEndItemResponseType() {
//		return endItemResponseType;
//	}
//
//
//	public void setEndItemResponseType(EndItemResponseType endItemResponseType) {
//		this.endItemResponseType = endItemResponseType;
//	}
//
//
//	public EbayEndItemCall()
//	 {
//		 }
//
//	
//	 public EbayEndItemCall(ApiContext apiContext)
//	 {
//		 super(apiContext);
//		 }
//
//	
//	 public Calendar endItem() throws ApiException, SdkException, Exception
//	 {
//		 EndItemRequestType req = new EndItemRequestType();
//		
//		 if ((this.itemID == null) && (this.sellerInventoryID == null)) {
//			 throw new SdkException("Please set ItemID or sellerInventoryID (for Half items) to be ended.");
//		 }
//		 if ((this.itemID != null) && (this.sellerInventoryID != null)) {
//			 throw new SdkException(
//					"Please set either an ItemID or a sellerInventoryID (for Half items) to be ended.");
//			 }
//		 if (this.itemID != null) {
//			 req.setItemID(this.itemID);
//		 }
//		 if (this.endingReason != null) {
//			 req.setEndingReason(this.endingReason);
//		 }
//		 if (this.sellerInventoryID != null) {
//			 req.setSellerInventoryID(this.sellerInventoryID);
//			 }
//		 EndItemResponseType resp = (EndItemResponseType) execute(req);
//		 endItemResponseType =resp;
//		 this.returnedEndTime = resp.getEndTime();
//		 return getReturnedEndTime();
//		 }
//
//	
//	 public EndReasonCodeType getEndingReason()
//	 {
//		 return this.endingReason;
//		 }
//
//	
//	 public void setEndingReason(EndReasonCodeType endingReason)
//	 {
//		 this.endingReason = endingReason;
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
//	 public String getSellerInventoryID()
//	 {
//		 return this.sellerInventoryID;
//		 }
//
//	
//	 public void setSellerInventoryID(String sellerInventoryID)
//	 {
//		 this.sellerInventoryID = sellerInventoryID;
//		 }
//
//	
//	 public Calendar getReturnedEndTime()
//	 {
//		 return this.returnedEndTime;
//		 }
//}
