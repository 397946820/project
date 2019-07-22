package com.it.ocs.salesStatistics.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.sys.vo.PermissionVO;

public class Tools {

	private static Map<String, String> map = new HashMap<>();
	private static Map<String, String> countrys = new HashMap<>();
	private static List<String> strings = new ArrayList<>();

	static {
		map.put("US", "America/Los_Angeles");
		map.put("USA", "America/Los_Angeles");
		map.put("Amazon.com", "America/Los_Angeles");
		map.put("JP", "Asia/Tokyo");
		map.put("Amazon.co.jp", "Asia/Tokyo");
		map.put("DE", "Europe/Berlin");
		map.put("Amazon.de", "Europe/Berlin");
		map.put("Amazon.fr", "Europe/Paris");
		map.put("Amazon.co.uk", "Europe/London");
		map.put("Amazon.es", "Europe/Madrid");
		map.put("Amazon.it", "Europe/Rome");
		map.put("UK", "Europe/London");
		map.put("Germany", "Europe/Berlin");
		map.put("Italy", "Europe/Rome");
		map.put("France", "Europe/Paris");
		map.put("eBayMotors", "America/Los_Angeles");
		map.put("Amazon.ca", "America/Toronto");
		map.put("CA", "America/Toronto");

		countrys.put("Amazon.com", "US");
		countrys.put("Amazon.co.jp", "JP");
		countrys.put("Amazon.ca", "CA");
		countrys.put("Amazon.de", "DE");
		countrys.put("Amazon.fr", "FR");
		countrys.put("Amazon.it", "IT");
		countrys.put("Amazon.es", "ES");
		countrys.put("Amazon.co.uk", "GB");
		countrys.put("US", "US");
		countrys.put("DE", "DE");
		countrys.put("UK", "GB");
		countrys.put("Germany", "DE");
		countrys.put("eBayMotors", "DE");
		countrys.put("France", "FR");
		countrys.put("Italy", "IT");
		countrys.put("EU", "DE");
		countrys.put("JP", "JP");
		countrys.put("CA", "CA");

		strings.add("订单状态_status");
		strings.add("月环比_monthrate");
		strings.add("周环比_weekrate");
		strings.add("同比_sametermrate");
		strings.add("总数_count");
		strings.add("金额(不含税)_priceExcludingTax");
		strings.add("金额(含税)_priceTax");
		strings.add("金额（含税）_price");
		strings.add("税额_taxrate");
		strings.add("折扣额_deduction");
		strings.add("币种_currencycode");
		strings.add("订单数量_qty");
		strings.add("站点_station");
		strings.add("账号_platform");
		strings.add("Asin_asin");
		strings.add("sku_sku");
	}

	public static String getCurrencyCode(String string) {
		if (string.equals("Amazon.com") || string.equals("US") || string.equals("USA")) {
			return "USD";
		} else if (string.equals("Amazon.co.jp") || string.equals("JP")) {
			return "JPY";
		} else if (string.equals("Amazon.ca") || string.equals("CA")) {
			return "CAD";
		} else if (string.equals("Amazon.de") || string.equals("DE")) {
			return "EUR";
		} else if (string.equals("Amazon.fr") || string.equals("FR")) {
			return "EUR";
		} else if (string.equals("Amazon.it") || string.equals("IT")) {
			return "EUR";
		} else if (string.equals("Amazon.es") || string.equals("ES")) {
			return "EUR";
		} else if (string.equals("Amazon.co.uk") || string.equals("GB") || string.equals("UK")) {
			return "GBP";
		}
		return null;
	}

	// 通过判断字段名 获取对应的字段的值
	public static Date getValue(SalesStatisticsModel model, String string) {
		if (string.equals("purchaseat")) {
			return model.getPurchaseat();

		} else if (string.equals("lastestshipdate")) {
			return model.getLastestshipdate();

		} else if (string.equals("updatedat")) {
			return model.getUpdatedat();

		} else if (string.equals("paidtime")) {
			return model.getPaidtime();

		} else if (string.equals("lastfetchtime")) {
			return model.getLastfetchtime();

		} else if (string.equals("createdat")) {
			return model.getCreatedat();

		}
		return null;
	}

