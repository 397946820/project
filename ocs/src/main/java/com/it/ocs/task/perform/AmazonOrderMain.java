package com.it.ocs.task.perform;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cache.OrderCache;
import com.it.ocs.cache.TaximeterCache;
import com.it.ocs.cache.TimeCache;
import com.it.ocs.cal.common.IBaseService;
import com.it.ocs.cal.dao.ITaximeterDao;
import com.it.ocs.cal.model.TaximeterModel;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.dao.ISalesStatisticsDao;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.util.OutInfo;
@Service
public class AmazonOrderMain extends IBaseService {

	@Autowired
	private ISalesStatisticsDao salesStatisticsDao;

	@Autowired
	private ITaximeterDao taximeterDao;

	private String AMAZON_KEY = "amazon";
	private String EBAY_KEY = "ebay";
	private String LIGHT_KEY = "light";

	public void amazonOrderAddOrUpdate() throws Exception {
		OutInfo.Out("开始执行时间：" + new Date().toString() + "\n", OrderRecord.amazonOrderAddOrUpdatePath);
		if (OrderCache.getTime() != null) {
			// 查询时间段的
			Date date = salesStatisticsDao.getMaxUpdateAt();
			
			OutInfo.Out("   获取数据的时间段 : " + OrderCache.getTime() + "< time >=" + date + "\n",
					OrderRecord.amazonOrderAddOrUpdatePath);
			
			List<SalesStatisticsModel> datas = salesStatisticsDao.amazonQueryByUpdateAt(OrderCache.getTime(), date);
			// 修改缓存
			updateOrderChe(datas);
		} else {
			// 查询全部
			OutInfo.Out("   获取全部数据 : "  + "\n",OrderRecord.amazonOrderAddOrUpdatePath);
			
			List<SalesStatisticsModel> datas = salesStatisticsDao.amazonQuery();
			
			if (!CollectionUtil.isNullOrEmpty(datas)) {
				// 修改缓存
				updateOrderChe(datas);
				// 设置时间
				OrderCache.setTime(salesStatisticsDao.getMaxUpdateAt());
			} else {
				OrderCache.setAmazonOrder(AMAZON_KEY, datas);
			}
		}
		if (!CollectionUtil.isNullOrEmpty(OrderCache.getOrder(AMAZON_KEY))) {
			List<SalesStatisticsModel> amazons = OrderCache.getOrder(AMAZON_KEY);
			// 取出大于2017年的数据
			List<SalesStatisticsModel> list = getOrdersByList(amazons, AMAZON_KEY);
			OrderCache.setAmazonOrder(AMAZON_KEY + "2017", list);
			List<String> strings = new ArrayList<>();
			for (SalesStatisticsModel model : list) {
				strings.add(TimeTools.dateToString(model.getPurchaseat()));
			}
			TimeCache.setTimeMap(AMAZON_KEY + "time", strings);
		}

		// ebay订单
		List<SalesStatisticsModel> ebays = salesStatisticsDao.ebayQuery();
		if (!CollectionUtil.isNullOrEmpty(ebays)) {
			OrderCache.setAmazonOrder(EBAY_KEY, ebays);
		}

		// 官网订单
		List<SalesStatisticsModel> lights = salesStatisticsDao.lightQuery();
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
		
		OutInfo.Out("执行结束时间：" + new Date().toString() + "\n", OrderRecord.amazonOrderAddOrUpdatePath);
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

	private void updateOrderChe(List<SalesStatisticsModel> datas) {
		if (!CollectionUtil.isNullOrEmpty(OrderCache.getOrder(AMAZON_KEY))) {
			List<SalesStatisticsModel> order = OrderCache.getOrder(AMAZON_KEY);

			Map<String, SalesStatisticsModel> map1 = new HashMap<>();
			Map<String, SalesStatisticsModel> map2 = new HashMap<>();

			for (SalesStatisticsModel model : order) {
				map1.put(model.getOrderId() + model.getItemId(), model);
			}
			for (SalesStatisticsModel model : datas) {
				map2.put(model.getOrderId() + model.getItemId(), model);
			}

			Iterator<Entry<String, SalesStatisticsModel>> it = map2.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, SalesStatisticsModel> entry = it.next();
				map1.put(entry.getKey(), entry.getValue());
			}

			OrderCache.setAmazonOrder(AMAZON_KEY, new ArrayList<>(map1.values()));

		} else {
			OrderCache.setAmazonOrder(AMAZON_KEY, datas);
		}

	}
}
