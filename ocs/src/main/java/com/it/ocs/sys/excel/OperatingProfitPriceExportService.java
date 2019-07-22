package com.it.ocs.sys.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.utils.Utils;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.sys.dao.IOperatingProfitSkuDao;
import com.it.ocs.sys.excel.model.OperatingProfitSkuExcelModel;
import com.it.ocs.sys.excel.model.OperatingProfitStockExcelModel;

@Service("operatingProfitPriceExport")
public class OperatingProfitPriceExportService extends AExcelExport {
	
	@Autowired
	private IOperatingProfitSkuDao operatingProfitSkuDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		return operatingProfitSkuDao.queryPrice(new Double(request.getParameter("flag").toString()));
	}

	@Override
	protected void init(HttpServletRequest request) {
		Double flag = new Double(request.getParameter("flag").toString());
		if(flag == 2) {
			initModel(OperatingProfitStockExcelModel.class, "深圳仓无SOURCING_COST数据" + Utils.getFileName() + ".xlsx");
		} else {
			initModel(OperatingProfitSkuExcelModel.class, "海外仓无CIF数据" + Utils.getFileName() + ".xlsx");
		}
	}

}
