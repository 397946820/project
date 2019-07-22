//package com.it.ocs.synchronou.service.impl;
//
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Service;
//
//import com.ebay.sdk.ApiAccount;
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiCredential;
//import com.ebay.sdk.pictureservice.PictureInfo;
//import com.ebay.sdk.pictureservice.PictureService;
//import com.ebay.sdk.pictureservice.eps.eBayPictureServiceFactory;
//import com.ebay.soap.eBLBaseComponents.Base64BinaryType;
//import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
//import com.ebay.soap.eBLBaseComponents.PictureSetCodeType;
//import com.ebay.soap.eBLBaseComponents.SiteHostedPictureDetailsType;
//import com.ebay.soap.eBLBaseComponents.UploadSiteHostedPicturesRequestType;
//import com.ebay.soap.eBLBaseComponents.UploadSiteHostedPicturesResponseType;
//import com.it.ocs.pic.util.ImageUtil;
//import com.it.ocs.synchronou.mapping.UserIDToken;
//import com.it.ocs.synchronou.service.IEBayUploadSiteHostedPicturesService;
//@Service
//public class EBayUploadSiteHostedPicturesService implements IEBayUploadSiteHostedPicturesService{
//    private static final Logger logger = Logger.getLogger(EBayUploadSiteHostedPicturesService.class);
//	@Override
//	public SiteHostedPictureDetailsType uploadSitePictures(String url,String ebayAccount) {
//		try {
//			url = url.replace(" ", "%20");
//			return uploadPicture(url,ebayAccount);
//		} catch (Exception e) {
//			logger.error("上传图片失败", e);
//			return null;
//		}
//		
//	}
//	public static ApiContext getApiContext(String ebayAccount) throws Exception {  
//	    ApiContext apiContext = new ApiContext();  
//	    ApiAccount apiAccount = new ApiAccount();  
//	    String serverUrl = null;
//		String serverUrl2 = null;
//		if(ebayAccount.equals("testuser_yangguanbao")){
//			apiAccount.setApplication("yangguan-LedEbay-SBX-908fa563f-b2f96b11");  
//		    apiAccount.setCertificate("SBX-08fa563f4423-41b3-4ca9-a5df-6774");  
//		    apiAccount.setDeveloper("bb1ab1e0-0eb1-4466-b49b-2b6c51752020");
//			serverUrl = "https://api.sandbox.ebay.com/wsapi";
//			serverUrl2 = "https://api.sandbox.ebay.com/ws/api.dll";
//		}else{
//			apiAccount.setApplication("yangguan-LedEbay-PRD-9090b840d-c638c240");  
//		    apiAccount.setCertificate("PRD-090b840d76b4-f3a6-4719-be8d-e5f4");  
//		    apiAccount.setDeveloper("bb1ab1e0-0eb1-4466-b49b-2b6c51752020");
//			serverUrl = "https://api.ebay.com/wsapi";
//			serverUrl2 = "https://api.ebay.com/ws/api.dll";
//		}
//	    
//	    apiContext.getApiCredential().setApiAccount(apiAccount);  
//	    ApiCredential cred = apiContext.getApiCredential(); 
//	    String token = UserIDToken.searchTokenByUserID(ebayAccount);
//	    cred.seteBayToken(token);  
//	    apiContext.setApiServerUrl(serverUrl);  
//	    apiContext.setEpsServerUrl(serverUrl2);    
//	    return apiContext;  
//	} 
//	
//	public static SiteHostedPictureDetailsType uploadPicture(String paths,String ebayAccount) throws Exception {  
//		  SiteHostedPictureDetailsType result = new SiteHostedPictureDetailsType();
//		  ApiContext apiContext = getApiContext(ebayAccount);  
//	      PictureService picService = eBayPictureServiceFactory.getPictureService(apiContext);  
//	    
//	      //  PictureInfo picInfo = new PictureInfo();  
//	       // picInfo.setPictureFilePath(paths);  
//	        UploadSiteHostedPicturesRequestType request = new UploadSiteHostedPicturesRequestType();  
//	        request.setPictureSet(PictureSetCodeType.SUPERSIZE);
//	        DetailLevelCodeType[] detailLevelCodeTypes = new DetailLevelCodeType[1];
//	        detailLevelCodeTypes[0] =DetailLevelCodeType.RETURN_ALL;
//	        request.setDetailLevel(detailLevelCodeTypes);
//	        request.setExternalPictureURL(new String[]{paths});
//	      //  picService.UpLoadSiteHostedPicture(picInfo, request); 
//	        UploadSiteHostedPicturesResponseType uprt = picService.uploadSiteHostedPictures(request);
//	        result = uprt.getSiteHostedPictureDetails();
//	        return result;  
//	}  
//
//	
//}
