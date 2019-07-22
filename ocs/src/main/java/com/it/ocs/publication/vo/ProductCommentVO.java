package com.it.ocs.publication.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.it.ocs.publication.model.ProductCommentModel;

public class ProductCommentVO extends ProductCommentModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6805076100009941716L;
	private List<Map<String, Object>> ebayImg;// ebay图片
	private List<Map<String, Object>> templateImg;// 模版图片
	private List<Map<String, Object>> comment;
	public List<Map<String, Object>> getEbayImg() {
		return ebayImg;
	}

	public void setEbayImg(List<Map<String, Object>> ebayImg) {
		this.ebayImg = ebayImg;
	}

	public List<Map<String, Object>> getTemplateImg() {
		return templateImg;
	}

	public void setTemplateImg(List<Map<String, Object>> templateImg) {
		this.templateImg = templateImg;
	}

	public List<Map<String, Object>> getComment() {
		return comment;
	}

	public void setComment(List<Map<String, Object>> comment) {
		this.comment = comment;
	}

}
