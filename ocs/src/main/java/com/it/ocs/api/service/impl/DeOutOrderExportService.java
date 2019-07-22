package com.it.ocs.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.api.dao.IDeWarehouseDao;
import com.it.ocs.api.model.DeOutOrderExportModel;
import com.it.ocs.excel.service.AExcelDynamicExport;


@Service("deOutOrder")
public class DeOutOrderExportService extends AExcelDynamicExport {

	@Autowired
	private IDeWarehouseDao deWarehouseDao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			param.put(entry.getKey(), null == entry.getValue() || entry.getValue().length == 0 ? null : entry.getValue()[0]);
		}
		return this.deWarehouseDao.getDeOutOrderExportData(param);
	}

	@Override
	protected void init(HttpServletRequest request) {
		String filename = String.format("DEOUTORDER_%s.xlsx", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		String paramIncludeFields = request.getParameter("includeFields");
		String[] includeFields = StringUtils.isBlank(paramIncludeFields) ? null : paramIncludeFields.split(",");
		super.initModel(DeOutOrderExportModel.class, filename, includeFields);
	}

}
