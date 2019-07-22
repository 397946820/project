package com.it.ocs.cal.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.cal.excel.vo.LECalculateExcelVO;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelExport;

public class LightEbayTaximeterExcelExport extends AExcelExport{
	private static LightEbayTaximeterExcelExport LE_METER_EXPORT = null;
	@SuppressWarnings("unchecked")
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		List<Map<String,Object>> requestData = (List<Map<String, Object>>) request.getAttribute("data");
		List<Map<String,Object>> datas = Lists.newArrayList();
		CollectionUtil.each(requestData, new IAction<Map<String,Object>>() {
			@Override
			public void excute(Map<String, Object> data) {
				Map<String,Object> newData = keyToUpperCase(data);
				datas.add(newData);
			}
		});
		return datas;
	}
	private Map<String,Object> keyToUpperCase(Map<String,Object> data) {
		if (null != data && !data.isEmpty()) {
			Map<String,Object> result = Maps.newConcurrentMap();
			CollectionUtil.each(data.keySet(), new IAction<String>() {
				@Override
				public void excute(String obj) {
					if (null != data.get(obj)) {
						result.put(obj.toUpperCase(), data.get(obj));
					} else {
						result.put(obj.toUpperCase(), " ");
					}
				}
			});
			return result;
		}
		return data;
	}
	@Override
	protected void init(HttpServletRequest request) {
		String fileName = (String) request.getAttribute("fileName");
		Class<?> clazz = (Class<?>) request.getAttribute("clazz");
		initModel(clazz, fileName);
	}
	public static LightEbayTaximeterExcelExport getInstance() {
		if (null == LE_METER_EXPORT) {
			LE_METER_EXPORT = new LightEbayTaximeterExcelExport();
		}
		return LE_METER_EXPORT;
	}
	
	

}
