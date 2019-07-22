package com.it.ocs.salesStatistics.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Sets;
import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cache.PricePlanCache;
import com.it.ocs.cal.common.IBaseService;
import com.it.ocs.cal.dao.IPricePlanDao;
import com.it.ocs.cal.dao.ITaximeterDao;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.model.TaximeterModel;
import com.it.ocs.cal.utils.PricePlanUtils;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.listener.CurrencyRateListener;
import com.it.ocs.salesStatistics.dao.ISalesStatisticsDao;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.service.IOrderQueryService;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.salesStatistics.utils.Tools;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;
import com.it.ocs.sys.vo.PermissionVO;

@Service
@Transactional
public class OrderQueryService extends IBaseService implements IOrderQueryService {

	@Autowired
	private ISalesStatisticsDao salesStatisticsDao;

	@Autowired
	private ITaximeterDao taximeterDao;

	@Autowired
	private IPricePlanDao pricePlanDao;

	protected String[] types = { "af", "sf", "co" };

	// 保留四位小数
	protected DecimalFormat df = new DecimalFormat("0.0000");
	// 保留两位小数
	protected DecimalFormat sdf = new DecimalFormat("0.00");

	@Override
	public ResponseResult<SalesStatisticsVo> findAll(RequestParam param, List<String> columns, PermissionVO permission,
			Boolean allSourceFlag, String string, List<String> columns2) throws Exception {

		ResponseResult<SalesStatisticsVo> result = new ResponseResult<>();
		if (!allSourceFlag) {
			if (permission == null) {
				return result;
			}
		}

		SalesStatisticsVo salesStatistics = BeanConvertUtil.mapToObject(param.getParam(), SalesStatisticsVo.class);
		if (salesStatistics == null) {
			return result;
		}

		CurrencyRateListener.onApplicationEvent();

		// 时间处理
		salesStatistics = getsalesStatistics(salesStatistics);
		// 来源
		String source = salesStatistics.getSource();
		// 判断账号站点
		List<String> platforms = new ArrayList<>();
		List<String> stations = new ArrayList<>();

		if (StringUtils.isBlank(salesStatistics.getPlatform())) {
			platforms = Tools.getPlatformOrStation(permission, source, "platform", "");
			stations = Tools.getPlatformOrStation(permission, source, "station", "");
		} else {
			if (StringUtils.isBlank(salesStatistics.getStation())) {
				platforms.add(salesStatistics.getPlatform());
				stations = Tools.getPlatformOrStation(permission, source, "station", salesStatistics.getPlatform());
			} else {
				platforms.add(salesStatistics.getPlatform());
				stations.add(salesStatistics.getStation());
			}
		}

		if (string.equals("")) {
			PageHelper.startPage(param.getPage(), param.getRows());
		}
		List<SalesStatisticsModel> list1 = salesStatisticsDao.query(salesStatistics, platforms, stations,
				param.getSort(), param.getOrder());

		if (string.equals("")) {
			// 汇总
			List<LinkedHashMap<String, Object>> footers = new ArrayList<>();
			columns = Tools.changeStrings(columns, source, "footer");
			LinkedHashMap<String, Object> hashMap = Tools.getMap(columns, columns2, source);
			footers.add(hashMap);
			LinkedHashMap<String, Object> linkedHashMap = Tools.getMapByMap(hashMap);
			// 标记按站点汇总还是账号汇总
			String flag = "";
			if (source.equals("amazon")) {
				if (StringUtils.isBlank(salesStatistics.getPlatform())) {
					if (!allSourceFlag) {
						if (platforms.size() < 2) {
							// 站点汇总
							footers = getFooters(footers, linkedHashMap, platforms, stations, "station",
									salesStatistics, columns2);
							flag = "station";
						} else {
							// 账号汇总
							footers = getFooters(footers, linkedHashMap, platforms, stations, "platform",
									salesStatistics, columns2);
							flag = "platform";
						}
					} else {
						// 账号汇总
						footers = getFooters(footers, linkedHashMap, platforms, stations, "platform", salesStatistics,
								columns2);
						flag = "platform";
					}
				} else {
					// 站点汇总
					footers = getFooters(footers, linkedHashMap, platforms, stations, "station", salesStatistics,
							columns2);
					flag = "station";
				}
			} else {
				// 账号汇总
				footers = getFooters(footers, linkedHashMap, platforms, stations, "platform", salesStatistics,
						columns2);
				flag = "platform";
			}
			List<SalesStatisticsModel> list = null;
			for (int i = 1; i < footers.size(); i++) {
				LinkedHashMap<String, Object> footerMap = footers.get(i);
				if (!CollectionUtil.isNullOrEmpty(columns2)) {
					if (columns2.contains("af总毛利额(美元)")) {
						Object object = footerMap.get(linkedHashMap.get("美元统计").toString());
						footerMap.put(linkedHashMap.get("af总毛利额(美元)").toString(),
								sdf.format(new Double(object.toString()) * 0.15));
						footerMap.put(linkedHashMap.get("sf总毛利额(美元)").toString(),
								sdf.format(new Double(object.toString()) * 0.22));
						footerMap.put(linkedHashMap.get("co总毛利额(美元)").toString(),
								sdf.format(new Double(object.toString()) * 0.15));
					}
					if (columns2.contains("af总毛润率")) {
						// 站号或者站点
						String account = footerMap.get(linkedHashMap.get("账号/站点").toString()).toString();
						// 总数
						int total = new Integer(footerMap.get(linkedHashMap.get("卖的总数").toString()).toString());
						if (list == null) {
							list = salesStatisticsDao.queryRate(salesStatistics, platforms, stations);
						}
						changeFooterMap(footerMap, list, account, source, flag, linkedHashMap, total);

					}
				}
			}

			PageInfo<SalesStatisticsModel> pageInfo = new PageInfo<>(list1);
			List<SalesStatisticsModel> changeRows = changeRows(pageInfo.getList(), source);
			if (source.equals("amazon") && (columns.contains("actualSalesPrice") || columns.contains("actualYield")
					|| columns.contains("actualProfitCoefficient"))) {
				profitCoefficient(changeRows, salesStatistics);
			}
			result.setRows(CollectionUtil.beansConvert(changeRows, SalesStatisticsVo.class));

			result.setTotal((int) pageInfo.getTotal());
			if (footers.size() == 1) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				CollectionUtil.each(hashMap.keySet(), new IAction<String>() {
					@Override
					public void excute(String obj) {
						if (hashMap.get(obj).toString().equals("账号/站点")) {
							map.put(obj, "总汇总");
						} else {
							map.put(obj, 0);
						}
					}
				});
				footers.add(map);
			}
			result.setFooter(footers);
		} else {
			List<SalesStatisticsModel> changeRows = changeRows(list1, source);
			if (source.equals("amazon") && (columns.contains("真实售价_actualSalesPrice")
					|| columns.contains("真实售价的利润率_actualYield") || columns.contains("利润系数_actualProfitCoefficient"))) {
				profitCoefficient(changeRows, salesStatistics);
			}
			result.setRows(CollectionUtil.beansConvert(changeRows, SalesStatisticsVo.class));
		}
		result.setSource(source);
		return result;
	}

	private void profitCoefficient(List<SalesStatisticsModel> changeRows, SalesStatisticsVo salesStatistics)
			throws Exception {
		if (!CollectionUtil.isNullOrEmpty(changeRows)) {
			Set<String> skus = new HashSet<>();
			CollectionUtil.each(changeRows, new IAction<SalesStatisticsModel>() {
				@Override
				public void excute(SalesStatisticsModel obj) {
					if (StringUtils.isNotBlank(obj.getSku())) {
						skus.add(obj.getSku());
					}
				}
			});
			Map<String, PricePlanModel> hash = PricePlanCache.getMap(pricePlanDao.findBySkus(new ArrayList<>(skus)));
			String start = TimeTools.dateToString(salesStatistics.getBegintime());
			String end = TimeTools.dateToString(salesStatistics.getStoptime());
			Map<String, Map<String, Object>> map = tease(-1L);
			// 获取美元转人民币的汇率
			Double parities = CurrencyRateCache.getCurrencyRate("RMB");
			for (SalesStatisticsModel model : changeRows) {
				if (StringUtils.isNotBlank(model.getSku())) {
					PricePlanModel planModel = hash.get(Tools.getCountry(model.getStation()) + model.getSku() + "af");
					List<TaximeterModel> list = taximeterDao.queryBySku(start, end, model.getSku(),
							Tools.getPlatForm(model.getPlatform()));
					if (!CollectionUtil.isNullOrEmpty(list) && planModel != null && model.getActualSalesPrice() > 0) {
						Double cost = getCost(list, parities, map);
						if (cost != 0) {
							model.setActualYield(new Double(df.format(1 / (1 + planModel.getVat())
									- cost / model.getActualSalesPrice() - (planModel.getProfitRate()
											+ planModel.getUnfulliableRate() + planModel.getReplacementRate()))));
							model.setActualProfitCoefficient(
									new Double(df.format(model.getActualYield() / planModel.getProfitRate())));
						}

					}
				}
			}
		}
	}

	private Double getCost(List<TaximeterModel> result, Double parities, Map<String, Map<String, Object>> map)
			throws Exception {
		// 采购加权单价
		for (int i = 0; i < result.size(); i++) {
			Double sumprice = 0d; // 总价
			Double sumqty = 0d; // 总数
			Double unitPrice = 0d; // 单价
			for (int j = i; j < result.size(); j++) {
				// 计算采购加权单价
				if (result.get(j).getCurrencyCode().equals("USD")) {
					// 采购单价换成人民币
					unitPrice = result.get(j).getUnitPrice() * parities;
				} else {
					unitPrice = result.get(j).getUnitPrice();
				}
				sumprice += unitPrice * result.get(j).getQty();
				sumqty += result.get(j).getQty();
			}
			result.get(i).setWeightedprice(new Double(df.format(sumprice / sumqty)));
		}

		Long qty = 0L; // 累计发货数量
		Double cost = 0d; // 累计成本
		PricePlanModel pricePlan = null;
		// 结存库存单价
		for (int i = 0; i < result.size(); i++) {
			Long balanceamount = 0L; // 累计实际库存
			Double amount = 0d; // 累计库存金额
			for (int j = i; j < result.size(); j++) {
				TaximeterModel model = result.get(j);
				pricePlan = calculatePlanPrice(pricePlan, model.getSku(),
						Tools.getCountry(model.getCountry().substring(0, 2)), model.getShippingtype(),
						model.getWeightedprice(), null, map);
				if (pricePlan == null) {
					continue;
				}
				if (model.getRepertory() > 0) {
					// 库存金额
					balanceamount += model.getRepertory();
					amount += model.getRepertory() * pricePlan.getFinalCost();
				}
				pricePlan = null;
			}
			qty += result.get(i).getQty();
			if (balanceamount != 0L) {
				cost += (amount / balanceamount) * result.get(i).getQty();
			}
		}
		return new Double(df.format(cost / qty));
	}

	private void changeFooterMap(LinkedHashMap<String, Object> footerMap, List<SalesStatisticsModel> list,
			String account, String source, String flag, LinkedHashMap<String, Object> hashMap, int total) {
		if (StringUtils.isNotBlank(flag)) {
			if (!CollectionUtil.isNullOrEmpty(list)) {
				double af = 0d, sf = 0d, co = 0d;
				for (SalesStatisticsModel model : list) {
					String string = getString(source, model);
					if (!account.equals("总汇总")) {
						if (flag.equals("station")) {
							if (model.getStation().equals(account)) {
								if (model.getType().equals("af")) {
									af += model.getPrice();
								} else if (model.getType().equals("sf")) {
									sf += model.getPrice();
								} else if (model.getType().equals("co")) {
									co += model.getPrice();
								}
							}
						} else if (flag.equals("platform")) {
							if (model.getPlatform().equals(account)) {
								if (model.getType().equals("af")) {
									af += model.getPrice()
											/ CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string));
								} else if (model.getType().equals("sf")) {
									sf += model.getPrice()
											/ CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string));
								} else if (model.getType().equals("co")) {
									co += model.getPrice()
											/ CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string));
								}
							}
						}
					} else {
						if (model.getType().equals("af")) {
							af += model.getPrice() / CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string));
						} else if (model.getType().equals("sf")) {
							sf += model.getPrice() / CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string));
						} else if (model.getType().equals("co")) {
							co += model.getPrice() / CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string));
						}
					}
				}

				if (total == 0) {
					footerMap.put(hashMap.get("af总毛润率").toString(), 0);
					footerMap.put(hashMap.get("sf总毛润率").toString(), 0);
					footerMap.put(hashMap.get("co总毛润率").toString(), 0);
				} else {
					footerMap.put(hashMap.get("af总毛润率").toString(), sdf.format(af / total));
					footerMap.put(hashMap.get("sf总毛润率").toString(), sdf.format(sf / total));
					footerMap.put(hashMap.get("co总毛润率").toString(), sdf.format(co / total));
				}
			}
		}

	}

	/*
	private Boolean getFlag(SalesStatisticsVo salesStatistics) {
		Boolean flag = false;
		String source = salesStatistics.getSource();
		String sku = salesStatistics.getSku();
		String status = salesStatistics.getStatus();
		if (source.equals("amazon")) {
			if (StringUtils.isBlank(sku)) {
				flag = true;
			}
		} else {
			if (StringUtils.isBlank(sku) && StringUtils.isBlank(source)) {
				flag = true;
			}
		}
		return flag;
	}
	*/

	private List<LinkedHashMap<String, Object>> getFooters(List<LinkedHashMap<String, Object>> footers,
			LinkedHashMap<String, Object> linkedHashMap, List<String> platforms, List<String> stations, String string,
			SalesStatisticsVo salesStatistics, List<String> columns) throws Exception {
		String source = salesStatistics.getSource();
		List<SalesStatisticsModel> list = salesStatisticsDao.query(salesStatistics, platforms, stations, null, null);
		if (!CollectionUtil.isNullOrEmpty(list)) {
			if (string.equals("station")) {
				for (String station : stations) {
					SalesStatisticsModel model = new SalesStatisticsModel();
					model.setStation(station);
					for (SalesStatisticsModel statisticsModel : list) {
						if (statisticsModel.getStation().equals(station)) {
							model = getModel(model, statisticsModel, source);
						}
					}
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put("footer", true);
					map.put(linkedHashMap.get("账号/站点").toString(), model.getStation());
					map = getMap(model, string, linkedHashMap, map, source);
					footers.add(map);
				}
			} else if (string.equals("platform")) {
				for (String platform : platforms) {
					SalesStatisticsModel model = new SalesStatisticsModel();
					model.setPlatform(platform);
					for (SalesStatisticsModel statisticsModel : list) {
						if (statisticsModel.getPlatform().equals(platform)) {
							model = getModel(model, statisticsModel, source);
						}
					}
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put("footer", true);
					map.put(linkedHashMap.get("账号/站点").toString(), model.getPlatform());
					map = getMap(model, string, linkedHashMap, map, source);
					footers.add(map);
				}
			}
			SalesStatisticsModel model = new SalesStatisticsModel();
			for (SalesStatisticsModel statisticsModel : list) {
				model = getModel(model, statisticsModel, source);
			}
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put("footer", true);
			map.put(linkedHashMap.get("账号/站点").toString(), "总汇总");
			map = getMap(model, "总汇总", linkedHashMap, map, source);
			footers.add(map);
		}
		return footers;
	}

	private String getString(String source, SalesStatisticsModel statisticsModel) {
		String string = null;
		if (source.equals("amazon")) {
			string = statisticsModel.getStation();
		} else if (source.equals("walmart")) {
			string = statisticsModel.getPlatform();
		}
		return string;
	}

	private SalesStatisticsModel getModel(SalesStatisticsModel model, SalesStatisticsModel statisticsModel,
			String source) {
		String string = getString(source, statisticsModel);
		model.setQty(model.getQty() + statisticsModel.getQty());
		model.setCount(model.getCount() + statisticsModel.getCount());
		model.setPrice(model.getPrice()
				+ statisticsModel.getPrice() / CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string)));

		model.setLastYearPrice(model.getLastYearPrice() + statisticsModel.getLastYearPrice()
				/ CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string)));

		model.setLastMonthPrice(model.getLastMonthPrice() + statisticsModel.getLastMonthPrice()
				/ CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string)));

		model.setLastWeekPrice(model.getLastWeekPrice() + statisticsModel.getLastWeekPrice()
				/ CurrencyRateCache.getCurrencyRate(Tools.getCurrencyCode(string)));
		return model;
	}

	private LinkedHashMap<String, Object> getMap(SalesStatisticsModel model, String string,
			LinkedHashMap<String, Object> linkedHashMap, LinkedHashMap<String, Object> map, String source) {
		if (string.equals("station")) {
			map.put(linkedHashMap.get("站点金额统计").toString(),
					sdf.format(model.getPrice() * CurrencyRateCache.getCurrencyRate(Tools
							.getCurrencyCode(source.equals("amazon") ? model.getStation() : model.getPlatform()))));
		}
		if (model.getLastYearPrice() > 0
				&& (model.getPrice() - model.getLastYearPrice()) / model.getLastYearPrice() != 0) {
			map.put(linkedHashMap.get("同比").toString(),
					sdf.format((model.getPrice() - model.getLastYearPrice()) / model.getLastYearPrice() * 100) + "%");
		} else {
			map.put(linkedHashMap.get("同比").toString(), 0);
		}

		if (model.getLastMonthPrice() > 0
				&& (model.getPrice() - model.getLastMonthPrice()) / model.getLastMonthPrice() != 0) {
			map.put(linkedHashMap.get("月环比").toString(),
					sdf.format((model.getPrice() - model.getLastMonthPrice()) / model.getLastMonthPrice() * 100) + "%");
		} else {
			map.put(linkedHashMap.get("月环比").toString(), 0);
		}
		if (model.getLastWeekPrice() > 0
				&& (model.getPrice() - model.getLastWeekPrice()) / model.getLastWeekPrice() != 0) {
			map.put(linkedHashMap.get("周环比").toString(),
					sdf.format((model.getPrice() - model.getLastWeekPrice()) / model.getLastWeekPrice() * 100) + "%");
		} else {
			map.put(linkedHashMap.get("周环比").toString(), 0);
		}
		map.put(linkedHashMap.get("订单总数").toString(), model.getQty());
		map.put(linkedHashMap.get("卖的总数").toString(), model.getCount());
		map.put(linkedHashMap.get("美元统计").toString(), sdf.format(model.getPrice()));
		map.put(linkedHashMap.get("人民币统计").toString(),
				sdf.format(model.getPrice() * CurrencyRateCache.getCurrencyRate("RMB")));

		return map;
	}

	public List<SalesStatisticsModel> changeRows(List<SalesStatisticsModel> rows, String source) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			Set<String> skus = Sets.newHashSet();
			CollectionUtil.each(rows, new IAction<SalesStatisticsModel>() {
				@Override
				public void excute(SalesStatisticsModel obj) {
					if (StringUtils.isNotBlank(obj.getSku())) {
						skus.add(obj.getSku());
					}
				}
			});
			Map<String, PricePlanModel> map = PricePlanCache.getMap(pricePlanDao.findBySkus(new ArrayList<>(skus)));
			for (SalesStatisticsModel model : rows) {
				Double unitprice = 0d;
				if (model.getCount() > 0) {
					unitprice = model.getPrice() / model.getCount();
					model.setActualSalesPrice(new Double(df.format(unitprice)));
				} else {
					model.setActualSalesPrice(0d);
				}
				String country = "";
				if (source.equals("light") || source.equals("walmart")) {
					country = model.getPlatform();
					country = country.equals("USA") ? "US" : country;
				} else {
					country = model.getStation();
				}
				String sku = model.getSku();
				if (StringUtils.isNotBlank(sku)) {
					for (String type : types) {
						PricePlanModel pricePlanModel = map.get(Tools.getCountry(country) + sku + type);
						if (pricePlanModel != null) {
							Double finalPrice = pricePlanModel.getFinalPrice();
							if (pricePlanModel.getProfitRateAction() != 1) {
								finalPrice = PricePlanUtils.getFinalPrice(pricePlanModel);
							}
							if (type.equals("af")) {
								model.setAfRate(new Double(df.format(unitprice / finalPrice)));
							} else if (type.equals("sf")) {
								model.setSfRate(new Double(df.format(unitprice / finalPrice)));
							} else if (type.equals("co")) {
								model.setCoRate(new Double(df.format(unitprice / finalPrice)));
							}
						}
					}
				}
			}
		}
		return rows;
	}

	private SalesStatisticsVo getsalesStatistics(SalesStatisticsVo salesStatistics) throws Exception {
		if (!salesStatistics.getTimeQuantum().equals("0")) {
			// 选了时间段
			salesStatistics.setStoptime(TimeTools.getEndTime());

			if (salesStatistics.getTimeQuantum().equals("-30")) {
				// 一个月月份直接减一
				salesStatistics.setBegintime(TimeTools.getChangeMonth(salesStatistics.getStoptime(), -1));
			} else {
				salesStatistics.setBegintime(TimeTools.getChangeDay(salesStatistics.getStoptime(),
						new Integer(salesStatistics.getTimeQuantum())));
			}
		} else {
			// 自定义时间
			if (salesStatistics.getStarttime() != null && salesStatistics.getEndtime() == null) {
				// 选择了开始时间 未选择结束时间 默认往后推一个月
				salesStatistics.setBegintime(TimeTools.strToDate(salesStatistics.getStarttime()));
				salesStatistics.setStoptime(TimeTools.getChangeMonth(salesStatistics.getBegintime(), 1));

			} else if (salesStatistics.getStarttime() == null && salesStatistics.getEndtime() != null) {
				// 选择了结束时间 未选择开始时间 默认往前推一个月
				salesStatistics.setStoptime(TimeTools.strToDate(salesStatistics.getEndtime()));
				salesStatistics.setBegintime(TimeTools.getChangeMonth(salesStatistics.getStoptime(), -1));

			} else {
				salesStatistics.setBegintime(TimeTools.strToDate(salesStatistics.getStarttime()));
				salesStatistics.setStoptime(TimeTools.strToDate(salesStatistics.getEndtime()));
			}
		}
		if (salesStatistics.getSource().equals("walmart")) {
			salesStatistics.setBegintime(TimeTools.getTimeByStation(salesStatistics.getBegintime(), "USA"));
			Date stopTime = TimeTools.getTimeByStation(salesStatistics.getStoptime(), "USA");
			salesStatistics.setStoptime(TimeTools.getChangeDay(stopTime, 1));
		}
		return salesStatistics;
	}

}
