package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;

import com.it.ocs.common.model.BaseModel;

public class AmazonAccountModel extends BaseModel {
	private String platform;
	private String sellerId;
	private String secretKey;
	private String accessKey;
	private String token;
	private String url;
	private String siteId;
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public List<String> getMarketplaceId() {
		List<String> marketplaceId = new ArrayList<String>();
		if(null != this.siteId){
			String sites[] = siteId.split(",");
			for(int i=0;i<sites.length;i++){
				marketplaceId.add(sites[i]);
			}
		}
		return marketplaceId;
	}
	
}
