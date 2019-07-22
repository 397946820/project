package com.it.ocs.salesStatistics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.RequestParamUtils;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.model.SalesOrderModel;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.service.ISalesOrderService;
import com.it.ocs.salesStatistics.service.inner.BaseService;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.salesStatistics.utils.Tools;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;
import com.it.ocs.sys.vo.PermissionVO;

@Service
@Transactional
public class SalesOrderService extends BaseService implements ISalesOrderService {

	@Override
	public ResponseResult<SalesStatisticsVo> findAll(RequestParam param, PermissionVO permission, Boolean flag,
			String string) throws Exception {

		ResponseResult<SalesStatisticsVo> result = new ResponseResult<>();

		SalesStatisticsVo salesStatistics = BeanConvertUtil.mapToObject(param.getParam(), SalesStatisticsVo.class);

		if (!flag) {
			if (permission == null) {
				return result;
			}
		}

		// 两个时间都选择了
		String starttime = salesStatistics.getStarttime();
		String endtime = salesStatistics.getEndtime();
		if (StringUtils.isNotBlank(starttime) && StringUtils.isNotBlank(endtime)) {
			salesStatistics.setBegintime(TimeTools.getTime(starttime));
			Date date = TimeTools.getTime(endtime);
			salesStatistics.setStoptime(TimeTools.getChangeDay(date, 1));
		} else {
			salesStatistics.setStoptime(TimeTools.getChangeDay(TimeTools.getStoptime(), 1));
			salesStatistics.setBegintime(TimeTools.getTime("2000-01-01"));
		}

		String source = salesStatistics.getSource();

		List<SalesStatisticsModel> list = new ArrayList<>();

		String platform = salesStatistics.getPlatform();

		String station = salesStatistics.getStation();

		if (source.equals("amazon") || source.equals("walmart")) {
			// 判断账号站点
			List<String> platforms = new ArrayList<>();
			List<String> stations = new ArrayList<>();

			if (StringUtils.isBlank(platform)) {
				platforms = Tools.getPlatformOrStation(permission, source, "platform", "");
				stations = Tools.getPlatformOrStation(permission, source, "station", "");
			} else {
				if (StringUtils.isBlank(station)) {
					platforms.add(salesStatistics.getPlatform());
					stations = Tools.getPlatformOrStation(permission, source, "station", salesStatistics.getPlatform());
				} else {
					platforms.add(platform);
					stations.add(station);
				}
			}

			if (StringUtils.isBlank(string)) {
				PageHelper.startPage(param.getPage(), param.getRows());
			}

			list = salesStatisticsDao.queryDeatals(salesStatistics, platforms, stations, param.getSort(),
					param.getOrder());
			if (StringUtils.isBlank(string)) {
				PageInfo<SalesStatisticsModel> pageInfo = new PageInfo<>(list);
				result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), SalesStatisticsVo.class));
				result.setTotal((int) pageInfo.getTotal());
			} else {
				result.setRows(CollectionUtil.beansConvert(list, SalesStatisticsVo.class));
			}

		} else {

			Map<String, List<SalesStatisticsModel>> map = getMap(source, permission);

			List<String> keys = getKeys(source, permission, map.keySet());

			for (String key : map.keySet()) {
				List<SalesStatisticsModel> models = map.get(key);

				if (keys.contains(key)) {
					key = changeKey(key, source, platform, station,permission);
					String temp_ = key.substring(0, key.lastIndexOf("-"));
					if (temp_.equals("")) {
						continue;
					}
					// 这个时间段的
					List<SalesStatisticsModel> temp = getSpell(salesStatistics.getBegintime(),
							salesStatistics.getStoptime(), temp_, salesStatistics, models);
					list.addAll(temp);
				}
			}

			// 排序分页
			if (param.getSort() != null && param.getOrder() != null) {
				CollectionUtil.sort(list, param.getSort(), param.getOrder());
			} else {
				if (source.equals("amazon")) {
					CollectionUtil.sort(list, "purchaseat", "desc");
				} else if (source.equals("ebay")) {
					CollectionUtil.sort(list, "paidtime", "desc");
				} else if (source.equals("light")) {
					CollectionUtil.sort(list, "createdat", "desc");
				}
			}
			if (StringUtils.isBlank(string)) {
				List<SalesStatisticsModel> rows = CollectionUtil.pageList(list, param.getStartRow(), param.getEndRow());
				rows = getPrice(rows, source);
				result.setTotal(list.size());
				result.setRows(CollectionUtil.beansConvert(rows, SalesStatisticsVo.class));
			} else {
				result.setRows(CollectionUtil.beansConvert(list, SalesStatisticsVo.class));
			}
		}

		result.setSource(source);

		return result;
	}

	private List<SalesStatisticsModel> getPrice(List<SalesStatisticsModel> rows, String source) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (SalesStatisticsModel model : rows) {
				if (source.equals("amazon")) {
					if (model.getCount() != 0) {
						model.setUnitprice(new Double(sdf.format(model.getPrice() / model.getCount())));
					}
				} else {
					model.setPrice(new Double(sdf.format(model.getUnitprice() * model.getCount())));
				}
			}
		}
		return rows;
	}

	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String json,
			PermissionVO permission, Boolean flag) throws Exception {

		ResponseResult<SalesStatisticsVo> result = findAll(
				RequestParamUtils.getRequestParam(json, SalesStatisticsVo.class), permission, flag, "excel");

		String fileName = "销售订单" + Utils.getFileName() + ".xlsx";

		List<SalesOrderModel> models = CollectionUtil.beansConvert(result.getRows(), SalesOrderModel.class);

		List<String> list = getList(result.getSource());

		ExportExcelUtil.writeExcel(response, request, fileName, models, SalesOrderModel.class, null, list);
	}

	private List<String> getList(String source) {
		List<String> result = new ArrayList<>();
		result.add("sku");
		if (source.equals("amazon")) {
			result.add("Asin");
			result.add("购买时间");
			result.add("发货时间");
			result.add("订单更新时间");
		} else if (source.equals("ebay")) {
			result.add("最新拉取数据时间");
			result.add("支付时间 ");
			result.add("发货时间");
		} else if (source.equals("light")) {
			result.add("订单创建时间");
			result.add("订单更新时间");
		} else if (source.equals("walmart")) {
			result.add("订单创建时间");
		}
		result.add("账号");
		if (!source.equals("light") && !source.equals("walmart")) {
			result.add("站点");
		}
		result.add("币种");
		if (!source.equals("ebay")) {
			result.add("折扣额");
			result.add("税额");
		}
		result.add("金额");
		result.add("数量");
		result.add("单价");
		result.add("订单状态");
		return result;
	}

}
