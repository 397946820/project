package com.it.ocs.salesStatistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.RequestParamUtils;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.model.DetailsModel;
import com.it.ocs.salesStatistics.model.SalesStatisticsAllModel;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.service.IOrderQueryService;
import com.it.ocs.salesStatistics.service.ISalesStatisticsService;
import com.it.ocs.salesStatistics.service.inner.BaseService;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.salesStatistics.vo.SalesStatisticsAllVo;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;
import com.it.ocs.sys.vo.PermissionVO;

@Service
@Transactional
public class SalesStatisticsService extends BaseService implements ISalesStatisticsService {

	@Autowired
	private IOrderQueryService orderQueryService;

	@Override
	public ResponseResult<SalesStatisticsVo> findAll(RequestParam param, List<String> strings, PermissionVO permission,
			Boolean lean, String string, List<String> list) throws Exception {

		return getResponseResult(param, strings, permission, lean, string, list);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseResult<SalesStatisticsAllVo> findAllSoure(RequestParam param, List<String> columns,
			PermissionVO permissionVO, Boolean allSourceFlag, String string, List<String> columns2) throws Exception {
		List<SalesStatisticsAllVo> models = new ArrayList<>();
		String[] strings = new String[] { "amazon", "ebay", "light","walmart" };
		if (param.getParam() == null) {
			param.setParam(new HashMap<String, Object>());
		}
		Boolean flag = false;
		if (param.getParam().size() > 0) {
			if (param.getParam().get("sku") != null && !param.getParam().get("sku").toString().equals("")) {
				flag = true;
			}
		}
		for (String key : strings) {
			param.getParam().put("source", key);
			if (key.equals("amazon")) {
				param.getParam().put("whichTime", "purchaseat");
			} else if (key.equals("ebay")) {
				param.getParam().put("whichTime", "paidtime");
			} else if (key.equals("light")) {
				param.getParam().put("whichTime", "createdat");
			}
			ResponseResult<SalesStatisticsVo> result = null;
			if (key.equals("amazon") || key.equals("walmart")) {
				result = orderQueryService.findAll(param, columns, permissionVO, allSourceFlag, "", columns2);
			} else {
				result = findAll(param, columns, permissionVO, allSourceFlag, "", columns2);
			}
			List footer = result.getFooter();
			LinkedHashMap<String, Object> map = com.it.ocs.salesStatistics.utils.Tools
					.getMapByMap((LinkedHashMap<String, Object>) footer.get(0));
			for (Object object : footer) {
				LinkedHashMap<String, Object> hashMap = (LinkedHashMap<String, Object>) object;
				String account = hashMap.get(map.get("账号/站点").toString()).toString();
				if (account.equals("总汇总")) {
					SalesStatisticsAllVo model = new SalesStatisticsAllVo();
					model.setTerrace(key);
					if (flag) {
						model.setSku(param.getParam().get("sku").toString());
					}
					model.setQty(new Long(hashMap.get(map.get("订单总数").toString()).toString()));
					model.setCount(new Long(hashMap.get(map.get("卖的总数").toString()).toString()));
					model.setPriceUsd(new Double(hashMap.get(map.get("美元统计").toString()).toString()));
					model.setPriceRmb(new Double(hashMap.get(map.get("人民币统计").toString()).toString()));
					model.setTotalAf(new Double(hashMap.get(map.get("af总毛利额(美元)").toString()).toString()));
					model.setTotalSf(new Double(hashMap.get(map.get("sf总毛利额(美元)").toString()).toString()));
					model.setTotalCo(new Double(hashMap.get(map.get("co总毛利额(美元)").toString()).toString()));
					model.setTotalAfRate(new Double(hashMap.get(map.get("af总毛润率").toString()).toString()));
					model.setTotalSfRate(new Double(hashMap.get(map.get("sf总毛润率").toString()).toString()));
					model.setTotalCoRate(new Double(hashMap.get(map.get("co总毛润率").toString()).toString()));
					model.setSametermrate(hashMap.get(map.get("同比").toString()).toString());
					model.setWeekrate(hashMap.get(map.get("周环比").toString()).toString());
					model.setMonthrate(hashMap.get(map.get("月环比").toString()).toString());
					models.add(model);
				}
			}
		}
		ResponseResult<SalesStatisticsAllVo> result = new ResponseResult<>();
		result.setRows(models);
		result.setTotal(models.size());
		if (StringUtils.isBlank(string)) {
			if (param.getParam().containsKey("sku")) {
				String sku = param.getParam().get("sku").toString();
				if (StringUtils.isNotBlank(sku)) {
					List<Map<String, Object>> footer = new ArrayList<>();
					Map<String, Object> map = new HashMap<>();
					map.put("footer", true);
					map.put("monthrate", "co总毛利额(美元)");
					map.put("weekrate", "sf总毛利额(美元)");
					map.put("sametermrate", "af总毛利额(美元)");
					map.put("totalCoRate", "人民币统计");
					map.put("totalSfRate", "美元统计");
					map.put("totalAfRate", "卖的总数");
					map.put("totalCo", "订单总数");
					footer.add(map);
					Map<String, Object> hashMap = new HashMap<>();
					Long qty = 0L;
					Long count = 0L;
					Double priceUsd = 0d;
					Double priceRmb = 0d;
					Double totalAf = 0d;
					Double totalSf = 0d;
					Double totalCo = 0d;
					for (SalesStatisticsAllVo model : models) {
						qty += model.getQty();
						count += model.getCount();
						priceUsd += model.getPriceUsd();
						priceRmb += model.getPriceRmb();
						totalAf += model.getTotalAf();
						totalSf += model.getTotalSf();
						totalCo += model.getTotalCo();
					}
					hashMap.put("footer", true);
					hashMap.put("totalSf", "汇总");
					hashMap.put("monthrate", sdf.format(totalCo));
					hashMap.put("weekrate", sdf.format(totalSf));
					hashMap.put("sametermrate", sdf.format(totalAf));
					hashMap.put("totalCoRate", sdf.format(priceRmb));
					hashMap.put("totalSfRate", sdf.format(priceUsd));
					hashMap.put("totalAfRate", count);
					hashMap.put("totalCo", qty);
					footer.add(hashMap);

					result.setFooter(footer);
				}
			}
		}
		return result;
	}

	// 获取sku的详情
	@Override
	public ResponseResult<SalesStatisticsVo> querySkuDetails(RequestParam param, String string) throws Exception {

		return getResponseResult_(param, string);
	}

	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String json, List<String> list,
			PermissionVO permissionVO, Boolean flag, List<String> list2) {

		try {
			String fileName = "销售统计数据" + Utils.getFileName() + ".xlsx";

			RequestParam param = RequestParamUtils.getRequestParam(json, SalesStatisticsVo.class);
			String source = param.getParam().get("source").toString();
			if (source.equals("allSource") || source.equals("")) {
				ResponseResult<SalesStatisticsAllVo> result = findAllSoure(param, list, permissionVO, flag, "excel",
						list2);
				List<SalesStatisticsAllModel> models = CollectionUtil.beansConvert(result.getRows(),
						SalesStatisticsAllModel.class);
				ExportExcelUtil.writeExcel(response, request, fileName, models, SalesStatisticsAllModel.class, null,
						null);
			} else {
				ResponseResult<SalesStatisticsVo> result = null;
				if ((source.equals("light") || source.equals("ebay"))) {
					result = findAll(param, list, permissionVO, flag, "excel", null);
				} else if (source.equals("amazon") || source.equals("walmart")) {
					result = orderQueryService.findAll(param, list, permissionVO, flag, "excel", list2);
				}

				List<SalesStatisticsModel> models = CollectionUtil.beansConvert(result.getRows(),
						SalesStatisticsModel.class);

				list = com.it.ocs.salesStatistics.utils.Tools.changeStrings(list, result.getSource(), "excel");

				if (list.contains("af毛利额(15%)") || list.contains("sf毛利额(22%)") || list.contains("co毛利额(15%)")
						|| list.contains("af利润率") || list.contains("sf利润率") || list.contains("co利润率")) {
					models = changeRows(models, result.getSource());
				}

				ExportExcelUtil.writeExcel(response, request, fileName, models, SalesStatisticsModel.class, null, list);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void detailsExcel(HttpServletRequest request, HttpServletResponse response, String json,
			List<String> details) {

		try {
			RequestParam param = RequestParamUtils.getRequestParam(json, SalesStatisticsVo.class);

			ResponseResult<SalesStatisticsVo> result = querySkuDetails(param, "excel");

			List<SalesStatisticsModel> models = CollectionUtil.beansConvert(result.getRows(),
					SalesStatisticsModel.class);

			details = com.it.ocs.salesStatistics.utils.Tools.changeDetails(details, result.getSource());

			if (details.contains("详情af利润率") || details.contains("详情sf利润率") || details.contains("详情co利润率")) {
				models = changeRows(models, result.getSource());
			}

			List<DetailsModel> detailsModels = new ArrayList<>();

			for (SalesStatisticsModel model : models) {
				DetailsModel detailsModel = new DetailsModel();
				BeanUtils.copyProperties(model, detailsModel);
				detailsModels.add(detailsModel);
			}
			String fileName = "sku详情数据" + Utils.getFileName() + ".xlsx";
			ExportExcelUtil.writeExcel(response, request, fileName, detailsModels, DetailsModel.class, null, details);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public ResponseResult<SalesStatisticsVo> findOrderDetails(RequestParam param) throws Exception {
		ResponseResult<SalesStatisticsVo> result = new ResponseResult<>();

		SalesStatisticsVo model = BeanConvertUtil.mapToObject(param.getParam(), SalesStatisticsVo.class);

		if (model != null) {
			// 自定义时间
			if (model.getStarttime() != null && model.getEndtime() == null) {
				// 选择了开始时间 未选择结束时间 默认往后推一个月
				model.setBegintime(TimeTools.strToDate(model.getStarttime()));
				model.setStoptime(TimeTools.getChangeMonth(model.getBegintime(), 1));

			} else if (model.getStarttime() == null && model.getEndtime() != null) {
				// 选择了结束时间 未选择开始时间 默认往前推一个月
				model.setStoptime(TimeTools.strToDate(model.getEndtime()));
				model.setBegintime(TimeTools.getChangeMonth(model.getStoptime(), -1));

			} else if (model.getStarttime() != null && model.getEndtime() != null) {
				model.setBegintime(TimeTools.strToDate(model.getStarttime()));
				model.setStoptime(TimeTools.strToDate(model.getEndtime()));
			}
			model.setWhichTime("updatedat");
		} else {
			model = new SalesStatisticsVo();
			model.setWhichTime("updatedat");
		}
		
		PageHelper.startPage(param.getPage(), param.getRows());
		
		List<SalesStatisticsModel> list = salesStatisticsDao.findOrder(model);

		PageInfo<SalesStatisticsModel> pageInfo = new PageInfo<>(list);
		
		List<SalesStatisticsVo> rows = CollectionUtil.beansConvert(pageInfo.getList(), SalesStatisticsVo.class);
		
		String flag = "&nbsp;&nbsp;&nbsp;&nbsp;";
		Map<String, String> map = new HashMap<>();
		for (SalesStatisticsVo statisticsVo : rows) {
			String url = "";
			if (StringUtils.isNotBlank(statisticsVo.getAsin()) && StringUtils.isNotBlank(statisticsVo.getPlatform())) {
				map = salesStatisticsDao.findImgAndUrl(statisticsVo.getAsin(), statisticsVo.getPlatform());
				if (map != null) {
					statisticsVo.setProductImg(map.get("IMAGE"));
					url = map.get("URL");
				}
			}
			StringBuffer buffer = new StringBuffer();
			String title = statisticsVo.getTitle();
			if (title != null) {
				if (title.length() > 51) {
					title = title.substring(0, 50);
				}
				if (!url.equals("")) {
					buffer.append("<font color='blue'>" + statisticsVo.getOrderId() + "</font>").append("<br/><br/>")
							.append("<B><a href='" + url + "' target='_blank'>" + title + "</a></B>");
					statisticsVo.setUrl(url);
				} else {
					buffer.append("<font color='blue'>" + statisticsVo.getOrderId() + "</font>").append("<br/><br/>")
							.append("<B>" + title + "</B>");
				}

				buffer.append("<br/>").append("QTY :" + statisticsVo.getCount()).append(flag)
						.append("ASIN: " + statisticsVo.getAsin()).append(flag + flag).append("SKU: ")
						.append(statisticsVo.getSku()).append("<br/><br/>");

			} else {
				buffer.append(
						"<font color='blue'>" + statisticsVo.getOrderId() + "</font><br/><br/><br/><br/><br/><br/>");
			}
			buffer.append("Contact Buyer: <font color='blue'>" + statisticsVo.getCustomerName() + "</font>")
					.append("<br/>").append("Sales Channel: " + statisticsVo.getStation()).append("<br/>")
					.append("Fulfillment method: Amazon").append("<br/>")
					.append("Billing Country: " + statisticsVo.getPlatform());
			statisticsVo.setDetails(buffer.toString());
			statisticsVo.setCurrencySymbol(Tools.getCurrencyCode(statisticsVo.getPlatform()));
		}
		result.setRows(rows);
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public List<String> getEbayStation() {
		return salesStatisticsDao.getEbayStation();
	}
}
