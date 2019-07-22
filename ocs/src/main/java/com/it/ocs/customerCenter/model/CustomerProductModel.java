package com.it.ocs.customerCenter.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.excel.annotation.ExcelLink;

public class CustomerProductModel extends BaseModel{
	
	private Long product_id;
	@ExcelLink(title="国家",index=0)
	private String site;
	@ExcelLink(title="订单号",index=1)
	private String orders;
	@ExcelLink(title="产品SKU",index=2)
	private String sku;
	private String product_grade;
	@ExcelLink(title="问题类型",index=5)
	private String problem_type;
	@ExcelLink(title="解决日期",index=7)
	private String solution_date;
	@ExcelLink(title="提出人",index=8)
	private String present_people;
	@ExcelLink(title="解决方案",index=6)
	private String solution;

	@ExcelLink(title="中文翻译",index=4)
	private String chinese_translation;
	@ExcelLink(title="评论内容",index=3)
	private String comment_content;
	
	@ExcelLink(title="Catagories",index=9)
	private String catagories;
	
	@ExcelLink(title="销售大类",index=10)
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
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
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
	public String getProduct_grade() {
		return product_grade;
	}
	public void setProduct_grade(String product_grade) {
		this.product_grade = product_grade;
	}
	public String getProblem_type() {
		return problem_type;
	}
	public void setProblem_type(String problem_type) {
		this.problem_type = problem_type;
	}
	
	public String getSolution_date() {
		return solution_date;
	}
	public void setSolution_date(String solution_date) {
		this.solution_date = solution_date;
	}
	public String getPresent_people() {
		return present_people;
	}
	public void setPresent_people(String present_people) {
		this.present_people = present_people;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getChinese_translation() {
		return chinese_translation;
	}
	public void setChinese_translation(String chinese_translation) {
		this.chinese_translation = chinese_translation;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	
	
}
