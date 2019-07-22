package com.it.ocs.customerCenter.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.customerCenter.dao.ICustomerRefundDao;
import com.it.ocs.customerCenter.model.CustomerRefundModel;
import com.it.ocs.customerCenter.util.MapUtil;
import com.it.ocs.excel.service.AExcelExport;

@Service("exportRefnd")
public class RefundExportService extends AExcelExport {

	@Autowired
	private ICustomerRefundDao refundDao;
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		
		Map<String, String[]> maps =  request.getParameterMap();
		Map<String, String> map = MapUtil.MapsToMap(maps);
		List<Map<String, Object>> customerRefundModels = refundDao.selectCustomerR(map);
		return customerRefundModels;
	}

	@Override
	protected void init(HttpServletRequest request) {
		
		super.initModel(CustomerRefundModel.class, "RefundRecord.xlsx");

	}

}
