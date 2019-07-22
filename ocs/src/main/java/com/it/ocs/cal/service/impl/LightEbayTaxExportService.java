package com.it.ocs.cal.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ILightEbayTaxDao;
import com.it.ocs.cal.model.LightEbayTaxBaseModel;
import com.it.ocs.cal.vo.LightEbayTaxVo;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("lightEbayTaxExport")
public class LightEbayTaxExportService extends AExcelExport {

	@Autowired
	private ILightEbayTaxDao lightEbayTaxDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		LightEbayTaxVo model = ExcelUtils.getObject(request, LightEbayTaxVo.class);
		return lightEbayTaxDao.queryByObject(model);
	}

	@Override
	protected void init(HttpServletRequest request) {
		String fileName = "杂费单价数据.xlsx";
		super.initModel(LightEbayTaxBaseModel.class, fileName);
	}

}
