package com.it.ocs.customerCenter.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.customerCenter.dao.ICustomerTicketsDao;
import com.it.ocs.customerCenter.model.CustomerTicketsModel;
import com.it.ocs.customerCenter.util.MapUtil;
import com.it.ocs.excel.service.AExcelExport;

@Service("exportCTickets")
public class TicketsExportService extends AExcelExport {

	@Autowired
	private ICustomerTicketsDao ticketsDao;
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		
		Map<String, String[]> maps = request.getParameterMap();
		Map<String, String> map = MapUtil.MapsToMap(maps);
		List<Map<String, Object>> customerTickets = ticketsDao.selectCustomerT(map);
		return customerTickets;
	}

	@Override
	protected void init(HttpServletRequest request) {
		
		super.initModel(CustomerTicketsModel.class, "Tickets信息.xlsx");
	}

}
