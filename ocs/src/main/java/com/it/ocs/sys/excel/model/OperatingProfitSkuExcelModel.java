package com.it.ocs.sys.excel.model;

import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.sys.model.OperatingProfitSkuModel;

public class OperatingProfitSkuExcelModel extends OperatingProfitSkuModel {

	@ExcelLink(title = "CIF", index = 5)
	private Double cif;

	public Double getCif() {
		return cif;
	}

	public void setCif(Double cif) {
		this.cif = cif;
	}

}
