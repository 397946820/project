package com.it.ocs.cal.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.it.ocs.cal.dao.ILightEbayRateDao;
import com.it.ocs.cal.model.LightEbayRateModel;
import com.it.ocs.cal.model.LightEbayTaxDeModel;
import com.it.ocs.cal.vo.LightEbayRateVo;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.excel.utils.ExcelUtils;

import net.sf.json.JSONObject;

@Service("lightEbayRateExport")
public class LightEbayRateExportService extends AExcelExport {

	@Autowired
	private ILightEbayRateDao lightEbayRateDao;


	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		LightEbayRateVo model = ExcelUtils.getObject(request, LightEbayRateVo.class);
		return lightEbayRateDao.queryByObject(model);
	}

	@Override
	protected void init(HttpServletRequest request) {
		String fileName = "税数据.xlsx";
		super.initModel(LightEbayRateModel.class, fileName);
	}

}
