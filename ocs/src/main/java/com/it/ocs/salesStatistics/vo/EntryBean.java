package com.it.ocs.salesStatistics.vo;

public class EntryBean implements java.io.Serializable {

	private static final long serialVersionUID = 6835295688198746419L;
	
	private String value;
	private String text;
	
	public EntryBean() {}
	
	public EntryBean(String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

}
