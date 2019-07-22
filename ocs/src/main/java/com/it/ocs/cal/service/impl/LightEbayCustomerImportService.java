package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ILightEbayCustomerDao;
import com.it.ocs.cal.model.LightEbayCustomerModel;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("lightEbayCustomerImport")
public class LightEbayCustomerImportService extends AExcelImport {

	@Autowired
	private ILightEbayCustomerDao lightEbayCustomerDao;

	private final String[] fields = new String[] { "country", "region", "shippingType", "fromWeight", "toWeight",
			"unitPrice", "currencyCode", "volumeWeightCoefficient" };

	private static Map<String, String> map = new HashMap<>();

	static {
		map.put("country", "国家");
		map.put("region", "地区");
		map.put("shippingType", "运输方式");
		map.put("fromWeight", "起始重量");
		map.put("toWeight", "结束重量");
		map.put("unitPrice", "单价");
		map.put("currencyCode", "货币");
		map.put("volumeWeightCoefficient", "体积重系数");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return ExcelUtils.validate(row, errorsMsg, fields, map);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		return false;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		CollectionUtil.each(rows, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				map.put("createdAt", new Date());
				map.put("updatedAt", new Date());
				lightEbayCustomerDao.add(map);
			}
		});
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		return 0;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return LightEbayCustomerModel.class;
	}

}
