package com.it.ocs.amazon.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.it.ocs.amazon.model.AmazonRequestConfig;
import com.it.ocs.synchronou.model.AmazonAccountModel;

public class AmazonHttpUtil {
	private final static Logger log = Logger.getLogger(AmazonHttpUtil.class);
	
	public static String amazonPOST(AmazonRequestConfig amazonRequest){
		String url = amazonRequest.getUrl();
		try {
			Map<String, String> parameters = amazonRequest.getBaseParameters();
			HttpPost request = new HttpPost(url);
			List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
    		for (Entry<String, String> entry : parameters.entrySet()) {
    			String key = entry.getKey() == null ? "" : entry.getKey();
    			String value = entry.getValue() == null ? "" : entry.getValue();
    			parameterList.add(new BasicNameValuePair(key, value));
    		}
    		request.setEntity(new UrlEncodedFormEntity(parameterList, HTTP.UTF_8));
    		request.addHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
			request.addHeader("X-Amazon-User-Agent", "ocs/1.0 (Language=zh_CN)");
			
	        BasicHttpParams httpParams = new BasicHttpParams();
	        httpParams.setParameter(CoreProtocolPNames.USER_AGENT, "ocs/1.0 (Language=zh_CN)");
	        HttpConnectionParams.setConnectionTimeout(httpParams, 50000);
	        HttpConnectionParams.setSoTimeout(httpParams, 50000);
	        HttpConnectionParams.setStaleCheckingEnabled(httpParams, true);
	        HttpConnectionParams.setTcpNoDelay(httpParams, true);

			HttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpResponse response = httpClient.execute(request);
			String responseBodyString = getResponsBodyAsString(response.getEntity().getContent());
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			if(status == HttpStatus.SC_OK && responseBodyString != null){
				return responseBodyString;
			}else{
				log.info("amazon获取数据失败,地址："+url);
				log.info(responseBodyString);
			}
		} catch (Exception e) {
			log.error("amazon请求失败,地址："+url,e);
			throw new RuntimeException();
		}
		return null;
	}
	
	private static String getResponsBodyAsString(InputStream input) throws IOException {
		String responsBodyString = null;
		try {
			Reader reader = new InputStreamReader(input, "UTF-8");
			StringBuilder b = new StringBuilder();
			char[] c = new char[1024];
			int len;
			while (0 < (len = reader.read(c))) {
				b.append(c, 0, len);
			}
			responsBodyString = b.toString();
		} finally {
			input.close();
		}
		return responsBodyString;
	}
	
	public static void main(String[] args) {
		AmazonAccountModel account = new AmazonAccountModel();
		account.setPlatform("US");
		account.setSellerId("AAF37WJS3P6BT");
		account.setSecretKey("HlO24NtOL4BW4jiHI+xtug61gaNZweOLadWhhPlA");
		account.setAccessKey("AKIAJKN2XEOEH4DE4NEQ");
		account.setUrl("https://mws.amazonservices.com/FulfillmentOutboundShipment/2010-10-01");//https://mws.amazonservices.com/FulfillmentOutboundShipment/2010-10-01
		account.setSiteId("ATVPDKIKX0DER");
		AmazonRequestConfig config = new AmazonRequestConfig(account, "ListAllFulfillmentOrders", "2010-10-01");
		Map<String,String> queryParam = new HashMap<>();
		queryParam.put("QueryStartDateTime", "2017-09-30T16:00:00Z");
		config.setQueryParameters(queryParam);
		System.out.println(AmazonHttpUtil.amazonPOST(config));
	}
}
