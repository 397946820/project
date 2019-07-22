package com.it.ocs.cache;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.common.util.CollectionUtil;

public class PricePlanCache {

	public static Map<String, PricePlanModel> getMap(List<PricePlanModel> list) {
		Map<String, PricePlanModel> map = Maps.newHashMap();
		if(!CollectionUtil.isNullOrEmpty(list)) {
			for (PricePlanModel model : list) {
				map.put(model.getCountryId() + model.getSku() + model.getShippingType(), model);
			}
		}
		return map;
	}
	
}
