package com.it.ocs.eda.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.InventoryHistoryModel;
import com.it.ocs.excel.service.AExcelExport;

@Service("inventoryHistoryExport")
public class InventoryHistoryExportSevice extends AExcelExport{
	@Autowired
	private IEDADao iEDADao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {

		Map<String,Object> param = new HashMap<>();
		param.put("sku" , request.getParameter("sku"));
		param.put("timeStart" ,request.getParameter("timeStart"));
		param.put("timeEnd" , request.getParameter("timeEnd"));
		param.put("billNum" , request.getParameter("billNum"));
		param.put("recordType" , request.getParameter("recordType"));
		param.put("warehouseId" , request.getParameter("warehouseId"));
		List<Map<String, Object>> list = iEDADao.exportInventoryHistoryDataByParam(param);
		//查询对应订单号
		for(Map<String, Object> map : list){
			int warehouseId = Integer.parseInt(map.get("WAREHOUSEID").toString());
			if(warehouseId == 2){
				map.put("WAREHOUSEID", "Los Angeles Warehouse");
			}else if(warehouseId == 7){
				map.put("WAREHOUSEID", "US New Jersey Warehouse");
			}
			Object billNumObj = map.get("BILLNUM");
			if(null != billNumObj){
				String billNum = billNumObj.toString();
				Map<String,Object> eda = iEDADao.getEDAOrderInfoByORAId(billNum);
				if(null == eda){
					map.put("ORDERID", "");
					map.put("EDAORDERCREATEDATE", "");
					continue;
				}
				map.put("ORDERID", eda.get("ORDERID"));
				map.put("EDAORDERCREATEDATE", eda.get("EDAORDERCREATEDATE"));
			}else{
				map.put("ORDERID", "");
				map.put("EDAORDERCREATEDATE", "");
			}
		}
		return list;
	}

	@Override
	protected void init(HttpServletRequest request) {
		//InventoryHistoryExportModel
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		super.initModel(InventoryHistoryModel.class, "US美西仓未发货订单信息-" + dateStr.replace(":", "-") + ".xlsx");
	}
}
