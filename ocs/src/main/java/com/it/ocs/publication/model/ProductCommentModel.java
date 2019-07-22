package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class ProductCommentModel extends BaseModel {
	private String topPromotionType = "";// 顶部推广类型
	private String footerPromotionType = "";// 底部推广类型
	private String counterType = "";// 计数器类型
	private boolean openPageProtect = false;// 是否启用网页保护
	private String templateTitle = "";// 模版标题
	private String ebayImgs = "";// ebay图片
	private String templateImgs = "";// 模版图片
	private String appComment = "";// 移动端描述
	private String comments = "";

	public String getTopPromotionType() {
		return topPromotionType;
	}

	public void setTopPromotionType(String topPromotionType) {
		this.topPromotionType = topPromotionType;
	}

	public String getFooterPromotionType() {
		return footerPromotionType;
	}

	public void setFooterPromotionType(String footerPromotionType) {
		this.footerPromotionType = footerPromotionType;
	}

	public String getCounterType() {
		return counterType;
	}

	public void setCounterType(String counterType) {
		this.counterType = counterType;
	}

	public boolean isOpenPageProtect() {
		return openPageProtect;
	}

	public void setOpenPageProtect(boolean openPageProtect) {
		this.openPageProtect = openPageProtect;
	}

	public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public String getEbayImgs() {
		return ebayImgs;
	}

	public void setEbayImgs(String ebayImgs) {
		this.ebayImgs = ebayImgs;
	}

	public String getTemplateImgs() {
		return templateImgs;
	}

	public void setTemplateImgs(String templateImgs) {
		this.templateImgs = templateImgs;
	}

	public String getAppComment() {
		return appComment;
	}

	public void setAppComment(String appComment) {
		this.appComment = appComment;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
