package com.it.ocs.amazon.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.amazon.dao.IAmazonReportDao;
import com.it.ocs.amazon.model.ReportRequestListModel;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.TimeConvertUtil;
import com.mysql.jdbc.StringUtils;

@Service
public class AmazonFinancialService {
	@Autowired
	private IAmazonReportDao amazonReportDao;
	private static List<Map<String,String>> TIME_MAP = Lists.newArrayList();
	public static Map<String,String> TIMEZON_MAPPING = Maps.newConcurrentMap();
	static {
		Map<String,String> fivem = Maps.newConcurrentMap();
		fivem.put("st", "2018-05-01 00:00:00");
		fivem.put("et", "2018-06-01 00:00:00");
		TIME_MAP.add(fivem);
		Map<String,String> sixm = Maps.newConcurrentMap();
		sixm.put("st", "2018-06-01 00:00:00");
		sixm.put("et", "2018-07-01 00:00:00");
		TIME_MAP.add(sixm);
		Map<String,String> sevenm = Maps.newConcurrentMap();
		sevenm.put("st", "2018-07-01 00:00:00");
		sevenm.put("et", "2018-08-01 00:00:00");
		TIME_MAP.add(sevenm);
		Map<String,String> eightm = Maps.newConcurrentMap();
		eightm.put("st", "2018-08-01 00:00:00");
		eightm.put("et", "2018-09-01 00:00:00");
		TIME_MAP.add(eightm);
		Map<String,String> nighm = Maps.newConcurrentMap();
		nighm.put("st", "2018-09-01 00:00:00");
		nighm.put("et", "2018-10-01 00:00:00");
		TIME_MAP.add(nighm);
		Map<String,String> tenm = Maps.newConcurrentMap();
		tenm.put("st", "2018-10-01 00:00:00");
		tenm.put("et", "2018-11-01 00:00:00");
		TIME_MAP.add(tenm);
		TIMEZON_MAPPING.put("amazon.it", "Europe/Rome");
		TIMEZON_MAPPING.put("amazon.co.uk", "Europe/London");
		TIMEZON_MAPPING.put("amazon.fr", "Europe/Paris");
		TIMEZON_MAPPING.put("amazon.ca", "America/Los_Angeles");
		TIMEZON_MAPPING.put("amazon.com", "America/Los_Angeles");
		TIMEZON_MAPPING.put("amazon.es", "Europe/Madrid");
		TIMEZON_MAPPING.put("amazon.jp", "Asia/Tokyo");
		TIMEZON_MAPPING.put("amazon.com.au", "Australia/Sydney");
		TIMEZON_MAPPING.put("amazon.de", "Europe/Berlin");
	}
	public void updateMonth() {
		CollectionUtil.each(TIME_MAP, new IAction<Map<String,String>>() {
			@Override
			public void excute(Map<String, String> tm) {
				CollectionUtil.each(TIMEZON_MAPPING.entrySet(), new IAction<Entry<String, String>>() {
					@Override
					public void excute(Entry<String, String> entry) {
						try {
							Map<String,String> param = Maps.newConcurrentMap();
							String cstartT = TimeConvertUtil.timeConvert(tm.get("st"), entry.getValue(), "UTC");
							String cendT = TimeConvertUtil.timeConvert(tm.get("et"), entry.getValue(), "UTC");
							param.put("startTime", cstartT);
							param.put("endTime", cendT);
							param.put("site", entry.getKey());
							List<ReportRequestListModel> list = amazonReportDao.getParsedReport(param);
							List<String> reportIds = Lists.newArrayList();
							CollectionUtil.each(list, new IAction<ReportRequestListModel>() {
								@Override
								public void excute(ReportRequestListModel requestModel) {
									if (!StringUtils.isNullOrEmpty(requestModel.getReportGetId())) {
										reportIds.add(requestModel.getReportGetId());
									}
								}
							});
							if (!CollectionUtil.isNullOrEmpty(reportIds)) {
								amazonReportDao.dataRangeReportMonthU(reportIds, entry.getKey(), getUpdateMonth(tm.get("st")));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
	public static void main(String[] args) {
		try {
			System.out.println(TimeConvertUtil.timeConvert("2018-09-01 00:00:00", "amazon.co.uk", "UTC"));
			System.out.println(TimeConvertUtil.timeConvert("2018-10-01 00:00:00", "amazon.co.uk", "UTC"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private String getUpdateMonth(String time) {
		return time.replace("-", ".").substring(0,7);
	}

}
