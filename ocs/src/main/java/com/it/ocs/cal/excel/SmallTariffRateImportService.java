package com.it.ocs.cal.excel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ISmallTariffRateDao;
import com.it.ocs.cal.model.SmallTariffRateModel;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;

@Service("smallTariffRateImport")
public class SmallTariffRateImportService extends AExcelImport {

	@Autowired
	private ISmallTariffRateDao smallTariffRateDao;

	private final String[] fields = new String[] { "fromWeight", "toWeight", "minimumDeclaredPrice" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("fromWeight", "开始重量");
		map.put("toWeight", "结束重量");
		map.put("minimumDeclaredPrice", "最低申报价格");
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
				smallTariffRateDao.add(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("updatedAt", new Date());
				smallTariffRateDao.update(map);
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
		return SmallTariffRateModel.class;
	}

}
