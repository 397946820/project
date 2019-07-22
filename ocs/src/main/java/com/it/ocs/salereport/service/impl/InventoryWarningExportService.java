package com.it.ocs.salereport.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.salereport.model.InventoryWarningExportModel;
import com.it.ocs.sys.dao.IInventoryWarningDao;

@Service("inventoryWarningExport")
public class InventoryWarningExportService extends AExcelExport{
	
	@Autowired
	private IInventoryWarningDao iInventoryWarningDao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sku", request.getParameter("sku"));
		map.put("scode", request.getParameter("scode"));
		map.put("dayTime", request.getParameter("dayTime"));
		map.put("ship_type", request.getParameter("ship_type"));
		map.put("platform", request.getParameter("platform"));
		return iInventoryWarningDao.queryInventoryWarning(map);
	}

	@Override
	protected void init(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		super.initModel(InventoryWarningExportModel.class, "SKU库存预警表-" + dateStr.replace(":", "-") + ".xlsx");
		
	}

}
