package com.it.ocs.transportSetting.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.it.ocs.transportSetting.model.InternationalRuleModel;

public class InternationalRuleVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8430777943915074124L;

	// 不运送地区
	private String noArrive;

	private List<InternationalRuleModel> models = new ArrayList<>();

	public String getNoArrive() {
		return noArrive;
	}

	public void setNoArrive(String noArrive) {
		this.noArrive = noArrive;
	}

	public List<InternationalRuleModel> getModels() {
		return models;
	}

	public void setModels(List<InternationalRuleModel> models) {
		this.models = models;
	}

}
