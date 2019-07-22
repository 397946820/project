package com.it.ocs.common;

import java.util.HashMap;

import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.StringUtil;

public class CamelKeyMap extends HashMap {

	@Override
	public Object put(Object key, Object value) {
		key = StringUtil.convertByStyle(key.toString(), Style.underlineToCamelhumpAndUppercase);
		return super.put(key, value);
	}

}
