package com.it.ocs.customerCenter.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.customerCenter.dao.IClaimProductDao;
import com.it.ocs.customerCenter.model.ClaimProductModel;
import com.it.ocs.customerCenter.util.MapUtil;
import com.it.ocs.excel.service.AExcelExport;
@Service("exportCClaimProduct")
public class ClaimProductExportService extends AExcelExport {
	@Autowired
	private IClaimProductDao claimProductDao;
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String, String[]> maps = request.getParameterMap();
		Map<String, String> map = MapUtil.MapsToMap(maps);
		List<Map<String, Object>> claimProducts = claimProductDao.selectClaimProduct(map);
		return claimProducts;
	}

	@Override
	protected void init(HttpServletRequest request) {
		super.initModel(ClaimProductModel.class, "Claim Products.xlsx");
	}

}
