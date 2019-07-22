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
import com.it.ocs.eda.model.OrderExportOfEDAModel;
import com.it.ocs.eda.model.ShipOrderInfo;
import com.it.ocs.excel.service.AExcelExport;

import net.sf.json.JSONArray;

@Service("orderShippingEastExport")
public class OrderShippingEastExportService extends AExcelExport {
	
	@Autowired
	private IEDADao iEDADao;
	
	@Autowired
	private EDAService edaService;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		// ebay
		Map<String, List<ShipOrderInfo>> map = new HashMap();
		Map<String,Map<String, List<ShipOrderInfo>>> orders = edaService.getOrderForEast(iEDADao.getEBayUSShipOrders());
		map.putAll(orders.get("east"));
		//walmart
		orders = edaService.getOrderForEast(iEDADao.getWalmartShipOrders());
		map.putAll(orders.get("east"));
		//官网
		orders = edaService.getOrderForEast(iEDADao.getLightShipOrders());
		map.putAll(orders.get("east"));

		//解析每个订单存储生成excel数据导出
		List<Map<String, Object>> rows = new ArrayList<>();
		for (Map.Entry<String, List<ShipOrderInfo>> entry : map.entrySet()) {
			boolean first = true;
			Map<String, Object> row = new HashMap<>();
			int index = 1;
			for(ShipOrderInfo s : entry.getValue()){
				if(first){
					linkExcelModel(row, s);
					first = false;
				}
				row.put("SKU"+index,s.getWarehouseSku());
				row.put("QTY"+index,s.getQuantity());
				index++;
			}
			rows.add(row);
		}
		return rows;
	}

	private void linkExcelModel(Map<String, Object> row, ShipOrderInfo s) {
		row.put("WAREHOUSECODE","");
		String pf = s.getPlatform();
		String poNumber = s.getOrderOCSId();
		row.put("REFERENCECODE",pf.substring(0, 2).toUpperCase() + poNumber);
		row.put("DELIVERYSTYLE","");
		row.put("INSURANCETYPE","");
		row.put("SALESPLATFORM","");
		row.put("SALESTRANSACTION","");
		row.put("NAME",s.getName());
		row.put("COMPANY","");
		row.put("COUNTRY",USWestShipPackageSelectionSupport.formatCountry(s.getCountry()));
		row.put("PROVINCE",USWestShipPackageSelectionSupport.formatProve(s.getProvState()));
		row.put("CITY",s.getCity());
		row.put("STREET",s.getAddressLine1());
		row.put("DOORPLATE",s.getAddressLine2());
		row.put("ZIPCODE",s.getPostalCode());
		row.put("EMAIL",s.getEmail());
		row.put("PHONE",s.getPhone());
		row.put("REMARK",s.getDescription());
		row.put("SKU1","");
		row.put("QTY1","");
		row.put("SKU2","");
		row.put("QTY2","");
		row.put("SKU3","");
		row.put("QTY3","");
		row.put("SKU4","");
		row.put("QTY4","");
		row.put("SKU5","");
		row.put("QTY5","");
		row.put("SKU6","");
		row.put("QTY6","");
		row.put("SKU7","");
		row.put("QTY7","");
		row.put("SKU8","");
		row.put("QTY8","");
		row.put("SKU9","");
		row.put("QTY9","");
		row.put("SKU10","");
		row.put("QTY10","");
		row.put("SKU11","");
		row.put("QTY11","");
		row.put("SKU12","");
		row.put("QTY12","");
		row.put("SKU13","");
		row.put("QTY13","");
		row.put("SKU14","");
		row.put("QTY14","");
		row.put("SKU15","");
		row.put("QTY15","");
		row.put("SKU16","");
		row.put("QTY16","");
		row.put("SKU17","");
		row.put("QTY17","");
		row.put("SKU18","");
		row.put("QTY18","");
		row.put("SKU19","");
		row.put("QTY19","");
	}

	@Override
	protected void init(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		super.initModel(OrderExportOfEDAModel.class, "US美东仓未发货订单信息-" + dateStr.replace(":", "-") + ".xlsx");
		
	}

}
