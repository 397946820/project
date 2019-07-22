package com.it.ocs.eda.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EDARequestModel {
	
	private String url;
	private String userName;
	private String password;
	private Object data;
	
	public EDARequestModel(String url,String userName,String password){
		this.url = url;
		this.userName = userName;
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRequestData(){
		JSONObject json = formatDataToJSON();
		json.put("userName", userName);
		json.put("password", password);
		return json.toString();
	}
	public String getRequestSaveJson(){
		JSONObject json = formatDataToJSON();
		return json.toString();
	}
	private JSONObject formatDataToJSON() {
		JSONObject json = new JSONObject();
		if(data instanceof ArrayList){
			json.put("data", JSONArray.fromObject(data));
		}else if(data instanceof Map){
			json.put("data", JSONObject.fromObject(data));
		}else{
			json.put("data", data);
		}
		return json;
	}
	public static void main(String[] args) {
		EDARequestModel eda = new EDARequestModel("http://wms.omniselling.net:48080/omniv4/webservice","LE_Test","123456");
		Map map = new HashMap<>();
		List list = new ArrayList<>();
		map.put("a1", "aa");
		map.put("a2", "aa");
		map.put("a3", new String[]{"aaa","bbbb","ccc"});
		list.add(map);
		eda.setData(list);
		System.out.println(eda.getRequestData());
	}
}
