package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class ReturnPolicyModel extends BaseModel {
	private String policyType = "";// 政策类型
	private String returnDays = "";// 退货天数
	private boolean allowDelay = false;// 是否提供节假日延期退货至12月31日
	private String returnType = "";// 退款方式
	private String fareTakeInHander = "";// 退款承担者
	private String depreciationRate = "";// 折旧费
	private String description = "";// 描述

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getReturnDays() {
		return returnDays;
	}

	public void setReturnDays(String returnDays) {
		this.returnDays = returnDays;
	}

	

	public boolean isAllowDelay() {
		return allowDelay;
	}

	public void setAllowDelay(boolean allowDelay) {
		this.allowDelay = allowDelay;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getFareTakeInHander() {
		return fareTakeInHander;
	}

	public void setFareTakeInHander(String fareTakeInHander) {
		this.fareTakeInHander = fareTakeInHander;
	}

	public String getDepreciationRate() {
		return depreciationRate;
	}

	public void setDepreciationRate(String depreciationRate) {
		this.depreciationRate = depreciationRate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
