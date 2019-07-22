package com.it.ocs.salereport.utils;

import java.util.HashMap;
import java.util.Map;

public class StockCodeUtil {
	public static final Map<String,Integer> sc;
	//US01 美国亚马逊
	//US02 ebay 官网美国站
	//US03 销量0
	//DE01 亚马逊 德国，法国，意大利IT，西班牙ES，英国 SKU非UK
	//DE02 ebay 官网德国站
	//UK01 亚马逊 英国SKU是UK的
	//UK02 ebay 官网 英国站
	//JP01 日本站 亚马逊
	//CA01 加拿大站亚马逊
	static{
		sc = new HashMap<>();
		sc.put("US01", 1);
		sc.put("US02", 2);
		sc.put("US03", 3);
		sc.put("DE01", 4);
		sc.put("DE02", 5);
		sc.put("UK03", 6);
		sc.put("UK02", 7);
		sc.put("JP01", 8);
		sc.put("CA01", 9);
	}
	
	public static int format(String scode){
		Integer s = sc.get(scode);
		if(null == s){
			return 0;
		}else{
			return s;
		}
		
	}
}
