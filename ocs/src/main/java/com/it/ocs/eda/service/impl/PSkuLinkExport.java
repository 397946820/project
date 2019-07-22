package com.it.ocs.eda.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.PSkuLinkExportModel;
import com.it.ocs.excel.service.AExcelExport;

@Service("pSkuLinkExport")
public class PSkuLinkExport extends AExcelExport{
	
	@Autowired
	private IEDADao iEDADao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		List<Map<String, Object>>  rows = iEDADao.getPSkuLinkDatas();
		List<Map<String,Object>> returnData = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> row : rows){
			Map<String, Object> map = new HashMap<>();
			map.put("PSKU", row.get("PSKU"));
			map.put("SKUATTR", "");
			map.put("ITEMID", "");
			map.put("TITLE", "");
			map.put("DESCRIPTION", "");
			map.put("KEYWORD", "");
			map.put("PRICE", "");

			String skusStr = (String)row.get("SKU");
			String skus[] = skusStr.split(",");
			String qtyStr = (String)row.get("QTY");
			String qtys[] = qtyStr.split(",");
			for(int i=0;i<16;i++){
				if(i<skus.length){
					map.put("SKU"+i, skus[i]);
					map.put("QTY"+i, qtys[i]);
				}else{
					map.put("SKU"+i, "");
					map.put("QTY"+i, "");
				}
			}
			returnData.add(map);
		}
		return returnData;
	}

	@Override
	protected void init(HttpServletRequest request) {
		super.initModel(PSkuLinkExportModel.class, "销售SKU与原始SKU关联数据.xls");
	}

}
