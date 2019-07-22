package com.it.ocs.eda.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.OrderExportOfUSWestModel;
import com.it.ocs.eda.model.ShipOrderInfo;
import com.it.ocs.eda.utils.ListUtils;
import com.it.ocs.excel.service.AExcelExport;

@Service("orderShippingWestExport")
public class OrderShippingWestExportService extends AExcelExport {
	@Autowired
	private IEDADao iEDADao;
	
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		String exportType = request.getParameter("exportType");
		
		Map<String,Object> param = new HashMap<>();
		param.put("platform" , request.getParameter("platform"));
		if("1".equals(exportType)){
			param.put("status" , "0");
		}else{
			param.put("status" , request.getParameter("status"));
		}
		
		param.put("MerchantSKU" , request.getParameter("MerchantSKU"));
		param.put("CarrierServiceTypeCode" , request.getParameter("CarrierServiceTypeCode"));
		param.put("orderId" , request.getParameter("orderId"));
		param.put("timeStart" , request.getParameter("timeStart"));
		param.put("timeEnd" , request.getParameter("timeEnd"));
		param.put("optTtimeStart" , request.getParameter("optTtimeStart"));
		param.put("optTimeEnd" , request.getParameter("optTimeEnd"));
		List<Map<String, Object>> list = iEDADao.exportDataByParam(param);
		for(Map<String, Object> map : list){
			String creatTime = map.get("DISPLAYABLEORDERDATE").toString();
			creatTime = creatTime.replace(" ", "T");
			creatTime = creatTime.replace(".0", "");
			map.put("DISPLAYABLEORDERDATE", creatTime);
			//平台
			String platform = map.get("PLATFORM").toString();
			if("light".equals(platform)){
				String address1 = map.get("ADDRESSFIELDONE").toString();
				String ad[] = address1.split("\\n");
				map.put("ADDRESSFIELDONE", ad[0].replaceAll("\\s", " "));
				Object address2 = map.get("ADDRESSFIELDTWO");
				if(ad.length > 1){
					String ad2 = "";
					for(int i = 1;i<ad.length;i++){
						ad2 = ad2 +" "+ ad[i];
					}
					if(null != address2){
						ad2 = " " + address2.toString();
					}
					map.put("ADDRESSFIELDTWO", ad2);
				}
				
				
				
			}
		}
		return list;
	}

	@Override
	protected void init(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		super.initModel(OrderExportOfUSWestModel.class, "US美西仓未发货订单信息-" + dateStr.replace(":", "-") + ".xlsx");
	}

}
