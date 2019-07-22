package com.it.ocs.sys.excel.model;

import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.sys.model.OperatingProfitViewModel;

public class OperatingProfitViewExcelModel extends OperatingProfitViewModel {

	@ExcelLink(title = "人员", index = 29)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
