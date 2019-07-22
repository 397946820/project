package com.it.ocs.transportSetting.model;

public class DomisticRuleModel {

	// 运输名称
	private String dtype;
	// 运费
	private Double freight;
	// 是否免费
	private String isFree;
	// 额外加收
	private Double added;
	// AK,HI,PR额外收费
	private Double extra;

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public Double getAdded() {
		return added;
	}

	public void setAdded(Double added) {
		this.added = added;
	}

	public Double getExtra() {
		return extra;
	}

	public void setExtra(Double extra) {
		this.extra = extra;
	}

}
