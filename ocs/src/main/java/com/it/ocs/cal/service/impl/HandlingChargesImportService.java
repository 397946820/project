package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.IHandlingChargesDao;
import com.it.ocs.cal.model.LightHandlingChargesModel;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("handlingChargesImport")
public class HandlingChargesImportService extends AExcelImport {

	@Autowired
	private IHandlingChargesDao handlingChargesDao;

	private final String[] fields = new String[] { "fromWeight", "toWeight", "cost", "sortOrder", "type" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("fromWeight", "起始重量");
		map.put("toWeight", "结束重量");
		map.put("cost", "费用");
		map.put("sortOrder", "排序");
		map.put("type", "规则类型");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		
		return ExcelUtils.validate(row, errorsMsg, fields, map);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		Double flag = new Double(row.get("sortOrder").toString());
		int sortOrder = (int) Math.ceil(flag);
		flag = new Double(row.get("type").toString());
		int type = (int) Math.ceil(flag);
		LightHandlingChargesModel model = handlingChargesDao
				.queryByParam(new LightHandlingChargesModel(sortOrder, type));
		if (model != null) {
			row.put("id", model.getId());
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
				handlingChargesDao.add(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("updatedAt", new Date());
				handlingChargesDao.update(map);
			}
		}
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return LightHandlingChargesModel.class;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}

}
