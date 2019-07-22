package com.it.ocs.itemDescribe.model;

/**
 * 
 * @author yangguanbao
 * 描述：推广
 */
public class EBayPromoteModel {
	
	private String imgUrl;
	private String title;
	private String productAddress;
	private String price;
	private String buyNow;
	private String freeShipping="Free shipping";
	private String originalPrice;
	
	
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProductAddress() {
		return productAddress;
	}
	public void setProductAddress(String productAddress) {
		this.productAddress = productAddress;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBuyNow() {
		return buyNow;
	}
	public void setBuyNow(String buyNow) {
		this.buyNow = buyNow;
	}
	public String getFreeShipping() {
		return freeShipping;
	}
	public void setFreeShipping(String freeShipping) {
		this.freeShipping = freeShipping;
	}
	
}
