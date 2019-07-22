package com.it.ocs.fourPX.vo;

public enum FpxOrderPlatform {

	EBAY("ebay"),
	WALMART("walmart"),
	LIGHT("light"),
	AMAZON("amazon");
	
	private String name;
	
	private FpxOrderPlatform(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
