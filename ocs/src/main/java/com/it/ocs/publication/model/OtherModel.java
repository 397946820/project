package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class OtherModel extends BaseModel {
	private String tag = "";
	private String description = "";
	private String onlinePubCount = "";//保持最大在线刊登数量，null或0表示不启用此功能
	private String salesTax = "";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOnlinePubCount() {
		return onlinePubCount;
	}

	public void setOnlinePubCount(String onlinePubCount) {
		this.onlinePubCount = onlinePubCount;
	}

	public String getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(String salesTax) {
		this.salesTax = salesTax;
	}

}
