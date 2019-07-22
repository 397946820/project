package com.it.ocs.eda.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

public class EDAJsonUtil {
	
	public static Map<String,Object> createData(Map<String,Object> keys,List<Map<String,Object>> data){
		Map<String,Object> map = new HashMap<String, Object>();
		for(Map.Entry<String, Object> entry:keys.entrySet()){
			Object key = entry.getValue();
			if(key instanceof Map){
				List<Map<String,Object>> list = new ArrayList<>();
				for(int i=0;i<data.size();i++){
					list.add(createData((Map<String,Object>)key,data.subList(i, i+1)));
				}
				map.put(entry.getKey(),list);
			}else{
				Object value = null;
				if(data.size()>0){
					value = data.get(0).get(String.valueOf(key).toUpperCase());
					if(value instanceof String){
						if(String.valueOf(value).startsWith("{")){
							value = JSONObject.fromObject(value);
						}else if(String.valueOf(value).startsWith("[")){
							value = JSONArray.fromObject(value);
						}
					}
				}else{
					value = "";
				}
				map.put(entry.getKey(), value);
			}
		}
		return map;
	}

	public static Map<String,Object> parseResponseJSON(Map<String,Object> keys,String data){
		JSONObject json = JSONObject.fromObject(data);
		return parseResponseJSON(keys,json);
	}
	public static Map<String,Object> parseResponseJSON(Map<String,Object> keys,JSONObject json){
		Map<String,Object> map = new HashMap<String, Object>();
		for(Map.Entry<String, Object> entry:keys.entrySet()){
			Object key = entry.getValue();
			if(key instanceof Map){
				
			}else if(key instanceof ArrayList){
				
			}else{
				 String value = getJSONValue(json,String.valueOf(key));
				 map.put(entry.getKey(), value);
			}
		}
		return map;
	}

	public static Map<String, Object> parseDataJSON(Map<String,Object> orderInfoKeySet, String response) {
		JSONObject json = JSONObject.fromObject(response);
		String data = getJSONValue(json,"data");
		if(!"".equals(data)){
			Map<String,Object> map = parseResponseJSON(orderInfoKeySet,data);
			return map;
		}
		return null;
	}

	
	public static String getJSONValue(JSONObject json,String key){
		String value = "";
		if (json.has(key)) {
			value = json.getString(key);
		}
		if(null == value ||"[]".equals(value)||"{}".equals(value)||"null".equals(value)){
			value = "";
		}
		return value;
	}

	public static List<Map<String, Object>> parseDataListJSON(Map<String,Object> keySet, String response) {
		JSONObject json = JSONObject.fromObject(response);
		String data = getJSONValue(json,"data");
		if(!"".equals(data)){
			List<Map<String, Object>> list = new ArrayList<>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i=0;i<jsonArray.size();i++){
				Map<String, Object> one = parseResponseJSON(keySet,jsonArray.getJSONObject(i));
				list.add(one);
			}
			return list;
		}
		return null;
	}
	
}
