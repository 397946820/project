package com.it.ocs.cal.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.it.ocs.cal.dao.IProductSaleReportDao;
import com.it.ocs.cal.vo.SaleDisCountReportVO;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.synchronou.util.ValidationUtil;

@Service("skuDisCountExport")
public class ProductDisCountExportService extends AExcelExport {
	private static Map<String,String> siteR = Maps.newConcurrentMap();
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private IProductSaleReportDao iProductSaleReportDao;
	
	static {
		siteR.put("DE", "Amazon.de");
		siteR.put("UK", "Amazon.co.uk");
		siteR.put("FR", "Amazon.fr");
		siteR.put("US", "Amazon.com");
		siteR.put("CA", "Amazon.ca");
		siteR.put("ES", "Amazon.es");
		siteR.put("IT", "Amazon.it");
		siteR.put("JP", "Amazon.co.jp");
	}
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String, String[]> maps = request.getParameterMap();
		Map<String, Object> map = Maps.newConcurrentMap();
		CollectionUtil.each(maps.keySet(), new IAction<String>() {
			@Override
			public void excute(String key) {
				map.put(key, ValidationUtil.isNullOrEmpty(maps.get(key)) ? "" : maps.get(key)[0]);
			}
		});
		try {
			doTime(map);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<SaleDisCountReportVO> catCountReports = iProductSaleReportDao.queryDisCount(map);
		CollectionUtil.each(catCountReports, new IAction<SaleDisCountReportVO>() {
			@Override
			public void excute(SaleDisCountReportVO disReport) {
				disReport.setSite(siteR.get(disReport.getSite()));
			}
		});
		List<Map<String, Object>> result = BeanConvertUtil.listToMap(catCountReports);
		return result;
	}

	private void doTime(Map<String, Object> map) throws ParseException {
		String startt = (String) map.get("startTime");
		String endt = (String) map.get("endTime");
		if (ValidationUtil.isNullOrEmpty(startt)) {
			if (!ValidationUtil.isNullOrEmpty(endt)) {
				Date endTime = df.parse(endt);
				map.put("startTime", df.format(TimeTools.getChangeMonth(endTime, -1)));
			} else {
				Calendar ca = Calendar.getInstance();
				ca.add(Calendar.MONTH, -1);
				map.put("startTime", df.format(ca.getTime()));
			}
		}
		if (ValidationUtil.isNullOrEmpty(map.get("endTime"))) {
			if (!ValidationUtil.isNullOrEmpty(startt)) {
				Date endTime = df.parse(startt);
				map.put("endTime", df.format(TimeTools.getChangeMonth(endTime, 1)));
			} else {
				Calendar ca = Calendar.getInstance();
				map.put("endTime", df.format(ca.getTime()));
			}
		}
	}

	@Override
	protected void init(HttpServletRequest request) {
		super.initModel(SaleDisCountReportVO.class, "亚马逊DIS统计.xlsx");

	}

}
