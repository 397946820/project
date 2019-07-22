package com.it.ocs.synchronou.model;

import java.util.HashMap;
import java.util.Map;

import com.it.ocs.synchronou.service.impl.TemplateService;

public class RequestModel {
	private String requestMethod;
	private String requestDomainType;
	
	private String siteId;
	private String token;
	private String url;
	
	private Map<String,String> RequestHead;
	private Map<String,String> param;
	private TemplateModel template;
	private String requestXML;
	
	public RequestModel(TemplateModel template,EbayAccountModel account,Map<String,String> param){
		this.template = template;
		this.siteId = account.getSiteId();
		this.token = account.getToken();
		param.put("token", this.token);
		this.param = param;
	}
	
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getRequestDomainType() {
		return requestDomainType;
	}
	public void setRequestDomainType(String requestDomainType) {
		this.requestDomainType = requestDomainType;
	}
	public String getSiteId() {
		return siteId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Map<String, String> getRequestHead() {
		Map<String,String> map = new HashMap<>();
		map.put("X-EBAY-API-SITEID", siteId);
		map.put("X-EBAY-API-COMPATIBILITY-LEVEL", template.getVersion());
		map.put("X-EBAY-API-CALL-NAME", template.getMethod());
		return map;
	}
	public String getRequestXML() {
		return TemplateService.formatTemplat(param, template.getContent());
	}
	public String getUrl() {
		return template.getUrl();
	}
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		param.put("token", this.token);
		this.param = param;
	}

}
