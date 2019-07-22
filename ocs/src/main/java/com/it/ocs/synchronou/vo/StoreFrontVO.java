package com.it.ocs.synchronou.vo;

import java.util.List;

/**
 * 
 * @author yangguanbao
 * 描述：商家自定义类目
 */
public class StoreFrontVO {

	private long storeCategoryID;
	private long storeCategory2ID;
	private String storeCategoryName;
	private String storeCategory2Name;
	private String storeURL;
	private String storeName;
	private List any;
	public long getStoreCategoryID() {
		return storeCategoryID;
	}
	public void setStoreCategoryID(long storeCategoryID) {
		this.storeCategoryID = storeCategoryID;
	}
	public long getStoreCategory2ID() {
		return storeCategory2ID;
	}
	public void setStoreCategory2ID(long storeCategory2ID) {
		this.storeCategory2ID = storeCategory2ID;
	}
	public String getStoreCategoryName() {
		return storeCategoryName;
	}
	public void setStoreCategoryName(String storeCategoryName) {
		this.storeCategoryName = storeCategoryName;
	}
	public String getStoreCategory2Name() {
		return storeCategory2Name;
	}
	public void setStoreCategory2Name(String storeCategory2Name) {
		this.storeCategory2Name = storeCategory2Name;
	}
	public String getStoreURL() {
		return storeURL;
	}
	public void setStoreURL(String storeURL) {
		this.storeURL = storeURL;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public List getAny() {
		return any;
	}
	public void setAny(List any) {
		this.any = any;
	}

}
