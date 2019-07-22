package com.it.ocs.publication.vo;

import java.io.Serializable;

import com.it.ocs.common.model.BaseModel;
import com.it.ocs.publication.model.PublicationModel;

public class PublicationPageVO extends PublicationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3176555285319742201L;
	private String nameOrSku;// 名称和sku
	private String site;// 站点
	private String publicationType;// 刊登类型
	private String price;// 价格
	private String count;// 数量
	private String publicationDays;// 刊登天数
	private String ebayCategory;// eBay分类
	private String productPlacePost;// 物品所在地邮编
	public String getNameOrSku() {
		return nameOrSku;
	}

	public void setNameOrSku(String nameOrSku) {
		this.nameOrSku = nameOrSku;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	@Override
	public String getPublicationType() {
		return publicationType;
	}
	@Override
	public void setPublicationType(String publicationType) {
		this.publicationType = publicationType;
	}
	@Override
	public String getPrice() {
		return price;
	}
	@Override
	public void setPrice(String price) {
		this.price = price;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	@Override
	public String getPublicationDays() {
		return publicationDays;
	}
	@Override
	public void setPublicationDays(String publicationDays) {
		this.publicationDays = publicationDays;
	}

	public String getEbayCategory() {
		return ebayCategory;
	}

	public void setEbayCategory(String ebayCategory) {
		this.ebayCategory = ebayCategory;
	}

	public String getProductPlacePost() {
		return productPlacePost;
	}

	public void setProductPlacePost(String productPlacePost) {
		this.productPlacePost = productPlacePost;
	}

}
