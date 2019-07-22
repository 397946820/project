package com.it.ocs.salesStatistics.service.inner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Sets;
import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cache.OrderCache;
import com.it.ocs.cache.PricePlanCache;
import com.it.ocs.cal.dao.IPricePlanDao;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.utils.PricePlanUtils;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.listener.CurrencyRateListener;
import com.it.ocs.salesStatistics.dao.ISalesStatisticsDao;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.salesStatistics.utils.Tools;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;
import com.it.ocs.sys.vo.PermissionVO;

public class BaseService {

	@Autowired
	protected ISalesStatisticsDao salesStatisticsDao;

	@Autowired
	private IPricePlanDao pricePlanDao;

	// 搜索的字段
	protected String[] strArr = { "sku", "platform", "account", "currencycode", "station", "asin", "status",
			"orderId" };

	protected String[] types = { "af", "sf", "co" };

	// 保留四位小数
	protected DecimalFormat df = new DecimalFormat("0.0000");
	// 保留两位小数
	protected DecimalFormat sdf = new DecimalFormat("0.00");

	protected ResponseResult<SalesStatisticsVo> getResponseResult(RequestParam param, List<String> strings,
			PermissionVO permission, Boolean lean, String string, List<String> list) throws Exception {

		ResponseResult<SalesStatisticsVo> result = new ResponseResult<>();

		SalesStatisticsVo salesStatistics = BeanConvertUtil.mapToObject(param.getParam(), SalesStatisticsVo.class);

		if (salesStatistics == null) {
			return result;
		}

		if (!lean) {
			/*if (salesStatistics == null) {
				salesStatistics = new SalesStatisticsVo();
				if (permission != null) {
					salesStatistics.setSource(Tools.getSource(permission.getChildren().get(0).getName()));
				} else {
					return result;
				}
			} else {*/
				if (permission == null) {
					return result;
				}
			/*}*/
		}

		CurrencyRateListener.onApplicationEvent();

		// 时间处理
		salesStatistics = getsalesStatistics(salesStatistics);

		// 来源
		String source = salesStatistics.getSource();

		// 往前推相对应的时间差
		Date start = salesStatistics.getBegintime();
		Date end = salesStatistics.getStoptime();

		// 两个时间各往前推一年
		Date lastYearStartDate = TimeTools.getChangeYear(start);
		Date lastYearEndDate = TimeTools.getChangeYear(end);
		// 两个时间前推一个月
		Date lastMonthStartDate = TimeTools.getChangeMonth(start, -1);
		Date lastMonthEndDate = TimeTools.getChangeMonth(end, -1);
		// 两个时间前推7天
		Date lastWeekStartDate = TimeTools.getChangeDay(start, -7);
		Date lastWeekEndDate = TimeTools.getChangeDay(end, -7);

		String platform = salesStatistics.getPlatform(); // 账号
		String station = salesStatistics.getStation(); // 站点

		// 当前时间段
		List<SalesStatisticsModel> list1 = new ArrayList<>();

		// 同比
		List<SalesStatisticsModel> list3 = new ArrayList<>();
		// 月环比
		List<SalesStatisticsModel> list4 = new ArrayList<>();
		// 周环比
		List<SalesStatisticsModel> list5 = new ArrayList<>();

		// 汇总
		List<LinkedHashMap<String, Object>> footers = new ArrayList<>();

		strings = Tools.changeStrings(strings, source, "footer");
		LinkedHashMap<String, Object> hashMap = Tools.getMap(strings, list, source);
		footers.add(hashMap);
		LinkedHashMap<String, Object> linkedHashMap = Tools.getMapByMap(hashMap);

		Map<String, List<SalesStatisticsModel>> map = getMap(source, permission);

		List<String> keys = getKeys(source, permission, map.keySet());

		for (String key : map.keySet()) {
			List<SalesStatisticsModel> models = map.get(key);
			if (keys.contains(key)) {
				String temp = changeKey(key, source, platform, station, permission);
				String temp_ = temp.substring(0, temp.lastIndexOf("-"));
				if (temp_.equals("")) {
					continue;
				}
				key = temp.substring(temp.lastIndexOf("-") + 1);
				// 这个时间段的
				List<SalesStatisticsModel> temp1 = getSpell(start, end, temp_, salesStatistics, models);
				list1.addAll(temp1);
				// 去年这个时间段
				List<SalesStatisticsModel> temp3 = getSpell(lastYearStartDate, lastYearEndDate, temp_, salesStatistics,
						models);
				list3.addAll(temp3);
				// 上个月的
				List<SalesStatisticsModel> temp4 = getSpell(lastMonthStartDate, lastMonthEndDate, temp_,
						salesStatistics, models);
				list4.addAll(temp4);
				// 上周的
				List<SalesStatisticsModel> temp5 = getSpell(lastWeekStartDate, lastWeekEndDate, temp_, salesStatistics,
						models);
				list5.addAll(temp5);

				if (StringUtils.isBlank(string)) {
					if (StringUtils.isNotBlank(platform)
							|| (source.equals("ebay") && StringUtils.isNotBlank(station))) {
						// 直接统计
						footers.add(
								getFooter(temp1, temp3, temp4, temp5, key, "station", salesStatistics, linkedHashMap));
					}
				}
			}
		}

		if (StringUtils.isBlank(string)) {
			if (StringUtils.isNotBlank(platform) || (source.equals("ebay") && StringUtils.isNotBlank(station))) {
				footers.add(getFooter(list1, list3, list4, list5, "总汇总", "", salesStatistics, linkedHashMap));
			} else {
				footers = getFooters(footers, list1, list3, list4, list5, salesStatistics, linkedHashMap,
						Tools.getPlatformOrStation(permission, source, "platform", ""));
			}
		}

		// 数据合并
		List<SalesStatisticsModel> calculate1 = calculate(list1, source);
		List<SalesStatisticsModel> calculate3 = calculate(list3, source);
		List<SalesStatisticsModel> calculate4 = calculate(list4, source);
		List<SalesStatisticsModel> calculate5 = calculate(list5, source);

		// 计算年同比
		calculate1 = getProportion(salesStatistics, calculate1, calculate3, "sametermrate", source);
		// 计算月环比
		calculate1 = getProportion(salesStatistics, calculate1, calculate4, "month", source);
		// 计算周环比
		calculate1 = getProportion(salesStatistics, calculate1, calculate5, "week", source);

		calculate1 = changeRows(calculate1, source);
		if (StringUtils.isBlank(string)) {
			for (int i = 1; i < footers.size(); i++) {
				LinkedHashMap<String, Object> footerMap = footers.get(i);
				if (!CollectionUtil.isNullOrEmpty(list)) {
					if (list.contains("af总毛利额(美元)")) {
						Object object = footerMap.get(linkedHashMap.get("美元统计").toString());
						footerMap.put(linkedHashMap.get("af总毛利额(美元)").toString(),
								sdf.format(new Double(object.toString()) * 0.15));
						footerMap.put(linkedHashMap.get("sf总毛利额(美元)").toString(),
								sdf.format(new Double(object.toString()) * 0.22));
						footerMap.put(linkedHashMap.get("co总毛利额(美元)").toString(),
								sdf.format(new Double(object.toString()) * 0.15));
					}
					if (list.contains("af总毛润率")) {
						// 站号或者站点
						String account = footerMap.get(linkedHashMap.get("账号/站点").toString()).toString();
						// 总数
						int total = new Integer(footerMap.get(linkedHashMap.get("卖的总数").toString()).toString());
						changeFooterMap(footerMap, calculate1, account, total, platform, source, linkedHashMap);
					}
				}
			}

			// 排序、分页
			List<SalesStatisticsModel> rows = sortAndPageList(param, calculate1);
			result.setRows(CollectionUtil.beansConvert(rows, SalesStatisticsVo.class));
			result.setTotal(calculate1.size());
			result.setFooter(footers);
		} else {
			result.setRows(CollectionUtil.beansConvert(calculate1, SalesStatisticsVo.class));
		}
		result.setSource(source);

		return result;
	}

