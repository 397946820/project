package com.it.ocs.transportSetting.model;

import com.it.ocs.common.model.BaseModel;

public class TransportSettingModel extends BaseModel {

	private String ebayAccount;

	private Long siteId;

	private String domesticType;

	private String internationalType;

	private String published;

	private String domisticRule;

	private String internationalRule;

	private String ico;

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getEbayAccount() {
		return ebayAccount;
	}

	public void setEbayAccount(String ebayAccount) {
		this.ebayAccount = ebayAccount == null ? null : ebayAccount.trim();
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getDomesticType() {
		return domesticType;
	}

	public void setDomesticType(String domesticType) {
		this.domesticType = domesticType;
	}

	public String getInternationalType() {
		return internationalType;
	}

	public void setInternationalType(String internationalType) {
		this.internationalType = internationalType == null ? null : internationalType.trim();
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published == null ? null : published.trim();
	}

	public String getDomisticRule() {
		return domisticRule;
	}

	public void setDomisticRule(String domisticRule) {
		this.domisticRule = domisticRule == null ? null : domisticRule.trim();
	}

	public String getInternationalRule() {
		return internationalRule;
	}

	public void setInternationalRule(String internationalRule) {
		this.internationalRule = internationalRule == null ? null : internationalRule.trim();
	}

}