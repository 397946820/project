package com.it.ocs.synchronou.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.synchronou.dao.IEbaySellerListDao;
import com.it.ocs.synchronou.model.SaleNoShipDataExportModel;

import net.sf.json.JSONObject;
@Service("saleItemInfoExport")
public class SaleItemInfoExportService extends AExcelExport {
	@Autowired
	private IEbaySellerListDao ebaySellerListDao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		List<Map<String, Object>> datas = ebaySellerListDao.getNoShipData(null);
		for(Map<String, Object> data:datas){
			String shipAddress = (String)data.get("SHIPPINGADDRESS");
			JSONObject json = JSONObject.fromObject(shipAddress);
			data.put("NAME", getValueByJson(json,"Name"));
			data.put("STREET1", getValueByJson(json,"Street1"));
			data.put("STREET2", getValueByJson(json,"Street2"));
			data.put("CITYNAME", getValueByJson(json,"CityName"));
			data.put("COUNTRYCODE", getValueByJson(json,"Country"));
			data.put("POSTALCODE", getValueByJson(json,"PostalCode"));
			data.put("PHONE", getValueByJson(json,"Phone"));
		}
		return datas;
	}

	@Override
	protected void init(HttpServletRequest request) {
		super.initModel(SaleNoShipDataExportModel.class, "未发货订单详情.xlsx");
		
	}

}
