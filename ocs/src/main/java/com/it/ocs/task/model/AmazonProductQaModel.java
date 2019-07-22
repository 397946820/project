package com.it.ocs.task.model;

import java.sql.Timestamp;
import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class AmazonProductQaModel extends BaseModel {
	private Long entity_id;
	private String platform;
	private String asin;
	private Long product_id;
	private String question;
	private String forum;
	private String answers;
	private Long answer_total;
	private Timestamp answer_updated_at;
	private Long has_new;
	private Long qa_total;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Date last_update_date = new Date();
	private Long source_id;
	public Long getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(Long entity_id) {
		this.entity_id = entity_id;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getForum() {
		return forum;
	}
	public void setForum(String forum) {
		this.forum = forum;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public Long getAnswer_total() {
		return answer_total;
	}
	public void setAnswer_total(Long answer_total) {
		this.answer_total = answer_total;
	}
	public Timestamp getAnswer_updated_at() {
		return answer_updated_at;
	}
	public void setAnswer_updated_at(Timestamp answer_updated_at) {
		this.answer_updated_at = answer_updated_at;
	}
	public Long getHas_new() {
		return has_new;
	}
	public void setHas_new(Long has_new) {
		this.has_new = has_new;
	}
	public Long getQa_total() {
		return qa_total;
	}
	public void setQa_total(Long qa_total) {
		this.qa_total = qa_total;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public Date getLast_update_date() {
		last_update_date = new Date();
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	public Long getSource_id() {
		return source_id;
	}
	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}
	
}
