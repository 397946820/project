package com.it.ocs.pic.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.it.ocs.pic.model.PicModel;

public class PicVO extends PicModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4299452849632925084L;

	private String categoryName;

	private MultipartFile file;
	


	private List<PicModel> pics;
	
	
	
	

	

	public List<PicModel> getPics() {
		return pics;
	}

	public void setPics(List<PicModel> pics) {
		this.pics = pics;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
