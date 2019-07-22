package com.it.ocs.customerCenter.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.customerCenter.dao.ICustomerProductDao;
import com.it.ocs.customerCenter.model.CustomerProductModel;
import com.it.ocs.customerCenter.util.MapUtil;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.synchronou.util.ValidationUtil;
@Service("exportCProduct")
public class ProductExportService extends AExcelExport {
	
	@Autowired
	private ICustomerProductDao productDao;
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		
		Map<String, String[]> maps = request.getParameterMap();
		Map<String, String> map = MapUtil.MapsToMap(maps);
		List<CustomerProductModel>  customerProducts = productDao.selectCustomerPR(map);
		List<Map<String, Object>> result = Lists.newArrayList();
		for (CustomerProductModel customerProductModel : customerProducts) {
			Map<String, Object> map2 = MapUtil.beanToMap(customerProductModel);
			result.add(map2);
		}
		
		return result;
	}

	@Override
	protected void init(HttpServletRequest request) {
		
		super.initModel(CustomerProductModel.class, "Product信息.xlsx");
	}

}
