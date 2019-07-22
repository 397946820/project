package com.it.ocs.customerCenter.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.excel.annotation.ExcelLink;

public class CustomerFeedbackModel extends BaseModel {
	
	private Long feedbacks_id;
	@ExcelLink(title="国家",index=0)
	private String site;
	@ExcelLink(title="订单号",index=1)
	private String orders;
	@ExcelLink(title="产品SKU",index=2)
	private String sku;
	@ExcelLink(title="订单时间",index=3)
	private String order_date;
	@ExcelLink(title="评论",index=4)
	private String comments;
	@ExcelLink(title="中文翻译",index=5)
	private String translates;
	@ExcelLink(title="原因",index=6)
	private String reason;
	@ExcelLink(title="是否删除",index=7)
	private String is_delete;
	@ExcelLink(title="提升点",index=8)
	private String lifting_point;
	@ExcelLink(title="备注",index=9)
	private String remarks;
	@ExcelLink(title="问题时间",index=10)
	private String problem_time;
	
	@ExcelLink(title="Catagories",index=11)
	private String catagories;
	
	@ExcelLink(title="销售大类",index=12)
	private String parent_category;
	
	
	public String getParent_category() {
		return parent_category;
	}
	public void setParent_category(String parent_category) {
		this.parent_category = parent_category;
	}
	public String getCatagories() {
		return catagories;
	}
	public void setCatagories(String catagories) {
		this.catagories = catagories;
	}
	public Long getFeedbacks_id() {
		return feedbacks_id;
	}
	public void setFeedbacks_id(Long feedbacks_id) {
		this.feedbacks_id = feedbacks_id;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTranslates() {
		return translates;
	}
	public void setTranslates(String translates) {
		this.translates = translates;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getProblem_time() {
		return problem_time;
	}
	public void setProblem_time(String problem_time) {
		this.problem_time = problem_time;
	}
	public String getLifting_point() {
		return lifting_point;
	}
	public void setLifting_point(String lifting_point) {
		this.lifting_point = lifting_point;
	}
	
	
}
