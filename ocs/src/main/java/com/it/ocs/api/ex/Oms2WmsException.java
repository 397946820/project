package com.it.ocs.api.ex;

public class Oms2WmsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1764755729566673746L;
	
	private String code = "-1";
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Oms2WmsException(String message) {
		super(message);
	}
	
	public Oms2WmsException(String message, Throwable e) {
		super(message, e);
	}

	public Oms2WmsException(String message, String code) {
		this(message);
		this.code = code;
	}

	public Oms2WmsException(String message, Throwable e, String code) {
		this(message, e);
		this.code = code;
	}
}
