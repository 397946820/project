package com.it.ocs.synchronou.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

public class ListUtil {
	  public static int RETAIN = 0;
	  public static int REPLACE=1;
	/**
	 * 
	 * @param source(原集合)
	 * @param count(获取个数)
	 * @param method(0代表返回结果，1代表返回结果并替换原来的source)
	 * @return
	 */
	public static <T> List<T> getBeforeByCount(List<T> source,int count,int method){
		List<T> result = Lists.newArrayList();
		if (source.size()<=count) {
			return source;
		}
		for (int i = 0; i <count; i++) {
			result.add(source.get(i));
		}
		if (method==1) {
			source = Lists.newArrayList(result);
		}
	
		return result;
		
	}

    public static <T> List<T> getAfterByCount(List<T> source,int count,int method){
    	List<T> result = Lists.newArrayList();
    	int size = source.size();
		if (size<=count) {
			return source;
		}
		for (int i = 0; i <count; i++,size--) {
			result.add(source.get(size-1));
		}
		if (method==1) {
			source = Lists.newArrayList(result);
		}
		return result;
    	
    }

    public static <T> List<T> getByIndex(List<T> source,Integer... index ){
    	List<T> result = Lists.newArrayList();
    	int size = source.size();
    	for (int i : index) {
			if(i<size){
				result.add(source.get(i));
			}
		}
    	return result;
    }
  
    public static <T> List<T> getNewPointer(List<T> source){
    	List<T> result = Lists.newArrayList();
    	for (T t : source) {
    		result.add(t);
		}
    	return result;
    }
    public static int getMaxValue(int... objects){
    	int max = objects[0];
    	for (int object : objects) {
    		max= Math.max(object, max);
		}
    	return max;
    }
    public static <T> List<T> getListByMap(Map<String, List<T>> map){
    	List<T> result = Lists.newArrayList();
    	for(Map.Entry<String,List<T>> entry : map.entrySet()){
    		result.addAll(entry.getValue());
    	}
    	
    	return result;
    }
}
