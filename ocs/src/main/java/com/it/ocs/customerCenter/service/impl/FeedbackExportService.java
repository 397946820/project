package com.it.ocs.customerCenter.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.customerCenter.dao.ICustomerFeedbackDao;
import com.it.ocs.customerCenter.model.CustomerFeedbackModel;
import com.it.ocs.customerCenter.util.MapUtil;
import com.it.ocs.excel.service.AExcelExport;

@Service("exportCFeedback")
public class FeedbackExportService extends AExcelExport {
	@Autowired
	private ICustomerFeedbackDao feedbackDao;
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		
		Map<String,String[]> maps = request.getParameterMap();
		Map<String, String> map = MapUtil.MapsToMap(maps);
		List<Map<String,Object>> customerFeedbackModels = feedbackDao.selectCustomerF(map);
		return customerFeedbackModels;
	}

	@Override
	protected void init(HttpServletRequest request) {
		
		super.initModel(CustomerFeedbackModel.class, "反馈信息.xlsx");
	}

}
