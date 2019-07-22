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
import com.it.ocs.sys.dao.IOperatingProfitFeeDao;
import com.it.ocs.sys.model.OperatingProfitFeeModel;

@Service("operatingProfitFeeImport")
public class OperatingProfitFeeImportService extends AExcelImport {

	@Autowired
	private IOperatingProfitFeeDao operatingProfitFeeDao;

	private final String[] fields = new String[] { "freightUs","freightUk","freightDe","customerFee1",
			"customerFee2","customerFee3","customerFee4","customerFee5","feeEbay","packingCharge","clearFee","otherFee" };

	private static final Map<String, String> map = new HashMap<>();

	static {
		map.put("freightUs", "运费-美国外仓");
		map.put("freightUk", "运费-英国外仓");
		map.put("freightDe", "运费-德国外仓");
		map.put("customerFee1", "客服-60K");
		map.put("customerFee2", "客服-通讯费");
		map.put("customerFee3", "客服-ZENDESK-INC");
		map.put("customerFee4", "客服-RESELLERRATINGS.COM");
		map.put("customerFee5", "客服-TrustedShopsGmbH");
		map.put("feeEbay", "平台费-eBay");
		map.put("packingCharge", "打包费-Onlinepack");
		map.put("clearFee", "清关费");
		map.put("otherFee", "其他费用");
	}

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return ExcelUtils.validate(row, errorsMsg, fields, map);
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		String monthOfYear = new SimpleDateFormat("yyyy-MM").format(TimeTools.getChangeMonth(new Date(), -1));
		OperatingProfitFeeModel model = operatingProfitFeeDao.queryByParam(new OperatingProfitFeeModel(monthOfYear));
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
				operatingProfitFeeDao.add(map);
			}
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			for (Map<String, Object> map : rows) {
				map.put("updatedAt", new Date());
				operatingProfitFeeDao.update(map);
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
		return OperatingProfitFeeModel.class;
	}

}
