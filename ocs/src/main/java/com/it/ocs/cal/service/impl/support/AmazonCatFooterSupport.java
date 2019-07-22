package com.it.ocs.cal.service.impl.support;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cal.vo.SaleCatCountReportVO;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.listener.CurrencyRateListener;
import com.it.ocs.salesStatistics.utils.Tools;

public class AmazonCatFooterSupport {
	private AmazonCatFooterSupport() {}
	private static AmazonCatFooterSupport instance;
	protected DecimalFormat sdf = new DecimalFormat("0.00");
	public static AmazonCatFooterSupport getInstance() {
		if (null == instance) {
			instance = new AmazonCatFooterSupport();
		}
		return instance;
	}
	public List<Map<String, Object>> getFooter(List<SaleCatCountReportVO> reports,
			List<SaleCatCountReportVO> weekReports, List<SaleCatCountReportVO> monthReports,
			List<SaleCatCountReportVO> yearReports, Map<String, Object> param) {
		CurrencyRateListener.onApplicationEvent();
		List<Map<String, Object>> result = Lists.newArrayList();
		result.add(initCatFooter());
		List<Map<String, Object>> countByNow = countBySite(reports);
		List<Map<String, Object>> countByWeek = countBySite(weekReports);
		List<Map<String, Object>> countByMonth = countBySite(monthReports);
		List<Map<String, Object>> countByYear = countBySite(yearReports);
		doRatio(countByNow, countByWeek, "price");
		doRatio(countByNow, countByMonth, "orderQty");
		doRatio(countByNow, countByYear, "saleQty");
		result.addAll(countByNow);
		return result;
	}
	private void doRatio(List<Map<String, Object>> result, List<Map<String, Object>> source, String key) {
		CollectionUtil.each(result, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> resultMap) {
				Map<String, Object> sourceMap = CollectionUtil.search(source,
						new IFunction<Map<String, Object>, Boolean>() {
							@Override
							public Boolean excute(Map<String, Object> item) {
								return ((String) resultMap.get("productType")).equals(((String) item.get("productType")));
							}
						});
				if (null != sourceMap) {
					double priceR = Double.parseDouble((String)resultMap.get("taxrate"));
					double priceS = Double.parseDouble((String) sourceMap.get("taxrate"));
					resultMap.put(key, sdf.format(((priceR - priceS) / priceS)*100) + "%");
				}
			}
		});
	}

	private List<Map<String, Object>> countBySite(List<SaleCatCountReportVO> reports) {
		List<Map<String, Object>> result = Lists.newArrayList();
		List<String> sites = Lists.newArrayList();
		CollectionUtil.each(reports, new IAction<SaleCatCountReportVO>() {
			@Override
			public void excute(SaleCatCountReportVO report) {
				if (!sites.contains(report.getSite())) {
					sites.add(report.getSite());
				}
			}
		});
		CollectionUtil.each(sites, new IAction<String>() {
			@Override
			public void excute(String site) {
				Map<String, Object> siteMap = Maps.newConcurrentMap();
				siteMap.put("productType", site);
				siteMap.put("deduction", 0.0);
				siteMap.put("account", 0);
				siteMap.put("site", 0);
				CollectionUtil.each(reports, new IAction<SaleCatCountReportVO>() {
					@Override
					public void excute(SaleCatCountReportVO report) {
						if (site.equals(report.getSite())) {
							double price = (double) siteMap.get("deduction");
							siteMap.put("deduction", price + report.getPrice());
							int orderQty = (int) siteMap.get("account");
							siteMap.put("account", orderQty + report.getOrderQty());
							int saleQty = (int) siteMap.get("site");
							siteMap.put("site", saleQty + report.getSaleQty());
						}
					}
				});
				double price = (double) siteMap.get("deduction");
				siteMap.put("deduction", sdf.format(price));
				siteMap.put("taxrate", sdf.format(price / CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(site))));
				double priceM = Double.parseDouble((String)siteMap.get("taxrate"));
				siteMap.put("currencycode", sdf.format(priceM * CurrencyRateCache.getCurrencyRate("RMB")));
				result.add(siteMap);
			}
		});
		return result;
	}

	private Map<String, Object> initCatFooter() {
		Map<String, Object> result = Maps.newConcurrentMap();
		result.put("productType", "站点");
		result.put("account", "订单总数");
		result.put("site", "卖的总数");
		result.put("price", "周环比");
		result.put("orderQty", "月环比");
		result.put("saleQty", "同比");
		result.put("deduction", "站点金额统计");
		result.put("taxrate", "美元统计");
		result.put("currencycode", "人民币统计");
		return result;
	}
}