	protected String changeKey(String key, String source, String platform, String station, PermissionVO permission) {
		String temp = "";
		if (source.equals("ebay")) {
			if (StringUtils.isBlank(platform) && StringUtils.isNotBlank(station)) {
				if (key.startsWith(station)) {
					temp = key.substring(0, key.lastIndexOf("-"));
					key = key.substring(key.lastIndexOf("-") + 1);
				} else {
					key = "";
				}
			} else if ((StringUtils.isNotBlank(platform) && StringUtils.isBlank(station))) {
				if (key.startsWith(platform)) {
					key = key.substring(key.lastIndexOf("-") + 1);
					temp = key;
				} else {
					key = "";
				}
			} else if (StringUtils.isNotBlank(platform) && StringUtils.isNotBlank(station)) {
				if (key.equals(platform + "-" + station)) {
					key = key.substring(key.lastIndexOf("-") + 1);
					temp = key;
				} else {
					key = "";
				}
			} else if (StringUtils.isBlank(platform) && StringUtils.isBlank(station)) {
				List<String> lists = Tools.getPlatformOrStation(permission, source, "platform", "");
				if (lists.contains(key.substring(0, key.lastIndexOf("-")))) {
					key = key.substring(key.lastIndexOf("-") + 1);
					temp = key;
				} else {
					key = "";
				}
			}
		} else {
			if (StringUtils.isNotBlank(platform)) {
				if (key.equals(platform)) {
					key = platform;
					temp = key;
				} else {
					key = "";
				}
			} else {
				temp = key;
			}
		}
		return temp + "-" + key;
	}

