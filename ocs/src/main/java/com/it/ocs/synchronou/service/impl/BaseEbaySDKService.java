//package com.it.ocs.synchronou.service.impl;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiCredential;
//import com.ebay.services.client.ClientConfig;
//
//public class BaseEbaySDKService {
//
//	public static ApiContext getApiContext(String token,String serverUrl){
//		ApiContext apiContext = new ApiContext();
//		ApiCredential cred = apiContext.getApiCredential();
//		cred.seteBayToken(token);
//		apiContext.setApiServerUrl(serverUrl);
//		return apiContext;
//	}
//	public static ClientConfig getClientConfig(String appId,String url,String globalId){
//		ClientConfig clientConfig = new ClientConfig();
//		clientConfig.setEndPointAddress(url);
//		clientConfig.setApplicationId(appId);
//		clientConfig.setGlobalId(globalId);
//		return clientConfig;
//	}
//}
