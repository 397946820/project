package com.it.ocs.synchronou.model;

import java.io.Serializable;

public class EbayUserCaseModel implements Serializable {
	private Integer id;
	private String caseId;
	private String caseType;
	private String status;
	private String buyerId;
	private String account;
	private String itemId;
	private String itemTitle;
	private String transactionId;
	private String amount;
	private String quantity;
	private String globalid;
	private String transactionDate;
	private String transactionPrice;
	private String respondByDate;
	private String creationDateStr;
	private String lastModifiedDateStr;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getGlobalid() {
		return globalid;
	}
	public void setGlobalid(String globalid) {
		this.globalid = globalid;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionPrice() {
		return transactionPrice;
	}
	public void setTransactionPrice(String transactionPrice) {
		this.transactionPrice = transactionPrice;
	}
	public String getRespondByDate() {
		return respondByDate;
	}
	public void setRespondByDate(String respondByDate) {
		this.respondByDate = respondByDate;
	}
	public String getCreationDateStr() {
		return creationDateStr;
	}
	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
	}
	public String getLastModifiedDateStr() {
		return lastModifiedDateStr;
	}
	public void setLastModifiedDateStr(String lastModifiedDateStr) {
		this.lastModifiedDateStr = lastModifiedDateStr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
