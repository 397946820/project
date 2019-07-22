package com.it.ocs.cal.service.inner.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.jam.internal.elements.VoidClassImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cache.OrderCache;
import com.it.ocs.cache.PricePlanCache;
import com.it.ocs.cache.TaximeterCache;
import com.it.ocs.cache.TimeCache;
import com.it.ocs.cal.common.IBaseService;
import com.it.ocs.cal.dao.ITaximeterDao;
import com.it.ocs.cal.model.CurrencyRateModel;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.model.TaximeterModel;
import com.it.ocs.cal.service.inner.ISynchronizationService;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.dao.ISalesStatisticsDao;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.utils.TimeTools;

@Service
@Transactional
public class SynchronizationService extends IBaseService implements ISynchronizationService {

	private String AMAZON_KEY = "amazon";
	private String EBAY_KEY = "ebay";
	private String LIGHT_KEY = "light";

	@Autowired
	private ISalesStatisticsDao dao;
	
	@Autowired
	private ITaximeterDao taximeterDao;
	
	@Override
	public void synchronizationOrder() {
		try {
			synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void synchronizationPricePlan() {

	}

	@Override
	public void synchronization() throws Exception {

		// ebay订单
		List<SalesStatisticsModel> ebays = dao.ebayQuery();
		if (!CollectionUtil.isNullOrEmpty(ebays)) {
			OrderCache.setAmazonOrder(EBAY_KEY, ebays);
		}

		// 官网订单
		List<SalesStatisticsModel> lights = dao.lightQuery();
		if (!CollectionUtil.isNullOrEmpty(lights)) {
			OrderCache.setAmazonOrder(LIGHT_KEY, lights);
			// 取出大于2017年的数据
			List<SalesStatisticsModel> list = getOrdersByList(lights, LIGHT_KEY);
			OrderCache.setAmazonOrder(LIGHT_KEY + "2017", list);
			List<String> strings = new ArrayList<>();
			for (SalesStatisticsModel model : list) {
				strings.add(TimeTools.dateToString(model.getCreatedat()));
			}
			TimeCache.setTimeMap(LIGHT_KEY + "time", strings);
		}
	
	}

	private List<SalesStatisticsModel> getOrdersByList(List<SalesStatisticsModel> list, String string)
			throws ParseException {
		List<SalesStatisticsModel> result = new ArrayList<>();
		Date time = TimeTools.getTime("2017-01-01");
		if (string.equals("amazon")) {
			for (SalesStatisticsModel model : list) {
				if (model.getPurchaseat().getTime() >= time.getTime()) {
					result.add(model);
				}
			}
		} else if (string.equals("light")) {
			for (SalesStatisticsModel model : list) {
				if (model.getCreatedat().getTime() >= time.getTime()) {
					result.add(model);
				}
			}
		}
		return result;
	}

}
