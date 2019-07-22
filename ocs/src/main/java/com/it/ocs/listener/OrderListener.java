package com.it.ocs.listener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.it.ocs.cache.OrderCache;
import com.it.ocs.cache.TimeCache;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.dao.ISalesStatisticsDao;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.utils.TimeTools;

@Component
public class OrderListener implements ApplicationListener<ContextRefreshedEvent> {
	private String AMAZON_KEY = "amazon";
	private String EBAY_KEY = "ebay";
	private String LIGHT_KEY = "light";
	
	@Autowired
	private ISalesStatisticsDao dao;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		try {
			/*	
			
			if (CollectionUtil.isNullOrEmpty(OrderCache.getOrder(EBAY_KEY))) {
				System.out.println("------------ebay order init start ------------------");
				long time = System.currentTimeMillis();
				List<SalesStatisticsModel> datas = dao.ebayQuery();
				if (!CollectionUtil.isNullOrEmpty(datas)) {
					OrderCache.setAmazonOrder(EBAY_KEY, datas);
				}
				System.out.println("------------ebay order init end ------------------");
				System.out.println("-------------order count : " + datas.size() + " ----------------------");
				System.out
						.println("---------------共耗费:" + (System.currentTimeMillis() - time) + "ms ------------------");
			}

			if (CollectionUtil.isNullOrEmpty(OrderCache.getOrder(LIGHT_KEY))) {
				System.out.println("------------light order init start ------------------");
				long time = System.currentTimeMillis();
				List<SalesStatisticsModel> datas = dao.lightQuery();
				if (!CollectionUtil.isNullOrEmpty(datas)) {
					OrderCache.setAmazonOrder(LIGHT_KEY, datas);
					// 取出大于2017年的数据
					List<SalesStatisticsModel> list = getOrdersByList(datas, LIGHT_KEY);
					OrderCache.setAmazonOrder(LIGHT_KEY + "2017", list);
					List<String> strings = new ArrayList<>();
					for (SalesStatisticsModel model : list) {
						strings.add(TimeTools.dateToString(model.getCreatedat()));
					}
					TimeCache.setTimeMap(LIGHT_KEY + "time", strings);
				}
				System.out.println("------------light order init end ------------------");
				System.out.println("-------------order count : " + datas.size() + " ----------------------");
				System.out
						.println("---------------共耗费:" + (System.currentTimeMillis() - time) + "ms ------------------");
			}
		*/
		} catch (Exception e) {
			e.printStackTrace();
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
