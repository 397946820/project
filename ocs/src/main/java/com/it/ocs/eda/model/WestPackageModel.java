package com.it.ocs.eda.model;

public class WestPackageModel {
	private String code;
	private Double min;
	private Double max;
	private Double length;
	private Double width;
	private Double height;
	
	private WestPackageModel(String code,Double min,Double max,Double length,Double width,Double height){
		this.code = code;
		this.min = min;
		this.max = max;
		this.length = length;
		this.width = width;
		this.height = height;
	}
	public static WestPackageModel getInstance(String code,Double min,Double max,Double length,Double width,Double height){
		return new WestPackageModel(code,min,max,length,width,height);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	
}
