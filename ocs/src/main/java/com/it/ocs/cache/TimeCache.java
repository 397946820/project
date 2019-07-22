package com.it.ocs.cache;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class TimeCache {

	private static Map<String, List<String>> timeMap = Maps.newConcurrentMap();

	public static List<String> getTimes(String key) {
		return timeMap.containsKey(key) ? timeMap.get(key) : null;
	}

	public static void setTimeMap(String string,List<String> list) {
		timeMap.put(string, list);
	}
}
