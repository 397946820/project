package com.it.ocs.cal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.IPackagingRuleDao;
import com.it.ocs.cal.model.AuFbaRuleModel;
import com.it.ocs.cal.model.PackagingWeightRuleModel;
import com.it.ocs.cal.model.UsPackagingWeightRuleModel;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("packagingRuleImport")
public class PackagingRuleImportService extends AExcelImport {

	@Autowired
	private IPackagingRuleDao packagingDao;

	private String type = "1";

	private final String[] fields = new String[] { "title", "weight", "length", "width", "height", "packagingWeight",
			"sortOrder" };

	private static Map<String, String> map = new HashMap<>();

	private final String[] fields_ = new String[] { "weight", "length", "width", "height", "regular", "discount",
			"isMonth", "sortOrder" };

	private static Map<String, String> map_ = new HashMap<>();

	private final String[] fields_1 = new String[] { "title", "weight", "packagingWeight", "sortOrder" };

	private static Map<String, String> map_1 = new HashMap<>();

	static {
		map.put("title", "标题");
		map.put("weight", "重量");
		map.put("length", "长");
		map.put("width", "宽");
		map.put("height", "高");
		map.put("packagingWeight", "包装重量");
		map.put("sortOrder", "排序");

		map_.put("weight", "重量");
		map_.put("length", "长");
		map_.put("width", "宽");
		map_.put("height", "高");
		map_.put("regular", "价格");
		map_.put("discount", "折扣价格");
		map_.put("isMonth", "适用月份");
		map_.put("sortOrder", "排序");

		map_1.put("title", "标题");
		map_1.put("weight", "重量");
		map_1.put("packagingWeight", "包装重量");
		map_1.put("sortOrder", "排序");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return type.equals("1") ? ExcelUtils.validate(row, errorsMsg, fields, map)
				: type.equals("2") ? ExcelUtils.validate(row, errorsMsg, fields_, map_)
						: ExcelUtils.validate(row, errorsMsg, fields_1, map_1);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		return false;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("type", type);
				packagingDao.add(map);
			}
		}
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
		type = request.getParameter("type");
		return type.equals("1") ? PackagingWeightRuleModel.class
				: type.equals("2") ? AuFbaRuleModel.class : UsPackagingWeightRuleModel.class;
	}

}
