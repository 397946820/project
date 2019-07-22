package com.it.ocs.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;

public class OrderCache {
	private static Map<String, List<SalesStatisticsModel>> cacheMap = Maps.newConcurrentMap();

	private static Date time = null;

	public static void setTime(Date date) {
		time = date;
	}

	public static Date getTime() {
		return time;
	}

	public static List<SalesStatisticsModel> getOrder(String key) {
		return cacheMap.containsKey(key) ? cacheMap.get(key) : null;
	}

	public static void setAmazonOrder(String key, List<SalesStatisticsModel> datas) {
		cacheMap.put(key, datas);
	}

	public static Map<String, List<SalesStatisticsModel>> getMaps(String source) {

		if (cacheMap.containsKey(source)) {
			return getMap(cacheMap.get(source), source);
		}

		return new HashMap<>();
	}

	private static Map<String, List<SalesStatisticsModel>> getMap(List<SalesStatisticsModel> list, String source) {
		Map<String, List<SalesStatisticsModel>> map = new HashMap<>();
		if (!CollectionUtil.isNullOrEmpty(list)) {
			String key = "";
			if (source.equals("light")) {
				for (SalesStatisticsModel model : list) {
					key = model.getPlatform();
					add(key, map, model);
				}
			} else {
				for (SalesStatisticsModel model : list) {
					key = model.getPlatform() + "-" + model.getStation();
					add(key, map, model);
					key = model.getStation() + "-" + model.getPlatform();
					add(key, map, model);
				}
			}
		}
		return map;
	}

	private static void add(String key, Map<String, List<SalesStatisticsModel>> map, SalesStatisticsModel model) {
		if (StringUtils.isNotBlank(key)) {
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<SalesStatisticsModel>());
			}
			map.get(key).add(model);
		}
	}
}