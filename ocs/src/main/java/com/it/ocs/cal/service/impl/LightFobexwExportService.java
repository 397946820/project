package com.it.ocs.cal.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.it.ocs.cal.model.FobexwModel;
import com.it.ocs.excel.service.AExcelExport;

@Service("lightFobexwExport")
public class LightFobexwExportService extends AExcelExport {

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		String[] strings = new String[] {};
		if (map.containsKey("template")) {
			strings = map.get("template");
		}
		String string = strings[0];
		if (!string.equals("{}")) {
			return (List<Map<String, Object>>) JSONArray.parse(string);
		}
		return null;
	}

	@Override
	protected void init(HttpServletRequest request) {
		String fileName = "EXWFOB推算数据.xlsx";
		super.initModel(FobexwModel.class, fileName);
	}

}
