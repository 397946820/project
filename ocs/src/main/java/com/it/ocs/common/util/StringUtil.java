package com.it.ocs.common.util;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.it.ocs.common.enums.Style;

public class StringUtil {
	/**
	 * 根据指定的样式进行转换
	 *
	 * @param str
	 * @param style
	 * @return
	 */
	public static String convertByStyle(String str, Style style) {
		if (StringUtils.isNotBlank(str)) {
			switch (style) {
			case camelhump:
				return camelhumpToUnderline(str);
			case uppercase:
				return str.toUpperCase();
			case lowercase:
				return str.toLowerCase();
			case camelhumpAndLowercase:
				return camelhumpToUnderline(str).toLowerCase();
			case camelhumpAndUppercase:
				return camelhumpToUnderline(str).toUpperCase();
			case underlineToCamelhump:
				return underlineToCamelhump(str);
			case underlineToCamelhumpAndLowercase:
				return underlineToCamelhump(str).toLowerCase();
			case underlineToCamelhumpAndUppercase:
				return underlineToCamelhump(str).toUpperCase();
			case normal:
			default:
				return str;
			}
		}
		return str;
	}

	/**
	 * 将驼峰风格替换为下划线风格
	 */
	public static String camelhumpToUnderline(String str) {
		final int size;
		final char[] chars;
		final StringBuilder sb = new StringBuilder((size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
		char c;
		for (int i = 0; i < size; i++) {
			c = chars[i];
			if (isUppercaseAlpha(c)) {
				sb.append('_').append(toLowerAscii(c));
			} else {
				sb.append(c);
			}
		}
		return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
	}

	/**
	 * 将下划线风格替换为驼峰风格
	 */
	public static String underlineToCamelhump(String str) {
		StringBuilder sb = new StringBuilder();

		boolean nextUpperCase = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '_') {
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
			} else {
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
			}
		}
		return sb.toString();
	}

	public static boolean isUppercaseAlpha(char c) {
		return (c >= 'A') && (c <= 'Z');
	}

	public static boolean isLowercaseAlpha(char c) {
		return (c >= 'a') && (c <= 'z');
	}

	public static char toUpperAscii(char c) {
		if (isLowercaseAlpha(c)) {
			c -= (char) 0x20;
		}
		return c;
	}

	public static char toLowerAscii(char c) {
		if (isUppercaseAlpha(c)) {
			c += (char) 0x20;
		}
		return c;
	}
	
	static final String COMMASPACE = ", ";
	
	@SuppressWarnings("rawtypes")
	public static String instanceDetail(Object obj) {
		if(obj == null) {
			return "null";
		}
		Class clazz = obj.getClass();
		StringBuilder detail = new StringBuilder(clazz.getName()).append(" => [ ");
		eachAppendPropertyDetail(obj, clazz, detail);
		String ret = detail.toString();
		if(ret.endsWith(COMMASPACE)) {
			ret = ret.substring(0, ret.lastIndexOf(COMMASPACE));
		}
		//Example: "com.it.ocs.*.ClassName => [ property1=value1, property2=value2 ]"
		return ret + " ]";
	}

	@SuppressWarnings("rawtypes")
	private static void eachAppendPropertyDetail(Object obj, Class clazz, StringBuilder propertyDetail) {
		if(clazz == null) {
			return;
		}
		
		Field[] fields = clazz.getDeclaredFields();
		int l = fields == null ? 0 : fields.length;
		if(l > 0) {
			Field f = null;
			Object v = null;
			for (int i = 0; i < l; i++) {
				f = fields[i];
				try {
					f.setAccessible(true);
					v = f.get(obj);
				} catch (Exception e) {
					v = null;
				}
				propertyDetail.append(f.getName()).append("=").append(String.valueOf(v)).append(COMMASPACE);
			}
		}
		eachAppendPropertyDetail(obj, clazz.getSuperclass(), propertyDetail);
	}
	
	public static String cancelNewline(String ov) {
		if(StringUtils.isBlank(ov)) {
			return ov;
		}

        return Pattern.compile("\r|\n").matcher(ov).replaceAll("");  
    }
	
	/*
	public static void main(String[] args) {
		String str = "\\abc\r\ra s\ndf\r\na s	d";
		System.out.println(str);
		System.out.println();
		System.out.println(cancelNewline(str));
	}*/
}