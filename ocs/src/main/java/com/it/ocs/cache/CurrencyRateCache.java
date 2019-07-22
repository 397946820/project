package com.it.ocs.cache;

import java.util.Map;

import com.google.common.collect.Maps;

public class CurrencyRateCache {
	public static Map<String, Double> rateMap = Maps.newConcurrentMap();

	public static Double getCurrencyRate(String key) {
		return rateMap.containsKey(key) ? rateMap.get(key) : null;
	}

	public static void setRateMap(Map<String, Double> map) {
		rateMap = map;
	}
}
