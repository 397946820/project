//package com.it.ocs.synchronou.call;
//
//import java.util.Calendar;
//
//import com.ebay.sdk.ApiCall;
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.sdk.pictureservice.PictureInfo;
//import com.ebay.sdk.pictureservice.PictureService;
//import com.ebay.sdk.pictureservice.eps.eBayPictureServiceFactory;
//import com.ebay.soap.eBLBaseComponents.DiscountReasonCodeType;
//import com.ebay.soap.eBLBaseComponents.FeesType;
//import com.ebay.soap.eBLBaseComponents.GalleryTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.ItemType;
//import com.ebay.soap.eBLBaseComponents.ListingDetailsType;
//import com.ebay.soap.eBLBaseComponents.ListingRecommendationsType;
//import com.ebay.soap.eBLBaseComponents.PhotoDisplayCodeType;
//import com.ebay.soap.eBLBaseComponents.PictureDetailsType;
//import com.ebay.soap.eBLBaseComponents.PictureSourceCodeType;
//import com.ebay.soap.eBLBaseComponents.ProductSuggestionsType;
//import com.ebay.soap.eBLBaseComponents.RelistItemRequestType;
//import com.ebay.soap.eBLBaseComponents.RelistItemResponseType;
//
//public class EBayRelistItemCall extends ApiCall {
//	 private ItemType itemToBeRelisted = null;
//	 private String[] deletedField = null;
//	 private String returnedItemID = null;
//	 private FeesType listingFees = null;
//	 private Calendar returnedStartTime = null;
//	 private Calendar returnedEndTime = null;
//	 private String returnedCategoryID = null;
//	 private String returnedCategory2ID = null;
//	 private DiscountReasonCodeType[] returnedDiscountReason = null;
//	 private ProductSuggestionsType returnedProductSuggestions = null;
//	 private ListingRecommendationsType returnedListingRecommendations = null;
//	 private PictureService pictureService;
//	 private RelistItemResponseType relistItemResponseType;
//	 
//	
//	 public RelistItemResponseType getRelistItemResponseType() {
//		return relistItemResponseType;
//	}
//
//
//	public void setRelistItemResponseType(RelistItemResponseType relistItemResponseType) {
//		this.relistItemResponseType = relistItemResponseType;
//	}
//
//
//	public EBayRelistItemCall()
//	 {
//		 }
//
//	
//	 public EBayRelistItemCall(ApiContext apiContext)
//	 {
//		 super(apiContext);
//		
//		 this.pictureService = eBayPictureServiceFactory.getPictureService(apiContext);
//		 }
//
//	
//	 public FeesType relistItem() throws ApiException, SdkException, Exception
//	 {
//		 RelistItemRequestType req = new RelistItemRequestType();
//		
//		 if (this.itemToBeRelisted == null) {
//			 throw new SdkException("ItemToBeRelisted property is not set.");
//		 }
//		 String origItemID = this.itemToBeRelisted.getItemID();
//		 req.setItem(this.itemToBeRelisted);
//		 if (this.itemToBeRelisted != null) {
//			 req.setItem(this.itemToBeRelisted);
//		 }
//		 if (this.deletedField != null) {
//			 req.setDeletedField(this.deletedField);
//			 }
//		 RelistItemResponseType resp = (RelistItemResponseType) execute(req);
//		 relistItemResponseType=resp;
//		 this.returnedItemID = resp.getItemID();
//		 this.listingFees = resp.getFees();
//		 this.returnedStartTime = resp.getStartTime();
//		 this.returnedEndTime = resp.getEndTime();
//		 this.returnedCategoryID = resp.getCategoryID();
//		 this.returnedCategory2ID = resp.getCategory2ID();
//		 this.returnedDiscountReason = resp.getDiscountReason();
//		 this.returnedProductSuggestions = resp.getProductSuggestions();
//		 this.returnedListingRecommendations = resp.getListingRecommendations();
//		 if (this.itemToBeRelisted.getListingDetails() == null) {
//			 this.itemToBeRelisted.setListingDetails(new ListingDetailsType());
//		 }
//		 this.itemToBeRelisted.getListingDetails().setRelistedItemID(origItemID);
//		
//		 this.itemToBeRelisted.setItemID(resp.getItemID());
//		 return getListingFees();
//		 }
//
//	
//	 public String[] getDeletedField()
//	 {
//		 return this.deletedField;
//		 }
//
//	
//	 public void setDeletedField(String[] deletedField)
//	 {
//		 this.deletedField = deletedField;
//		 }
//
//	
//	 public ItemType getItemToBeRelisted()
//	 {
//		 return this.itemToBeRelisted;
//		 }
//
//	
//	 public void setItemToBeRelisted(ItemType itemToBeRelisted)
//	 {
//		 this.itemToBeRelisted = itemToBeRelisted;
//		 }
//
//	
//	 public void uploadPictures(String[] pictureFiles, PictureDetailsType pictureDetails) throws SdkException {
//		 if (pictureFiles == null) {
//			 return;
//			 }
//		 PictureInfo[] pictureInfoArray = new PictureInfo[pictureFiles.length];
//		 for (int i = 0; i < pictureFiles.length; ++i) {
//			 pictureInfoArray[i] = new PictureInfo();
//			 pictureInfoArray[i].setPictureFilePath(pictureFiles[i]);
//			 }
//		 uploadPictures(pictureInfoArray, pictureDetails);
//		 }
//
//	
//	 public void uploadPictures(PictureInfo[] pictureInfoArray, PictureDetailsType pictureDetails)
//			throws SdkException {
//		 String epsUrl = getApiContext().getEpsServerUrl();
//		 if ((pictureInfoArray == null) || (epsUrl == null)) {
//			 return;
//			 }
//		 if (pictureDetails == null) {
//			 pictureDetails = new PictureDetailsType();
//			 }
//		 if (pictureDetails.getPhotoDisplay() == null) {
//			 pictureDetails.setPhotoDisplay(PhotoDisplayCodeType.NONE);
//			 }
//		 if (pictureDetails.getGalleryType() == null) {
//			 pictureDetails.setGalleryType(GalleryTypeCodeType.NONE);
//			 }
//		 if (pictureDetails.getPictureSource() == null) {
//			 pictureDetails.setPictureSource(PictureSourceCodeType.VENDOR);
//			 }
//		 this.itemToBeRelisted.setPictureDetails(pictureDetails);
//		
//		 int uploads = this.pictureService.uploadPictures(pictureDetails.getPhotoDisplay(), pictureInfoArray);
//		 if (uploads != pictureInfoArray.length)
//		 {
//			 StringBuffer sb = new StringBuffer();
//			 for (int i = 0; i < pictureInfoArray.length; ++i) {
//				 if (pictureInfoArray[i].hasError()) {
//					 sb.append(
//							pictureInfoArray[i].getErrorType() + " : " + pictureInfoArray[i].getErrorMessage() + "\n");
//					 }
//				 }
//			 throw new SdkException(sb.toString());
//			 }
//		
//		 String[] uris = new String[pictureInfoArray.length];
//		 for (int i = 0; i < pictureInfoArray.length; ++i) {
//			 uris[i] = pictureInfoArray[i].getURL();
//			 }
//		 this.itemToBeRelisted.getPictureDetails().setPictureURL(uris);
//		 }
//
//	
//	 public FeesType getListingFees()
//	 {
//		 return this.listingFees;
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
//	 public Calendar getReturnedEndTime()
//	 {
//		 return this.returnedEndTime;
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
//
//	
//	 public Calendar getReturnedStartTime()
//	 {
//		 return this.returnedStartTime;
//		 }
//}
