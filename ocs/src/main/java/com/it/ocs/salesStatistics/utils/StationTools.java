package com.it.ocs.salesStatistics.utils;

import java.util.HashMap;
import java.util.Map;


public class StationTools {

	private static Map<String, String[]> map = new HashMap<>();
	
	static {
		map.put("US",new String[]{"Amazon.com"});
		map.put("DE",new String[]{"Amazon.de","Amazon.fr","Amazon.co.uk","Amazon.es","Amazon.it"});
		map.put("JP",new String[]{"Amazon.co.jp"});
		map.put("CA",new String[]{"Amazon.ca"});
		map.put("uk_nm",new String[]{"US","Germany","eBayMotors","UK"});
		map.put("uk_le",new String[]{"US","Germany","UK"});
		map.put("nm_deutschland",new String[]{"US","Germany","eBayMotors","UK"});
		map.put("le_deutschland",new String[]{"US","Germany","France","UK","Italy"});
	}

	public static String[] getStations(String country) {
		return map.get(country);
	}
	
}
