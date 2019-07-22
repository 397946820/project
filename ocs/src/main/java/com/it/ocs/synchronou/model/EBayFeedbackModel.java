package com.it.ocs.synchronou.model;

import java.util.Date;

import com.it.ocs.common.model.BaseModel;

public class EBayFeedbackModel extends BaseModel {

	private String left_comment_text;//评价内容
	private Date left_comment_time;//评价提交时间
	private String left_comment_type;//评价类型
	private String left_feedback_id;//评价ID号
	private String received_comment_text;//评价内容
	private Date received_comment_time;//评价提交时间
	private String receivedt_comment_type;//评价类型
	private String received_feedback_id;//评价ID号
	private String follow_up_text;//回复内容
	private String reply_text;//回复内容
	private String seller_id;//卖价ID号
	private String buyer_id;//买家ID号
	private String item_id;//物品号
	private String transaction_id;//交易号
	private String order_line_item_id;//订单物品号
	private String item_title;//物品标题
	private Double item_price;//物品价格
	private String currency_id;//货币单位
	private String seller_follow_up_text;//回复内容
	private Integer commenting_user_score;//信用积分
	private String left_feed_back_response;
	private String received_feed_back_response;
	private String remark;
	private Date creation_date;
	private Long create_by;
	private Long last_update_by;
	private Date last_update_date;
	private String enabled_flag;
	private Date sysdate = new Date();
	private String imagesUrl ;
	private String response_type;
	private String product_url;
	private String sku;
	
	private String user_url;
	
	
	public String getUser_url() {
		return user_url;
	}
	public void setUser_url(String user_url) {
		this.user_url = user_url;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProduct_url() {
		return product_url;
	}
	public void setProduct_url(String product_url) {
		this.product_url = product_url;
	}
	public String getResponse_type() {
		return response_type;
	}
	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}
	public String getImagesUrl() {
		return imagesUrl;
	}
	public void setImagesUrl(String imagesUrl) {
		this.imagesUrl = imagesUrl;
	}
	public String getLeft_feed_back_response() {
		return left_feed_back_response;
	}
	public void setLeft_feed_back_response(String left_feed_back_response) {
		this.left_feed_back_response = left_feed_back_response;
	}
	public String getReceived_feed_back_response() {
		return received_feed_back_response;
	}
	public void setReceived_feed_back_response(String received_feed_back_response) {
		this.received_feed_back_response = received_feed_back_response;
	}

	
	
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	public Long getCreate_by() {
		return create_by;
	}
	public void setCreate_by(Long create_by) {
		this.create_by = create_by;
	}
	public Long getLast_update_by() {
		return last_update_by;
	}
	public void setLast_update_by(Long last_update_by) {
		this.last_update_by = last_update_by;
	}
	public Date getLast_update_date() {
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	public String getEnabled_flag() {
		return enabled_flag;
	}
	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}
	public Date getSysdate() {
		return new Date();
	}
	public void setSysdate(Date sysdate) {
		this.sysdate = sysdate;
	}
	public Double getItem_price() {
		return item_price;
	}
	public void setItem_price(Double item_price) {
		this.item_price = item_price;
	}
	public Integer getCommenting_user_score() {
		return commenting_user_score;
	}
	public void setCommenting_user_score(Integer commenting_user_score) {
		this.commenting_user_score = commenting_user_score;
	}
	public String getLeft_comment_text() {
		return left_comment_text;
	}
	public void setLeft_comment_text(String left_comment_text) {
		this.left_comment_text = left_comment_text;
	}
	
	public Date getLeft_comment_time() {
		return left_comment_time;
	}
	public void setLeft_comment_time(Date left_comment_time) {
		this.left_comment_time = left_comment_time;
	}
	public String getLeft_comment_type() {
		return left_comment_type;
	}
	public void setLeft_comment_type(String left_comment_type) {
		this.left_comment_type = left_comment_type;
	}
	public String getLeft_feedback_id() {
		return left_feedback_id;
	}
	public void setLeft_feedback_id(String left_feedback_id) {
		this.left_feedback_id = left_feedback_id;
	}
	public String getReceived_comment_text() {
		return received_comment_text;
	}
	public void setReceived_comment_text(String received_comment_text) {
		this.received_comment_text = received_comment_text;
	}
	
	public Date getReceived_comment_time() {
		return received_comment_time;
	}
	public void setReceived_comment_time(Date received_comment_time) {
		this.received_comment_time = received_comment_time;
	}
	public String getReceivedt_comment_type() {
		return receivedt_comment_type;
	}
	public void setReceivedt_comment_type(String receivedt_comment_type) {
		this.receivedt_comment_type = receivedt_comment_type;
	}
	
	public String getReceived_feedback_id() {
		return received_feedback_id;
	}
	public void setReceived_feedback_id(String received_feedback_id) {
		this.received_feedback_id = received_feedback_id;
	}
	public String getFollow_up_text() {
		return follow_up_text;
	}
	public void setFollow_up_text(String follow_up_text) {
		this.follow_up_text = follow_up_text;
	}
	public String getReply_text() {
		return reply_text;
	}
	public void setReply_text(String reply_text) {
		this.reply_text = reply_text;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOrder_line_item_id() {
		return order_line_item_id;
	}
	public void setOrder_line_item_id(String order_line_item_id) {
		this.order_line_item_id = order_line_item_id;
	}
	public String getItem_title() {
		return item_title;
	}
	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}
	
	public String getCurrency_id() {
		return currency_id;
	}
	public void setCurrency_id(String currency_id) {
		this.currency_id = currency_id;
	}
	public String getSeller_follow_up_text() {
		return seller_follow_up_text;
	}
	public void setSeller_follow_up_text(String seller_follow_up_text) {
		this.seller_follow_up_text = seller_follow_up_text;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
