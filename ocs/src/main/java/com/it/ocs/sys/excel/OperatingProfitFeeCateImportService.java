package com.it.ocs.sys.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.sys.dao.IOperatingProfitFeeCateDao;
import com.it.ocs.sys.model.OperatingProfitFeeCateModel;

@Service("operatingProfitFeeCateImport")
public class OperatingProfitFeeCateImportService extends AExcelImport {

	@Autowired
	private IOperatingProfitFeeCateDao operatingProfitFeeCateDao;

	private final String[] fields = new String[] { "platform", "category", "promotionFee" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("platform", "平台");
		map.put("category", "类目");
		map.put("promotionFee", "推广费-市场部");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return ExcelUtils.validate(row, errorsMsg, fields, map);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		String monthOfYear = new SimpleDateFormat("yyyy-MM").format(TimeTools.getChangeMonth(new Date(), -1));
		OperatingProfitFeeCateModel model = operatingProfitFeeCateDao.queryByParam(new OperatingProfitFeeCateModel(
				row.get("platform").toString(), row.get("category").toString(), monthOfYear));
		if (model != null) {
			row.put("entityId", model.getEntityId());
			return true;
		} else {
			row.put("monthOfYear", monthOfYear);
		}
		return false;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				operatingProfitFeeCateDao.add(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("updatedAt", new Date());
				operatingProfitFeeCateDao.update(map);
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
		return OperatingProfitFeeCateModel.class;
	}

}
