package com.it.ocs.walmart.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.synchronou.model.ParseWalmartOrderXMLModel;
import com.it.ocs.walmart.model.WalmartRequestModel;

public class WalmartHttpUtil {
	private final static Logger logger = Logger.getLogger(WalmartHttpUtil.class);

	public static String httpGet(WalmartRequestModel requestModel) {
		String url = requestModel.getRequestUrl();
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			Map<String, String> heardMap = requestModel.getRequestHeader();
			for (Map.Entry<String, String> entry : heardMap.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String strResult = EntityUtils.toString(response.getEntity());
				
				return strResult;
			} else {
				String strResult = EntityUtils.toString(response.getEntity());
				logger.info("请求walmart错误:" + strResult);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		}
		return null;
	}

	public static String httpPost(WalmartRequestModel requestModel) {
		return post(requestModel,"application/xml");
	}

	private static String post(WalmartRequestModel requestModel,String contentType) {
		String url = requestModel.getRequestUrl();
		try {
			// 创建httpclient工具对象
			DefaultHttpClient client = new DefaultHttpClient();
			// 创建post请求方法
			HttpPost request = new HttpPost(url);
			Map<String, String> heardMap = requestModel.getRequestHeader();
			for (Map.Entry<String, String> entry : heardMap.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
			request.addHeader("Content-Type",contentType);
			if (StringUtils.isNotBlank(requestModel.getRequestParam().get("content"))) {
				StringEntity entity = new StringEntity(requestModel.getRequestParam().get("content"), Charset.forName("UTF-8"));
	            entity.setContentEncoding("UTF-8");
	            request.setEntity(entity);
			}
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String strResult = EntityUtils.toString(response.getEntity());
				
				return strResult;
			} else {
				String strResult = EntityUtils.toString(response.getEntity());
				logger.info("请求walmart错误:" + strResult);
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		}
		return null;
	}
	
	public static String httpPostByJSON(WalmartRequestModel requestModel) {
		 return post(requestModel,"application/json");
	}
	
	public static String createGetUrl(String url, Map<String, String> paramMap) {
		// 设置参数
		List<NameValuePair> params = Lists.newArrayList();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			String param = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
			url = url + (StringUtils.isBlank(param) ? "" : "?" + param);
		} catch (ParseException e) {
			logger.error("解析参数失败:" + paramMap.toString(), e);
		} catch (IOException e) {
			logger.error("网络异常:" + paramMap.toString(), e);
		}

		return url;
	}
	
	public static void main(String[] args) {
		try {
			WalmartRequestModel wm = new WalmartRequestModel("6e88c7a7-cc47-42c0-8f23-c34bbdc14bbc",
					"ceshi//https://api-gateway.walmart.com/v3/orders/4580861107799/refund",
					"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIzeI3Rsh6VqzlyU7m+M3Nqs/SEsLiFTLgUBzgeK9VACGMbpt014HAauV7kG2bkcL1TW2DFOryGeEtWtVkEsV09dNSNsWTK1JtX3x0Mpqbbkax/nq9ytwM36XAf6kecX8jie5a/QP67u0hzA1okwAGt7LMC6RiBkzGo0KedVSLfLAgMBAAECgYAtTTLxugVVeSesNmkHjrFn0cIF7QNYzDs8ePwdCN8jP8lgDsFYNIwuydqEMmPKV/oQupcEJF3F3eQIu5nFk9DvxbhG3k/ou7g9oxx5rTcKr+bLJjpaXP3Hs3qa8OooY6wdV2/1/JT5slydXOZBGahGRq7Dw5ex3/ClMhjDq60xAQJBANkjL9blmRM0tGaZcqcvCaq7+QnfTeBcPvcRJ7Nfj7vpzZTTUI5UPcpVKpZ/4aWyQyCLxPUKXELzm2fosCfzEyECQQCmFGwYKbZZ+l/QULCRb1t3wlrXUaC9EO8A7q3NkoacjI9WrRn3mHpVVmupJxzs3qdGBAVQMjm3Bixj0clFIZlrAkEAym1ERxoKx93lpJS/T17Jq1LMS8jTCCvXQ2vJ0C5xiCTiVDFgMyAT7tHQ/zc+/MMgU3IZ7rt24a/LnGZP+x5gIQJALtfxyTrysRxarBImS08ieHv8TWE7ujgtgZDQuvwKNdu7jlIS0fBTCQDwKuzaTsFaYvg9W2pfNw1ptdn1MCeS8wJBAM0ULPfqGYHLYGksItL7mwvx/NPeFrev8tnfkOjeJoAiLrnh8RmCOo7iheWW9GC4NCSGXEwtRcxe2ZEj3FxI1Jk=", WalmartRequestModel.POST, ""); 
			
			String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						"<ns2:orderRefund xmlns:ns2=\"http://walmart.com/mp/v3/orders\" xmlns:ns3=\"http://walmart.com/\">" +
						 "<ns2:purchaseOrderId>4580861107799</ns2:purchaseOrderId>" +
						 "<ns2:orderLines>" +
						 "<ns2:orderLine> " +
						"<ns2:lineNumber>1</ns2:lineNumber>" +
						"<ns2:refunds>" +
						"<ns2:refund>" +
						 "<ns2:refundComments>CustomerChangedMind</ns2:refundComments>" +
						 "<ns2:refundCharges>" +
						  "<ns2:refundCharge>" +
						   "<ns2:refundReason>DamagedItem</ns2:refundReason>" +
						   "<ns2:charge>" +
						  "<ns2:chargeType>PRODUCT</ns2:chargeType>" +
						  "<ns2:chargeName>Item Price</ns2:chargeName>" +
						  "<ns2:chargeAmount>" +
						   "<ns2:currency>USD</ns2:currency>" +
						   "<ns2:amount>-39.99</ns2:amount>" +
						  "</ns2:chargeAmount>" +
						  "<ns2:tax>" +
						   "<ns2:taxName>Item Price Tax</ns2:taxName>" +
						   "<ns2:taxAmount>" +
						    "<ns2:currency>USD</ns2:currency>" +
						    "<ns2:amount>-0</ns2:amount>" +
						   "</ns2:taxAmount>" +
						  "</ns2:tax>" +
						   "</ns2:charge>" +
						  "</ns2:refundCharge>" +
						 "</ns2:refundCharges>" +
						"</ns2:refund>" +
						"</ns2:refunds>" +
						"</ns2:orderLine>" +
						"</ns2:orderLines>" +
						"</ns2:orderRefund>";
			Map<String, String> params = Maps.newHashMap();
			params.put("content", content);
			wm.setRequestParam(params);
			String response = WalmartHttpUtil.httpPost(wm);
			Document doc = DocumentHelper.parseText(response);
			ParseWalmartOrderXMLModel parse = new ParseWalmartOrderXMLModel(doc, "http://walmart.com/mp/v3/orders");
			List<Map<String, Object>> data = parse.getResult();
			System.out.println(data);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	
}
