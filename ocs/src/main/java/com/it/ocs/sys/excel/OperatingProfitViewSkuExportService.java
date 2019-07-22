package com.it.ocs.sys.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.sys.dao.IOperatingProfitViewDao;
import com.it.ocs.sys.model.OperatingProfitNewSkuModel;

@Service("operatingProfitViewSkuExport")
public class OperatingProfitViewSkuExportService extends AExcelExport {
	
	@Autowired
	private IOperatingProfitViewDao operatingProfitViewDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		String type = request.getParameter("type");
		return operatingProfitViewDao.exportSku(type);
	}

	@Override
	protected void init(HttpServletRequest request) {
		String type = request.getParameter("type");
		String name = "";
		if(type.equals("0")) {
			name = "没有映射或者产品表找不到的SKU数据.xlsx";
		} else {
			name = "找不到分类的SKU数据.xlsx";
		}
		initModel(OperatingProfitNewSkuModel.class, name);
	}
}
