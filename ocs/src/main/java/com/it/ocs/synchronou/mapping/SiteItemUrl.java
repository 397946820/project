package com.it.ocs.synchronou.mapping;

import java.util.HashMap;

import com.it.ocs.synchronou.util.ValidationUtil;

public class SiteItemUrl {

	public static String getItemUrlBySIDAndIID(String user,String itemId){
		if(user.equals("testuser_yangguanbao")){
			return "http://cgi.sandbox.ebay.com/ws/eBayISAPI.dll?ViewItem&item="+itemId;
		}else{
			return "https://cgi.ebay.com/ws/eBayISAPI.dll?ViewItem&item="+itemId;
		}
		
	}
}
