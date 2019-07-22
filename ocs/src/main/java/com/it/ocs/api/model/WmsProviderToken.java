package com.it.ocs.api.model;

/**
* @ClassName: WmsProviderToken 
* @Description: WMS供应商token
* @author wgc 
* @date 2018年4月23日 上午11:07:30 
*
 */
public class WmsProviderToken {
	
	/**
	 * 供应商名称
	 */
	private String providerName;
	
	/**
	 * 供应商key
	 */
	private String appKey;
	
	/**
	 * 供应商secret
	 */
	private String appSecret;
	
	/**
	 * 供应商token
	 */
	private String token;

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
