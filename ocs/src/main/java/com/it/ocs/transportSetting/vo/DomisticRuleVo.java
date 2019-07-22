package com.it.ocs.transportSetting.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.it.ocs.transportSetting.model.DomisticRuleModel;

public class DomisticRuleVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312264233637550649L;

	// 工作日
	private String workday;

	// 快速货运
	private String quickSend;

	//运输方式集合
	private List<DomisticRuleModel> models = new ArrayList<>();

	public String getWorkday() {
		return workday;
	}

	public void setWorkday(String workday) {
		this.workday = workday;
	}

	public String getQuickSend() {
		return quickSend;
	}

	public void setQuickSend(String quickSend) {
		this.quickSend = quickSend;
	}

	public List<DomisticRuleModel> getModels() {
		return models;
	}

	public void setModels(List<DomisticRuleModel> models) {
		this.models = models;
	}

}
