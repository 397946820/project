package com.it.ocs.cal.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import com.it.ocs.common.RequestParam;

public class Tools {
	
	private static Map<String, Object> map = new HashMap<>();
	static {
		map.put("US", "United States");
		map.put("GB", "United Kingdom");
		map.put("DE", "German");
		map.put("FR", "France");
		map.put("IT", "Italy");
		map.put("ES", "Spain");
		map.put("JP", "Japan");
		map.put("CA", "Canada");
		map.put("AU", "Australia");
		map.put("CN", "China");
		map.put("af", "BY AF");
		map.put("sf", "BY SF");
		map.put("co", "BY CO");
		map.put("af_efn", "BY AF EFN");
		map.put("sf_efn", "BY SF EFN");
		map.put("co_efn", "BY CO EFN");
		map.put("af_peu", "BY AF PEU");
		map.put("sf_peu", "BY SF PEU");
		map.put("co_peu", "BY CO PEU");
		map.put("RMB", "￥");
		map.put("CNY", "￥");
		map.put("USD", "$");
	}
	
	private static Map<String, String> hashMap = new HashMap<>();
	static {
		hashMap.put("CN", "￥");
		hashMap.put("CA", "C$");
		hashMap.put("JP", "J.￥");
		hashMap.put("ES", "€");
		hashMap.put("IT", "€");
		hashMap.put("FR", "€");
		hashMap.put("DE", "€");
		hashMap.put("GB", "£");
		hashMap.put("US", "$");
	}

	//处理排序的字段
	public static RequestParam getRequestParam(RequestParam param) {
		if(StringUtils.isNotBlank(param.getSort())) {
			if(param.getSort().equals("purchasePrice")) {
				param.setSort("price");
			} else if(param.getSort().equals("taxRebateRate")) {
				param.setSort("tax_rebate_rate");
			} else if(param.getSort().equals("isMultiOne")) {
				param.setSort("is_multi_one");
			} else if(param.getSort().equals("profitRateAction")) {
				param.setSort("profit_rate_action");
			} else {
				param.setSort(Utils.changeString(param.getSort()));
			}
		}
		return param;
	}
	
	//获取国家全称
	public static String getCountry(String country) {
		return map.get(country).toString();
	}
	
	// 获取运输方式的全称
	public static String getShippingType(String shippingType) {
		return map.get(shippingType).toString();
	}
	
	//根据货币符号获取货币代码
	public static String getCurrencySymbol(String currencyCode) {
		return map.get(currencyCode).toString();
	}
	
	//根据国家获取货币代码
	public static String getCurrencyCode(String country) {
		return hashMap.get(country).toString();
	}
	
	//处理国家
	@SuppressWarnings("hiding")
	public static <T> List<T> changeList(List<T> list) {
		
		Field field = null;
		
		for (T t : list) {
			if(field == null) {
				field = getField(t.getClass(), "countryId");
			}
			try {
				boolean accessible = field.isAccessible();
				if(!accessible) {
					field.setAccessible(true);
					field.set(t, getCountry(field.get(t).toString()));
					field.setAccessible(accessible);
				} else {
					field.set(t, getCountry(field.get(t).toString()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public static Field getField(Class cls, String country) {
		Field[] fields = cls.getDeclaredFields();
		Field field = null;
		for (Field f : fields) {

			if (f.getName().equalsIgnoreCase(country)) {
				field = f;
				break;
			}
		}
		return field;
	} 
}
