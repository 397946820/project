package com.it.ocs.salesStatistics.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.salesStatistics.dao.NoShipOrderDao;
import com.it.ocs.salesStatistics.model.DENoShipOrderExportModel;
import com.it.ocs.salesStatistics.model.NoShipSKUModel;

import net.sf.json.JSONObject;

@Service("DENoShipOrderExportService")
public class DENoShipOrderExportService extends AExcelExport {

	@Autowired
	private NoShipOrderDao noShipOrderDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		List<Map<String, Object>> noShipOrder = noShipOrderDao.getDENoShipOrderInfo();
		//US补发单临时放入DE发货列表
		/*List<Map<String, Object>> noShipUSWOrder = noShipOrderDao.getUSWOrder();
		if(null != noShipUSWOrder && noShipUSWOrder.size() > 0){
			noShipOrder.addAll(noShipUSWOrder);
		}*/
		List<Map<String, Object>> returnData = new ArrayList<>();
		for (Map<String, Object> order : noShipOrder) {
			order.put("DISPLAYABLEORDERCOMMENT", "Thank you for order");
			order.put("DELIVERYSLA", "Standard");
			order.put("CARRIER", "");
			order.put("TRACKINGNO", "");
			String pskus = (String) order.get("SELLINGSKU");
			String pqtys = (String) order.get("QTY");
			String platform = (String) order.get("PLATFORM");
			String skus[] = pskus.split(",");
			String qtys[] = pqtys.split(",");
			String transactionIds[] = order.get("TRANSACTIONID").toString().split(",");
			String itemIds[] = order.get("ITEMID").toString().split(",");
			
			if ("ebay".equals(platform)) {
				//处理补发单新地址问题
				String merchantFulfillmentOrderID = order.get("MERCHANTFULFILLMENTORDERID").toString();
				if(merchantFulfillmentOrderID.indexOf("W_") > -1){
					String orderId = merchantFulfillmentOrderID.substring(2,merchantFulfillmentOrderID.lastIndexOf("_"));
					String times = merchantFulfillmentOrderID.substring(merchantFulfillmentOrderID.lastIndexOf("_")+1);
					String  account = order.get("PLATFORMACCOUNT").toString();
					Map<String,Object> paramMap = new HashMap<>();
					paramMap.put("orderId", orderId);
					paramMap.put("times", times);
					paramMap.put("account", account);
					String newAddress = noShipOrderDao.getWOrderNewAddress(paramMap);
					if(null != newAddress && !"".equals(newAddress)){
						JSONObject json = JSONObject.fromObject(newAddress);
						order.put("ADDRESSNAME", json.getString("name"));
						order.put("ADDRESSFIELDONE", json.getString("addressLine1"));
						order.put("ADDRESSFIELDTWO", json.getString("addressLine2"));
						order.put("ADDRESSCITY", json.getString("city"));
						order.put("ADDRESSPOSTALCODE", json.getString("postalCode"));
						order.put("ADDRESSPHONENUMBER", json.getString("phone"));
						order.put("ADDRESSSTATEORREGION", json.getString("provState"));
						order.put("ADDRESSCOUNTRYCODE", json.getString("country"));
					}
				}
				for (int i = 0; i < skus.length; i++) {
					String psku = skus[i];
					if (psku.contains("^")) {
						psku = psku.replace("^", ",");
					} 
					getEbayDeNoShip(order, psku, transactionIds[i], itemIds[i], qtys[i], returnData);
				}

			} else if ("light".equals(platform)) {
				List<Map<String, Object>> orderData = new ArrayList<>();
				for (int i = 0; i < skus.length; i++) {
					String sku = skus[i];
					int qty = Integer.parseInt(qtys[i]);
					Map<String, Object> row = new HashMap<>();
					row.putAll(order);
					row.put("SELLINGSKU", sku);
					row.put("QTY", qty);
					row.put("MERCHANTSKU", sku);
					row.put("QUANTITY", qty);
					row.put("MERCHANTFULFILLMENTORDERITEMID", sku);
					orderData.add(row);
				}
				if (orderData.size() > 0) {
					returnData.addAll(orderData);
				}
			}

		}
		return returnData;
	}

	private void getEbayDeNoShip(Map<String, Object> order, String psku, String transactionId, String itemId,
			String qty, List<Map<String, Object>> returnData) {
		List<Map<String, Object>> orderData = new ArrayList<>();
		order.put("TRANSACTIONID", transactionId);
		order.put("ITEMID", itemId);
		int pqty = Integer.parseInt(qty);
		// 获取sku映射关系
		List<NoShipSKUModel> skuInfo = noShipOrderDao.getSkusByPSku(psku);
		if (null == skuInfo || skuInfo.size() == 0) {
			NoShipSKUModel noShipSku = noShipOrderDao.getSku(psku);
			if (null == noShipSku) {
				Map<String, Object> row = new HashMap<>();
				row.putAll(order);
				row.put("SELLINGSKU", psku);
				row.put("QTY", pqty);
				row.put("MERCHANTSKU", "");
				row.put("QUANTITY", pqty);
				row.put("MERCHANTFULFILLMENTORDERITEMID", "");
				orderData.add(row);
			} else {
				Map<String, Object> row = new HashMap<>();
				row.putAll(order);
				row.put("SELLINGSKU", psku);
				row.put("QTY", pqty);
				row.put("MERCHANTSKU", noShipSku.getSku());
				row.put("QUANTITY", pqty);
				row.put("MERCHANTFULFILLMENTORDERITEMID", noShipSku.getSku());
				orderData.add(row);
			}
		} else {
			for (NoShipSKUModel noShipSku : skuInfo) {
				Map<String, Object> row = new HashMap<>();
				row.putAll(order);
				row.put("SELLINGSKU", psku);
				row.put("QTY", pqty);
				row.put("MERCHANTSKU", noShipSku.getSku());
				row.put("QUANTITY", noShipSku.getQty() * pqty);
				row.put("MERCHANTFULFILLMENTORDERITEMID", noShipSku.getSku());
				orderData.add(row);
			}
		}

		if (orderData.size() > 0) {
			returnData.addAll(orderData);
		}
	}

	@Override
	protected void init(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		super.initModel(DENoShipOrderExportModel.class, "DE未发货订单信息-" + dateStr.replace(":", "-") + ".xlsx");

	}

}
