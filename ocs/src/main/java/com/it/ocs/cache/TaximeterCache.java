package com.it.ocs.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it.ocs.cal.model.TaximeterModel;

public class TaximeterCache {

	public static Map<String, List<TaximeterModel>> map = new HashMap<>();
	
	public static List<TaximeterModel> getTaximeter(String key) {
		return map.containsKey(key) ? map.get(key) : null;
	}

	public static void setMap(String key, List<TaximeterModel> list) {
		map.put(key, list);
	}
}
