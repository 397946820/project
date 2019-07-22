package com.it.ocs.common;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;

public class RequestParamUtils {

	public static <T> RequestParam getRequestParam(String json, Class<T> clazz) throws Exception {
		RequestParam param = new RequestParam();
		if (!json.equals("{}")) {
			JSONObject jsonObject = JSON.parseObject(json);
			JSONObject jsonArray = jsonObject.getJSONObject("param");
			Object object = jsonArray.toJavaObject(clazz);
			Map<String, Object> map = BeanUtils.describe(object);
			param.setParam(map);
		}
		return param;

	}
}
