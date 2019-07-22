package com.it.ocs.publication.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.it.ocs.publication.model.PublicationModel;

public class PublicationVO extends PublicationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3522805083243229419L;
	private String timingDate;//定时时间
	private String endDate;//结束时间
	private String inland;//国内运输总费用
	private String internation;//国际运输总费用
	private String correlationId;
	private List<Map<String, Object>> articleTitle;
	private List<Map<String, Object>> bundleProduct;

	
	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getInland() {
		return inland;
	}

	public void setInland(String inland) {
		this.inland = inland;
	}

	public String getInternation() {
		return internation;
	}

	public void setInternation(String internation) {
		this.internation = internation;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTimingDate() {
		return timingDate;
	}

	public void setTimingDate(String timingDate) {
		this.timingDate = timingDate;
	}

	public List<Map<String, Object>> getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(List<Map<String, Object>> articleTitle) {
		this.articleTitle = articleTitle;
	}

	public List<Map<String, Object>> getBundleProduct() {
		return bundleProduct;
	}

	public void setBundleProduct(List<Map<String, Object>> bundleProduct) {
		this.bundleProduct = bundleProduct;
	}

}
