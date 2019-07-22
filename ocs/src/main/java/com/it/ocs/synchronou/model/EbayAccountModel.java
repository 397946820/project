package com.it.ocs.synchronou.model;

import java.io.Serializable;

import com.it.ocs.common.model.BaseModel;

public class EbayAccountModel extends BaseModel implements Serializable{
	private String account;
	private String token;
	private String siteId;
	private Boolean isStore;
	private String storeUrl;
	private String store;
	
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public Boolean getIsStore() {
		return isStore;
	}
	public void setIsStore(Integer isStore) {
		if(isStore == 1){
			this.isStore = true;
		}else{
			this.isStore = false;
		}
	}
	public String getStoreUrl() {
		return storeUrl;
	}
	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}
	
	
}
