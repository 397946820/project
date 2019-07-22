package com.it.ocs.sys.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.cal.dao.IProductEntityDao;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.sys.dao.IOperatingProfitSkuDao;
import com.it.ocs.sys.model.OperatingProfitNewSkuModel;

@Service("operatingProfitNewSkuImport")
public class OperatingProfitNewSkuImportService extends AExcelImport {

	@Autowired
	private IOperatingProfitSkuDao operatingProfitSkuDao;

	@Autowired
	private IProductEntityDao productEntityDao;

	private final String[] fields = new String[] { "sku" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("sku", "SKU");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return ExcelUtils.validate(row, errorsMsg, fields, map);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		String monthOfYear = new SimpleDateFormat("yyyy-MM").format(TimeTools.getChangeMonth(new Date(), -1));
		OperatingProfitNewSkuModel model = operatingProfitSkuDao
				.queryNewSkuByParam(new OperatingProfitNewSkuModel(row.get("sku").toString(), monthOfYear));
		if(model == null) {
			row.put("monthOfYear", monthOfYear);
			return false;
		} else {
			row.put("entityId", model.getEntityId());
		}
		return true;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				operatingProfitSkuDao.addNewSku(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("updatedAt", new Date());
				operatingProfitSkuDao.updateNewSku(map);
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
		return OperatingProfitNewSkuModel.class;
	}

}
