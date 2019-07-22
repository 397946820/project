package com.it.ocs.cal.service.impl.support;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cal.vo.SaleDisCountReportVO;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.listener.CurrencyRateListener;
import com.it.ocs.salesStatistics.utils.Tools;

public class AmazonDisFooterSupport {
	private AmazonDisFooterSupport() {}
	private static AmazonDisFooterSupport instance;
	protected DecimalFormat sdf = new DecimalFormat("0.00");
	public static AmazonDisFooterSupport getInstance() {
		if (null == instance) {
			instance = new AmazonDisFooterSupport();
		}
		return instance;
	}
	public List<Map<String, Object>> getFooter(List<SaleDisCountReportVO> reports,
			List<SaleDisCountReportVO> weekReports, List<SaleDisCountReportVO> monthReports,
			List<SaleDisCountReportVO> yearReports, Map<String, Object> param) {
		CurrencyRateListener.onApplicationEvent();
		List<Map<String, Object>> result = Lists.newArrayList();
		result.add(initCatFooter());
		List<Map<String, Object>> countByNow = countByDisFlag(reports);
		List<Map<String, Object>> countByWeek = countByDisFlag(weekReports);
		List<Map<String, Object>> countByMonth = countByDisFlag(monthReports);
		List<Map<String, Object>> countByYear = countByDisFlag(yearReports);
		doRatio(countByNow, countByWeek, "deduction");
		doRatio(countByNow, countByMonth, "taxrate");
		doRatio(countByNow, countByYear, "currencycode");
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
								return ((String) resultMap.get("site")).equals(((String) item.get("site")));
							}
						});
				if (null != sourceMap) {
					double priceR = Double.parseDouble((String) resultMap.get("orderqty"));
					double priceS = Double.parseDouble((String) sourceMap.get("orderqty"));
					resultMap.put(key, sdf.format(((priceR - priceS) / priceS) * 100) + "%");
				}
			}
		});
	}

	private List<Map<String, Object>> countByDisFlag(List<SaleDisCountReportVO> reports) {
		List<Map<String, Object>> result = Lists.newArrayList();
		List<String> disFlags = Lists.newArrayList();
		CollectionUtil.each(reports, new IAction<SaleDisCountReportVO>() {
			@Override
			public void excute(SaleDisCountReportVO report) {
				if (!disFlags.contains(report.getDisFlag())) {
					disFlags.add(report.getDisFlag());
				}
			}
		});
		CollectionUtil.each(disFlags, new IAction<String>() {
			@Override
			public void excute(String disFlag) {
				Map<String, Object> disFlagMap = Maps.newConcurrentMap();
				disFlagMap.put("site", disFlag);
				disFlagMap.put("account", 0);
				disFlagMap.put("price", 0);
				disFlagMap.put("orderqty", 0.0);
				disFlagMap.put("saleqty", 0.0);
				CollectionUtil.each(reports, new IAction<SaleDisCountReportVO>() {
					@Override
					public void excute(SaleDisCountReportVO report) {
						if (disFlag.equals(report.getDisFlag())) {
							int orderQty = (int) disFlagMap.get("account");
							disFlagMap.put("account", orderQty + report.getOrderqty());
							int saleQty = (int) disFlagMap.get("price");
							disFlagMap.put("price", saleQty + report.getSaleqty());
							double priceM = (double) disFlagMap.get("orderqty");
							double priceR = (double) disFlagMap.get("saleqty");
							double reportPriceM = report.getPrice() / CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(report.getSite()));
							double reportPriceR = priceM * CurrencyRateCache.getCurrencyRate("RMB");
							disFlagMap.put("orderqty", priceM + reportPriceM);
							disFlagMap.put("saleqty", priceR + reportPriceR);
						}
					}
				});
				disFlagMap.put("orderqty", sdf.format(disFlagMap.get("orderqty")));
				disFlagMap.put("saleqty", sdf.format(disFlagMap.get("saleqty")));
				result.add(disFlagMap);
			}
		});
		return result;
	}

	private Map<String, Object> initCatFooter() {
		Map<String, Object> result = Maps.newConcurrentMap();
		result.put("site", "状态");
		result.put("account", "订单总数");
		result.put("price", "卖的数量");
		result.put("orderqty", "美元统计");
		result.put("saleqty", "人民币统计");
		result.put("deduction", "周环比");
		result.put("taxrate", "月环比");
		result.put("currencycode", "同比");
		
		return result;
	}
}