	protected Map<String, List<SalesStatisticsModel>> getMap(String source, PermissionVO permission) {
		Map<String, List<SalesStatisticsModel>> map = new HashMap<>();
		if (permission != null) {
			List<PermissionVO> children = permission.getChildren();
			for (PermissionVO permissionVO : children) {
				String source2 = Tools.getSource(permissionVO.getName());
				if (source.equals(source2)) {
					map = OrderCache.getMaps(source2);
				}
			}
		}
		return map;
	}

	protected List<String> getKeys(String source, PermissionVO permission, Set<String> set) {
		List<String> keys = null;
		if (source.equals("light")) {
			keys = Tools.getPlatformOrStation(permission, source, "platform", "");
		} else {
			keys = new ArrayList<>(set);
		}
		return keys;
	}

	private void changeFooterMap(LinkedHashMap<String, Object> footerMap, List<SalesStatisticsModel> list,
			String account, int total, String platform, String source, LinkedHashMap<String, Object> hashMap) {
		String string1 = "af总毛润率";
		String string2 = "sf总毛润率";
		String string3 = "co总毛润率";
		Double count1 = 0d;
		Double count2 = 0d;
		Double count3 = 0d;
		if (!CollectionUtil.isNullOrEmpty(list)) {
			for (SalesStatisticsModel model : list) {
				if (!account.equals("总汇总")) {
					if (!source.equals("ebay")) {
						if (model.getPlatform().equals(account)
								|| (model.getStation() != null && model.getStation().equals(account))) {
							count1 += getCount(model, string1);
							count2 += getCount(model, string2);
							count3 += getCount(model, string3);
						}
					} else {
						if (StringUtils.isNotBlank(platform)) {
							if (model.getPlatform().equals(platform) && model.getStation().equals(account)) {
								count1 += getCount(model, string1);
								count2 += getCount(model, string2);
								count3 += getCount(model, string3);
							}
						} else {
							if (model.getPlatform().equals(account)) {
								count1 += getCount(model, string1);
								count2 += getCount(model, string2);
								count3 += getCount(model, string3);
							}
						}
					}

				} else {
					count1 += getCount(model, string1);
					count2 += getCount(model, string2);
					count3 += getCount(model, string3);
				}
			}
		}
		if (total == 0) {
			footerMap.put(hashMap.get(string1).toString(), 0);
			footerMap.put(hashMap.get(string2).toString(), 0);
			footerMap.put(hashMap.get(string3).toString(), 0);
		} else {
			footerMap.put(hashMap.get(string1).toString(), sdf.format(count1 / total));
			footerMap.put(hashMap.get(string2).toString(), sdf.format(count2 / total));
			footerMap.put(hashMap.get(string3).toString(), sdf.format(count3 / total));
		}

	}

	private Double getCount(SalesStatisticsModel model, String string) {
		Double flag = 0d;
		if (string.equals("af总毛润率") && model.getAfRate() != null) {
			flag = model.getAfRate();
		} else if (string.equals("sf总毛润率") && model.getSfRate() != null) {
			flag = model.getSfRate();
		} else if (string.equals("co总毛润率") && model.getCoRate() != null) {
			flag = model.getCoRate();
		}
		return flag * model.getCount();
	}

