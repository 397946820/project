package com.it.ocs.vc.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.excel.service.AExcelDynamicExport;
import com.it.ocs.vc.dao.IAmazonVcDAO;
import com.it.ocs.vc.model.AmazonVcModel;

@Service("amazonVcExport")
public class AmazonVcExportService extends AExcelDynamicExport {
	@Autowired
	private IAmazonVcDAO amazonVcDAO;
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			param.put(entry.getKey(), null == entry.getValue() || entry.getValue().length == 0 ? null : entry.getValue()[0]);
		}
		List<AmazonVcModel> vcModels = amazonVcDAO.queryByFormParam(param);
		List<Map<String, Object>> result = BeanConvertUtil.listToMap(vcModels);
		return result;
	}

	@Override
	protected void init(HttpServletRequest request) {
		String filename = String.format("amazon_vc_%s.xlsx", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		super.initModel(AmazonVcModel.class, filename, null);
	}

}
