package com.it.ocs.common.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.common.support.IAction;

public class MapUtil {
	public static List<Map<String, Object>> strToListMap(String str) {
		List<Map<String, Object>> result = Lists.newArrayList();
		if (str.trim().equals("[]") || !str.trim().startsWith("[") || !str.trim().endsWith("]")) {
			return result;
		}
		String arrayContent = str.trim().substring(1, str.length() - 1);
		StringBuffer mapStr = new StringBuffer();
		for (char charcode : arrayContent.toCharArray()) {
			if (entryEnd(charcode, mapStr)) {
				Map<String, Object> map = constructMapForStr(mapStr.toString());
				result.add(map);
				mapStr = new StringBuffer();
				continue;
			}
			mapStr.append(charcode);
		}
		if (valueIsObj(mapStr)) {
			Map<String, Object> map = constructMapForStr(mapStr.toString());
			result.add(map);
		}
		return result;
	}

	public static Map<String, Object> strToMap(String str) {
		return constructMapForStr(str);
	}

	private static Map<String, Object> constructMapForStr(String str) {
		Map<String, Object> result = null;
		if (!StringUtils.isEmpty(str)) {
			if (!str.trim().startsWith("{") || !str.trim().endsWith("}")) {
				return result;
			} else {
				result = Maps.newConcurrentMap();
				String jsonContent = str.trim().substring(1, str.length() - 1);
				char[] jsonchar = jsonContent.toCharArray();
				StringBuffer keyBuffer = new StringBuffer();
				boolean keyAppend = true;
				StringBuffer valueBuffer = new StringBuffer();
				boolean valueAppend = false;
				for (char code : jsonchar) {
					if (code == ' ' || code == '\"') {
						continue;
					} else if (code == '=' || code == ':') {
						if (!keyAppend && valueAppend) {
							valueBuffer.append(code);
						} else {
							keyAppend = false;
							valueAppend = true;
						}
					} else if (entryEnd(code, valueBuffer)) {
						addEntry(result, keyBuffer, valueBuffer);
						keyBuffer = new StringBuffer();
						valueBuffer = new StringBuffer();
						valueAppend = false;
						keyAppend = true;
					} else {
						if (keyAppend && !valueAppend) {
							keyBuffer.append(code);
						}
						if (valueAppend && !keyAppend) {
							valueBuffer.append(code);
						}
					}
				}
				addEntry(result, keyBuffer, valueBuffer);
			}
		}
		return result;
	}

	private static void addEntry(Map<String, Object> result, StringBuffer keyBuffer, StringBuffer valueBuffer) {
		if (valueIsObj(valueBuffer)) {
			Map<String, Object> map = constructMapForStr(valueBuffer.toString());
			if (!MapUtils.isEmpty(map)) {
				result.put(keyBuffer.toString(), map);
			}
		} else if (valueIsArray(valueBuffer)) {
			List<Map<String, Object>> maps = strToListMap(valueBuffer.toString());
			if (!CollectionUtil.isNullOrEmpty(maps)) {
				result.put(keyBuffer.toString(), maps);
			}
		} else {
			result.put(keyBuffer.toString(), valueBuffer.toString());
		}
	}

	private static boolean entryEnd(char charcode, StringBuffer valueBuffer) {
		boolean result = false;
		if (charcode == ',') {
			if (valueIsObj(valueBuffer) || valueIsArray(valueBuffer)
					|| (!valueBuffer.toString().contains("{") && !valueBuffer.toString().contains("["))) {
				result = true;
			}
		}
		return result;
	}

	private static boolean valueIsObj(StringBuffer valueBuffer) {
		return !StringUtils.isEmpty(valueBuffer) && valueBuffer.toString().startsWith("{")
				&& valueBuffer.toString().endsWith("}")
				&& tagCount('{', valueBuffer.toString()) == tagCount('}', valueBuffer.toString());
	}

	private static boolean valueIsArray(StringBuffer valueBuffer) {
		return !StringUtils.isEmpty(valueBuffer) && valueBuffer.toString().startsWith("[")
				&& valueBuffer.toString().endsWith("]")
				&& tagCount('[', valueBuffer.toString()) == tagCount(']', valueBuffer.toString());
	}

	private static int tagCount(char tag, String str) {
		int count = 0;
		for (char charcode : str.toCharArray()) {
			if (charcode == tag) {
				count++;
			}
		}
		return count;
	}
	public static Map<String, Object> removeNullOrEmpty(Map<String,Object> param) {
		Map<String,Object> result = Maps.newConcurrentMap();
		CollectionUtil.each(param.keySet(), new IAction<String>() {
			@Override
			public void excute(String key) {
				if (param.containsKey(key) && param.get(key) != null && !param.get(key).toString().trim().equals("")) {
					result.put(key, param.get(key));
				}
			}
		});
		return result;
	}
}
