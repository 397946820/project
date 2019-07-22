package com.it.ocs.eda.model;

public class JSONNode {
	public final static int String = 1;
	public final static int Int = 2;
	private String key;
	private Object value;
	private int type;
	
	private JSONNode(String key){
		this.key = key;
		this.type = String;
	}
	
	private JSONNode(String key,int type){
		this.key = key;
		this.type = type;
	}
	
	public static JSONNode getInstance(String key){
		return new JSONNode(key);
	}
	
	public static JSONNode getInstance(String key,int type){
		return new JSONNode(key,type);
	}
	
	public void setValue(Object value){
		this.value = value;
	}
}
