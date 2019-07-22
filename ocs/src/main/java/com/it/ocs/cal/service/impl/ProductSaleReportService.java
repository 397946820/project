package com.it.ocs.cal.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.it.ocs.cal.constant.Ratio;
import com.it.ocs.cal.dao.IProductSaleReportDao;
import com.it.ocs.cal.service.IProductSaleReportService;
import com.it.ocs.cal.service.impl.support.AmazonCatFooterSupport;
import com.it.ocs.cal.service.impl.support.AmazonDisFooterSupport;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.SaleCatCountReportVO;
import com.it.ocs.cal.vo.SaleCountReportVO;
import com.it.ocs.cal.vo.SaleDisCountReportVO;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.synchronou.util.ValidationUtil;

@Service
public class ProductSaleReportService implements IProductSaleReportService {
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
	public List<SaleCountReportVO> findSaleCountReportList(RequestParam param) {
		Map<String, Object> map = param.getParam();
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		if (ValidationUtil.isNullOrEmpty(startTime) || ValidationUtil.isNullOrEmpty(endTime)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar ca = Calendar.getInstance();
			endTime = df.format(ca.getTime());
			ca.add(Calendar.MONTH, -1);
			startTime = df.format(ca.getTime());
			map.put("startTime", startTime);
			map.put("endTime", endTime);
		}
		List<SaleCountReportVO> skuCount = iProductSaleReportDao.queryByPage(map);
		return formartSaleCountData(skuCount);
	}

	private List<SaleCountReportVO> formartSaleCountData(List<SaleCountReportVO> skuCount) {
		// 获取数据汇总
		Double platform = 0d;
		Double amazon = 0d;
		Double ebay = 0d;
		Double light = 0d;
		for (SaleCountReportVO skuData : skuCount) {
			amazon = amazon + Double.parseDouble(skuData.getAmazonPrice());
			ebay = ebay + Double.parseDouble(skuData.getEbayPrice());
			light = light + Double.parseDouble(skuData.getLightPrice());
		}
		platform = amazon + ebay + light;
		for (SaleCountReportVO skuData : skuCount) {
			Map<String, Object> obj = new HashMap<>();
			Double amazonPrice = Double.parseDouble(skuData.getAmazonPrice());
			Double ebayPrice = Double.parseDouble(skuData.getEbayPrice());
			Double lightPrice = Double.parseDouble(skuData.getLightPrice());
			Double d = amazonPrice + ebayPrice + lightPrice;
			skuData.setTotal(String.valueOf(d));
			skuData.setAmazonPEC(Utils.getPercent(amazonPrice, amazon));
			skuData.setEbayPEC(Utils.getPercent(ebayPrice, ebay));
			skuData.setLightPEC(Utils.getPercent(lightPrice, light));
			skuData.setTotalPEC(Utils.getPercent(d, platform));
		}
		return skuCount;
	}

	@Override
	public ResponseResult<SaleDisCountReportVO> findSaleDisCountReportList(RequestParam param) throws ParseException {
		ResponseResult<SaleDisCountReportVO> result = new ResponseResult<SaleDisCountReportVO>();
		Map<String,Object> map = param.getParam();
		Map<String,Object> nowMap = doTime(map, Ratio.NOW);
		List<SaleDisCountReportVO> reports = iProductSaleReportDao.queryDisCount(nowMap);
		Map<String,Object> weekMap = doTime(map,Ratio.WEEK);
		List<SaleDisCountReportVO> weekReports = iProductSaleReportDao.queryDisCount(weekMap);
		Map<String,Object> monthMap = doTime(map,Ratio.MONTH);
		List<SaleDisCountReportVO> monthReports = iProductSaleReportDao.queryDisCount(monthMap);
		Map<String,Object> yearMap = doTime(map,Ratio.YEAR);
		List<SaleDisCountReportVO> yearReports = iProductSaleReportDao.queryDisCount(yearMap);
		result.setTotal(reports.size());
		AmazonDisFooterSupport support = AmazonDisFooterSupport.getInstance();
		List<Map<String,Object>> footers = support.getFooter(reports, weekReports, monthReports, yearReports, param.getParam());
		result.setFooter(footers);
		CollectionUtil.each(reports, new IAction<SaleDisCountReportVO>() {
			@Override
			public void excute(SaleDisCountReportVO disReport) {
				disReport.setSite(siteR.get(disReport.getSite()));
			}
		});
		result.setRows(splitPage(reports, param));
		return result;
	}

