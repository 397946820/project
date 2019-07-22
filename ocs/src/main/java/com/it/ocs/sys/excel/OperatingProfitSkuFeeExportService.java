package com.it.ocs.sys.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.utils.Utils;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.sys.dao.IOperatingProfitSkuFeeDao;
import com.it.ocs.sys.model.OperatingProfitSkuFeeModel;

@Service("operatingProfitSkuFeeExport")
public class OperatingProfitSkuFeeExportService extends AExcelExport {
	
	@Autowired
	private IOperatingProfitSkuFeeDao operatingProfitSkuFeeDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		return operatingProfitSkuFeeDao.noParticipationPnOperation();
	}

	@Override
	protected void init(HttpServletRequest request) {
		initModel(OperatingProfitSkuFeeModel.class, "没有参与运算的sku数据" + Utils.getFileName() + ".xlsx");
	}

}
