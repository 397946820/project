package com.it.ocs.sys.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.sys.dao.IOperatingProfitViewDao;
import com.it.ocs.sys.excel.model.BasicsBackupsModel;
import com.it.ocs.sys.excel.model.FbaRuleBackupsModel;
import com.it.ocs.sys.excel.model.OperatingProfitViewExcelModel;
import com.it.ocs.sys.excel.model.OperatingProfitViewSuperExcelModel;
import com.it.ocs.sys.excel.model.PricePlanBackupsModel;
import com.it.ocs.sys.excel.model.ProductBackupsModel;
import com.it.ocs.sys.model.OperatingProfitViewModel;
import com.it.ocs.sys.utils.CodeUtils;

@Service("operatingProfitViewExport")
public class OperatingProfitViewExportService extends AExcelExport {

	@Autowired
	private IOperatingProfitViewDao operatingProfitViewDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		String type = request.getParameter("type");
		String fromDate = request.getParameter("date1");
		String toDate = request.getParameter("date2");
		if (StringUtils.isNotBlank(type)) {
			return operatingProfitViewDao.exportBackups(type, fromDate, toDate);
		} else {
			String code = CodeUtils.getCode();
			if (code == null) {
				return null;
			}
			return operatingProfitViewDao.exportData(code, UserUtils.getUserId(), fromDate, toDate);
		}
	}

	@Override
	protected void init(HttpServletRequest request) {
		String type = request.getParameter("type");
		if (StringUtils.isNotBlank(type)) {
			switch (type) {
			case "0":
				initModel(PricePlanBackupsModel.class, "计价器数据.xlsx");
				break;
			case "1":
				initModel(ProductBackupsModel.class, "产品数据.xlsx");
				break;
			case "2":
				initModel(FbaRuleBackupsModel.class, "FBA规则数据.xlsx");
				break;
			case "3":
				initModel(BasicsBackupsModel.class, "汇率运费税率数据.xlsx");
				break;
			default:
				break;
			}
		} else {
			String code = CodeUtils.getCode();
			if (code.equals("1")) {
				initModel(OperatingProfitViewSuperExcelModel.class, "经营利润透视数据.xlsx");
			} else if (code.equals("2") || code.equals("6")) {
				initModel(OperatingProfitViewExcelModel.class, "经营利润透视数据.xlsx");
			} else {
				initModel(OperatingProfitViewModel.class, "经营利润透视数据.xlsx");
			}
		}
	}

}
