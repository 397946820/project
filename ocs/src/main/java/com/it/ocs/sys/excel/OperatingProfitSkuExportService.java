package com.it.ocs.sys.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.utils.Utils;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.excel.utils.ExcelUtils;
import com.it.ocs.sys.dao.IOperatingProfitSkuDao;
import com.it.ocs.sys.model.OperatingProfitSkuModel;

@Service("operatingProfitSkuExport")
public class OperatingProfitSkuExportService extends AExcelExport {
	
	@Autowired
	private IOperatingProfitSkuDao operatingProfitSkuDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		OperatingProfitSkuModel model = ExcelUtils.getObject(request, OperatingProfitSkuModel.class);
		return operatingProfitSkuDao.queryByObject(model);
	}

	@Override
	protected void init(HttpServletRequest request) {
		initModel(OperatingProfitSkuModel.class, "经营利润sku库存维护数据" + Utils.getFileName() + ".xlsx");
	}

}
