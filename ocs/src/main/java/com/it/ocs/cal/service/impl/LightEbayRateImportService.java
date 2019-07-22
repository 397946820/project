package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ILightEbayRateDao;
import com.it.ocs.cal.model.LightEbayRateModel;
import com.it.ocs.cal.vo.LightEbayRateVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("lightEbayRateImport")
public class LightEbayRateImportService extends AExcelImport {

	@Autowired
	private ILightEbayRateDao lightEbayRateRateDao;

	private final String[] fields = new String[] { "platform", "country", "shippingType", "referralRate",
			"grossProfitRate", "paypalFee", "vat","advertisingRate" };

	private static Map<String, String> map = new HashMap<>();

	static {
		map.put("platform", "平台");
		map.put("country", "国家");
		map.put("shippingType", "运输方式");
		map.put("referralRate", "推介费率");
		map.put("grossProfitRate", "毛利率");
		map.put("paypalFee", "paypal手续费");
		map.put("vat", "增值税");
		map.put("advertisingRate", "广告费率");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		
		return ExcelUtils.validate(row, errorsMsg, fields, map);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		String platform = row.get("platform").toString();
		String country = row.get("country").toString();
		String shippingType = row.get("shippingType").toString();
		LightEbayRateModel model = lightEbayRateRateDao
				.queryByParam(new LightEbayRateModel(platform, country, shippingType));
		if (model != null) {
			row.put("entityId", model.getEntityId());
			return true;
		}
		return false;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("createdAt", new Date());
				map.put("updatedAt", new Date());
				lightEbayRateRateDao.add(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("updatedAt", new Date());
				lightEbayRateRateDao.update(map);
			}
		}
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return LightEbayRateModel.class;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}

}
