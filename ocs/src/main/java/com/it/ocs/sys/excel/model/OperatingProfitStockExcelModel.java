package com.it.ocs.sys.excel.model;

import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.sys.model.OperatingProfitStockModel;

public class OperatingProfitStockExcelModel extends OperatingProfitStockModel {

	@ExcelLink(title = "SOURCING_COST", index = 2)
	private Double soucringCost;

	public Double getSoucringCost() {
		return soucringCost;
	}

	public void setSoucringCost(Double soucringCost) {
		this.soucringCost = soucringCost;
	}

}
