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
import com.it.ocs.salesStatistics.model.NoShipSKUModel;
import com.it.ocs.synchronou.model.EbaySmallPackageNewExportModel;

@Service("ebaySmallPackageOrderExportService")
public class EbaySmallPackageOrderExportService extends AExcelExport{
	
	@Autowired
	private NoShipOrderDao noShipOrderDao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		/*List<Map<String,Object>> list = noShipOrderDao.getSmallPackageOrder();
		for(Map<String,Object> map :list){
			map.put("CARRIER1", "");
			map.put("TRANCKINGNUMBER1", "");
			map.put("CARRIER2", "");
			map.put("TRANCKINGNUMBER2", "");
			map.put("CARRIER3", "");
			map.put("TRANCKINGNUMBER3", "");
		}*/
		List<Map<String,Object>> list = noShipOrderDao.getSmallPackageOrderNew();
		for(Map<String,Object> map :list){
			map.put("PRODUCT", "");
			map.put("USERID", "");
			map.put("CUSTOMLABEL", "");
			map.put("ISRETURN", "");
			map.put("CATEGORY", "");
			map.put("SHIPPINGANDHANDING", "");
			map.put("USTAX", "");
			map.put("PAYMENTMETHOD", "");
			map.put("SALEDATE", "");
			map.put("CHECKOUTDATE", "");
			map.put("PAIDONDATE", "");
			map.put("SHIPPEDONDATE", "");
			map.put("FEEDBACKLEFT", "");
			map.put("FEEDBACKRECEIVED", "");
			map.put("NOTESTOYOURSELF", "");
			map.put("LISTEDON", "");
			map.put("SOLDON", "");
			map.put("PAYPALTRANSACTIONID", "");
			map.put("DTRANSACTIONID", "");
			map.put("CARRIER", "");
			map.put("TRANCKINGNUMBER", "");
			//查询sku重量
			String sku = map.get("SKU").toString();
			NoShipSKUModel noShipSku = noShipOrderDao.getSkusWeight(sku);
			if(null == noShipSku){
				map.put("WEIGHT", "");
			}else{
				map.put("WEIGHT", null == noShipSku.getWeight() ? "":noShipSku.getWeight());
			}
		}
		return list;
	}

	@Override
	protected void init(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		super.initModel(EbaySmallPackageNewExportModel.class, "ebay小包未发货订单信息-" + dateStr.replace(":", "-") + ".xlsx");
		
	}

}
