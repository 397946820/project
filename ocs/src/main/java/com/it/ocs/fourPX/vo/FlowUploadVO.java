package com.it.ocs.fourPX.vo;

import java.io.Serializable;

import com.it.ocs.excel.annotation.ExcelLink;

public class FlowUploadVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5191001964811104631L;
	@ExcelLink(index=0)
	private String uniqueCode;
	@ExcelLink(index=1)
	private String codeType;
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	
	
	

}
