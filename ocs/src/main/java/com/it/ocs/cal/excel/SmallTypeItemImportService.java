package com.it.ocs.cal.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ISmallTypeItemDao;
import com.it.ocs.cal.model.SmallTypeItemModel;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.sys.model.OperatingProfitNewSkuModel;

@Service("smallTypeItemImport")
public class SmallTypeItemImportService extends AExcelImport {

	@Autowired
	private ISmallTypeItemDao smallTypeItemDao;

	private final String[] fields = new String[] { "typeName", "shippingType", "minWeight", "fromWeight", "toWeight",
			"operatingExpenses", "kilogramWeight" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("typeName", "类型名称");
		map.put("shippingType", "运输方式");
		map.put("minWeight", "最小重量");
		map.put("fromWeight", "开始重量");
		map.put("toWeight", "结束重量");
		map.put("operatingExpenses", "操作费/挂号费");
		map.put("kilogramWeight", "公斤重");
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
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				smallTypeItemDao.add(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("updatedAt", new Date());
				smallTypeItemDao.update(map);
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
		return SmallTypeItemModel.class;
	}

}