	public static String getSoreceId(String key) {
		return map.get(key);
	}

	public static String getCountry(String key) {
		return countrys.get(key);
	}

	public static List<String> changeStrings(List<String> list, String source, String flag) {
		if (!source.equals("amazon")) {
			list.remove("真实售价_actualSalesPrice");
			list.remove("真实售价的利润率_actualYield");
			list.remove("利润系数_actualProfitCoefficient");
		}
		List<String> sourceList = new ArrayList<>();
		for (String string : strings) {
			if (source.equals("ebay")) {
				if (string.equals("Asin_asin") || string.equals("金额_price")) {
					continue;
				}
			} else {
				if (string.equals("金额(不含税)_priceExcludingTax") || string.equals("金额(含税)_priceTax")) {
					continue;
				}
				if (source.equals("light")) {
					if (string.equals("Asin_asin")) {
						continue;
					}
				} else if(source.equals("walmart")) {
					//沃尔玛显示站点
					if (/*string.equals("站点_station") ||*/ string.equals("Asin_asin")) {
						continue;
					}
				}
			}
			sourceList.add(string);
		}

		List<String> result = new ArrayList<>();

		if (!CollectionUtil.isNullOrEmpty(list)) {
			for (String string : list) {
				if (flag.equals("footer")) {
					result.add(string.substring(string.lastIndexOf("_") + 1));
				} else if (flag.equals("excel")) {
					result.add(string.substring(0, string.lastIndexOf("_")));
				}
			}
		}

		for (String string : sourceList) {
			if (flag.equals("footer")) {
				result.add(string.substring(string.lastIndexOf("_") + 1));
			} else if (flag.equals("excel")) {
				result.add(string.substring(0, string.lastIndexOf("_")));
			}
		}

		return result;
	}

	public static LinkedHashMap<String, Object> getMap(List<String> strings, List<String> list, String source) {
		if (!source.equals("amazon")) {
			strings.remove("actualSalesPrice");
			strings.remove("actualYield");
			strings.remove("actualProfitCoefficient");
		}
		LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
		hashMap.put("footer", true);
		if (!CollectionUtil.isNullOrEmpty(list)) {
			if (list.contains("af总毛利额(美元)") && list.contains("af总毛润率")) {
				hashMap.put(strings.get(0), "co总毛润率");
				hashMap.put(strings.get(1), "sf总毛润率");
				hashMap.put(strings.get(2), "af总毛润率");
				hashMap.put(strings.get(3), "co总毛利额(美元)");
				hashMap.put(strings.get(4), "sf总毛利额(美元)");
				hashMap.put(strings.get(5), "af总毛利额(美元)");
				hashMap.put(strings.get(6), "人民币统计");
				hashMap.put(strings.get(7), "美元统计");
				hashMap.put(strings.get(8), "站点金额统计");
				hashMap.put(strings.get(9), "同比");
				hashMap.put(strings.get(10), "月环比");
				hashMap.put(strings.get(11), "周环比");
				hashMap.put(strings.get(12), "卖的总数");
				hashMap.put(strings.get(13), "订单总数");
				hashMap.put(strings.get(14), "账号/站点");
			} else {
				if (list.contains("af总毛利额(美元)")) {
					hashMap.put(strings.get(0), "co总毛利额(美元)");
					hashMap.put(strings.get(1), "sf总毛利额(美元)");
					hashMap.put(strings.get(2), "af总毛利额(美元)");
				}
				if (list.contains("af总毛润率")) {
					hashMap.put(strings.get(0), "co总毛润率");
					hashMap.put(strings.get(1), "sf总毛润率");
					hashMap.put(strings.get(2), "af总毛润率");
				}
				hashMap.put(strings.get(3), "人民币统计");
				hashMap.put(strings.get(4), "美元统计");
				hashMap.put(strings.get(5), "站点金额统计");
				hashMap.put(strings.get(6), "同比");
				hashMap.put(strings.get(7), "月环比");
				hashMap.put(strings.get(8), "周环比");
				hashMap.put(strings.get(9), "卖的总数");
				hashMap.put(strings.get(10), "订单总数");
				hashMap.put(strings.get(11), "账号/站点");
			}
		} else {
			hashMap.put(strings.get(0), "人民币统计");
			hashMap.put(strings.get(1), "美元统计");
			hashMap.put(strings.get(2), "站点金额统计");
			hashMap.put(strings.get(3), "同比");
			hashMap.put(strings.get(4), "月环比");
			hashMap.put(strings.get(5), "周环比");
			hashMap.put(strings.get(6), "卖的总数");
			hashMap.put(strings.get(7), "订单总数");
			hashMap.put(strings.get(8), "账号/站点");
		}

		return hashMap;
	}

