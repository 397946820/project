package com.it.ocs.cal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.IProductCostDao;
import com.it.ocs.cal.model.ProductInfoAvgMonthImportModel;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.synchronou.util.ValidationUtil;

@Service("productInfoAvgMonthImport")
public class ProductInfoAvgMonthImportService extends AExcelImport{
	@Autowired
	protected IProductCostDao iProductCost;
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		String rowNumber = String.valueOf(row.get("rowNumber"));
		Object countrys = row.get("country");
		if (ValidationUtil.isNullOrEmpty(countrys)) {
			errorsMsg.add("第" + rowNumber + "行国家为空");
			return false;
		}
		Object avgMonths = row.get("avgMonth");
		if (ValidationUtil.isNullOrEmpty(avgMonths)) {
			errorsMsg.add("第" + rowNumber + "行平均存储月份为空");
			return false;
		}
		
		String countryStr = countrys.toString();
		String avgMonthStr = avgMonths.toString();
		if(countryStr.split(",").length != avgMonthStr.split(",").length){
			errorsMsg.add("第" + rowNumber + "行国家与平均存储月份数据不对等");
			return false;
		}
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
			String sku = row.get("sku").toString().trim();
			//查询parentID
			Integer parentId = iProductCost.getIdBySKU(sku);
			if(null == parentId){
				String rowNumber = String.valueOf(row.get("rowNumber"));
				errorsMsg.add("第" + rowNumber + "行sku在OMS中不存在或者停售状态");
				continue;
			}
			String countryStr = row.get("country").toString().trim();
			String avgMonthStr = row.get("avgMonth").toString().trim();
			String countrys[]  = countryStr.split(",");
			String avgMonths[] = avgMonthStr.split(",");
			for(int i =0;i<countrys.length;i++){
				Map<String,Object> map = new HashMap();
				map.put("parentId", parentId);
				map.put("country", countrys[i]);
				map.put("avgMonth", Double.parseDouble(avgMonths[i]));
				iProductCost.updateAvgMonth(map);
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
		return ProductInfoAvgMonthImportModel.class;
	}

}
