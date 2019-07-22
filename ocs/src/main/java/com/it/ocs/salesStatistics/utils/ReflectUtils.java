package com.it.ocs.salesStatistics.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

public class ReflectUtils {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getMethod(String name, Class cla) throws Exception {
		char[] ch = name.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		String fieldName = new String(ch);
		fieldName = "get" + fieldName;
		return cla.getMethod(fieldName);

	}

	public static String getValue(Object object, Method method) throws Exception {
		Object invoke = method.invoke(object);
		if (invoke == null) {
			return null;
		}
		return invoke.toString();
	}

	@SuppressWarnings("rawtypes")
	public static Field getField(Class cls, String whichTime) {
		Field[] fields = cls.getDeclaredFields();
		Field field = null;
		for (Field f : fields) {

			if (f.getName().equalsIgnoreCase(whichTime)) {
				field = f;
				break;
			}
		}
		return field;
	}

	/**
	 * 通过字段名从对象或对象的父类中得到字段的值
	 * 
	 * @param object
	 *            对象实例
	 * @param fieldName
	 *            字段名
	 * @return 字段对应的值
	 * @throws Exception
	 */
	public static Object getValue(Object object, String fieldName) throws Exception {
		if (object == null) {
			return null;
		}
		if (StringUtils.isBlank(fieldName)) {
			return null;
		}
		Method[] methods = object.getClass().getMethods();
		try {
			for (Method method : methods) {
				if (method.getName().startsWith("get") && method.getName().toLowerCase().equals("get"+fieldName.toLowerCase())) {
					return method.invoke(object) == null ? "" : method.invoke(object);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object setValue(Object object, String fieldName, Double value) throws Exception {
		if (object == null) {
			return null;
		}
		if (StringUtils.isBlank(fieldName)) {
			return null;
		}
		Method[] methods = object.getClass().getMethods();
		try {
			for (Method method : methods) {
				if (method.getName().startsWith("set") && method.getName().toLowerCase().contains(fieldName)) {
					boolean accessible = method.isAccessible();
					if(!accessible) {
						method.setAccessible(true);
						method.invoke(object, value);
						method.setAccessible(false);
					} else {
						method.invoke(object, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
