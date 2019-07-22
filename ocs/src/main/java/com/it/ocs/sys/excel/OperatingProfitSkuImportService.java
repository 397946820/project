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
import com.it.ocs.sys.dao.IOperatingProfitSkuDao;
import com.it.ocs.sys.model.OperatingProfitSkuModel;

@Service("operatingProfitSkuImport")
public class OperatingProfitSkuImportService extends AExcelImport {

	@Autowired
	private IOperatingProfitSkuDao operatingProfitSkuDao;

	private final String[] fields = new String[] { "platform", "country", "sku", "stock", "shippingType" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("platform", "平台");
		map.put("country", "国家");
		map.put("sku", "SKU");
		map.put("stock", "库存数量");
		map.put("shippingType", "运输方式");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return ExcelUtils.validate(row, errorsMsg, fields, map);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		String monthOfYear = new SimpleDateFormat("yyyy-MM").format(TimeTools.getChangeMonth(new Date(), -1));
		OperatingProfitSkuModel model = operatingProfitSkuDao
				.queryByParam(new OperatingProfitSkuModel(monthOfYear, row.get("platform").toString(),
						row.get("country").toString(), row.get("sku").toString(), row.get("shippingType").toString()));
		if (model != null) {
			row.put("entityId", model.getEntityId());
			return true;
		} else {
			row.put("quarter", TimeTools.getSeason(monthOfYear));
			row.put("monthOfYear", monthOfYear);
		}
		return false;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				operatingProfitSkuDao.add(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("updatedAt", new Date());
				operatingProfitSkuDao.update(map);
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
		return OperatingProfitSkuModel.class;
	}

}
