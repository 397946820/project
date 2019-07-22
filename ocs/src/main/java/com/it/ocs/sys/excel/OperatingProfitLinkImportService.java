package com.it.ocs.sys.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.sys.dao.IOperatingProfitLinkDao;
import com.it.ocs.sys.model.OperatingProfitLinkModel;

@Service("operatingProfitLinkImport")
public class OperatingProfitLinkImportService extends AExcelImport {

	@Autowired
	private IOperatingProfitLinkDao operatingProfitLinkDao;

	private final String[] fields = new String[] { "platform", "category" };

	private static final Map<String, String> map = new HashMap<>();

	private static final List<String> countrys = Lists.newArrayList();

	static {
		map.put("platform", "平台");
		map.put("category", "类目");
		countrys.add("US");
		countrys.add("GB");
		countrys.add("DE");
		countrys.add("FR");
		countrys.add("IT");
		countrys.add("ES");
		countrys.add("JP");
		countrys.add("CA");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return ExcelUtils.validate(row, errorsMsg, fields, map);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		String monthOfYear = new SimpleDateFormat("yyyy-MM").format(TimeTools.getChangeMonth(new Date(), -1));
		OperatingProfitLinkModel model = operatingProfitLinkDao.queryByParam(new OperatingProfitLinkModel(
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
				operatingProfitLinkDao.add(map);
				update(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				update(map);
			}
		}
		return 0;
	}

	private void update(Map<String, Object> row) {
		Map<String, Object> param = null;
		for (String country : countrys) {
			String nick = row.get(country.toLowerCase()) == null ? null : row.get(country.toLowerCase()).toString();
			if (StringUtils.isNotBlank(nick)) {
				Long userId = operatingProfitLinkDao.getUserIdByNick(nick);
				if (userId != null) {
					param = new HashMap<>();
					param.put("country", country);
					param.put("userId", userId);
					param.put("parentId", row.get("entityId"));
					if (operatingProfitLinkDao.getCount(row.get("entityId"), country) > 0) {
						// 更新
						param.put("updatedAt", new Date());
						operatingProfitLinkDao.updateCountry(param);
					} else {
						// 插入
						operatingProfitLinkDao.addCountry(param);
					}
				}
			}
		}
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return OperatingProfitLinkModel.class;
	}

}
