package com.it.ocs.synchronou.service;


import java.util.Map;

import org.dom4j.Document;

import net.sf.json.JSONObject;

public interface IBaseHttps {

		public JSONObject getResponseJson(String url,Map<String,String> map);
		
		public Document getPesponseXml(String url,Map<String, String> map,String requestXml);
		
		
}
