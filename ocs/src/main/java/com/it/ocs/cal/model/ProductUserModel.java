package com.it.ocs.cal.model;

public class ProductUserModel {

	private Long id;
	private Long userId;
	private Long productId;
	
	public ProductUserModel(Long u,Long p) {
		this.userId = u;
		this.productId = p;
	}
	public ProductUserModel(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
