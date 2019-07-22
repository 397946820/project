package com.it.ocs.walmart.model;

import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.it.ocs.synchronou.model.ParseWalmartOrderXMLModel;
import com.it.ocs.walmart.utils.WalmartHttpUtil;

public class WalmartRequestModel {
	private final static Logger log = Logger.getLogger(WalmartRequestModel.class);
	public final static String WM_SVC_NAME = "WM_SVC.NAME";
	public final static String WM_QOS_CORRELATION_ID = "WM_QOS.CORRELATION_ID";
	public final static String WM_SEC_TIMESTAMP = "WM_SEC.TIMESTAMP";
	public final static String WM_SEC_AUTH_SIGNATURE = "WM_SEC.AUTH_SIGNATURE";
	public final static String WM_CONSUMER_CHANNEL_TYPE = "WM_CONSUMER.CHANNEL.TYPE";
	public final static String WM_CONSUMER_ID = "WM_CONSUMER.ID";
	public final static String GET = "GET";
	public final static String POST = "POST";
	private final static String NEXTLINE = "\n";
	private Map<String, String> requestParam;
	private Map<String, String> requestHeader;
	private String consumerId;
	private String baseUrl;
	private String nextBasrUrl;
	private String privateEncodedStr;
	private String httpMethod;
	private String timestamp;
	private String nextCursor;
	private String requestUrl;

	public WalmartRequestModel(String consumerId, String baseUrl, String privateEncodedStr, String httpMethod,
			String nextBasrUrl) {
		this.consumerId = consumerId;
		this.baseUrl = baseUrl;
		this.privateEncodedStr = privateEncodedStr;
		this.httpMethod = httpMethod;
		this.nextBasrUrl = nextBasrUrl;
	}

	public Map<String, String> getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(Map<String, String> requestParam) {
		initRequest(requestParam,"application/xml");
	}

	private void initRequest(Map<String, String> requestParam,String accept) {
		requestUrl = WalmartHttpUtil.createGetUrl(baseUrl, requestParam);
		this.requestParam = requestParam;
		Map<String, String> requestHeader = new HashMap<>();
		requestHeader.put(WalmartRequestModel.WM_SVC_NAME, "Walmart Marketplace");
		requestHeader.put(WalmartRequestModel.WM_QOS_CORRELATION_ID, consumerId);
		this.timestamp = String.valueOf(System.currentTimeMillis());
		requestHeader.put(WalmartRequestModel.WM_SEC_TIMESTAMP, timestamp);
		requestHeader.put(WalmartRequestModel.WM_SEC_AUTH_SIGNATURE, getSignedString(requestUrl));
		requestHeader.put(WalmartRequestModel.WM_CONSUMER_CHANNEL_TYPE, "0f3e4dd4-0514-4346-b39d-af0e00ea066d");
		requestHeader.put(WalmartRequestModel.WM_CONSUMER_ID, consumerId);
		requestHeader.put(HttpHeaders.ACCEPT, accept);
		this.requestHeader = requestHeader;
	}
	
	public void setRequestParamByJSON(Map<String, String> requestParam) {
		initRequest(requestParam,"application/json");
	}

	public Map<String, String> getRequestHeader() {
		return requestHeader;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestHeader(Map<String, String> requestHeader) {
		if (null == this.requestHeader) {
			this.requestHeader = new HashMap<String, String>();
		}
		for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
			this.requestHeader.put(entry.getKey(), entry.getValue());
		}
	}

	public String getSignedString(String url) {
		StringBuffer stringToSign = new StringBuffer();
		stringToSign.append(consumerId);
		stringToSign.append(NEXTLINE);
		stringToSign.append(url);
		stringToSign.append(NEXTLINE);
		stringToSign.append(httpMethod);
		stringToSign.append(NEXTLINE);
		stringToSign.append(timestamp);
		stringToSign.append(NEXTLINE);
		String str = signData(stringToSign.toString(), privateEncodedStr);
		log.info("timestamp:" + timestamp);
		log.info("stringToSign:" + str);
		return str;
	}

