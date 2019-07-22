package com.it.ocs.sys.model;

import com.it.ocs.common.model.BaseModel;

public class EbayOnlineModel extends BaseModel {
	private String itemId;
	private String siteName;
	private String siteId;
	private String accountName;
	private String status;
	private String ebayXML;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEbayXML() {
		return ebayXML;
	}
	public void setEbayXML(String ebayXML) {
		this.ebayXML = ebayXML;
	}
	
	
}
