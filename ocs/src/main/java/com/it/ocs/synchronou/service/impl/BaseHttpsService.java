package com.it.ocs.synchronou.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

import com.it.ocs.synchronou.service.IBaseHttps;

import net.sf.json.JSONObject;
@Service
public class BaseHttpsService implements IBaseHttps {
	private static Logger logger = Logger.getLogger(BaseHttpsService.class);
	@Override
	public JSONObject getResponseJson(String url, Map<String, String> map) {
		URL realUrl;
	    JSONObject jsonObject = new JSONObject();
		try {
			realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setConnectTimeout(150000);
			connection.setReadTimeout(150000);
			for (String key : map.keySet()) {
				   connection.setRequestProperty(key, map.get(key));
			 }
			/*connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("contentType", "UTF-8");
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");*/
			StringBuilder jsonString = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine())!= null) {
				    jsonString.append(inputLine);
			}
			in.close();
		    jsonObject = JSONObject.fromObject(jsonString.toString());
					
		} catch (IOException e) {
			logger.error("BaseHttpsService", e);
		}	
		
		return jsonObject;

	}
	public JSONObject getResponseJson(String url ,Map<String,String> map,String requestJson){
		URL realUrl;
	    JSONObject jsonObject = new JSONObject();
		try {
			realUrl = new URL(url);
			byte[] jsonDate = requestJson.getBytes();
			URLConnection connection = realUrl.openConnection();
			connection.setConnectTimeout(150000);
			connection.setReadTimeout(150000);
			connection.setDoOutput(true);
			for (String key : map.keySet()) {
				   connection.setRequestProperty(key, map.get(key));
			 }
			OutputStream out= connection.getOutputStream();
			out.write(jsonDate);
			out.flush();
			StringBuilder jsonString = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine())!= null) {
				    jsonString.append(inputLine);
			}
			in.close();
		    jsonObject = JSONObject.fromObject(jsonString.toString());
					
		} catch (IOException e) {
			logger.error("BaseHttpsService请求", e);
		}	
		return jsonObject;
	}
	@Override
	public Document getPesponseXml(String url, Map<String, String> map, String requestXml) {
		Document doc = null;
		try {
			URL realUrl = new URL(url);
			byte[] xmlDate = requestXml.getBytes();
			URLConnection connection = realUrl.openConnection();
			connection.setConnectTimeout(150000);
			connection.setReadTimeout(150000);
			connection.setDoOutput(true);
			for (String key : map.keySet()) {
				   connection.setRequestProperty(key, map.get(key));
			 }
			OutputStream out= connection.getOutputStream();
			out.write(xmlDate);
			out.flush();
			StringBuilder text = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine = null;
			while ( (inputLine = in.readLine()) != null) {		  
				text.append(inputLine);	  
			}
			
			doc = DocumentHelper.parseText(text.toString());
			out.close();
		} catch (MalformedURLException e) {
//			in.close();
			logger.error("请求ebay异常",e);
		} catch (IOException e) {
			logger.error("请求ebay异常",e);
		} catch (DocumentException e) {
			logger.error("请求ebay异常",e);
		}
		return doc;
	}
	public Document getResponseXml(RequestParam param){
		Document doc = null;
		try {
			URL realUrl = new URL(param.getPath());
			byte[] xmlDate = param.getBody().getBytes();
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setConnectTimeout(150000);
			connection.setReadTimeout(150000);
			connection.setDoOutput(true);
			connection.setRequestMethod(param.getMethod());
			Map<String, String> map = param.getHeaders();
			for (String key : map.keySet()) {
				   connection.setRequestProperty(key, map.get(key));
			 }
			OutputStream out= connection.getOutputStream();
			out.write(xmlDate);
			out.flush();
			StringBuilder text = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine = null;
			while ( (inputLine = in.readLine()) != null) {		  
				text.append(inputLine);	  
			}
			
			doc = DocumentHelper.parseText(text.toString());
			out.close();
			in.close();
			
		} catch (MalformedURLException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (DocumentException e) {
			logger.error(e);
		}
		return doc;
	}
	
}