	public static List<String> changeDetails(List<String> details, String source) {
		List<String> result = new ArrayList<>();
		result.add("sku");
		if (!source.equals("ebay")) {
			result.add("金额");
			if (source.equals("amazon")) {
				result.add("Asin");
			}
		} else {
			result.add("金额(含税)");
			result.add("金额(不含税)");
		}
		result.add("账号");
		if (!source.equals("light")) {
			result.add("站点");
		}
		result.add("订单数量");
		result.add("单价");
		result.add("币种");
		result.add("折扣额");
		result.add("税额");
		result.add("总数");
		if (!CollectionUtil.isNullOrEmpty(details)) {
			for (String string : details) {
				result.add(string);
			}
		}
		result.add("开始时间");
		result.add("结束时间");
		return result;
	}

	public static String getSource(String name) {
		if (StringUtils.isNotBlank(name)) {
			if (name.equals("亚马逊")) {
				return "amazon";
			} else if (name.equals("Ebay")) {
				return "ebay";
			} else if (name.equals("官网")) {
				return "light";
			} else if (name.equals("沃尔玛")) {
				return "walmart";
			}
		}
		return name;
	}

	public static LinkedHashMap<String, Object> getMapByMap(LinkedHashMap<String, Object> hashMap) {
		LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
		for (String key : hashMap.keySet()) {
			linkedHashMap.put(hashMap.get(key).toString(), key);
		}
		return linkedHashMap;
	}

	public static List<String> getPlatformOrStation(PermissionVO permission, String source, String string,
			String platform) {
		List<String> result = new ArrayList<>();
		if (permission != null) {
			CollectionUtil.each(CollectionUtil.search(permission.getChildren(), new IFunction<PermissionVO, Boolean>() {
				@Override
				public Boolean excute(PermissionVO obj) {
					return source.equals(getSource(obj.getName()));
				}
			}).getChildren(), new IAction<PermissionVO>() {
				@Override
				public void excute(PermissionVO obj1) {
					if (string.equals("station")) {
						CollectionUtil.each(obj1.getChildren(), new IAction<PermissionVO>() {
							@Override
							public void excute(PermissionVO obj2) {
								if (platform.equals("")) {
									result.add(obj2.getName());
								} else {
									if (obj1.getName().equals(platform)) {
										result.add(obj2.getName());
									}
								}
							}
						});
					} else if (string.equals("platform")) {
						result.add(obj1.getName());
					}

				}
			});
		}
		return result;
	}

	public static String getPlatForm(String platform) {
		if (platform.equals("CA")) {
			return "CA01";
		} else if (platform.equals("US")) {
			return "US01";
		} else if (platform.equals("JP")) {
			return "JP01";
		} else if (platform.equals("DE")) {
			return "EU01";
		}

		return "";
	}

	public static Map<String, String> getMap(Map<String, Object> map) {
		Map<String, String> param = Maps.newHashMap();
		for (String key : map.keySet()) {
			param.put(key, map.get(key) == null ? "" : map.get(key).toString());
		}
		return param;
	}
}