	protected ResponseResult<SalesStatisticsVo> getResponseResult_(RequestParam param, String flag) throws Exception {
		ResponseResult<SalesStatisticsVo> result = new ResponseResult<>();

		SalesStatisticsVo salesStatistics = BeanConvertUtil.mapToObject(param.getParam(), SalesStatisticsVo.class);
		// 来源
		String source = salesStatistics.getSource();

		if (source.equals("amazon") || source.equals("walmart")) {
			salesStatistics = getsalesStatisticsTime(salesStatistics);
			List<SalesStatisticsModel> models = salesStatisticsDao.queryOrderDetails(salesStatistics);
			List<SalesStatisticsModel> rows = changeRows(models, source);
			result.setRows(CollectionUtil.beansConvert(rows, SalesStatisticsVo.class));
		} else {
			// 时间处理
			salesStatistics = getsalesStatistics(salesStatistics);
			List<SalesStatisticsModel> list = null;
			if (source.equals("ebay")) {
				list = OrderCache.getOrder("ebay");
			} else if (source.equals("light")) {
				list = OrderCache.getOrder("light");
			}

			String string = null;
			if (!source.equals("light")) {
				string = salesStatistics.getStation();
			} else {
				string = salesStatistics.getPlatform();
			}
			Date start = TimeTools.getTimeByStation(salesStatistics.getBegintime(), string);
			Date end = TimeTools.getTimeByStation(salesStatistics.getStoptime(), string);
			salesStatistics.setBegintime(start);
			salesStatistics.setStoptime(end);
			List<SalesStatisticsModel> models = queryBySalesStatisticsVo(salesStatistics, list, "details");
			// 根据所选时间段正序排序
			String whichTime = salesStatistics.getWhichTime();
			CollectionUtil.sort(models, whichTime, "asc");
			List<SalesStatisticsModel> rows = getRows(models, source, whichTime);
			List<SalesStatisticsModel> rows_ = new ArrayList<>();
			String currencycode = salesStatistics.getCurrencycode();
			for (SalesStatisticsModel model : rows) {
				if (currencycode == null) {
					if (model.getCurrencycode() == currencycode) {
						rows_.add(model);
					}
				} else {
					if (model.getCurrencycode().equals(currencycode)) {
						rows_.add(model);
					}
				}
			}
			if (StringUtils.isBlank(flag)) {
				// 分页
				List<SalesStatisticsModel> statisticsModels = CollectionUtil.pageList(rows_, param.getStartRow(),
						param.getEndRow());

				statisticsModels = changeRows(statisticsModels, source);

				result.setRows(CollectionUtil.beansConvert(statisticsModels, SalesStatisticsVo.class));
				result.setTotal(rows.size());
			} else {
				result.setRows(CollectionUtil.beansConvert(rows, SalesStatisticsVo.class));
			}
		}

		result.setSource(source);
		return result;

	}

