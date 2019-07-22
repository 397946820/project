package com.it.ocs.synchronou.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;

public class ObjectAndJsonUtil {
	private static Method findMehtodByField(Field field,Method[] methods) {
		return CollectionUtil.search(methods, new IFunction<Method, Boolean>() {
			@Override
			public Boolean excute(Method obj) {
				return obj.getName().startsWith("get") 
						&& obj.getName().toLowerCase().contains(field.getName().toLowerCase())
						&& obj.getName().length() == ("get"+field.getName()).length();
			}
		});
	}
	public static <T> String ObjectToJson(Object ob,Class<T> object){
		StringBuffer result = new StringBuffer();
		result.append("{");
		Field[] fields = object.getDeclaredFields();
		Method[] methods = object.getDeclaredMethods();
		Method method=null ;
		try {
			T objectT = object.newInstance();
			Class obClass = ob.getClass();
			for (Field field : fields) {
				
				method = findMehtodByField(field, methods);
			    if (method!=null) {
			    	System.out.println(method.getName());
			    	Method method2 = obClass.getDeclaredMethod(method.getName());
			    	result.append(field.getName()+":");
			    	result.append(method.invoke(ob)+",");
				}
				
			}
			result.deleteCharAt(result.length()-1);
			result.append("}");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	public static String MapToJson(Map<String,String> map){
		
		StringBuffer result = new StringBuffer();
		result.append("{");
		for(Map.Entry<String, String> entry : map.entrySet()){
			
			result.append(entry.getKey()+":\""+entry.getValue()+"\",");
		}
		result.deleteCharAt(result.length()-1);
		result.append("}");
		return result.toString();
	}

	public static String MapsToJsonArray(List<Map<String, String>> maps,String name){
		
		StringBuffer result = new StringBuffer();
		if(!ValidationUtil.isNull(name)){
			result.append("{"+name+":");
		}
		result.append("[");
		
		for (Map<String, String> map : maps) {
			result.append("{");
			for(Map.Entry<String, String> entry : map.entrySet()){
				result.append(entry.getKey()+":\""+entry.getValue()+"\",");
			}
			result.deleteCharAt(result.length()-1);
			result.append("},");
		}
		result.deleteCharAt(result.length()-1);
		result.append("]");
		if(result.length()<2){
			return null;
		}else{
			if(!ValidationUtil.isNull(name)){
				result.append("}");
			}
			return result.toString();
		}
		
	}
	/**
	 * 
	 * @param maps
	 * @return
	 * 描述：字符串数组转jsonArray字符串
	 */
	public static String ArrayToJsonArray(Map<String, String[]> maps){
		StringBuffer result = new StringBuffer();
		result.append("[");
		for (Map.Entry<String, String[]> entry : maps.entrySet()) {
			String name = entry.getKey();
			String[] value = entry.getValue();
			for (String string : value) {
				result.append("{"+name+":\"");
				result.append(string+"\"},");
				
			}
			result.deleteCharAt(result.length()-1);
		}
		result.append("]");
		
		return result.toString();
	}
}
