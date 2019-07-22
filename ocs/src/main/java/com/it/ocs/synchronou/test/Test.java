//package com.it.ocs.synchronou.test;
//
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.core.Conventions;
//
//import com.ebay.sdk.ApiAccount;
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiCredential;
//import com.ebay.sdk.call.AddItemCall;
//import com.ebay.sdk.pictureservice.PictureInfo;
//import com.ebay.sdk.pictureservice.PictureService;
//import com.ebay.sdk.pictureservice.eps.eBayPictureServiceFactory;
//import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
//import com.ebay.soap.eBLBaseComponents.PictureSetCodeType;
//import com.ebay.soap.eBLBaseComponents.SiteHostedPictureDetailsType;
//import com.ebay.soap.eBLBaseComponents.StoreCustomListingHeaderLinkType;
//import com.ebay.soap.eBLBaseComponents.UploadSiteHostedPicturesRequestType;
//import com.ebay.soap.eBLBaseComponents.UploadSiteHostedPicturesResponseType;
//import com.it.ocs.cal.utils.Tools;
//import com.it.ocs.salesStatistics.utils.TimeTools;
//import com.it.ocs.synchronou.mapping.UserIDToken;
//import com.it.ocs.synchronou.model.EbayAccountModel;
//import com.it.ocs.synchronou.service.impl.BaseEbaySDKService;
//import com.it.ocs.synchronou.service.impl.BaseHttpsService;
//import com.it.ocs.synchronou.util.ConversionDateUtil;
//import com.it.ocs.synchronou.util.ServerUrl;
//import com.paypal.api.payments.Payout;
//import com.paypal.api.payments.PayoutBatch;
//import com.paypal.base.rest.APIContext;
//import com.paypal.core.credential.TokenAuthorization;
//
//import net.sf.json.JSON;
//import net.sf.json.JSONObject;
//
//public class Test  {
//    private static String appID="yangguan-LedEbay-SBX-908fa563f-b2f96b11";  
//    private static String devID="bb1ab1e0-0eb1-4466-b49b-2b6c51752020";  
//    private static String cert="SBX-08fa563f4423-41b3-4ca9-a5df-6774";  
//    private static String ruName="yang_guanbao-yangguan-LedEba-iofhpppe";  
//    private static String serverUrl="https://api.sandbox.ebay.com/wsapi"; 
//    
//    public SiteHostedPictureDetailsType uploadSitePictures(String url,String ebayAccount) {
//		// TODO Auto-generated method stub
//
//		try {
//			return uploadPicture(url,ebayAccount);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//		
//	}
//	public static ApiContext getApiContext(String ebayAccount) throws Exception {  
//	    ApiContext apiContext = new ApiContext();  
//	    ApiAccount apiAccount = new ApiAccount();  
//	    /*apiAccount.setApplication("yangguan-LedEbay-SBX-908fa563f-b2f96b11");  
//	    apiAccount.setCertificate("SBX-08fa563f4423-41b3-4ca9-a5df-6774");  
//	    apiAccount.setDeveloper("bb1ab1e0-0eb1-4466-b49b-2b6c51752020");*/
//	    apiAccount.setApplication("yangguan-LedEbay-PRD-9090b840d-c638c240");  
//	    apiAccount.setCertificate("PRD-090b840d76b4-f3a6-4719-be8d-e5f4");  
//	    apiAccount.setDeveloper("bb1ab1e0-0eb1-4466-b49b-2b6c51752020");
//	    apiContext.getApiCredential().setApiAccount(apiAccount);  
//	    ApiCredential cred = apiContext.getApiCredential(); 
//	    
//	    
//	    //EbayAccountModel ebayAccountModel = eBayAccounService.getAccountModelByName(ebayAccount);
//	    String token = "AgAAAA**AQAAAA**aAAAAA**u1RjWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**Xq8DAA**AAMAAA**nzc7lCXNjRxjfZiTVjNJ6hYFYlZxK+5R6x7rr5WSGrTGJTjr3ykfT4YvBuhdDheZSpftIpfgfQMA//5Y1AhRHhckFpPnVU+7t2R7pViACV9wyiDGh2UjZ/XRuh1v5dr6zqUQN8AIo47gJE+AXeeehIC7G77E0DY2/0p+IxIWCtqP9MNjAfMIPWYsOhX4OnqMQgBIQdcMZZ5/3PoQRj/0ELiTn3RqltZq/T9BnyNv14qXBfXcUiijizx2a0OZBv1dfIrog7/ohP/sqBgvgyl31Ebi78Qp/TgIZtjP9r44tbw7kmsk/X1jtjeeXYfKZ+JelJlYZLZX4DTCCobYAZ7E5sbPI7frJFyRBsm7gxV5sZeBPdwDC5hB9pOTQpM27CdOsz2e089ughAEQw8H9ZV+DXUR4Aw3BNr40QwAkh34dBDF7UFo6JU8bWYTeweFT2vAdxUhvEfFjb5FRMcU5A9LK6SUkcoXP5f2DOD9nloZQOw6X6rczVsDeXd2dCPGTK+vpf82gjY+NWbVanPjICAEwFQZqPQe2OesAcbbK5ztYmeftzAvIEoWR71fHMrKDNrVeEFzzP9a9Pdl/C1UZ5Ys/xlLU07BnqL/idcx0+2WdcW3ZKpwMD7M6NobbqAuHBE7pvezSZ1DAVl+h5mrEURUQdOUMCRV5nKNkOYooeORqtKVclyNAOMPnvJwgn1sr/gdLuzoYtgsREiKBv1wJmni2YwcU80d+uLmCk41nqG3n8unVN0CURR+8g5BGSNFVvl1";
//	    ebayAccount="k";
//	    String serverUrl = null;
//		String serverUrl2 = null;
//		if(ebayAccount.equals("testuser_yangguanbao")){
//			serverUrl = "https://api.sandbox.ebay.com/wsapi";
//			serverUrl2 = "https://api.sandbox.ebay.com/ws/api.dll";
//		}else{
//			serverUrl = "https://api.ebay.com/wsapi";
//			serverUrl2 = "https://api.ebay.com/ws/api.dll";
//		}
//	    cred.seteBayToken(token);  
//	    apiContext.setApiServerUrl(serverUrl);  
//	    apiContext.setEpsServerUrl(serverUrl2);    // 这个要设置，不然会出错  
//	    /*apiContext.setApiServerUrl("https://api.ebay.com/wsapi"); 
//	    apiContext.setEpsServerUrl("https://api.ebay.com/ws/api.dll");*/
//	    return apiContext;  
//	} 
//	
//	public static SiteHostedPictureDetailsType uploadPicture(String paths,String ebayAccount) throws Exception {  
//	    ApiContext apiContext = getApiContext(ebayAccount);  
//	    PictureService picService = eBayPictureServiceFactory.getPictureService(apiContext);  
//	    
//	      //  PictureInfo picInfo = new PictureInfo();  
//	       // picInfo.setPictureFilePath(paths);  
//	        UploadSiteHostedPicturesRequestType request = new UploadSiteHostedPicturesRequestType();  
//	        request.setPictureSet(PictureSetCodeType.SUPERSIZE);
//	        DetailLevelCodeType[] detailLevelCodeTypes = new DetailLevelCodeType[1];
//	        detailLevelCodeTypes[0] =DetailLevelCodeType.RETURN_ALL;
//	        request.setDetailLevel(detailLevelCodeTypes);
//	        request.setExternalPictureURL(new String[]{"https://img.alicdn.com/tfs/TB185hrbVOWBuNjy0FiXXXFxVXa-520-280.jpg"});
//	      //  picService.UpLoadSiteHostedPicture(picInfo, request); 
//	        picService.uploadSiteHostedPictures(request);
//	       // UploadSiteHostedPicturesResponseType responseType =  picInfo.getReponse();
//	       // System.out.println(picInfo.getURL());
//	       // SiteHostedPictureDetailsType result = responseType.getSiteHostedPictureDetails();
//
//	    return null;  
//	}  
//    public static void main(String[] args) {
//    	try {
//			uploadPicture("D:\\images\\eBay.jpg","uk.le");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	/*String token = UserIDToken.searchTokenByUserID("uk.le");
//    	String serverUrl = ServerUrl.getServerUrl("uk.le");
//    	ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//    	UploadSiteHostedPicturesRequestType request = new UploadSiteHostedPicturesRequestType();
//    	PictureInfo picInfo = new PictureInfo(); 
//    	request.setPictureSet(PictureSetCodeType.SUPERSIZE);
//        DetailLevelCodeType[] detailLevelCodeTypes = new DetailLevelCodeType[1];
//        detailLevelCodeTypes[0] =DetailLevelCodeType.RETURN_ALL;
//        request.setDetailLevel(detailLevelCodeTypes);
//    	PictureService picService = eBayPictureServiceFactory.getPictureService(apiContext);  
//        picInfo.setPictureFilePath("https://www.baidu.com/img/bd_logo1.png");
//    	request.setExternalPictureURL(new String[]{"https://www.baidu.com/img/bd_logo1.png"});
//    	picService.UpLoadSiteHostedPicture(picInfo, request); */
//    	/*String clientId = "AQ45-O2pVl6GASJVnhY0ARJsBdLKv40q3ir4kamiXnmeVhLNtvUl9V9mBTS--3qmmAC9mnq83QjEqiiU";
//    	String secret = "EBeOySjvwdPKleAsGkg7iDtGHwVA3iM10PiavETQQvuBGjWWDIsChPlIQftdmnWBP3p0ar3CTc8mNFKC";
//    	Calendar calendar = Calendar.getInstance();
//    	System.out.println(calendar.getTime());
//    	String dateStr = ConversionDateUtil.dateToCharFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
//    	String usDate = TimeTools.timeConvert(dateStr, calendar.getTimeZone().getID(), "Europe/London");
//    	System.out.println(usDate);
//    	String[] zoneId = calendar.getTimeZone().getAvailableIDs();
//    	for (String string : zoneId) {
//			System.out.println(string);
//		}*/
//    	//退款
//		/*RefundRequest refundRequest = new RefundRequest();
//		APIContext apiContext = new APIContext(clientId, secret, "");
//		try {
//			Capture capture = Capture.get(apiContext, "19R42820L9965124R");
//			Amount amount = new Amount("USD", "100");
//			refundRequest.setAmount(amount);
//			Refund refund = capture.refund(apiContext, refundRequest);
//		} catch (PayPalRESTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
// 
//        
//
//    
//    }
//   
//  /*  public static void main(String[] args) {
//		String json = "{"
//				+"    'variations': ["
//				+"       {"
//				+"            'SKU': '34000331-red-10w',"
//				+"            'StartPrice': '22',"
//				+"            'Quantity': '50',"
//				+"            'NameValueList': ["
//				+"                {"
//				+"                    'Name': 'color',"
//				+"                    'Value': 'red'"
//				+"                },"
//				+"                {"
//				+"                    'Name': 'size',"
//				+"                    'Value': '10W'"
//				+"                }"
//				+"            ]"
//				+"        },"
//				+"        {"
//				+"            'SKU': '34000331-yellow-10w',"
//				+"            'StartPrice': '23',"
//				+"            'Quantity': '50',"
//				+"            'NameValueList': ["
//				+"                {"
//				+"                    'Name': 'color',"
//				 +"                   'Value': 'yellow'"
//				+"                },"
//				+"                {"
//				+"                    'Name': 'size',"
//				 +"                   'Value': '10W'"
//				 +"               }"
//				 +"           ]"
//				 +"       },"
//				 +"       {"
//				 +"           'SKU': '34000331-red-12w',"
//				 +"           'StartPrice': '24',"
//				 +"           'Quantity': '50',"
//				 +"           'NameValueList': ["
//				 +"               {"
//				 +"                   'Name': 'color',"
//				 +"                   'Value': 'red'"
//				 +"               },"
//				 +"               {"
//				 +"                   'Name': 'size',"
//				 +"                   'Value': '12W'"
//				 +"               }"
//				 +"           ]"
//				 +"       },"
//				 +"       {"
//				 +"           'SKU': '34000331-yellow-12w',"
//				 +"           'StartPrice': '25',"
//				 +"           'Quantity': '50',"
//				 +"           'NameValueList': ["
//				 +"               {"
//				 +"                   'Name': 'color',"
//				 +"                   'Value': 'yellow'"
//				 +"               },"
//				  +"              {"
//				 +"                   'Name': 'size',"
//				 +"                   'Value': '12W'"
//				 +"               }"
//				 +"           ]"
//				 +"       }"
//				 +"   ],"
//				 +"   'pictures': ["
//				 +"       {"
//				 +"           'VariationSpecificName': 'color',"
//				 +"           'VariationSpecificValue': 'red',"
//				 +"           'PictureURL':'http://i.ebayimg.com/t/TEST-BestOffer-RH1707-1-/00/s/MTAwMFgxMDAw/z/6qYAAOSwal5YGj~s/$_12.JPG'"
//				 +"       },"
//				 +"       {"
//				 +"           'VariationSpecificName': 'color',"
//				 +"           'VariationSpecificValue': 'yellow',"
//				+"            'PictureURL': 'http://i.ebayimg.com/t/TEST-BestOffer-RH1707-1-/00/s/MTUwMFgxNTAw/z/VRsAAOSwA3dYGj~h/$_12.JPG'"
//				 +"       }"
//				+"    ],"
//				 +"   'variationSpecificsSet': ["
//				 +"       {"
//				 +"           'Name': 'color',"
//				+"            'Values': ["
//				+"                'red',"
//				+"                'yellow'"
//				+"            ]"
//				 +"       },"
//				 +"       {"
//				+"            'Name': 'size',"
//				+"            'Values': ["
//				+"                '10W',"
//				 +"               '12W'"
//				 +"           ]"
//				 +"       }"
//				 +"   ]"
//				+"}";
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		JSONArray jsonArray = jsonObject.getJSONArray("variations");
//		JSONArray jsonArray2 = new JSONArray();
//		for (Object object : jsonArray) {
//			JSONObject jsonObject2 = JSONObject.fromObject(object);
//			System.out.println(jsonObject2.get("SKU"));
//			jsonObject2.put("Quantity", "0");
//			object=jsonObject2.toString();
//			
//			
//			jsonArray2.add(object);
//		}
//		System.out.println(jsonArray2.toString());
//		System.out.println(jsonArray.toString());
//	}
//    public static void main(String[] args) {
//    	try {
//    		StringBuilder text = new StringBuilder();
//			URL realUrl = new URL("https://www.1688.com/");
//			URLConnection connection = realUrl.openConnection();
//			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			String inputLine = null;
//			while ( (inputLine = in.readLine()) != null) {		  
//				text.append(inputLine+"\n");	  
//			}
//			System.out.println(text.toString());
//			in.close();
//    	} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    	
//    	String serverUrl = "https://api.ebay.com/wsapi";
//    	String token = UserIDToken.searchTokenByUserID("lightingever01");
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		GetUserPreferencesCall getUserPreferencesCall = new GetUserPreferencesCall(apiContext);
//		getUserPreferencesCall.setShowBidderNoticePreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShowCombinedPaymentPreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShowDispatchCutoffTimePreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShoweBayPLUSPreference(Boolean.TRUE);
//		getUserPreferencesCall.setShowEmailShipmentTrackingNumberPreference(Boolean.TRUE);
//		getUserPreferencesCall.setShowEndOfAuctionEmailPreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShowGlobalShippingProgramListingPreference(Boolean.TRUE);
//		getUserPreferencesCall.setShowGlobalShippingProgramListingPreference(Boolean.TRUE);
//		getUserPreferencesCall.setShowOverrideGSPServiceWithIntlServicePreference(Boolean.TRUE);
//		getUserPreferencesCall.setShowPickupDropoffPreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShowPurchaseReminderEmailPreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShowRequiredShipPhoneNumberPreference(Boolean.TRUE);
//		getUserPreferencesCall.setShowSellerExcludeShipToLocationPreference(Boolean.TRUE);
//		getUserPreferencesCall.setShowSellerFavoriteItemPreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShowSellerPaymentPreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShowSellerProfilePreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShowSellerReturnPreferences(Boolean.TRUE);
//		getUserPreferencesCall.setShowUnpaidItemAssistanceExclusionList(Boolean.TRUE);
//		getUserPreferencesCall.setShowUnpaidItemAssistancePreference(Boolean.TRUE);
//		
//		try {
//			getUserPreferencesCall.getUserPreferences();
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SdkException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//    //https://signin.ebay.com/ws/eBayISAPI.dll?SignIn&RuName=YourRuNameHere&SessID=YourSessionIDHere  
//	public static void main(String[] args) {  
//		      Map<String, String> map = new HashMap<>();
//		      map.put("serviceName", "MESSAGE_DOWNLOAD");
//		      String requestJson = "{\"userKey\": \"3B45AECC626FC24420170712141110 \", \"dataSource\": \"8\",\"arguments\": {  \"props\": {\"messageId\": \"45364E201706270001\", \"enterpriseNumber\": \" 深圳市云途通运物流有限公司\", \"organizationCode\": \"30601718X\", \"enterpriseName\": \"深圳市云途通运物流有限公司\", \"enterpriseType\": \"4\"}}}";
//	          BaseHttpsService baseHttpsService  = new  BaseHttpsService(); 
//	        
//	          JSONObject jsonObject =baseHttpsService.getResponseJson("http://opentest.guanmaoyun.net/service/ServiceProviderFacade?wsdl", map, requestJson);
//	         System.out.println(jsonObject.toString());       
//	   } 
//    public static String httpPost(String url,String date){
//    	        try {
//    	        	  DefaultHttpClient client = new DefaultHttpClient();
//
//    	            //发送post请求
//    	            HttpPost request = new HttpPost(url);
//    	            StringEntity entity = new StringEntity(date, Charset.forName("UTF-8"));
//    	            entity.setContentEncoding("UTF-8");
//    	            // 发送Json格式的数据请求
//    	            entity.setContentType("application/json");
//    	            request.setEntity(entity);
//    	            HttpResponse response = client.execute(request);
//    	            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//    	                String strResult = EntityUtils.toString(response.getEntity());
//    	            //    logger.info(strResult);//System.out.println(strResult);
//    	                return strResult;
//    	            } else {
//    	              String strResult = EntityUtils.toString(response.getEntity());
//    	             // logger.info("请求EDA错误:"+strResult);
//    	            }
//    	        } catch (IOException e) {
//    	           // logger.error("请求提交失败:" + url, e);
//    	        }
//    	  return null;
//    	 }
//   *//** 
//    *  
//    * @param sessionID 
//    * @throws ApiException 
//    * @throws SdkException 
//    * @throws Exception 
//    *//*  
//public static void getToken(String sessionID) throws ApiException, SdkException, Exception{  
//   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//   FetchTokenCall call = new FetchTokenCall();  
//   ApiContext apiContext = new ApiContext();  
//   apiContext.setApiServerUrl(serverUrl);  
//   ApiCredential credential = new ApiCredential();  
//   ApiAccount account = new ApiAccount();  
//   account.setApplication(appID);  
//   account.setDeveloper(devID);  
//   account.setCertificate(cert);  
//   credential.setApiAccount(account);  
//   apiContext.setApiCredential(credential);  
//   String url = "https://signin.sandbox.ebay.com/ws/eBayISAPI.dll?SignIn&" + "RuName=" + ruName  
//           + "&SessID=" + sessionID;   
//   openBrowser(url);
//   call.setApiContext(apiContext);  
//   call.setSessionID(sessionID);  
//   call.fetchToken();  
//   System.out.println(call.getReturnedRESTToken());
//   System.out.println("ReturnToken: "+call.getReturnedToken());  
//   Calendar ExpirationTime = call.getHardExpirationTime();  
//   System.out.println("HardExpirationTime: "+sdf.format(ExpirationTime.getTime()));  
// 
//     
//}  
// 
//*//** 
//*  
//* @return 
//* @throws ApiException 
//* @throws SdkException 
//* @throws Exception 
//*//*  
//public static String getSessionID() throws ApiException, SdkException, Exception{  
//   ApiContext apiContext = new ApiContext();  
//   apiContext.setApiServerUrl(serverUrl);  
//   ApiCredential credential = new ApiCredential();  
//   ApiAccount account = new ApiAccount();  
//   account.setApplication(appID);  
//   account.setDeveloper(devID);  
//   account.setCertificate(cert);  
//   credential.setApiAccount(account);  
//   apiContext.setApiCredential(credential);  
//   GetSessionIDCall call = new GetSessionIDCall();  
//   call.setApiContext(apiContext);  
//   call.setRuName(ruName);  
//   String sessionID = call.getSessionID();  
//   return sessionID;  
//}     
//
//	 private static String appID="yangguan-LedEbay-SBX-908fa563f-b2f96b11";  
//     private static String devID="bb1ab1e0-0eb1-4466-b49b-2b6c51752020";  
//     private static String cert="SBX-08fa563f4423-41b3-4ca9-a5df-6774";  
//     private static String ruName="yang_guanbao-yangguan-LedEba-atxum";  
//     private static String serverUrl="https://api.sandbox.ebay.com/wsapi";
//     private static String session= null;
//     //https://signin.ebay.com/ws/eBayISAPI.dll?SignIn&RuName=YourRuNameHere&SessID=YourSessionIDHere  
//  public static void main(String[] args){  
//                     
//                 ApiContext apiContext = BaseEbaySDKService.getApiContext("AgAAAA**AQAAAA**aAAAAA**R+iCWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GlDZeHow2dj6x9nY+seQ**0ScEAA**AAMAAA**XlRylrkXXCoY5UTwzSe/uNQwQLxTK6EYW/p15MKB454clz3lrKW9qzXAIq2oY/EUkzA2YiF2QbwrjfETbqAmBYWxqPr3ht5moulrZQr0y3sMoWTLA56ASPjsXm1TRojVEOezRvrAHKarQq+Gl2mGe9SS7VF7OBSG1TEPdc6BY59w3cEpZqt53j3FpHsjn0N3ZQoDb0NcyEfFRDw3CEPPWL1546z9x455lnjtOCnocwJg83gt3oDcCN+Uuq1KxoEAKboIjAIBr3VcV9mTbIu+gW65Kn2qdbUUtH/Qx22oU0xAjRSbQfpM0vfsfNmckrmgexjS7irpjQ9YFhMlEWM1i6Dk6iA0gubEijkKomxUl77vZCz7b3MV3urZJEbSNyPjGrrCRtH8lo6Th3o0lVdlVJhYmtx0Nw73pO+3UAI22V7lbf+SXqH6UI8dhZJ+zMLVGzgoB79aiEkTLeB+1VK83h21Iv1XdwAw/l5LI+4UwxR2rF4218ZTjRdQEOWgqokdxKsCiEVl7UdEF22+8lnRrjgYBESbkf136mqYvG4O5cFEVRKKVghD2VfTV4gwnWCkmIkrVxIbb0wvEfK2qA5Tas5BNFOazQPVmLchC6CjskzKXGS1ohy6TZ61QlfezEE4r5DHgpjyQOAkIpwKnmo8bD2zZY654lnOakEb4zRzIgVUks20FirrekeR9z/YCNyT0qsSk+6u2BE4oJV0hcVA0zeVzlaGTk0qiTlQjmCOL0/XXRcpFFWRQIhTVh+S2Pd8", serverUrl);
//                 
//                 ApiCredential credential = new ApiCredential();  
//                 ApiAccount account = new ApiAccount();  
//                 account.setApplication(appID);  
//                 account.setDeveloper(devID);  
//                 account.setCertificate(cert);  
//                 credential.setApiAccount(account);  
//                 apiContext.setApiCredential(credential);  
//                 GetSessionIDCall getSessionIDCall = new GetSessionIDCall(apiContext);
//                 getSessionIDCall.setRuName(ruName);
//                 
//                 try {
//					session = getSessionIDCall.getSessionID();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//               
//               String url = "https://signin.sandbox.ebay.com/ws/eBayISAPI.dll?SignIn&" + "RuName=" + ruName  
//                       + "&SessID=" + session;   
//               openBrowser("https://developer.ebay.com/DevZone/account/");
//               getUserToken();
//               
//  }  
//  public static boolean getUserToken() {  
//	    if (session == null) return false;  
//	    ApiContext localContext = new ApiContext();  
//	    ApiAccount apiAccount = new ApiAccount();  
//	    apiAccount.setApplication(appID);  
//	    apiAccount.setCertificate(cert);  
//	    apiAccount.setDeveloper(devID);  
//	    localContext.getApiCredential().setApiAccount(apiAccount);  
//	    localContext.setApiServerUrl("https://api.sandbox.ebay.com/wsapi");  
//	  
//	    ConfirmIdentityCall apiCall1 = new ConfirmIdentityCall(localContext);  
//	    apiCall1.setSessionID(session);  
//	    try {  
//	        apiCall1.confirmIdentity();  
//	        String userID = apiCall1.getReturnedUserID();  
//	    } catch (Exception e) {  
//	        e.printStackTrace();  
//	    }  
//	  
//	    FetchTokenCall apiCall = new FetchTokenCall(localContext);  
//	    apiCall.setSessionID(session);  
//	    try {  
//	        apiCall.fetchToken();  
//	    } catch (Exception e) {  
//	        e.printStackTrace();  
//	        return false;  
//	    }  
//	    return true;  
//	}  
//  public static boolean openBrowser(String url) {  
//	    if (url == null) {return false;} 
//	    String[] unixBrowser = new String[] { "google-chrome", "firefox" };  
//	    boolean success = false;  
//	    if (System.getProperty("os.name").toLowerCase().startsWith("win")) {  
//	        try {  
//	            Runtime.getRuntime().exec(  
//	                    new String[] { "rundll32.exe", "url.dll,FileProtocolHandler", url });  
//	            success = true;  
//	        } catch (Exception e) {  
//	        }  
//	    } else {  
//	        for (int i = 0; i < unixBrowser.length; ++i)  {
//	            try {  
//	                Runtime.getRuntime().exec(new String[] { unixBrowser[0], url });  
//	                success = true;  
//	                break;  
//	            } catch (Exception e) {  
//	            }  
//	        }
//	    }  
//	    return success;  
//	}  
//    *//** 
//     *  
//     * @param sessionID 
//     * @throws ApiException 
//     * @throws SdkException 
//     * @throws Exception 
//     *//*  
//public static void getToken(String sessionID) throws ApiException, SdkException, Exception{  
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//    FetchTokenCall call = new FetchTokenCall();  
//    ApiContext apiContext = new ApiContext();  
//    apiContext.setApiServerUrl(serverUrl);  
//    ApiCredential credential = new ApiCredential();  
//    ApiAccount account = new ApiAccount();  
//    account.setApplication(appID);  
//    account.setDeveloper(devID);  
//    account.setCertificate(cert);  
//    credential.setApiAccount(account);  
//    apiContext.setApiCredential(credential);  
//    call.setApiContext(apiContext);  
//    call.setSessionID(sessionID);  
//    call.fetchToken();  
//    System.out.println("ReturnToken: "+call.getReturnedToken());  
//    Calendar ExpirationTime = call.getHardExpirationTime();  
//    System.out.println("HardExpirationTime: "+sdf.format(ExpirationTime.getTime()));  
//  
//      
//}  
//  
//*//** 
// *  
// * @return 
// * @throws ApiException 
// * @throws SdkException 
// * @throws Exception 
// *//*  
//public static String getSessionID() throws ApiException, SdkException, Exception{  
//    ApiContext apiContext = new ApiContext();  
//    apiContext.setApiServerUrl(serverUrl);  
//    ApiCredential credential = new ApiCredential();  
//    ApiAccount account = new ApiAccount();  
//    account.setApplication(appID);  
//    account.setDeveloper(devID);  
//    account.setCertificate(cert);  
//    credential.setApiAccount(account);  
//    apiContext.setApiCredential(credential);  
//    GetSessionIDCall call = new GetSessionIDCall();  
//    call.setApiContext(apiContext);  
//    call.setRuName(ruName);  
//    String sessionID = call.getSessionID();  
//    return sessionID;  
//}     
//	 
//	
//	public static void main(String[] args) {
//		Class<?> clazz;
//		try {
//			String mm = "TemplateA";
//			clazz = Class.forName("com.it.ocs.itemDescribe.template.TemplateA");
//			BaseTemplate baseTemplate = (BaseTemplate) clazz.newInstance();
//		        System.out.println(baseTemplate.createLastLayout(new TemplateAStructure()));
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	public static void main(String[] args) {
//		String jsonString = "{"
//				+  "\"variations\": [{\"SKU\": \"123\",\"StartPrice\": \"12\", \"Quantity\": \"10\",\"NameValueList\": [{\"Name\": \"ad\",\"Value\": \"a\" },{\"Name\": \"g\",\"Value\": \"g\"}]},"
//			                   +"{\"SKU\": \"adf\",\"StartPrice\": \"16\",\"Quantity\": \"10\",\"NameValueList\": [{\"Name\": \"ad\",\"Value\": \"d\"},{\"Name\": \"g\",\"Value\": \"g\"}]}"
//			                   + "],"
//			                   + "\"pictures\": [{VariationSpecificName:\"ad\",VariationSpecificValue:\"2342\",PictureURL:\"asdfesadsd\"},{VariationSpecificName:\"ad\",VariationSpecificValue:\"fghgf\",PictureURL:\"sdfxcvcvdf345\"},"
//			                   + "{VariationSpecificName:\"abcd\",VariationSpecificValue:\"23\",PictureURL:\"sdfcvf3453\"},{VariationSpecificName:\"abcd\",VariationSpecificValue:\"df\",PictureURL:\"sdfads\"}],"
//			                   + "\"variationSpecificsSet\": [{\"Name\": \"ad\",\"Values\": [\"a\",\"d\"]},"
//			                   + "{\"Name\": \"g\",\"Values\": [\"g\",\"f\"]}]}";
//		System.out.println(jsonString);
//		JSONObject jsonObject = JSONObject.parseObject(jsonString);
//		JSONArray variationArray = jsonObject.getJSONArray("variations");
//		
//		JSONArray pictures = jsonObject.getJSONArray("pictures");
//		JSONArray variationSpecificsSet = jsonObject.getJSONArray("variationSpecificsSet");
//	    int variationSizt = variationArray.size();
//		System.out.println("variationArray大小:" + variationSizt);
//		for (Object object : variationArray) {
//			JSONObject jsonObject2 = JSONObject.parseObject(object.toString());
//			System.out.println("------------------------------------------------------");
//			System.out.println("SKU: "+jsonObject2.getString("SKU"));
//			System.out.println("StartPrice: "+jsonObject2.getDouble("StartPrice"));
//			System.out.println("Quantity: "+jsonObject2.getInteger("Quantity"));
//			System.out.println(jsonObject2.getJSONArray("NameValueList").toJSONString());
//			System.out.println("------------------------------------------------------");
//		}
//		System.out.println("pictures大小："+pictures.size());
//		HashMap<String, PicturesType> pictureArray = new HashMap<>();
//		
//		for (Object object : pictures) {
//			JSONObject jsonObject2 = JSONObject.parseObject(object.toString());
//			String name = jsonObject2.getString("VariationSpecificName");
//			String pictureUrl = jsonObject2.getString("PictureURL");
//			String specificValue = jsonObject2.getString("VariationSpecificValue");
//			String[] urls = new String[1];
//			urls[0]=pictureUrl;
//			PicturesType picturesType = new PicturesType();
//			picturesType.setVariationSpecificName(name);
//			VariationSpecificPictureSetType specificPicture = new VariationSpecificPictureSetType();
//			specificPicture.setPictureURL(urls);
//			specificPicture.setVariationSpecificValue(specificValue);
//			if(pictureArray.size()<1){
//				picturesType.setVariationSpecificPictureSet(addObjectToArray(picturesType.getVariationSpecificPictureSet(),specificPicture,VariationSpecificPictureSetType.class));
//				pictureArray.put(name,picturesType );
//			}else{
//				if(pictureArray.get(name)!=null){
//					pictureArray.get(name).setVariationSpecificPictureSet(addObjectToArray(pictureArray.get(name).getVariationSpecificPictureSet(),specificPicture,VariationSpecificPictureSetType.class));
//					
//				}else{
//					picturesType.setVariationSpecificPictureSet(addObjectToArray(picturesType.getVariationSpecificPictureSet(),specificPicture,VariationSpecificPictureSetType.class));
//					pictureArray.put(name,picturesType );
//				}
//			}
//			
//		}
//		
//		PicturesType picturesType = pictureArray.get("abcd");
//		VariationSpecificPictureSetType[] variationSpecificPictureSetType = picturesType.getVariationSpecificPictureSet();
//		for (VariationSpecificPictureSetType variationSpecificPictureSetType2 : variationSpecificPictureSetType) {
//			System.out.println("--------------");
//			System.out.println(variationSpecificPictureSetType2.getPictureURL()[0]);
//			System.out.println(variationSpecificPictureSetType2.getVariationSpecificValue());
//			System.out.println("--------------");
//		}
//		System.out.println(pictureArray.size());
//		System.out.println("variationSpecificsSet大小：" + variationSpecificsSet.size());
//		JSONObject jsonObject2 = JSONObject.parseObject(variationSpecificsSet.get(0).toString());
//		jsonObject2.getString("Name");
//		JSONArray jsonArray = jsonObject2.getJSONArray("Values");
//		for (Object object : jsonArray) {
//			System.out.println(object);
//		}
//	
//	}
//	public static <T> T[] addObjectToArray(T[] target, T source,Class<T> cla){
//		T[] result =null;
//		if(ValidationUtil.isNull(target)||target.length<1){
//			result = (T[]) Array.newInstance(cla, 1);
//			result[0] = source;
//			target=result;
//			return result;
//		}else{
//			int length = target.length;
//			result = (T[]) Array.newInstance(cla, length+1);
//			for (int i = 0; i < length; i++) {
//				result[i]=target[i];
//			}
//			result[length]=source;
//
//			target=result;
//			return result;
//		}
//	};
//	public static void main(String[] args) {
//      Date date2 =ConversionDateUtil.SiteDateConversionUTC("2017-08-08T06:38:44.409Z", 0L);
//        System.out.println(date2);
//		//Tools.getSoreceId("");
//	    String da = TimeTools.timeConvert("2017-08-08 15:38:00", "Asia/Shanghai", "America/New_York");
//		String[] string = TimeZone.getAvailableIDs();
//		System.out.println(string.length);
//		for (String string2 : string) {
//			System.out.println(string2);
//		}
//		System.out.println(da);
//	
//		// 当前系统默认时区的时间：
//		         //Calendar calendar = new GregorianCalendar();
//		         
//		         System.out.print("时区：" + calendar.getTimeZone().getID() + "  ");
//		         
//		         System.out.println("时间：" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
//		         
//		         // 美国洛杉矶时区
//				
//		         TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
//		         TimeZone.setDefault(tz);
//		         Calendar calendar = new GregorianCalendar();
//		         calendar.setTimeZone(tz);
//		         // 时区转换
//		         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//		         Date date = null;
//		         try {
//		        	 date=format.parse("2017-08-08T06:38:44.409Z");
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		         
//		         calendar.setTime(date);
//		         System.out.print("时区：" + calendar.getTimeZone().getID() + "  ");
//		         System.out.println("时间：" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
//		         
//		         
//		         calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//		         System.out.print("时区：" + calendar.getTimeZone().getID() + "  ");
//		         System.out.println("时间：" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
//		       
//		         
//		         // 1、取得本地时间：
//		         java.util.Calendar cal = java.util.Calendar.getInstance();
//		         
//		         // 2、取得时间偏移量：
//		         int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
//		         
//		         // 3、取得夏令时差：
//		         int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
//		         
//		         // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
//		        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
//		         
//		         // 之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
//		         System.out.println("UTC:" + new Date(cal.getTimeInMillis()));
//
//	}*/
//}
