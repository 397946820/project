package com.it.ocs.sys.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;
import com.it.ocs.sys.dao.IOperatingProfitSkuDao;
import com.it.ocs.sys.excel.model.OperatingProfitStockExcelModel;

@Service("operatingProfitPriceSImport")
public class OperatingProfitPriceSImportService extends AExcelImport {

	@Autowired
	private IOperatingProfitSkuDao operatingProfitSkuDao;

	private final String[] fields = new String[] { "sku","stock", "sourcingCost" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("sku", "SKU");
		map.put("stock", "库存数量");
		map.put("sourcingCost", "SOURCING_COST");
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
				operatingProfitSkuDao.updatePriceS(map);
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
		return OperatingProfitStockExcelModel.class;
	}

}
