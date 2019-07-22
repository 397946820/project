package com.it.ocs.cal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.IProductCostDao;
import com.it.ocs.cal.model.ProductInfoImportModel;
import com.it.ocs.excel.service.AExcelImport;

@Service("productInfoImport")
public class ProductInfoImportService extends AExcelImport{
	
	@Autowired
	protected IProductCostDao iProductCost;
	
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		for(Map<String, Object> row : rows){
			String sku = row.get("sku").toString();
			String capitalOccupancyRate = row.get("capitalOccupancyRate").toString();
			//获取产品表id
			Integer id = iProductCost.getIdBySKU(sku);
			if(null == id){
				errorsMsg.add("SKU:"+sku+" 没有查询到在售版本。");
			}else{
				//解析数据
				String countrys[] = row.get("country").toString().split(",");
				String categorys[] = row.get("category").toString().split(",");
				String unavailabilitys[] = row.get("unavailability").toString().split(",");
				String returnRates[] = row.get("returnRate").toString().split(",");
				String rechargeRates[] = row.get("rechargeRate").toString().split(",");
				if(countrys.length > 0 && countrys.length == categorys.length && categorys.length == unavailabilitys.length && unavailabilitys.length == returnRates.length && returnRates.length == rechargeRates.length){
					//更新数据库
					for(int i =0;i<countrys.length;i++){
						Map<String,Object> m = new HashMap<>();
						m.put("parentId", id);
						m.put("sku", sku);
						m.put("capitalOccupancyRate", Double.parseDouble(capitalOccupancyRate));
						m.put("country", countrys[i]);
						m.put("category", categorys[i]);
						m.put("unavailability", Double.parseDouble(unavailabilitys[i]));
						m.put("returnRate", Double.parseDouble(returnRates[i]));
						m.put("rechargeRate", Double.parseDouble(rechargeRates[i]));
						iProductCost.updateInfo(m);
					}
				}else{
					errorsMsg.add("SKU:"+sku+" 对应国家的录入信息缺失。");
				}
				
			}
		}
		return 0;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return ProductInfoImportModel.class;
	}

}
