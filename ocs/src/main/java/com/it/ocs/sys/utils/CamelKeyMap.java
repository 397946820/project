package com.it.ocs.sys.utils;

import java.util.HashMap;

import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.StringUtil;

public class CamelKeyMap extends HashMap {
	
	@Override
	public Object put(Object key, Object value) {
		key = StringUtil.convertByStyle(key.toString(), Style.underlineToCamelhump);
		return super.put(key, value);
	}
}
