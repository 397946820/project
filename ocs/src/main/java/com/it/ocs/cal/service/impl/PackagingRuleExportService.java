package com.it.ocs.cal.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.IPackagingRuleDao;
import com.it.ocs.cal.model.AuFbaRuleModel;
import com.it.ocs.cal.model.PackagingRuleModel;
import com.it.ocs.cal.model.PackagingWeightRuleModel;
import com.it.ocs.cal.model.UsPackagingWeightRuleModel;
import com.it.ocs.excel.service.AExcelExport;

@Service("packagingRuleExport")
public class PackagingRuleExportService extends AExcelExport {

	@Autowired
	private IPackagingRuleDao packagingDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		String type = request.getParameter("type");
		PackagingRuleModel model = new PackagingRuleModel();
		model.setType(new Integer(type));
		return packagingDao.queryByObject(model);
	}

	@Override
	protected void init(HttpServletRequest request) {
		String type = request.getParameter("type");
		String fileName = "";
		if(type.equals("1")) {
			super.initModel(PackagingWeightRuleModel.class, "AU_包装重量规则数据.xlsx");
		} else if(type.equals("2")) {
			super.initModel(AuFbaRuleModel.class, "AU_FBA规则数据.xlsx");
		} else if(type.equals("3")) {
			super.initModel(UsPackagingWeightRuleModel.class, "US_包装重量规则数据.xlsx");
		}
	}

}
