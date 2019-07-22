package com.it.ocs.compare.vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class DataCompareVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4216431794414386710L;
	private MultipartFile myfile;
	private String account;
	private String platForm;
	private String site;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public MultipartFile getMyfile() {
		return myfile;
	}

	public void setMyfile(MultipartFile myfile) {
		this.myfile = myfile;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPlatForm() {
		return platForm;
	}

	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}

}