	/**
	 * 生成签名
	 * 
	 * @param stringToBeSigned
	 * @param encodedPrivateKey
	 * @return
	 */
	private static String signData(String stringToBeSigned, String encodedPrivateKey) {
		String signatureString = null;
		try {
			byte encodedKeyBytes[] = Base64.decodeBase64(encodedPrivateKey);
			PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(encodedKeyBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			java.security.PrivateKey myPrivateKey = kf.generatePrivate(privSpec);
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(myPrivateKey);
			byte data[] = stringToBeSigned.getBytes("UTF-8");
			signature.update(data);
			byte signedBytes[] = signature.sign();
			signatureString = Base64.encodeBase64String(signedBytes);
		} catch (Exception e) {
			log.info("walmart生成验证签名失败", e);
		}
		return signatureString;
	}

	public void setNextCursor(String nextCursor) {
		this.nextCursor = nextCursor;
		this.timestamp = String.valueOf(System.currentTimeMillis());
		requestHeader.put(WalmartRequestModel.WM_SEC_TIMESTAMP, timestamp);
		requestHeader.put(WalmartRequestModel.WM_SEC_AUTH_SIGNATURE, getSignedString(nextBasrUrl + nextCursor));
		requestUrl = nextBasrUrl + nextCursor;
	}

	public static void main(String[] args) {
		String consumerId = "6e88c7a7-cc47-42c0-8f23-c34bbdc14bbc";
		String baseUrl = "https://marketplace.walmartapis.com/v3/orders?limit=100&hasMoreElements=true&soIndex=7939&poIndex=2100&partnerId=10000008738&sellerId=8515&createdStartDate=2017-01-15T00:00:00Z&createdEndDate=2017-10-20T02:03:55.982Z";// "https://marketplace.walmartapis.com/v3/orders";
		String privateEncodedStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIzeI3Rsh6VqzlyU7m+M3Nqs/SEsLiFTLgUBzgeK9VACGMbpt014HAauV7kG2bkcL1TW2DFOryGeEtWtVkEsV09dNSNsWTK1JtX3x0Mpqbbkax/nq9ytwM36XAf6kecX8jie5a/QP67u0hzA1okwAGt7LMC6RiBkzGo0KedVSLfLAgMBAAECgYAtTTLxugVVeSesNmkHjrFn0cIF7QNYzDs8ePwdCN8jP8lgDsFYNIwuydqEMmPKV/oQupcEJF3F3eQIu5nFk9DvxbhG3k/ou7g9oxx5rTcKr+bLJjpaXP3Hs3qa8OooY6wdV2/1/JT5slydXOZBGahGRq7Dw5ex3/ClMhjDq60xAQJBANkjL9blmRM0tGaZcqcvCaq7+QnfTeBcPvcRJ7Nfj7vpzZTTUI5UPcpVKpZ/4aWyQyCLxPUKXELzm2fosCfzEyECQQCmFGwYKbZZ+l/QULCRb1t3wlrXUaC9EO8A7q3NkoacjI9WrRn3mHpVVmupJxzs3qdGBAVQMjm3Bixj0clFIZlrAkEAym1ERxoKx93lpJS/T17Jq1LMS8jTCCvXQ2vJ0C5xiCTiVDFgMyAT7tHQ/zc+/MMgU3IZ7rt24a/LnGZP+x5gIQJALtfxyTrysRxarBImS08ieHv8TWE7ujgtgZDQuvwKNdu7jlIS0fBTCQDwKuzaTsFaYvg9W2pfNw1ptdn1MCeS8wJBAM0ULPfqGYHLYGksItL7mwvx/NPeFrev8tnfkOjeJoAiLrnh8RmCOo7iheWW9GC4NCSGXEwtRcxe2ZEj3FxI1Jk=";
		String nextBasrUrl = "https://api-gateway.walmart.com/v3/orders";
		WalmartRequestModel wm = new WalmartRequestModel(consumerId, baseUrl, privateEncodedStr,
				WalmartRequestModel.GET, nextBasrUrl);
		Map<String, String> map = new HashMap<>();
		// map.put("createdStartDate", "2017-01-28T00:00:00Z");
		// map.put("limit", "10");
		wm.setRequestParam(map);
		String response = WalmartHttpUtil.httpGet(wm);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(response);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParseWalmartOrderXMLModel parse = new ParseWalmartOrderXMLModel(doc, "http://walmart.com/mp/v3/orders");
		String nextCursor = parse.getNextCursor();
		List<Map<String, Object>> data = parse.getResult();
		wm.setNextCursor(nextCursor);
		String response1 = WalmartHttpUtil.httpGet(wm);
		// https://marketplace.walmartapis.com/v3/orders/released?limit=10&amp;hasMoreElements=true&amp;soIndex=32&amp;poIndex=10&amp;partnerId=10000008738&amp;sellerId=8515&amp;createdStartDate=2017-09-28T00:00:00Z&amp;createdEndDate=2017-09-29T01:47:46.547Z
	}
}
