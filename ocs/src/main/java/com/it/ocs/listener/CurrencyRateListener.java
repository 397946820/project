package com.it.ocs.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cal.model.CurrencyRateModel;
import com.it.ocs.cal.service.ICurrencyRateService;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.service.impl.PermissionService;

public class CurrencyRateListener {

	public static void onApplicationEvent() {
		ICurrencyRateService currencyRateDao = (ICurrencyRateService) ProjectApplicationContext.getBean("currencyRateService",PermissionService.class);
		List<CurrencyRateModel> list = currencyRateDao.findByTemplate();
		Map<String, Double> map = new HashMap<>();
		if (!CollectionUtil.isNullOrEmpty(list)) {
			for (CurrencyRateModel model : list) {
				if(!map.containsKey(model.getCurrencyCode())) {
					map.put(model.getCurrencyCode(), model.getCurrencyRate());
				}
			}
			CurrencyRateCache.setRateMap(map);
		}
	}

}
