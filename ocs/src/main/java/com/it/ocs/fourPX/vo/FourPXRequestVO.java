package com.it.ocs.fourPX.vo;

public class FourPXRequestVO {
	public final static String json = "json";
	public final static String xml = "xml";
	public final static String zh_CN = "zh_CN";
	public final static String en_US = "en_US";
	private final static String customerId_default = "100800";
	private final static String  token_default = "oDuCfVi88b40oOuMYQUOcTh2b/T+uJdDBsJ+VOrlG6Q=1";
	private String url;
	private String format;
	private String token;
	private String customerId;
	private String _method;
	private String language;
	private String version = "3.0.0";
	private String content;
	
	public FourPXRequestVO() {
		
	}
	
	public FourPXRequestVO(String url){
		this.url = url;
	}
	
	public FourPXRequestVO(String url, String content) {
		this(url);
		this.content = content;
	}
	
	public String getUrl() {
		if(null == url || "".equals(url)){
			return url;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		sb.append("?");
		if(null == this.customerId){
			customerId = customerId_default;
		}
		if(null == this.token){
			token = token_default;
		}
		sb.append("customerId="+customerId);
		sb.append("&token="+token);
		if(null == language){
			sb.append("&language="+en_US);
		}else{
			sb.append("&language="+language);
		}
		return sb.toString();
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String get_method() {
		return _method;
	}
	public void set_method(String _method) {
		this._method = _method;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
