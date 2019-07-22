package com.it.ocs.customerCenter.model;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.excel.annotation.ExcelLink;

public class CustomerTicketsModel extends BaseModel {

	private Long tickets_id;
	@ExcelLink(title = "Country", index = 1)
	private String country;
	@ExcelLink(title = "OrderNumber", index = 2)
	private String order_number;
	@ExcelLink(title = "产品SKU", index = 3)
	private String sku;
	private String sku_grade;
	@ExcelLink(title = "OrderDate", index = 4)
	private String order_date;
	@ExcelLink(title = "DefectiveQuantity", index = 5)
	private Long defective_quantity;
	@ExcelLink(title = "销售大类", index = 6)
	private String parent_category;
	@ExcelLink(title = "Catagories", index = 7)
	private String catagories;
	@ExcelLink(title = "使用场景补发退款原因", index = 8)
	private String remark;
	@ExcelLink(title = "ProblemTypeLvl2", index = 9)
	private String problem_type_lvl2;
	@ExcelLink(title = "Amount", index = 10)
	private Double amount;
	@ExcelLink(title = "TotalDays", index = 11)
	private Long total_days;
	@ExcelLink(title = "Currency", index = 12)
	private String currency;
	@ExcelLink(title = "ProblemDate", index = 13)
	private String problem_date;
	@ExcelLink(title = "Solution", index = 14)
	private String solution;
	@ExcelLink(title = "ReplacementTimes", index = 15)
	private Long replacement_times;
	@ExcelLink(title = "Operator", index = 16)
	private String operators;
	@ExcelLink(title = "Platform", index = 0)
	private String platform;
	@ExcelLink(title = "Operator", index = 17)
	private String payMethod;

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getParent_category() {
		return parent_category;
	}

	public void setParent_category(String parent_category) {
		this.parent_category = parent_category;
	}

	public String getProblem_date() {
		return problem_date;
	}

	public Long getTickets_id() {
		return tickets_id;
	}

	public void setTickets_id(Long tickets_id) {
		this.tickets_id = tickets_id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSku_grade() {
		return sku_grade;
	}

	public void setSku_grade(String sku_grade) {
		this.sku_grade = sku_grade;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public void setProblem_date(String problem_date) {
		this.problem_date = problem_date;
	}

	public Long getDefective_quantity() {
		return defective_quantity;
	}

	public void setDefective_quantity(Long defective_quantity) {
		this.defective_quantity = defective_quantity;
	}

	public String getCatagories() {
		return catagories;
	}

	public void setCatagories(String catagories) {
		this.catagories = catagories;
	}

	public String getProblem_type_lvl2() {
		return problem_type_lvl2;
	}

	public void setProblem_type_lvl2(String problem_type_lvl2) {
		this.problem_type_lvl2 = problem_type_lvl2;
	}

	public Long getTotal_days() {
		return total_days;
	}

	public void setTotal_days(Long total_days) {
		this.total_days = total_days;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Long getReplacement_times() {
		return replacement_times;
	}

	public void setReplacement_times(Long replacement_times) {
		this.replacement_times = replacement_times;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
