//package com.it.ocs.synchronou.call;
//
//import com.ebay.sdk.ApiCall;
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.sdk.call.VerifyAddItemCall;
//import com.ebay.soap.eBLBaseComponents.DiscountReasonCodeType;
//import com.ebay.soap.eBLBaseComponents.ExpressItemRequirementsType;
//import com.ebay.soap.eBLBaseComponents.ExternalProductIDType;
//import com.ebay.soap.eBLBaseComponents.FeesType;
//import com.ebay.soap.eBLBaseComponents.ItemType;
//import com.ebay.soap.eBLBaseComponents.ListingRecommendationsType;
//import com.ebay.soap.eBLBaseComponents.PhotoDisplayCodeType;
//import com.ebay.soap.eBLBaseComponents.PictureDetailsType;
//import com.ebay.soap.eBLBaseComponents.ProductSuggestionsType;
//import com.ebay.soap.eBLBaseComponents.VerifyAddItemRequestType;
//import com.ebay.soap.eBLBaseComponents.VerifyAddItemResponseType;
//
//public class EbayVerifyItemCall extends ApiCall{
//
//	 private ItemType item = null;
//	 private Boolean includeExpressRequirements = null;
//	 private ExternalProductIDType externalProductID = null;
//	 private String returnedItemID = null;
//	 private FeesType returnedFees = null;
//	 private Boolean returnedExpressListing = null;
//	 private ExpressItemRequirementsType returnedExpressItemRequirements = null;
//	 private String returnedCategoryID = null;
//	 private String returnedCategory2ID = null;
//	 private DiscountReasonCodeType[] returnedDiscountReason = null;
//	 private ProductSuggestionsType returnedProductSuggestions = null;
//	 private ListingRecommendationsType returnedListingRecommendations = null;
//	 private VerifyAddItemResponseType verifyAddItemResponseType=null;
//	
//	 public VerifyAddItemResponseType getVerifyAddItemResponseType() {
//		return verifyAddItemResponseType;
//	}
//
//
//	public void setVerifyAddItemResponseType(VerifyAddItemResponseType verifyAddItemResponseType) {
//		this.verifyAddItemResponseType = verifyAddItemResponseType;
//	}
//
//
//	private final String IMG_URL_FORMAT = "http://i0.ebayimg.ebay.com/i0/00/";
//	 private final String JPG_EXT = ".jpg";
//	
//	 private String[] pictureFiles = null;
//
//	
//	 public EbayVerifyItemCall()
//	 {
//		 }
//
//	
//	 public EbayVerifyItemCall(ApiContext apiContext)
//	 {
//		 super(apiContext);
//		 }
//
//	
//	 public FeesType verifyAddItem() throws ApiException, SdkException, Exception
//	 {
//		 VerifyAddItemRequestType req = new VerifyAddItemRequestType();
//		
//		 if (this.pictureFiles != null)
//		 {
//			 if (this.item.getPictureDetails() == null)
//			 {
//				 PictureDetailsType pictureDetails = new PictureDetailsType();
//				 pictureDetails.setPhotoDisplay(PhotoDisplayCodeType.NONE);
//				 this.item.setPictureDetails(pictureDetails);
//				 }
//			
//			 String[] uris = new String[this.pictureFiles.length];
//			 for (int i = 0; i < this.pictureFiles.length; ++i) {
//				 uris[i] = "http://i0.ebayimg.ebay.com/i0/00/" + new Integer(i).toString() + ".jpg";
//				 }
//			 this.item.getPictureDetails().setPictureURL(uris);
//			 }
//		 if (this.item != null) {
//			 req.setItem(this.item);
//		 }
//		 if (this.includeExpressRequirements != null) {
//			 req.setIncludeExpressRequirements(this.includeExpressRequirements);
//		 }
//		 if (this.externalProductID != null) {
//			 req.setExternalProductID(this.externalProductID);
//			 }
//		 VerifyAddItemResponseType resp = (VerifyAddItemResponseType) execute(req);
//		 setVerifyAddItemResponseType(resp);
//		 this.returnedItemID = resp.getItemID();
//		 this.returnedFees = resp.getFees();
//		 this.returnedExpressListing = resp.isExpressListing();
//		 this.returnedExpressItemRequirements = resp.getExpressItemRequirements();
//		 this.returnedCategoryID = resp.getCategoryID();
//		 this.returnedCategory2ID = resp.getCategory2ID();
//		 this.returnedDiscountReason = resp.getDiscountReason();
//		 this.returnedProductSuggestions = resp.getProductSuggestions();
//		 this.returnedListingRecommendations = resp.getListingRecommendations();
//		 return getReturnedFees();
//		 }
//
//	
//	 public ExternalProductIDType getExternalProductID()
//	 {
//		 return this.externalProductID;
//		 }
//
//	
//	 public void setExternalProductID(ExternalProductIDType externalProductID)
//	 {
//		 this.externalProductID = externalProductID;
//		 }
//
//	
//	 public Boolean getIncludeExpressRequirements()
//	 {
//		 return this.includeExpressRequirements;
//		 }
//
//	
//	 public void setIncludeExpressRequirements(Boolean includeExpressRequirements)
//	 {
//		 this.includeExpressRequirements = includeExpressRequirements;
//		 }
//
//	
//	 public ItemType getItem()
//	 {
//		 return this.item;
//		 }
//
//	
//	 public void setItem(ItemType item)
//	 {
//		 this.item = item;
//		 }
//
//	
//	 public String[] getPictureFiles()
//	 {
//		 return this.pictureFiles;
//		 }
//
//	
//	 public void setPictureFiles(String[] pictureFiles)
//	 {
//		 this.pictureFiles = pictureFiles;
//		 }
//
//	
//	 public FeesType verifyAddItem(ItemType item) throws ApiException, SdkException, Exception
//	 {
//		 VerifyAddItemRequestType request = new VerifyAddItemRequestType();
//		 request.setItem(item);
//		
//		 if (this.pictureFiles != null)
//		 {
//			 if (item.getPictureDetails() == null)
//			 {
//				 PictureDetailsType pictureDetails = new PictureDetailsType();
//				 pictureDetails.setPhotoDisplay(PhotoDisplayCodeType.NONE);
//				 item.setPictureDetails(pictureDetails);
//				 }
//			
//			 String[] uris = new String[this.pictureFiles.length];
//			 for (int i = 0; i < this.pictureFiles.length; ++i) {
//				 uris[i] = "http://i0.ebayimg.ebay.com/i0/00/" + new Integer(i).toString() + ".jpg";
//				 }
//			 item.getPictureDetails().setPictureURL(uris);
//			 }
//		
//		 VerifyAddItemResponseType resp = (VerifyAddItemResponseType) execute(request);
//		 setVerifyAddItemResponseType(resp);
//		 return resp.getFees();
//		 }
//
//	
//	 public String getReturnedCategory2ID()
//	 {
//		 return this.returnedCategory2ID;
//		 }
//
//	
//	 public String getReturnedCategoryID()
//	 {
//		 return this.returnedCategoryID;
//		 }
//
//	
//	 public DiscountReasonCodeType[] getReturnedDiscountReason()
//	 {
//		 return this.returnedDiscountReason;
//		 }
//
//	
//	 public ExpressItemRequirementsType getReturnedExpressItemRequirements()
//	 {
//		 return this.returnedExpressItemRequirements;
//		 }
//
//	
//	 public Boolean getReturnedExpressListing()
//	 {
//		 return this.returnedExpressListing;
//		 }
//
//	
//	 public FeesType getReturnedFees()
//	 {
//		 return this.returnedFees;
//		 }
//
//	
//	 public String getReturnedItemID()
//	 {
//		 return this.returnedItemID;
//		 }
//
//	
//	 public ListingRecommendationsType getReturnedListingRecommendations()
//	 {
//		 return this.returnedListingRecommendations;
//		 }
//
//	
//	 public ProductSuggestionsType getReturnedProductSuggestions()
//	 {
//		 return this.returnedProductSuggestions;
//		 }
//}
