package com.it.ocs.fourPX.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.it.ocs.common.support.IAction;
import com.it.ocs.fourPX.model.ClientUneditable;

public class ReflectUtils {
	
	private static final Logger logger = Logger.getLogger(ReflectUtils.class);
	
	public static <T extends Object> String[] clientUneditableFields(Class<T> clazz) {
		List<String> uneditableFields = new ArrayList<String>();
		scanFields(clazz, new IAction<Field>() {
			@Override
			public void excute(Field field) {
				if(field.isAnnotationPresent(ClientUneditable.class)) {
					uneditableFields.add(field.getName());
				}
			}
		});
		return uneditableFields.toArray(new String[uneditableFields.size()]);
	}
	
	public static void scanFields(Class<?> clazz, IAction<Field> action) {
		if(clazz == null) {
			return;
		}
		
		Field[] fields = clazz.getDeclaredFields();
		if(null != fields && fields.length > 0) {
			boolean accessible;
			for (Field field : fields) {
				accessible = field.isAccessible();
				field.setAccessible(true);
				action.excute(field);
				field.setAccessible(accessible);
			}
		}
		
		scanFields(clazz.getSuperclass(), action);
	}
	
	public static <T extends Object> void fillingTarget(T target, Map<String, Object> source, boolean sourceKeyToUpperCase, boolean enabledUneditable) {
		ReflectUtils.scanFields(target.getClass(), new IAction<Field>() {
			@Override
			public void excute(Field field) {
				//1、没有启用不可编辑或者字段没有被标记为客户端不可编辑
				//2、字段是非静态、非常量的
				//满足以上两种情况，才可以对目标字段值进行修改
				if((!enabledUneditable || !field.isAnnotationPresent(ClientUneditable.class))
						&& !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
					ReflectUtils.setFieldValue(target, field, source.get(sourceKeyToUpperCase ? field.getName().toUpperCase() : field.getName()));
				}
			}
		});
	}
	
	public static void setFieldValue(Object obj, Field field, Object value) {
		try {
			if(null != value) {
				if(field.getType() == Character.class) {
					field.set(obj, value.toString().charAt(0));
				} else if(field.getType() == Date.class) {
//					if(value instanceof String) {
//						value = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(value.toString());
//					} else if(value instanceof oracle.sql.TIMESTAMP) {
//						value = new Date(((oracle.sql.TIMESTAMP) value).dateValue().getTime());
//					} else if(value instanceof oracle.sql.DATE) {
//						value = new Date(((oracle.sql.DATE) value).dateValue().getTime());
//					}
				} else {
					try {
						Method method = field.getType().getMethod("valueOf", field.getType() == String.class ? Object.class : String.class);
						value = method.invoke(null, value.toString());
					} catch (NoSuchMethodException e) {
						logger.warn("[" + ReflectUtils.class.getName() + ".setFieldValue(...)] - " + e.getMessage());
					}
				}
			}
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
