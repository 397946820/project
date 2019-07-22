package com.it.ocs.salesStatistics.utils;

import java.util.HashMap;
import java.util.Map;

public class CurrencyRateTools {

	private static Map<String, String> map = new HashMap<>();
	static {
		map.put("FR","EUR");
		map.put("JP","JPY");
		map.put("CA","CAD");
		map.put("ES","EUR");
		map.put("US","USD");
		map.put("IT","EUR");
		map.put("UK","GBP");
		map.put("DE","EUR");
	}
	
	//根据国家获取币种
	public static String getCurrencyCode(String currencyCode) {
		return map.get(currencyCode);
	}
}
