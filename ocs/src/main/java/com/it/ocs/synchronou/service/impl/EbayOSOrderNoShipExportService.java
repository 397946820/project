package com.it.ocs.synchronou.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.salesStatistics.dao.NoShipOrderDao;
import com.it.ocs.synchronou.model.EbayOSOrderNoShipModel;

@Service("ebayOSOrderNoShipExportService")
public class EbayOSOrderNoShipExportService extends AExcelExport {
	
	@Autowired
	private NoShipOrderDao noShipOrderDao;

	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		List<Map<String,Object>> list = noShipOrderDao.getOSOrderNoShip();
		int index = 1;
		for(Map<String,Object> map :list){
			map.put("INDEX", index);
			map.put("shipTime", "");
			map.put("jjSku", "");
			map.put("sCost", "");
			map.put("str1", "");
			map.put("str2", "");
			map.put("str3", "");
			map.put("str4", "");
			map.put("shipTranckingNumber", "");
			index++;
		}
		return list;
	}

	@Override
	protected void init(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		super.initModel(EbayOSOrderNoShipModel.class, "EBay未发货OS订单信息-" + dateStr.replace(":", "-") + ".xlsx");
		
	}

}
