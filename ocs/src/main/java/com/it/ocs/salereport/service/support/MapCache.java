package com.it.ocs.salereport.service.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 多线程Map临时缓存区，线程安全
 * @author chenyong
 *
 */
public class MapCache {
	private Map<String,Object> map ;
	
	private MapCache(){
		this.map = new HashMap<>();
	}
	
	public static MapCache getInstance(){
		return new MapCache();
	}
	
	public synchronized void put(String key,Object obj){
		map.put(key, obj);
	}
	
	public Object get(String key){
		if(map.containsKey(key)){
			return map.get(key);
		}
		return null;
	}
	
	public boolean containsKey(String key){
		return map.containsKey(key);
	}
}
