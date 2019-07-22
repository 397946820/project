package com.it.ocs.cal.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ILightEbayCustomerDao;
import com.it.ocs.cal.model.LightEbayCustomerModel;
import com.it.ocs.cal.vo.LightEbayCustomerVo;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("lightEbayCustomerExport")
public class LightEbayCustomerExportService extends AExcelExport {

	@Autowired
	private ILightEbayCustomerDao lightEbayCustomerDao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		LightEbayCustomerVo model = ExcelUtils.getObject(request, LightEbayCustomerVo.class);
		return lightEbayCustomerDao.queryByObject(model);
	}

	@Override
	protected void init(HttpServletRequest request) {
		String fileName = "物流规则数据.xlsx";
		super.initModel(LightEbayCustomerModel.class, fileName);
	}

}
