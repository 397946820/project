package com.it.ocs.templateSetting.model;

import com.it.ocs.common.model.BaseModel;

public class TemplateSettingModel extends BaseModel {

	private String ebayAccount;

	private String carryTemplateUrl;

	private String topPromotionType;

	private String footerPromotionType;

	private String scaler;

	private String enablePageProtection;

	public String getEbayAccount() {
		return ebayAccount;
	}

	public void setEbayAccount(String ebayAccount) {
		this.ebayAccount = ebayAccount == null ? null : ebayAccount.trim();
	}

	public String getCarryTemplateUrl() {
		return carryTemplateUrl;
	}

	public void setCarryTemplateUrl(String carryTemplateUrl) {
		this.carryTemplateUrl = carryTemplateUrl == null ? null : carryTemplateUrl.trim();
	}

	public String getTopPromotionType() {
		return topPromotionType;
	}

	public void setTopPromotionType(String topPromotionType) {
		this.topPromotionType = topPromotionType == null ? null : topPromotionType.trim();
	}

	public String getFooterPromotionType() {
		return footerPromotionType;
	}

	public void setFooterPromotionType(String footerPromotionType) {
		this.footerPromotionType = footerPromotionType == null ? null : footerPromotionType.trim();
	}

	public String getScaler() {
		return scaler;
	}

	public void setScaler(String scaler) {
		this.scaler = scaler == null ? null : scaler.trim();
	}

	public String getEnablePageProtection() {
		return enablePageProtection;
	}

	public void setEnablePageProtection(String enablePageProtection) {
		this.enablePageProtection = enablePageProtection == null ? null : enablePageProtection.trim();
	}

}