	@Override
	public ResponseResult<SaleCatCountReportVO> findSaleCatCountReportList(RequestParam param) throws ParseException {
		ResponseResult<SaleCatCountReportVO> result = new ResponseResult<SaleCatCountReportVO>();
		Map<String,Object> map = param.getParam();
		Map<String,Object> nowMap = doTime(map, Ratio.NOW);
		List<SaleCatCountReportVO> reports = filterCatReport(iProductSaleReportDao.queryCatCount(nowMap),map);
		Map<String,Object> weekMap = doTime(map,Ratio.WEEK);
		List<SaleCatCountReportVO> weekReports = filterCatReport(iProductSaleReportDao.queryCatCount(weekMap),map);
		Map<String,Object> monthMap = doTime(map,Ratio.MONTH);
		List<SaleCatCountReportVO> monthReports = filterCatReport(iProductSaleReportDao.queryCatCount(monthMap),map);
		Map<String,Object> yearMap = doTime(map,Ratio.YEAR);
		List<SaleCatCountReportVO> yearReports = filterCatReport(iProductSaleReportDao.queryCatCount(yearMap),map);
		result.setTotal(reports.size());
		AmazonCatFooterSupport support = AmazonCatFooterSupport.getInstance();
		List<Map<String,Object>> footers = support.getFooter(reports, weekReports, monthReports, yearReports, param.getParam());
		result.setFooter(footers);
		result.setRows(splitPage(reports, param));
		return result;
	}
	private List<SaleCatCountReportVO> filterCatReport(List<SaleCatCountReportVO> reports,Map<String,Object> param) {
		return CollectionUtil.searchList(reports, new IFunction<SaleCatCountReportVO, Boolean>() {
			@Override
			public Boolean excute(SaleCatCountReportVO report) {
				Boolean val = true;
				if (!ValidationUtil.isNullOrEmpty(param.get("account"))) {
					val = report.getAccount().equals(param.get("account"));
				}
				if (val && !ValidationUtil.isNullOrEmpty(param.get("site"))) {
					val = report.getSite().equals(param.get("site"));
				}
				if (val && !ValidationUtil.isNullOrEmpty(param.get("productType"))) {
					val = report.getProductType().equals(param.get("productType"));
				}
				return val;
			}
		});
	}
	private <T> List<T> splitPage(List<T> datas,RequestParam param) {
		if (CollectionUtil.isNullOrEmpty(datas)) {
			return datas;
		}
		int endIndex = param.getEndRow();
		if (endIndex > datas.size()) {
			endIndex = datas.size();
		}
		return datas.subList(param.getStartRow()-1, endIndex);
		
	}
	private Map<String,Object> doTime(Map<String,Object> map,Ratio ratio) throws ParseException {
		Map<String,Object> result = Maps.newConcurrentMap();
		String startt = (String)map.get("startTime");
		String endt = (String)map.get("endTime");
		CollectionUtil.each(map.keySet(), new IAction<String>() {
			@Override
			public void excute(String key) {
				result.put(key, ValidationUtil.isNullOrEmpty(map.get(key))?"":map.get(key));
			}
		});
		switch (ratio) {
		case NOW:
			if (ValidationUtil.isNullOrEmpty(startt)) {
				if (!ValidationUtil.isNullOrEmpty(endt)) {
					Date endTime = df.parse(endt);
					result.put("startTime", df.format(TimeTools.getChangeMonth(endTime, -1)));
					map.put("startTime", df.format(TimeTools.getChangeMonth(endTime, -1)));
				} else {
					Calendar ca = Calendar.getInstance();
					ca.add(Calendar.MONTH, -1);
					result.put("startTime", df.format(ca.getTime()));
					map.put("startTime", df.format(ca.getTime()));
				}
			}
			if (ValidationUtil.isNullOrEmpty(map.get("endTime"))) {
				if (!ValidationUtil.isNullOrEmpty(startt)) {
					Date endTime = df.parse(startt);
					result.put("endTime", df.format(TimeTools.getChangeMonth(endTime, 1)));
					map.put("endTime", df.format(TimeTools.getChangeMonth(endTime, 1)));
				} else {
					Calendar ca = Calendar.getInstance();
					result.put("endTime", df.format(ca.getTime()));
					map.put("endTime", df.format(ca.getTime()));
				}
			}
			break;
		case WEEK:
			result.put("startTime", df.format(TimeTools.getChangeDay(df.parse(startt),-7)));
			result.put("endTime", df.format(TimeTools.getChangeDay(df.parse(endt),-7)));
			break;
		case MONTH:
			result.put("startTime", df.format(TimeTools.getChangeMonth(df.parse(startt),-1)));
			result.put("endTime", df.format(TimeTools.getChangeMonth(df.parse(endt),-1)));
			break;
		default:
			result.put("startTime", df.format(TimeTools.getChangeYear(df.parse(startt))));
			result.put("endTime", df.format(TimeTools.getChangeYear(df.parse(endt))));
			break;
		}
		return result;
	}

	@Override
	public List<SaleCatCountReportVO> findProductTypes() {
		List<SaleCatCountReportVO> result = iProductSaleReportDao.findProductTypes();
		return result;
	}

	@Override
	public List<SaleCatCountReportVO> querySiteByAccount(String account) {
		return iProductSaleReportDao.findSitesByAccount(account);
	}
}
