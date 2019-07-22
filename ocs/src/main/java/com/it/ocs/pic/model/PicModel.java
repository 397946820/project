package com.it.ocs.pic.model;

import com.it.ocs.common.model.BaseModel;

public class PicModel extends BaseModel {
	private String url;
	private String ebayPicRelation;
	private Long categoryId;
	private String fileSize;
	private String name;
	private String realName;
	private String imgType;
	private String realUrl;
	private String realUrlInfo;
	private String serverUrl;
	private String uploadDate;
	
	
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getRealUrlInfo() {
		return realUrlInfo;
	}

	public void setRealUrlInfo(String realUrlInfo) {
		this.realUrlInfo = realUrlInfo;
	}

	public String getImgType() {
		return imgType;
	}

	public String getRealUrl() {
		return realUrl;
	}

	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEbayPicRelation() {
		return ebayPicRelation;
	}

	public void setEbayPicRelation(String ebayPicRelation) {
		this.ebayPicRelation = ebayPicRelation;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	

}
