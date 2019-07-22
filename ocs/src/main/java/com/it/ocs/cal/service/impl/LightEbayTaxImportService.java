package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ILightEbayTaxDao;
import com.it.ocs.cal.dao.ILightTaximeterDao;
import com.it.ocs.cal.dao.IProductEntityDao;
import com.it.ocs.cal.model.LightEbayTaxBaseModel;
import com.it.ocs.cal.model.LightEbayTaxModel;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("lightEbayTaxImport")
public class LightEbayTaxImportService extends AExcelImport {

	@Autowired
	private ILightEbayTaxDao lightEbayTaxDao;

	@Autowired
	private IProductEntityDao productEntityDao;

	@Autowired
	private ILightTaximeterDao lightTaximeterDao;

	private final String[] fields = new String[] { "sku", "country", "inventoryQuantity", "saleTotal", "unavailability",
			"replacementRate", "storageDays", "costThan", "volatilityFactor" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("sku", "SKU");
		map.put("country", "国家");
		map.put("inventoryQuantity", "库存总量");
		map.put("saleTotal", "销售总量");
		map.put("unavailability", "不可用率");
		map.put("replacementRate", "补发率");
		map.put("storageDays", "存储天数");
		map.put("costThan", "费用占比");
		map.put("volatilityFactor", "波动因子");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		List<LightEbayTaxModel> models = BeanConvertUtil.mapToObject(row, LightEbayTaxBaseModel.class).getModels();
		Set<Boolean> set = new HashSet<>();
		if (!CollectionUtil.isNullOrEmpty(models)) {
			CollectionUtil.each(models, new IAction<LightEbayTaxModel>() {
				@Override
				public void excute(LightEbayTaxModel obj) {
					try {
						Map<String, Object> hashMap = BeanUtils.describe(obj);
						hashMap.put("rowNumber", row.get("rowNumber"));
						set.add(ExcelUtils.validate(hashMap, errorsMsg, fields, map));
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
			});
		} else {
			errorsMsg.add("第" + row.get("rowNumber") + "行某列数据缺少或者过多");
		}
		if (!CollectionUtil.isNullOrEmpty(set)) {
			return set.contains(false) ? false : true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {

		return false;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		CollectionUtil.each(rows, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> row) {
				CollectionUtil.each(BeanConvertUtil.mapToObject(row, LightEbayTaxBaseModel.class).getModels(),
						new IAction<LightEbayTaxModel>() {
							@Override
							public void excute(LightEbayTaxModel model) {
								LightEbayTaxModel param = lightEbayTaxDao.queryByCountryAndSku(model.getCountry(),
										model.getSku());
								model.setUpdatedAt(new Date());
								if (param == null) {
									model.setCreatedAt(new Date());
									lightEbayTaxDao.add(model);
								} else {
									model.setEntityId(param.getEntityId());
									lightEbayTaxDao.update(model);
								}

							}
						});
			}
		});
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {

		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return LightEbayTaxBaseModel.class;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		CollectionUtil.each(rows, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				String sku = map.get("sku").toString();
				List<Long> userIds = productEntityDao.findUserIdBySku(sku);
				CollectionUtil.each(userIds, new IAction<Long>() {
					@Override
					public void excute(Long userId) {
						lightTaximeterDao.refresh(sku, userId);
					}
				});
			}
		});
		return 0;
	}

}
