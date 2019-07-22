package com.it.ocs.customerCenter.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.excel.annotation.ExcelLink;

public class CustomerRefundModel extends BaseModel {

		private Long report_id;
		@ExcelLink(title="RefundDate",index=3)
		private String refund_date;
		@ExcelLink(title="SKU",index=5)
		private String sku;
		@ExcelLink(title="Qty",index=6)
		private Long qty;
		@ExcelLink(title="Currency",index=7)
		private String currency;
		@ExcelLink(title="PaymentMenthod",index=9)
		private String payment_menthod;
		@ExcelLink(title="FullOrPartial",index=10)
		private String full_or_partial;
		@ExcelLink(title="RefundTimes",index=11)
		private Long refund_times;
		@ExcelLink(title="Return",index=12)
		private String returns;
		@ExcelLink(title="ReplaceTimes",index=13)
		private Long replace_times;
		@ExcelLink(title="Reason",index=14)
		private String reason;
		@ExcelLink(title="ProblemTypes",index=15)
		private String problem_types;
		@ExcelLink(title="Approval",index=16)
		private String approval;
		@ExcelLink(title="Country",index=0)
		private String web_site;
		@ExcelLink(title="OrderDate",index=2)
		private String order_date;
		@ExcelLink(title="Platform",index=1)
		private String platform;
		@ExcelLink(title="OrderId",index=4)
		private String order_id;
		@ExcelLink(title="Amount",index=8)
		private Double amount;
		public Long getReport_id() {
			return report_id;
		}
		public void setReport_id(Long report_id) {
			this.report_id = report_id;
		}
		
		public String getRefund_date() {
			return refund_date;
		}
		public void setRefund_date(String refund_date) {
			this.refund_date = refund_date;
		}
		public String getSku() {
			return sku;
		}
		public void setSku(String sku) {
			this.sku = sku;
		}
		public Long getQty() {
			return qty;
		}
		public void setQty(Long qty) {
			this.qty = qty;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getPayment_menthod() {
			return payment_menthod;
		}
		public void setPayment_menthod(String payment_menthod) {
			this.payment_menthod = payment_menthod;
		}
		public String getFull_or_partial() {
			return full_or_partial;
		}
		public void setFull_or_partial(String full_or_partial) {
			this.full_or_partial = full_or_partial;
		}
		public Long getRefund_times() {
			return refund_times;
		}
		public void setRefund_times(Long refund_times) {
			this.refund_times = refund_times;
		}
		public String getReturns() {
			return returns;
		}
		public void setReturns(String returns) {
			this.returns = returns;
		}
		public Long getReplace_times() {
			return replace_times;
		}
		public void setReplace_times(Long replace_times) {
			this.replace_times = replace_times;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getProblem_types() {
			return problem_types;
		}
		public void setProblem_types(String problem_types) {
			this.problem_types = problem_types;
		}
		public String getApproval() {
			return approval;
		}
		public void setApproval(String approval) {
			this.approval = approval;
		}
		public String getWeb_site() {
			return web_site;
		}
		public void setWeb_site(String web_site) {
			this.web_site = web_site;
		}
		
		public String getOrder_date() {
			return order_date;
		}
		public void setOrder_date(String order_date) {
			this.order_date = order_date;
		}
		public String getPlatform() {
			return platform;
		}
		public void setPlatform(String platform) {
			this.platform = platform;
		}
		public String getOrder_id() {
			return order_id;
		}
		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		
		

}
