//package com.it.ocs.synchronou.call;
//
//import java.util.Calendar;
//import java.util.UUID;
//
//import com.ebay.sdk.ApiCall;
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.sdk.pictureservice.PictureInfo;
//import com.ebay.sdk.pictureservice.PictureService;
//import com.ebay.sdk.pictureservice.eps.eBayPictureServiceFactory;
//import com.ebay.soap.eBLBaseComponents.AddItemRequestType;
//import com.ebay.soap.eBLBaseComponents.AddItemResponseType;
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
//
//public class EbayAddItemCall extends ApiCall {
//	 private ItemType item = null;
//	 private String returnedItemID = null;
//	 private Calendar returnedStartTime = null;
//	 private Calendar returnedEndTime = null;
//	 private FeesType returnedFees = null;
//	 private String returnedCategoryID = null;
//	 private String returnedCategory2ID = null;
//	 private DiscountReasonCodeType[] returnedDiscountReason = null;
//	 private ProductSuggestionsType returnedProductSuggestions = null;
//	 private ListingRecommendationsType returnedListingRecommendations = null;
//	 private PictureService pictureService;
//	 private String[] pictureFiles = null;
//	 private boolean autoSetItemUUID = false;
//	 private AddItemResponseType addItemResponseType;
//
//	
//	 public AddItemResponseType getAddItemResponseType() {
//		return addItemResponseType;
//	}
//
//
//	public void setAddItemResponseType(AddItemResponseType addItemResponseType) {
//		this.addItemResponseType = addItemResponseType;
//	}
//
//
//	public EbayAddItemCall()
//	 {
//		 }
//
//	
//	 public EbayAddItemCall(ApiContext apiContext)
//	 {
//		 super(apiContext);
//		
//		 this.pictureService = eBayPictureServiceFactory.getPictureService(apiContext);
//		 }
//
//	
//	 public FeesType addItem() throws ApiException, SdkException, Exception
//	 {
//		 AddItemRequestType req = new AddItemRequestType();
//		
//		 if ((this.autoSetItemUUID) && (this.item.getUUID() == null)) {
//			 resetItemUUID(this.item);
//			 }
//		
//		 String epsUrl = getApiContext().getEpsServerUrl();
//		 if ((this.pictureFiles != null) && (epsUrl != null)) {
//			 PictureDetailsType pictureDetails = this.item.getPictureDetails();
//			 if (pictureDetails == null) {
//				 pictureDetails = new PictureDetailsType();
//				 }
//			 if (pictureDetails.getPhotoDisplay() == null) {
//				 pictureDetails.setPhotoDisplay(PhotoDisplayCodeType.NONE);
//				 }
//			 if (pictureDetails.getGalleryType() == null) {
//				 pictureDetails.setGalleryType(GalleryTypeCodeType.NONE);
//				 }
//			 if (pictureDetails.getPictureSource() == null) {
//				 pictureDetails.setPictureSource(PictureSourceCodeType.VENDOR);
//				 }
//			 this.item.setPictureDetails(pictureDetails);
//			 PhotoDisplayCodeType photoDisplay = this.item.getPictureDetails().getPhotoDisplay();
//			
//			 PictureInfo[] piList = new PictureInfo[this.pictureFiles.length];
//			 for (int i = 0; i < this.pictureFiles.length; ++i) {
//				 piList[i] = new PictureInfo();
//				 piList[i].setPictureFilePath(this.pictureFiles[i]);
//				 }
//			 int uploads = this.pictureService.uploadPictures(photoDisplay, piList);
//			 if (uploads != piList.length)
//			 {
//				 StringBuffer sb = new StringBuffer();
//				 for (int i = 0; i < piList.length; ++i) {
//					 if (piList[i].hasError()) {
//						 sb.append(piList[i].getErrorType() + " : " + piList[i].getErrorMessage() + "\n");
//						 }
//					 }
//				 throw new SdkException(sb.toString());
//				 }
//			
//			 String[] uris = new String[piList.length];
//			 for (int i = 0; i < piList.length; ++i) {
//				 uris[i] = piList[i].getURL();
//				 }
//			 this.item.getPictureDetails().setPictureURL(uris);
//			 }
//		
//		 if (this.item != null) {
//			 req.setItem(this.item);
//			 }
//		 AddItemResponseType resp = (AddItemResponseType) execute(req);
//		 addItemResponseType = resp;
//		 this.returnedItemID = resp.getItemID();
//		 this.returnedStartTime = resp.getStartTime();
//		 this.returnedEndTime = resp.getEndTime();
//		 this.returnedFees = resp.getFees();
//		 this.returnedCategoryID = resp.getCategoryID();
//		 this.returnedCategory2ID = resp.getCategory2ID();
//		 this.returnedDiscountReason = resp.getDiscountReason();
//		 this.returnedProductSuggestions = resp.getProductSuggestions();
//		 this.returnedListingRecommendations = resp.getListingRecommendations();
//		 this.item.setItemID(resp.getItemID());
//		 if (this.item.getListingDetails() == null) {
//			 this.item.setListingDetails(new ListingDetailsType());
//		 }
//		 if (resp.getStartTime() != null) {
//			 this.item.getListingDetails().setStartTime(resp.getStartTime());
//		 }
//		 if (resp.getEndTime() != null) {
//			 this.item.getListingDetails().setEndTime(resp.getEndTime());
//		 }
//		 return getReturnedFees();
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
//	 public boolean getAutoSetItemUUID()
//	 {
//		 return this.autoSetItemUUID;
//		 }
//
//	
//	 public String[] getPictureFiles()
//	 {
//		 return this.pictureFiles;
//		 }
//
//	
//	 public PictureService getPictureService()
//	 {
//		 return this.pictureService;
//		 }
//
//	
//	 public static String newUUID()
//	 {
//		 String uuid = UUID.randomUUID().toString();
//		
//		 StringBuffer goodUuid = new StringBuffer();
//		 for (int i = 0; i < uuid.length(); ++i) {
//			 char c = uuid.charAt(i);
//			 if (c != '-') {
//				 goodUuid.append(c);
//				 }
//			 }
//		
//		 return goodUuid.toString();
//		 }
//
//	
//	 public static void resetItemUUID(ItemType item)
//	 {
//		 item.setUUID(newUUID());
//		 }
//
//	
//	 public void setAutoSetItemUUID(boolean autoSetItemUUID)
//	 {
//		 this.autoSetItemUUID = autoSetItemUUID;
//		 }
//
//	
//	 public void setPictureFiles(String[] pictureFiles)
//	 {
//		 this.pictureFiles = pictureFiles;
//		 }
//
//	
//	 public void setPictureService(PictureService pictureService)
//	 {
//		 this.pictureService = pictureService;
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
//
//	
//	 public Calendar getReturnedStartTime()
//	 {
//		 return this.returnedStartTime;
//		 }
//}
