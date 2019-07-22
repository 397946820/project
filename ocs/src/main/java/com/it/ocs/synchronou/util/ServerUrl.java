package com.it.ocs.synchronou.util;

public class ServerUrl {

	public  static String getServerUrl(String seller_id) {
		String serverUrl;
		if(seller_id.equals("testuser_yangguanbao")){
			serverUrl = "https://api.sandbox.ebay.com/wsapi";
		}else{
			serverUrl="https://api.ebay.com/wsapi";
		}
		return serverUrl;
	}
}
