package com.it.ocs.fourPX.vo;

import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.fourPX.model.SellerInventoryLogModel;

public class SellerInventoryLogExportVO extends SellerInventoryLogModel implements java.io.Serializable {

	private static final long serialVersionUID = 9123545760995728476L;
	
	@ExcelLink(title = "不合格库存代码", index = 18)
	private String unqualifiedCode;

	@ExcelLink(title = "不合格库存数量", index = 19)
	private Integer unqualifiedQuantity;

	public String getUnqualifiedCode() {
		return unqualifiedCode;
	}

	public void setUnqualifiedCode(String unqualifiedCode) {
		this.unqualifiedCode = unqualifiedCode;
	}

	public Integer getUnqualifiedQuantity() {
		return unqualifiedQuantity;
	}

	public void setUnqualifiedQuantity(Integer unqualifiedQuantity) {
		this.unqualifiedQuantity = unqualifiedQuantity;
	}
	
}
