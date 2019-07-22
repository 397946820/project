package com.it.ocs.publication.model;

import com.it.ocs.common.model.BaseModel;

public class RelationDataModel extends BaseModel {
	private Long siteId = 0L;// 站点
	private String publicationType;
	private String ebayAccount;
	private String sku;
	private Long productFirstCategory;
	private String price;
	private String publicationDays;
	private String autionItemInfo;
	private String postCode;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(String publicationType) {
		this.publicationType = publicationType;
	}

	public String getEbayAccount() {
		return ebayAccount;
	}

	public void setEbayAccount(String ebayAccount) {
		this.ebayAccount = ebayAccount;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getProductFirstCategory() {
		return productFirstCategory;
	}

	public void setProductFirstCategory(Long productFirstCategory) {
		this.productFirstCategory = productFirstCategory;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPublicationDays() {
		return publicationDays;
	}

	public void setPublicationDays(String publicationDays) {
		this.publicationDays = publicationDays;
	}

	public String getAutionItemInfo() {
		return autionItemInfo;
	}

	public void setAutionItemInfo(String autionItemInfo) {
		this.autionItemInfo = autionItemInfo;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}
