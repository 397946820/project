package com.it.ocs.api.ex;

public class SkuInventoryException extends Oms2WmsException {

	/**
	 * 异常类型标记常量：接口API调用异常
	 */
	public static final String API_CALLFAILED = "callfailed";
	
	/**
	 * 异常类型标记常量：SKU库存不足
	 */
	public static final String INVENTORY_SHORTAGE = "shortage";
	
	/**
	 * 异常类型标记常量：SKU库存获取失败
	 */
	public static final String INVENTORY_GETFAILED = "getfailed";
	
	/**
	 * 异常类型标记常量：接口API返回中取不到有效信息
	 */
	public static final String API_RETINVALID = "retinvalid";

	/**
	 * 
	 */
	private static final long serialVersionUID = -8122486881404922901L;

	public SkuInventoryException(String message) {
		super(message);
	}

	public SkuInventoryException(String message, String code) {
		super(message, code);
	}

	public SkuInventoryException(String message, Throwable e) {
		super(message, e);
	}

	public SkuInventoryException(String message, Throwable e, String code) {
		super(message, e, code);
	}
}
