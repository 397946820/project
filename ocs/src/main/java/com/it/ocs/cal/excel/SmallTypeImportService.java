package com.it.ocs.cal.excel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ISmallTypeDao;
import com.it.ocs.cal.model.SmallTypeModel;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("smallTypeImport")
public class SmallTypeImportService extends AExcelImport {

	@Autowired
	private ISmallTypeDao smallTypeDao;

	private final String[] fields = new String[] { "typeName", "gst", "tariffRate", "clearPriceAdjustmentRatio" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("typeName", "类型名称");
		map.put("gst", "GST");
		map.put("tariffRate", "关税");
		map.put("clearPriceAdjustmentRatio", "清关价调整比例");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return ExcelUtils.validate(row, errorsMsg, fields, map);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		String typeName = row.get("typeName").toString();
		SmallTypeModel model = smallTypeDao.queryByParam(new SmallTypeModel(typeName));
		if (model == null) {
			return false;
		} else {
			row.put("entityId", model.getEntityId());
			row.put("updatedAt", new Date());
		}
		return true;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				smallTypeDao.add(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				smallTypeDao.update(map);
			}
		}
		return 0;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return SmallTypeModel.class;
	}

}
