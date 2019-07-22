package com.it.ocs.sys.excel.model;

import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.sys.model.OperatingProfitViewModel;

public class OperatingProfitViewSuperExcelModel extends OperatingProfitViewModel {

	@ExcelLink(title = "运营人员", index = 29)
	private String name;

	@ExcelLink(title = "产品经理", index = 30)
	private String productManager;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductManager() {
		return productManager;
	}

	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}

}
