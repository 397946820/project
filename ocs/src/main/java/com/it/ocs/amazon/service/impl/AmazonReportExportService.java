package com.it.ocs.amazon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.dao.IAmazonReportDao;
import com.it.ocs.amazon.model.AmazonReportExportModel;
import com.it.ocs.excel.service.AExcelExport;

@Service("amazonReportDataExport")
public class AmazonReportExportService extends AExcelExport{
	
	@Autowired
	private IAmazonReportDao iAmazonReportDao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		map.put("marketplace",request.getParameter("marketplace"));
		map.put("orderId",request.getParameter("orderId"));
		map.put("sku",request.getParameter("sku"));
		map.put("startTime",request.getParameter("startTime"));
		map.put("endTime",request.getParameter("endTime"));
		map.put("orderType", request.getParameter("orderType"));
		return iAmazonReportDao.getExportDataByParam(map);
	}

	@Override
	protected void init(HttpServletRequest request) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String platform =  request.getParameter("marketplace");
		platform = platform.substring(platform.lastIndexOf(".")).toUpperCase();
		super.initModel(AmazonReportExportModel.class, "Amazon报表数据_"+platform+"_"+startTime+"_"+endTime+".xlsx");
		
	}

}
