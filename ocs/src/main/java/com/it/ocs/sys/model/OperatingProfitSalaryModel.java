package com.it.ocs.sys.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public class OperatingProfitSalaryModel {
	private Long entityId;

	@ExcelLink(title = "部门", index = 0)
	private String department;

	@ExcelLink(title = "人员", index = 1)
	private String personnel;

	@ExcelLink(title = "工资奖金", index = 2)
	private Double wageBonuses;

	@ExcelLink(title = "公积金", index = 3)
	private Double accumulationFund;

	@ExcelLink(title = "社保", index = 4)
	private Double socialSecurity;

	@ExcelLink(title = "个人所得税", index = 5)
	private Double individualIncomeTax;

	@ExcelLink(title = "其他费用", index = 6)
	private Double otherFee;

	private String monthOfYear;

	private Date createdAt;

	private Date updatedAt;

	public OperatingProfitSalaryModel() {
		super();
	}

	public OperatingProfitSalaryModel(String department, String personnel,String monthOfYear) {
		this.department = department;
		this.personnel = personnel;
		this.monthOfYear = monthOfYear;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department == null ? null : department.trim();
	}

	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel == null ? null : personnel.trim();
	}

	public Double getWageBonuses() {
		return wageBonuses;
	}

	public void setWageBonuses(Double wageBonuses) {
		this.wageBonuses = wageBonuses;
	}
	
	public Double getAccumulationFund() {
		return accumulationFund;
	}

	public void setAccumulationFund(Double accumulationFund) {
		this.accumulationFund = accumulationFund;
	}

	public Double getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(Double socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public Double getIndividualIncomeTax() {
		return individualIncomeTax;
	}

	public void setIndividualIncomeTax(Double individualIncomeTax) {
		this.individualIncomeTax = individualIncomeTax;
	}

	public Double getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear == null ? null : monthOfYear.trim();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", entityId=").append(entityId);
		sb.append(", department=").append(department);
		sb.append(", personnel=").append(personnel);
		sb.append(", wageBonuses=").append(wageBonuses);
		sb.append(", socialSecurity=").append(socialSecurity);
		sb.append(", individualIncomeTax=").append(individualIncomeTax);
		sb.append(", otherFee=").append(otherFee);
		sb.append(", monthOfYear=").append(monthOfYear);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", updatedAt=").append(updatedAt);
		sb.append("]");
		return sb.toString();
	}
}
