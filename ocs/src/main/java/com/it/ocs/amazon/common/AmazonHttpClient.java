package com.it.ocs.amazon.common;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;

import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.model.GetReportRequestListRequest;
import com.amazonaws.mws.model.GetReportRequestListResponse;
import com.amazonservices.mws.client.MwsUtl;
import com.it.ocs.amazon.model.AmazonRequestMode;
import com.it.ocs.amazon.order.AmazonRequest;
import com.it.ocs.amazon.order.GetAmazonClient;
import com.it.ocs.amazon.utils.AmazonUtils;

public class AmazonHttpClient {
	private String action;
	private String version;
	private String timestamp;
	private String aWSAccessKeyId;
	private static URI uri;
	private Map<String, String> headers;
	private String userAgent = "ocs/1.0 (Language=zh_CN)";

	private void addRequiredParametersToRequest(HttpPost request) {

		action = "ListOrders";
		version = "2";
		aWSAccessKeyId = "AKIAJAQWT7A5XPYZ3ANA";
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Action", "ListOrders");
		parameters.put("Version", "2013-09-01");
		parameters.put("Timestamp", AmazonUtils.getFormattedTimestamp());
		parameters.put("AWSAccessKeyId", aWSAccessKeyId);
		parameters.put("MarketplaceId.Id.1", "A2EUQ1WTGCTBG2");
		parameters.put("MWSAuthToken", "YCFXlCQ+0PRE5bs1a/b7DHAN/6w0TN3AiprhuKZ7");
		parameters.put("SellerId", "A3W1ARFCWJR0HL");
		//parameters.put("SignatureVersion", "2");
		parameters.put("LastUpdatedAfter", "2017-08-31T16:00:00Z");
		parameters.put("LastUpdatedBefore", "2017-09-03T16:00:00Z");
		String signature = AmazonUtils.signParameters(uri, version, action, parameters, aWSAccessKeyId);
		parameters.put("Signature", signature);
		//parameters.put("SignatureMethod", "HmacSHA256");
		List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : parameters.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (!(key == null || key.equals("") || value == null || value.equals(""))) {
				parameterList.add(new BasicNameValuePair(key, value));
			}
		}
		try {
			request.setEntity(new UrlEncodedFormEntity(parameterList, "UTF-8"));
		} catch (Exception e) {
			throw MwsUtl.wrap(e);
		}
	}

	private HttpPost createRequest() {
		HttpPost request = new HttpPost(uri);
		try {
			request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			request.addHeader("X-Amazon-User-Agent", userAgent);
			/*
			 * headers = Collections.unmodifiableMap(headers); for
			 * (Map.Entry<String, String> header : headers.entrySet()) {
			 * request.addHeader(header.getKey(), header.getValue()); }
			 */
			addRequiredParametersToRequest(request);
		} catch (Exception e) {
			request.releaseConnection();
			e.printStackTrace();
		}
		return request;
	}

	private void executeRequest(HttpPost request){
		try {

	        BasicHttpParams httpParams = new BasicHttpParams();
	        httpParams.setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
	        HttpConnectionParams.setConnectionTimeout(httpParams, 50000);
	        HttpConnectionParams.setSoTimeout(httpParams, 50000);
	        HttpConnectionParams.setStaleCheckingEnabled(httpParams, true);
	        HttpConnectionParams.setTcpNoDelay(httpParams, true);

			HttpClient httpClient = new DefaultHttpClient(httpParams);
			/*if (proxyHost != null && proxyPort != 0) {
				String scheme = AmazonUtils.usesHttps(uri) ? "https" : "http";
				HttpHost hostConfiguration = new HttpHost(proxyHost, 443, scheme);
				httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, hostConfiguration);
			}*/
			HttpResponse response = httpClient.execute(request);
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			String message = statusLine.getReasonPhrase();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getResponseBody(HttpResponse postResponse) {
		InputStream input = null;
		try {
			input = postResponse.getEntity().getContent();
			Reader reader = new InputStreamReader(input, AmazonUtils.DEFAULT_ENCODING);
			StringBuilder b = new StringBuilder();
			char[] c = new char[1024];
			int len;
			while (0 < (len = reader.read(c))) {
				b.append(c, 0, len);
			}
			return b.toString();
		} catch (Exception e) {
			throw MwsUtl.wrap(e);
		} finally {
			MwsUtl.close(input);
		}
	}

	/*public static void main(String[] args) {
		try {
			uri = new URI("https://mws.amazonservices.com/Orders/2013-09-01");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		AmazonHttpClient ama = new AmazonHttpClient();
		try {
			ama.executeRequest(ama.createRequest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	public static void main(String[] args) throws Exception {
		/*MwsConnection connection = new MwsConnection();
		connection.setAwsAccessKeyId("AKIAJKN2XEOEH4DE4NEQ");
        connection.setAwsSecretKeyId("HlO24NtOL4BW4jiHI+xtug61gaNZweOLadWhhPlA");
        connection.setApplicationName("ocs");//"ocs", "1.0"
        connection.setApplicationVersion("1.0");
        connection.setLibraryVersion("2017-02-22");
        connection.setConnectionTimeout(5000);
        connection.setSocketTimeout(5000);
        connection.setSignatureVersion("2");
        connection.setSignatureMethod("HmacSHA256");
        URI fullURI = URI.create("https://mws.amazonservices.com/");
        URI partialURI = new URI(fullURI.getScheme(), null, fullURI.getHost(), fullURI.getPort(), null, null, null);
        connection.setEndpoint(partialURI);
        RequestModel request = new RequestModel();
        request.setSellerId("AAF37WJS3P6BT");
        connection.call(new AmazonRequestType("GetReportRequestList",HashMap.class , "?GetReportRequestList/2013-09-01"), request);*/
        
        
       /* AmazonRequestMode requestMode = new AmazonRequestMode();
		requestMode.setServiceURL("https://mws.amazonservices.com/");
		requestMode.setAccessKey("AKIAJKN2XEOEH4DE4NEQ");//"AKIAIQJPPHYX7ZMKPTNQ"
		requestMode.setSecretKey("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");//"oreVInCN3zs+gEdjUmxuVXRIAre+EQf6aLQh6OFS"
		requestMode.setSellerId("AAF37WJS3P6BT");//AV7KSH7XB8RNM
        List list = new ArrayList();
        list.add("ATVPDKIKX0DER");
		requestMode.setMarketplaceId(list);
        
        MarketplaceWebServiceOrdersClient client = GetAmazonClient.createClient(requestMode);
        ListOrdersRequest request1 = AmazonRequest.createOrderRequest(requestMode,1);
        // Make the call.
        String responseXML = AmazonClientRun.invokeListOrders(client, request1);*/
		AmazonRequestMode requestMode = new AmazonRequestMode();
		requestMode.setServiceURL("https://mws.amazonservices.com/");
		requestMode.setAccessKey("AKIAJKN2XEOEH4DE4NEQ");//"AKIAIQJPPHYX7ZMKPTNQ"
		requestMode.setSecretKey("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");//"oreVInCN3zs+gEdjUmxuVXRIAre+EQf6aLQh6OFS"
		requestMode.setSellerId("AAF37WJS3P6BT");//AV7KSH7XB8RNM
        List list = new ArrayList();
        list.add("ATVPDKIKX0DER");
		requestMode.setMarketplaceId(list);
		MarketplaceWebServiceClient client = GetAmazonClient.createReportClient(requestMode);
		GetReportRequestListRequest request = AmazonRequest.createReportRequestListRequest(requestMode);
		GetReportRequestListResponse response = client.getReportRequestList(request);

	}
	
	

}
