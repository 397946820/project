package com.it.ocs.cal.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.IHandlingChargesDao;
import com.it.ocs.cal.model.LightHandlingChargesModel;
import com.it.ocs.cal.vo.LightHandlingChargesVo;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("handlingChargesExport")
public class HandlingChargesExportService extends AExcelExport {

	@Autowired
	private IHandlingChargesDao handlingChargesDao;


	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		LightHandlingChargesVo model = ExcelUtils.getObject(request, LightHandlingChargesVo.class);
		if(model == null) {
			model = new LightHandlingChargesVo();
			model.setType(1);
		}
		return handlingChargesDao.queryByObject(model);
	}

	@Override
	protected void init(HttpServletRequest request) {
		String fileName = "费用规则数据.xlsx";
		super.initModel(LightHandlingChargesModel.class, fileName);
	}

}
