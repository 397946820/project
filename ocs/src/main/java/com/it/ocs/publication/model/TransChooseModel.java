package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class TransChooseModel extends BaseModel {
	private boolean transClauseExisPub = false;// 运输条款是否已在刊登描述中
	private String calCulateInfo = "";// 运费计算项
	private String domesticInfo = "";// 国内运输
	private String internationalInfo = "";// 国际运输

	public boolean isTransClauseExisPub() {
		return transClauseExisPub;
	}

	public void setTransClauseExisPub(boolean transClauseExisPub) {
		this.transClauseExisPub = transClauseExisPub;
	}

	public String getCalCulateInfo() {
		return calCulateInfo;
	}

	public void setCalCulateInfo(String calCulateInfo) {
		this.calCulateInfo = calCulateInfo;
	}

	public String getDomesticInfo() {
		return domesticInfo;
	}

	public void setDomesticInfo(String domesticInfo) {
		this.domesticInfo = domesticInfo;
	}

	public String getInternationalInfo() {
		return internationalInfo;
	}

	public void setInternationalInfo(String internationalInfo) {
		this.internationalInfo = internationalInfo;
	}

}
