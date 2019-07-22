package com.it.ocs.upload.vo;

public class FileVO {
	private Long id;
	private String name;
	private String size;
	private String savePath;
	private Long user;
	private String resetName;
	private String fieldId;
	private String serverName;
	private String serverUrl;
	private Integer isUpload;
	private String ebayUrl;
	private String ebayReturnInfo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	public String getResetName() {
		return resetName;
	}
	public void setResetName(String resetName) {
		this.resetName = resetName;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public Integer getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}
	public String getEbayUrl() {
		return ebayUrl;
	}
	public void setEbayUrl(String ebayUrl) {
		this.ebayUrl = ebayUrl;
	}
	public String getEbayReturnInfo() {
		return ebayReturnInfo;
	}
	public void setEbayReturnInfo(String ebayReturnInfo) {
		this.ebayReturnInfo = ebayReturnInfo;
	}
	
	
}
