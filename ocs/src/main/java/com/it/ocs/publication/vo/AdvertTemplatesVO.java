package com.it.ocs.publication.vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.publication.model.EBayAdvertTemplatesModel;

public class AdvertTemplatesVO extends EBayAdvertTemplatesModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
