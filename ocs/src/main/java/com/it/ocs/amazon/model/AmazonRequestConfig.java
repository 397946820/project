package com.it.ocs.amazon.model;

import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.it.ocs.amazon.common.AmazonSignature;
import com.it.ocs.synchronou.model.AmazonAccountModel;

/**
 * 亚马逊请求参数
 * @author chenyong
 *
 */
public class AmazonRequestConfig {
	
	private String url;
	private String action;
	private String sellerId;
	private String aWSAccessKeyId;
	private String version;
	private String signatureVersion = "2";
	private String timestamp;
	private String signature;
	private String awsSecretAccessKey;
	private String nextToken;
	private String pageAction;
	private Map<String, String> baseParameters;
	private Map<String,String> queryParameters;
	
	public AmazonRequestConfig(){}
	
	/**
	 * 
	 * @param account 账号信息
	 * @param action 方法名
	 * @param version 版本
	 */
	public AmazonRequestConfig(AmazonAccountModel account,String action,String version){
		this.url = account.getUrl();
		this.sellerId = account.getSellerId();
		this.aWSAccessKeyId = account.getAccessKey();
		this.awsSecretAccessKey = account.getSecretKey();
		this.action = action;
		this.version = version;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getaWSAccessKeyId() {
		return aWSAccessKeyId;
	}

	public void setaWSAccessKeyId(String aWSAccessKeyId) {
		this.aWSAccessKeyId = aWSAccessKeyId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSignatureVersion() {
		return signatureVersion;
	}

	public void setSignatureVersion(String signatureVersion) {
		this.signatureVersion = signatureVersion;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAwsSecretAccessKey() {
		return awsSecretAccessKey;
	}

	public void setAwsSecretAccessKey(String awsSecretAccessKey) {
		this.awsSecretAccessKey = awsSecretAccessKey;
	}

	public Map<String, String> getBaseParameters() throws SignatureException {
		Map<String, String> parameters = new HashMap<>();
		if(null == this.nextToken){
			parameters.put("Action", this.action);
			//params.put("Marketplace", request.getMarketplace());
			parameters.put("SellerId", this.sellerId);
			parameters.put("AWSAccessKeyId", this.aWSAccessKeyId);
			parameters.put("Version", this.version);
			parameters.put("SignatureVersion", "2");
			parameters.put("Timestamp",getFormattedTimestamp());
			//接口请求参数
			if(null != this.queryParameters){
				for(Map.Entry<String, String> entry:this.queryParameters.entrySet()){
					parameters.put(entry.getKey(), entry.getValue());
				}
			}
			parameters.put("SignatureMethod", AmazonSignature.ALGORITHM);
			parameters.put("Signature", AmazonSignature.signParameters(parameters, awsSecretAccessKey,this.url));
			
		}else{
			parameters.put("Action", this.pageAction);
			parameters.put("NextToken", this.nextToken);
		}
		return parameters;
	}
	
	private String getFormattedTimestamp() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(new Date());
	}

	public Map<String, String> getQueryParameters() {
		return queryParameters;
	}

	public void setQueryParameters(Map<String, String> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public String getNextToken() {
		return nextToken;
	}

	public void setNextToken(String nextToken) {
		this.nextToken = nextToken;
	}

	public String getPageAction() {
		return pageAction;
	}

	public void setPageAction(String pageAction) {
		this.pageAction = pageAction;
	}
	
	
	
}