	private SalesStatisticsVo getsalesStatisticsTime(SalesStatisticsVo salesStatistics) throws Exception {
		if (!salesStatistics.getTimeQuantum().equals("0")) {
			// 选了时间段
			salesStatistics.setStoptime(TimeTools.getEndTime());

			if (salesStatistics.getTimeQuantum().equals("-30")) {
				// 一个月月份直接减一
				salesStatistics.setBegintime(TimeTools.getChangeMonth(salesStatistics.getStoptime(), -1));
			} else {
				salesStatistics.setBegintime(TimeTools.getChangeMonth(salesStatistics.getStoptime(),
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
			salesStatistics.setStoptime(TimeTools.getTimeByStation(salesStatistics.getStoptime(), "USA"));
		}
		return salesStatistics;
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
				model.setAf(new Double(sdf.format(model.getPrice() * 0.15)));
				model.setSf(new Double(sdf.format(model.getPrice() * 0.22)));
				model.setCo(new Double(sdf.format(model.getPrice() * 0.15)));
				Double unitprice = 0d;
				if (model.getCount() > 0) {
					unitprice = model.getPrice() / model.getCount();
				}
				model.setActualSalesPrice(new Double(df.format(unitprice)));
				String country = "";
				if (source.equals("light") || source.equals("walmart")) {
					country = model.getPlatform();
					country = country.equals("USA") ? "US" : country;
				} else {
					country = model.getStation();
					if (source.equals("ebay")) {
						model.setPriceTax(model.getPrice());
						if (country.equals("Germany")) {
							model.setTaxrate(new Double(sdf.format(model.getPrice() / 1.19 * 0.19)));
							model.setPriceExcludingTax(new Double(sdf.format(model.getPrice() / 1.19)));
						} else if (country.equals("UK")) {
							model.setTaxrate(new Double(sdf.format(model.getPrice() / 1.2 * 0.2)));
							model.setPriceExcludingTax(new Double(sdf.format(model.getPrice() / 1.2)));
						} else if (country.equals("US")) {
							model.setPriceExcludingTax(model.getPrice());
						}
					}
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

	private LinkedHashMap<String, Object> getFooter(List<SalesStatisticsModel> temp1, List<SalesStatisticsModel> temp3,
			List<SalesStatisticsModel> temp4, List<SalesStatisticsModel> temp5, String key, String string,
			SalesStatisticsVo obj, LinkedHashMap<String, Object> linkedHashMap) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("footer", true);
		// 美元转人民币的汇率
		Double rate = CurrencyRateCache.getCurrencyRate("RMB");
		String source = obj.getSource();
		Boolean flag = false;
		if (StringUtils.isNotBlank(obj.getSku()) || StringUtils.isNotBlank(obj.getAsin())) {
			flag = true;
		}
		int monthrate = getCount_(flag, temp1, source), status = 0;
		double afRate = 0d, sfRate = 0d;
		//Map<String, Object> keys = new HashMap<>();
		for (SalesStatisticsModel model : temp1) {
			status += model.getCount();
			Double amount = model.getAmount();
			// 站点金额
			if (StringUtils.isNotBlank(string) || (source.equals("light") && !key.equals("总汇总"))) {
				if (amount != null) {
					afRate += amount;
				}
			}
			if (StringUtils.isNotBlank(model.getCurrencycode())) {
				if (amount != null) {
					sfRate += amount * CurrencyRateCache.getCurrencyRate(model.getCurrencycode());
				}
			}
		}
		map.put(linkedHashMap.get("账号/站点").toString(), key);
		map.put(linkedHashMap.get("订单总数").toString(), monthrate);
		map.put(linkedHashMap.get("卖的总数").toString(), status);
		map.put(linkedHashMap.get("站点金额统计").toString(), sdf.format(afRate));
		map.put(linkedHashMap.get("美元统计").toString(), sdf.format(sfRate));
		map.put(linkedHashMap.get("人民币统计").toString(), sdf.format(sfRate * rate));
		map = changeFooter(map, linkedHashMap, sfRate, temp3, temp4, temp5, source, flag);
		return map;
	}

	private int getCount_(Boolean flag, List<SalesStatisticsModel> temp1, String source) {
		int result = 0;
		if (!flag) {
			if (source.equals("light")) {
				Map<Long, Object> map = new HashMap<>();
				for (SalesStatisticsModel model : temp1) {
					if (!map.containsKey(model.getParentId())) {
						map.put(model.getParentId(), model);
					}
				}
				result = map.size();
			} else if (source.equals("ebay")) {
				Map<String, Object> map = new HashMap<>();
				for (SalesStatisticsModel model : temp1) {
					if (!map.containsKey(model.getOrderId())) {
						map.put(model.getOrderId(), model);
					}
				}
				result = map.size();
			}
		} else {
			result = temp1.size();
		}
		return result;
	}

	private LinkedHashMap<String, Object> changeFooter(LinkedHashMap<String, Object> map,
			LinkedHashMap<String, Object> linkedHashMap, double sfRate, List<SalesStatisticsModel> temp3,
			List<SalesStatisticsModel> temp4, List<SalesStatisticsModel> temp5, String source, Boolean flag) {
		double total2 = getTotal(temp3, source, flag);
		double total3 = getTotal(temp4, source, flag);
		double total4 = getTotal(temp5, source, flag);
		map.put(linkedHashMap.get("同比").toString(), getRate(sfRate, total2));
		map.put(linkedHashMap.get("月环比").toString(), getRate(sfRate, total3));
		map.put(linkedHashMap.get("周环比").toString(), getRate(sfRate, total4));
		return map;
	}

	private String getRate(double sfRate, double total) {
		String string = "0";
		if (total > 0) {
			double temp = new Double(sdf.format(((sfRate - total) / total) * 100));
			if (temp == 0d) {
				string = "0";
			} else {
				string = temp + "%";
			}
		}
		return string;
	}

	private double getTotal(List<SalesStatisticsModel> list, String source, Boolean flag) {
		Double result = 0d;
		if (!CollectionUtil.isNullOrEmpty(list)) {
			//Map<String, Object> map = new HashMap<>();
			for (SalesStatisticsModel model : list) {
				Double amount = model.getAmount();
				String currencycode = model.getCurrencycode();
				if (StringUtils.isNotBlank(currencycode) && amount != null) {
					result += amount * CurrencyRateCache.getCurrencyRate(currencycode);
				}
			}
		}

		return result;
	}

	protected List<SalesStatisticsModel> getSpell(Date start, Date end, String key, SalesStatisticsVo salesStatistics,
			List<SalesStatisticsModel> models) throws Exception {
		Date temp1 = TimeTools.getTimeByStation(start, key);
		Date temp2 = TimeTools.getTimeByStation(end, key);
		salesStatistics.setBegintime(temp1);
		salesStatistics.setStoptime(temp2);
		return queryBySalesStatisticsVo(salesStatistics, models, null);
	}

	// 根据平台根据站点区分出数据
	protected Map<String, List<SalesStatisticsModel>> groupByAccount(List<SalesStatisticsModel> list, String source,
			String string) {
		Map<String, List<SalesStatisticsModel>> map = new HashMap<>();
		if (!CollectionUtil.isNullOrEmpty(list)) {
			for (SalesStatisticsModel model : list) {
				String key = "";
				if (StringUtils.isBlank(string)) {
					if (source.equals("light")) {
						key = model.getPlatform();
					} else {
						key = model.getPlatform() + "_" + model.getStation();
					}
				} else {
					key = model.getPlatform();
				}
				if (StringUtils.isNotBlank(key)) {
					if (!map.containsKey(key)) {
						map.put(key, new ArrayList<SalesStatisticsModel>());
					}
					map.get(key).add(model);
				}
			}
		}

		return map;
	}

	private List<SalesStatisticsModel> getRows(List<SalesStatisticsModel> models, String source, String whichTime)
			throws Exception {
		int i = 1;
		String flag = "";
		Map<String, List<SalesStatisticsModel>> map = new LinkedHashMap<>();
		for (int j = 0; j < models.size(); j++) {
			SalesStatisticsModel model = models.get(j);
			int count = model.getCount();
			String string = "0";
			if (source.equals("amazon")) {
				if (count > 0 && model.getPrice() > 0) {
					string = sdf.format(model.getPrice() / count);
				}
				model.setUnitprice(new Double(string));
			} else if (source.equals("ebay") || source.equals("light")) {
				string = model.getUnitprice() + "";
			}
			if (flag.equals(string)) {
				map.get(string + i).add(model);
			} else {
				i++;
				map.put(string + i, new ArrayList<SalesStatisticsModel>());
				map.get(string + i).add(model);
			}
			flag = string;
		}
		List<SalesStatisticsModel> rows = new ArrayList<>();
		for (String key : map.keySet()) {
			rows.add(getsalesStatisticsByList(map.get(key), whichTime));
		}

		return rows;
	}

	private SalesStatisticsModel getsalesStatisticsByList(List<SalesStatisticsModel> list, String whichTime)
			throws Exception {
		Map<Double, SalesStatisticsModel> map = new HashMap<>();

		double unitprice = 0d;
		for (SalesStatisticsModel model : list) {
			unitprice = model.getUnitprice();
			if (!map.containsKey(unitprice)) {
				SalesStatisticsModel obj = new SalesStatisticsModel();
				BeanUtils.copyProperties(model, obj);
				obj.setQty(1);
				obj.setFromtime(Tools.getValue(obj, whichTime));
				map.put(unitprice, obj);
			} else {
				merge(model, map.get(unitprice));
			}

		}
		SalesStatisticsModel model = map.get(unitprice);
		model.setTotime(Tools.getValue(list.get(list.size() - 1), whichTime));
		return model;
	}

	private SalesStatisticsVo getsalesStatistics(SalesStatisticsVo salesStatistics) throws Exception {
		if (salesStatistics == null) {
			// 默认按购买时间 最近一个月统计
			salesStatistics = new SalesStatisticsVo();
			salesStatistics.setWhichTime("purchaseat");
			salesStatistics.setTimeQuantum("-30");
			salesStatistics.setSource("amazon");
			Date stoptime = TimeTools.getStoptime();
			salesStatistics.setBegintime(TimeTools.getChangeMonth(stoptime, -1));
			// 结束时间在当前时间在加上一天
			salesStatistics.setStoptime(TimeTools.getChangeDay(stoptime, 1));
		} else {
			// 改变平台触发事件的时候 此值为null
			if (salesStatistics.getTimeQuantum() == null) {
				if (salesStatistics.getSource().equals("amazon")) {
					salesStatistics.setWhichTime("purchaseat");
				} else if (salesStatistics.getSource().equals("ebay")) {
					salesStatistics.setWhichTime("paidtime");
				} else if (salesStatistics.getSource().equals("light")) {
					salesStatistics.setWhichTime("createdat");
				}
				salesStatistics.setTimeQuantum("-30");
				Date stoptime = TimeTools.getStoptime();
				salesStatistics.setBegintime(TimeTools.getChangeMonth(stoptime, -1));
				// 结束时间在当前时间在加上一天
				salesStatistics.setStoptime(TimeTools.getChangeDay(stoptime, 1));
			} else if (!salesStatistics.getTimeQuantum().equals("0")) {
				// 选了时间段
				Date stoptime = TimeTools.getStoptime();

				if (salesStatistics.getTimeQuantum().equals("-30")) {
					// 近一个月 月份直接减1
					salesStatistics.setBegintime(TimeTools.getChangeMonth(stoptime, -1));
				} else {
					salesStatistics.setBegintime(
							TimeTools.getChangeDay(stoptime, new Integer(salesStatistics.getTimeQuantum())));
				}
				// 结束时间在当前时间在加上一天
				salesStatistics.setStoptime(TimeTools.getChangeDay(stoptime, 1));
			} else {
				// 自定义时间
				if (salesStatistics.getStarttime() != null && salesStatistics.getEndtime() == null) {
					// 选择了开始时间 未选择结束时间 默认往后推一个月
					String starttime = salesStatistics.getStarttime();
					// 字符串转换成date
					salesStatistics.setBegintime(TimeTools.getTime(starttime));
					// 月份加一
					Date date = TimeTools.getChangeMonth(salesStatistics.getBegintime(), 1);
					salesStatistics.setStoptime(TimeTools.getChangeDay(date, 1));
					salesStatistics.setTimeQuantum("-30");

				} else if (salesStatistics.getStarttime() == null && salesStatistics.getEndtime() != null) {
					// 选择了结束时间 未选择开始时间 默认往前推一个月
					String endtime = salesStatistics.getEndtime();
					// 字符串转换成date
					Date date = TimeTools.getTime(endtime);
					// 开始时间在当前时间减一个月
					salesStatistics.setBegintime(TimeTools.getChangeMonth(date, -1));
					salesStatistics.setStoptime(TimeTools.getChangeDay(date, 1));
					salesStatistics.setTimeQuantum("-30");
				} else {
					// 两个时间都选择了
					String starttime = salesStatistics.getStarttime();
					String endtime = salesStatistics.getEndtime();
					salesStatistics.setBegintime(TimeTools.getTime(starttime));
					// 选择了同一天
					if (starttime.equals(endtime)) {
						salesStatistics.setTimeQuantum("-1");
					} else {
						// 获取时间差
						int change = TimeTools.getMistiming(starttime, endtime);
						salesStatistics.setTimeQuantum(change + "");
					}
					Date date = TimeTools.getTime(endtime);
					salesStatistics.setStoptime(TimeTools.getChangeDay(date, 1));
				}
			}
		}
		return salesStatistics;
	}

	protected List<SalesStatisticsModel> queryBySalesStatisticsVo(SalesStatisticsVo statistics,
			List<SalesStatisticsModel> list, String flag) throws Exception {

		List<SalesStatisticsModel> result = new ArrayList<>();
		Date startDate = statistics.getBegintime();
		Date endDate = statistics.getStoptime();
		String source = statistics.getSource();
		String fieldName = statistics.getWhichTime();
		// 先按时间段筛选
		if (startDate != null && endDate != null) {
			if (!CollectionUtil.isNullOrEmpty(list)) {
				for (SalesStatisticsModel model : list) {
					if (source.equals("ebay") && fieldName.equals("lastestshipdate")
							&& StringUtils.isBlank(model.getShipmentTrackingNumber())) {
						continue;
					}
					Date invoke = Tools.getValue(model, fieldName);
					// 取两个时间中间的
					if (invoke != null) {
						if (TimeTools.compare1(startDate, endDate, invoke)) {
							result.add(model);
						}
					}
				}
			}
		} else {
			for (SalesStatisticsModel salesStatisticsModel : list) {
				result.add(salesStatisticsModel);
			}
		}

		// 查询条件筛选
		if (flag == null) {
			result = CollectionUtil.queryByParam(result, statistics, strArr, "like");
		} else if (flag.equals("details")) {
			result = CollectionUtil.queryByParam(result, statistics, strArr, "eq");
		}

		return result;

	}

	// 计算同比环比
	public List<SalesStatisticsModel> getProportion(SalesStatisticsVo salesStatisticsVo,
			List<SalesStatisticsModel> list1, List<SalesStatisticsModel> list2, String string, String source) {

		Map<String, SalesStatisticsModel> map = new HashMap<>();
		for (SalesStatisticsModel model : list1) {
			map.put(getKey(model, source), model);
		}

		for (SalesStatisticsModel model : list2) {
			String key = getKey(model, source);
			if (map.containsKey(key)) {
				SalesStatisticsModel model2 = map.get(key);
				if (model.getPrice() > 0) {
					if (string.equals("sametermrate")) {
						model2.setSametermrate(
								new Double(df.format((model2.getPrice() - model.getPrice()) / model.getPrice())));
					} else if (string.equals("month")) {
						model2.setMonthrate(
								new Double(df.format((model2.getPrice() - model.getPrice()) / model.getPrice())));
					} else if (string.equals("week")) {
						model2.setWeekrate(
								new Double(df.format((model2.getPrice() - model.getPrice()) / model.getPrice())));
					}
				}
			}
		}

		return new ArrayList<SalesStatisticsModel>(map.values());
	}

	// 排序以及分页
	private List<SalesStatisticsModel> sortAndPageList(RequestParam param, List<SalesStatisticsModel> calculate1)
			throws Exception {
		List<SalesStatisticsModel> rows = new ArrayList<>();

		if (param.getSort() != null && param.getOrder() != null) {
			CollectionUtil.sort(calculate1, param.getSort(), param.getOrder());
		} else {
			CollectionUtil.sort(calculate1, "count", "desc");
		}

		rows = CollectionUtil.pageList(calculate1, param.getStartRow(), param.getEndRow());

		return rows;

	}

	// 汇总
	private List<LinkedHashMap<String, Object>> getFooters(List<LinkedHashMap<String, Object>> footers,
			List<SalesStatisticsModel> calculate1, List<SalesStatisticsModel> calculate3,
			List<SalesStatisticsModel> calculate4, List<SalesStatisticsModel> calculate5,
			SalesStatisticsVo salesStatistics, LinkedHashMap<String, Object> linkedHashMap, List<String> codes)
			throws Exception {
		String source = salesStatistics.getSource();
		// 把三个集合都分别区分开来
		Map<String, List<SalesStatisticsModel>> map1 = groupByAccount(calculate1, source, "platform");
		Map<String, List<SalesStatisticsModel>> map3 = groupByAccount(calculate3, source, "platform");
		Map<String, List<SalesStatisticsModel>> map4 = groupByAccount(calculate4, source, "platform");
		Map<String, List<SalesStatisticsModel>> map5 = groupByAccount(calculate5, source, "platform");

		// 国家为空 汇总
		changeFooters(footers, codes, map1, map3, map4, map5, salesStatistics, linkedHashMap);

		// 总汇总
		footers.add(
				getFooter(calculate1, calculate3, calculate4, calculate5, "总汇总", "", salesStatistics, linkedHashMap));

		return footers;
	}

	/*private Double getTotal(List<SalesStatisticsModel> list, SalesStatisticsVo obj, Boolean flag) {
		//String source = obj.getSource();
		Double result = 0d;
		Map<String, Object> map = new HashMap<>();
		for (SalesStatisticsModel model : list) {
			Double amount = model.getAmount();
			String currencycode = model.getCurrencycode();
			if (StringUtils.isNotBlank(currencycode)) {
				if (!flag) {
					String key = model.getOrderId();
					if (!map.containsKey(key)) {
						result += amount * CurrencyRateCache.getCurrencyRate(currencycode);
						map.put(key, 1);
					}
				} else {
					Double price = model.getPrice();
					if (price != null) {
						result += price * CurrencyRateCache.getCurrencyRate(currencycode);
					}
				}
			}
		}

		return result;
	}*/

	// 没选账号的时候的汇总
	private void changeFooters(List<LinkedHashMap<String, Object>> footers, List<String> strings,
			Map<String, List<SalesStatisticsModel>> map1, Map<String, List<SalesStatisticsModel>> map3,
			Map<String, List<SalesStatisticsModel>> map4, Map<String, List<SalesStatisticsModel>> map5,
			SalesStatisticsVo model, LinkedHashMap<String, Object> linkedHashMap) throws Exception {
		List<SalesStatisticsModel> list1 = new ArrayList<>();
		List<SalesStatisticsModel> list3 = new ArrayList<>();
		List<SalesStatisticsModel> list4 = new ArrayList<>();
		List<SalesStatisticsModel> list5 = new ArrayList<>();
		for (String string : strings) {
			if (map1.containsKey(string)) {
				list1 = map1.get(string);
				if (map3.containsKey(string)) {
					list3 = map3.get(string);
				}
				if (map4.containsKey(string)) {
					list4 = map4.get(string);
				}
				if (map5.containsKey(string)) {
					list5 = map5.get(string);
				}
				footers.add(getFooter(list1, list3, list4, list5, string, "", model, linkedHashMap));
			}
		}
	}

	private void merge(SalesStatisticsModel sourceModel, SalesStatisticsModel targetModel) {
		targetModel.setQty(targetModel.getQty() + 1);
		targetModel.setCount(targetModel.getCount() + sourceModel.getCount());
		targetModel.setPrice(new Double(df.format(targetModel.getPrice() + sourceModel.getPrice())));
		targetModel.setDeduction(new Double(df.format(sourceModel.getDeduction() + targetModel.getDeduction())));
		targetModel.setTaxrate(new Double(df.format(sourceModel.getTaxrate() + targetModel.getTaxrate())));
	}

	// 数据合并
	private List<SalesStatisticsModel> calculate(List<SalesStatisticsModel> rows, String source) {
		Map<String, SalesStatisticsModel> keyMaps = new HashMap<>();
		for (SalesStatisticsModel model : rows) {
			String key = getKey(model, source);
			if (!keyMaps.containsKey(key)) {
				SalesStatisticsModel obj = new SalesStatisticsModel();
				BeanUtils.copyProperties(model, obj);
				obj.setQty(1);
				keyMaps.put(key, obj);
			} else {
				merge(model, keyMaps.get(key));
			}
		}
		return new ArrayList<SalesStatisticsModel>(keyMaps.values());
	}

	private String getKey(SalesStatisticsModel obj, String source) {
		if (source.equals("") || source.equals("amazon")) {
			return obj.getSku() + obj.getAsin() + obj.getPlatform() + obj.getStation() + obj.getCurrencycode()
					+ obj.getStatus();

		} else if (source.equals("ebay")) {
			return obj.getSku() + obj.getPlatform() + obj.getStation() + obj.getCurrencycode() + obj.getStatus();

		} else if (source.equals("light")) {
			return obj.getSku() + obj.getPlatform() + obj.getCurrencycode() + obj.getStatus();
		}
		return null;

	}
}
