package com.it.ocs.synchronou.mapping;

import java.util.HashMap;

public class AmazonMapping {
 
	public static String searchPlatformBySite(String site){
		HashMap<String, String> map = new HashMap<>();
		map.put("amazon.it","IT");
		map.put("amazon.co.uk","UK");
		map.put("amazon.fr","FR");
		map.put("amazon.com","US");
		map.put("amazon.ca","CA");
		map.put("amazon.es","ES");
		map.put("amazon.jp","JP");
		map.put("amazon.de","DE");
		map.put("amazon.com.au","AU");
		return map.get(site);
	}
}
