package com.it.ocs.amazon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.dao.IAmazonReportDao;
import com.it.ocs.amazon.model.AmazonMyiUnsuppressedExportModel;
import com.it.ocs.excel.service.AExcelDynamicExport;


@Service("amazonMyiUnsuppressed")
public class AmazonMyiUnsuppressedExportService extends AExcelDynamicExport {

	@Autowired
	private IAmazonReportDao iAmazonReportDao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		String marketplace = request.getParameter("marketplace");
		map.put("marketplace", marketplace);
		map.put("requestMarketplace", "amazon.co.uk".equals(marketplace) ? "amazon.de" : marketplace); //特殊逻辑：英国站点下的数据对应请求列表的站点为德国
		String startTime = request.getParameter("startTime");
		map.put("startTime", startTime);
		return iAmazonReportDao.getMyiUnsuppressedExportData(map);
	}

	@Override
	protected void init(HttpServletRequest request) {
		String site =  request.getParameter("marketplace");
		site = site.substring(site.lastIndexOf(".") + 1).toUpperCase();
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String filename = String.format("Amazon_FBA_Inventory_%s_%s_%s.xlsx", site, startTime, endTime);
		String paramIncludeFields = request.getParameter("includeFields");
		String[] includeFields = StringUtils.isBlank(paramIncludeFields) ? null : paramIncludeFields.split(",");
		super.initModel(AmazonMyiUnsuppressedExportModel.class, filename, includeFields);
	}

}
