package com.it.ocs.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.common.collect.Maps;
import com.it.ocs.cal.utils.ReflectUtils;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;

public class BeanConvertUtil {
	public static <T> T mapToObject(Map<String, Object> map, Class<T> cls) {
		T obj = null;
		if (map != null && map.size() != 0) {
			try {
				Map<String,Object> objMap = Maps.newConcurrentMap();
				CollectionUtil.each(map.keySet(), new IAction<String>() {
					@Override
					public void excute(String obj) {
						if (map.get(obj) != null && !map.get(obj).equals("")) {
							objMap.put(obj, map.get(obj));
						}
					}
				});
				if(!objMap.isEmpty()) {
					obj = cls.newInstance();
					BeanUtils.populate(obj, objMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	@SuppressWarnings("rawtypes")
	public static <T> T mapToObject2(Map<String, Object> map, Class<T> cls) {
		T obj = null;
		if (map == null || map.isEmpty()) {
			return obj;
		}
		try {
			obj = cls.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(cls);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (map.containsKey(key)) {
					try {
						Object value = map.get(key);
						if (value instanceof Map && property.getPropertyType() instanceof Object) {
							Object typeObj = Class.forName(property.getPropertyType().getName()).newInstance();
							BeanUtils.populate(typeObj, (Map) value);
							Method setter = property.getWriteMethod();
							setter.invoke(obj, typeObj);
							continue;
						} else {
							Method setter = property.getWriteMethod();
							setter.invoke(obj, value);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;

	}
	public static <T> List<Map<String,Object>> listToMap(List<T> datas) {
		if (CollectionUtil.isNullOrEmpty(datas)) {
			return null;
		}
		List<Map<String,Object>> maps = new ArrayList<>();
		try {
			 // 获取javaBean属性
        	Field[] fields = ReflectUtils.getAllField(datas.get(0).getClass());
        	for (T obj : datas) {
        		Map<String,Object> dataMap = new HashMap<>();
        		for (Field field : fields) {
        			field.setAccessible(true);
        			dataMap.put(field.getName(),field.get(obj));
        		}
        		maps.add(dataMap);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}
	public static Map<String, Object> objectToMap(Object javaBean) {
		Map<String,Object> map = new HashMap<>();
        try {
            // 获取javaBean属性
        	Field[] fields = ReflectUtils.getAllField(javaBean.getClass());
        	for (Field f : fields) {
        		f.setAccessible(true);
        		map.put(f.getName(), f.get(javaBean));
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
	}
	public static Map<String,Object> objectToMap(Object javaBean,String[] fsName) {
		Map<String,Object> map = new HashMap<>();
		try {
            // 获取javaBean属性
        	Field[] fields = ReflectUtils.getAllField(javaBean.getClass());
        	for (Field f : fields) {
        		if (null != CollectionUtil.search(fsName, new IFunction<String, Boolean>() {
					@Override
					public Boolean excute(String obj) {
						return obj.toLowerCase().equals(f.getName().toLowerCase());
					}
				})) {
        			f.setAccessible(true);
        			map.put(f.getName(), f.get(javaBean));
        		}
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }  
        return map;
	}
}
