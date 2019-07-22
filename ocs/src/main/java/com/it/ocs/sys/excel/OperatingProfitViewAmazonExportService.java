package com.it.ocs.sys.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.sys.dao.IOperatingProfitViewDao;
import com.it.ocs.sys.excel.model.OperatingProfitViewAmazonExportModel;
import com.it.ocs.sys.excel.model.OperatingProfitViewFeeExportModel;

@Service("operatingProfitViewAmazonExport")
public class OperatingProfitViewAmazonExportService extends AExcelExport {

	@Autowired
	private IOperatingProfitViewDao operatingProfitViewDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		String fromDate = request.getParameter("date1");
		String toDate = request.getParameter("date2");
		String type = request.getParameter("type");
		return operatingProfitViewDao.exportAmazonData(type,fromDate, toDate);

	}

	@Override
	protected void init(HttpServletRequest request) {
		String type = request.getParameter("type");
		if(type.equals("1")) {
			initModel(OperatingProfitViewAmazonExportModel.class, "亚马逊报表汇总数据.xlsx");
		} else {
			initModel(OperatingProfitViewFeeExportModel.class, "费用分摊数据.xlsx");
		}
	}

}
