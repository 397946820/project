package com.it.ocs.synchronou.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

import com.google.common.collect.Lists;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.model.ParseLightOrderItemXMLModel;
import com.it.ocs.synchronou.model.ParseLightOrderXMLNode;
import com.it.ocs.synchronou.model.ParseLightOrderXMLNodeOne;
import net.sf.json.JSONArray;

public class HTTPUtils {
	private final static Logger log = Logger.getLogger(HTTPUtils.class);
	public static String httpPostByXML(String url, String requestXML) {
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		String xml = "";
		int count = 0;
		try {
			//采用绕过验证的方式处理https请求  
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            trustStore.load(null, null);  
            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);  
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
  
            HttpParams params = new BasicHttpParams();  
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);  
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);   
  
            SchemeRegistry registry = new SchemeRegistry();  
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
            registry.register(new Scheme("https", sf, 443));  
  
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);  
            
            //定义HttpClient
         	//HttpClient httpClient = new DefaultHttpClient();
            httpClient =  new DefaultHttpClient(ccm, params);  
		      
			// 定义HttpPost请求
			httpPost = new HttpPost(url);
			HttpEntity requestEntity = new StringEntity(requestXML, "UTF-8");
			httpPost.setEntity(requestEntity);
			httpPost.setHeader("Content-Type","text/xml; charset=UTF-8"); 
			
			HttpParams httpParams = httpClient.getParams();
			// 设置Http协议的版本
			httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			xml = excuteRequest(httpPost, httpClient);
			while(count<11&&null == xml){
				xml = excuteRequest(httpPost, httpClient);
				count++;
			}
		} catch (Exception e) {
			throw new RuntimeException("Http初始化异常", e);
		}finally{
			// 释放请求的连接
			if (httpPost != null) {
				httpPost.abort();
			}
		}
		if (null == xml){
			throw new RuntimeException("Http请求异常");
		}
		return xml;
	}

	private static String excuteRequest(HttpPost httpPost, HttpClient httpClient) {
		try{
			HttpResponse httpResponse = httpClient.execute(httpPost);
			// 获取响应实体
			HttpEntity responsetEntity = httpResponse.getEntity();
			InputStream inputStream = responsetEntity.getContent();
	
			StringBuilder reponseXml = new StringBuilder();
			byte[] b = new byte[2048];
			int length = 0;
			while ((length = inputStream.read(b)) != -1) {
				reponseXml.append(new String(b, 0, length));
			}
			//System.out.println(reponseXml);
			return reponseXml.toString();
		}catch(Exception e){
			log.info("Http请求异常",e);
		}
		return null;
	}
	
	
	 private static class MySSLSocketFactory extends SSLSocketFactory {  
	        SSLContext sslContext = SSLContext.getInstance("TLS"); ///TLS 
	  
	        public MySSLSocketFactory(KeyStore truststore)  
	                throws NoSuchAlgorithmException, KeyManagementException,  
	                KeyStoreException, UnrecoverableKeyException {  
	            super(truststore);  
	  
	            TrustManager tm = new X509TrustManager() {  
	                public void checkClientTrusted(X509Certificate[] chain, String authType)  
	                        throws CertificateException {  
	                }  
	  
	                public void checkServerTrusted(X509Certificate[] chain, String authType)  
	                        throws CertificateException {  
	                }  
	  
	                public X509Certificate[] getAcceptedIssuers() {  
	                    return null;  
	                }  
	            };  
	  
	            sslContext.init(null, new TrustManager[] { tm }, null);  
	        }  
	  
	        @Override  
	        public Socket createSocket(Socket socket, String host, int port, boolean autoClose)  
	                throws IOException, UnknownHostException {  
	            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);  
	        }  
	  
	        @Override  
	        public Socket createSocket() throws IOException {  
	            return sslContext.getSocketFactory().createSocket();  
	        }  
    }  
	
	public static String httpGet(String url){
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            request.addHeader("token", "2779757536B9CC2ABAADB8B13A4817C9");    
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
               String strResult = EntityUtils.toString(response.getEntity());
               System.out.println(strResult);
               return strResult;
            } else {
            	 String strResult = EntityUtils.toString(response.getEntity());
            	 log.error("请求light错误:"+strResult);
            	 throw new RuntimeException();
            }
        } catch (IOException e) {
            log.error("get请求提交失败:" + url, e);
            throw new RuntimeException();
        }
	}
	
	public static <T> String httpPostByJson(String url,T bodyMap) {
		try{
			HttpPost httpPost = new HttpPost(url);
			
			HttpParams params = new BasicHttpParams();  
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);  
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);   
	  
	        SchemeRegistry registry = new SchemeRegistry();
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            trustStore.load(null, null);  
	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);  
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
	        registry.register(new Scheme("https", sf, 443));  
	  
	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);  
	            
	        HttpClient httpClient =  new DefaultHttpClient(ccm, params);  
			
			httpPost.addHeader(new BasicHeader("X-EBAY-API-SITEID", "0"));
			httpPost.addHeader(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL", "967"));
			httpPost.addHeader(new BasicHeader("X-EBAY-API-CALL-NAME", "CompleteSale"));
			httpPost.addHeader(new BasicHeader("content-type", "application/json"));
			String bodyJson = JsonConvertUtil.getJsonString(bodyMap);
			httpPost.setEntity(new ByteArrayEntity(bodyJson.getBytes("UTF-8")));
			HttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			String ret = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			return ret;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		String requestXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:Magento\">"+
							"  <soapenv:Header/>" +
							" <soapenv:Body>" +
							"      <urn:login soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
							"         <username xsi:type=\"xsd:string\">java_api</username>" +
							"         <apiKey xsi:type=\"xsd:string\">6uM58MZU7hcpFle1</apiKey>" +
							"      </urn:login>" +
							"   </soapenv:Body>" +
							"</soapenv:Envelope>";
		String url = "http://192.168.31.231/us_rebuild/index.php/api/soap/index/";//"https://www.lightingever.com/index.php/api/soap/index/";//"http://192.168.31.231/us_rebuild/api/soap/?wsdl";
		String responseString = HTTPUtils.httpPostByXML(url, requestXML);
		try {
			Document doc = DocumentHelper.parseText(responseString);
			Map map = new HashMap();  
	        map.put("design","");  
	        XPath x = doc.createXPath("//design:loginReturn");  
	        x.setNamespaceURIs(map); 
	        Node node = x.selectSingleNode(doc);
	        String ack = node.getText();
	        
	        /*requestXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:Magento\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
	        "<SOAP-ENV:Body>" +
	       "<ns1:call>" +
	                "<sessionId xsi:type=\"xsd:string\">"+ack+"</sessionId>" +
	                "<resourcePath xsi:type=\"xsd:string\">sales_order.list</resourcePath>" +
	                "<args SOAP-ENC:arrayType=\"ns2:Map[1]\" xsi:type=\"SOAP-ENC:Array\">" +
	                  "<item xsi:type=\"ns2:Map\">" +
	                "<item>" +
	                   "<key xsi:type=\"xsd:string\">updated_at</key>" + 
	                  "<value xsi:type=\"ns2:Map\">" +
	                      "<item>" +
	                       "<key xsi:type=\"xsd:string\">from</key>" +
	                       "<value xsi:type=\"xsd:string\">2017-05-01T00:00:00Z</value>" + 
	                       "</item>" +
	                      "<item>" +
	                       "<key xsi:type=\"xsd:string\">to</key> " +
	                       "<value xsi:type=\"xsd:string\">2017-08-01T00:00:00Z</value>" + 
	                       "</item>" +
	                   "</value>" +
	                 "</item>" +
	              "</item>" +
	                "</args>" +
	             "</ns1:call>" +
	          "</SOAP-ENV:Body>" +
	       "</SOAP-ENV:Envelope>";
   	        responseString = HTTPUtils.httpPostByXML(url, requestXML);
   	        doc = DocumentHelper.parseText(responseString);
   			ParseLightOrderXMLNode parse = new ParseLightOrderXMLNode(doc, "");
   			List<Map<String,Object>> parseResult = parse.getResult();
   			List<String> list = Lists.newArrayList();
   			String a = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:Magento\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
					 "<SOAP-ENV:Body>" +
					 "	<ns1:call>" +
					 "		<sessionId xsi:type=\"xsd:string\">" + ack +"</sessionId>" +
					 "		<resourcePath xsi:type=\"xsd:string\">sales_order.info</resourcePath>" +
					 "		<args xsi:type=\"xsd:string\">${orderId}</args>" +
					 "	</ns1:call>" +
					 "</SOAP-ENV:Body>" +
					 "</SOAP-ENV:Envelope>";
   			CollectionUtil.each(parseResult, new IAction<Map<String, Object>>() {
   				@Override
   				public void excute(Map<String, Object> obj) {
   					try {
						String string = a.replace("${orderId}", obj.get("order_id")+"");
						String responseString1 = HTTPUtils.httpPostByXML(url, string);
						Document doc = DocumentHelper.parseText(responseString1);
						ParseLightOrderItemXMLModel parseItem = new ParseLightOrderItemXMLModel(doc, "");
						List<Map<String, Object>> parseItemResult = parseItem.getResult();
						if(parseItemResult.size() == 1 && new Double(parseItemResult.get(0).get("qty_ordered").toString()) > 1) {
							list.add(obj.get("order_id") + "--" + parseItemResult.get(0).get("light_item_id"));
						}
					} catch (Exception e) {
						
					}
   				}
   			});
   			System.out.println(list);*/ //100088145  99386
	        /*requestXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:Magento\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
					 "<SOAP-ENV:Body>" +
					 "	<ns1:call>" +
					 "		<sessionId xsi:type=\"xsd:string\">" + ack +"</sessionId>" +
					 "		<resourcePath xsi:type=\"xsd:string\">order_creditmemo.create</resourcePath>" +
					 "<args SOAP-ENC:arrayType=\"xsd:ur-type[3]\" xsi:type=\"SOAP-ENC:Array\"> " +
				     "   <item xsi:type=\"xsd:string\">100088132</item>                 "+
					"	<item xsi:type=\"ns2:Map\">                                    "+
					"	  <item>                                                     "+
				     "       <key xsi:type=\"xsd:string\">qtys</key>                    "+
				     "       <value xsi:type=\"ns2:Map\">                               "+
				     "         <item>                                                 "+
				     "           <key xsi:type=\"xsd:int\">99373</key>                  "+
				     "           <value xsi:type=\"xsd:int\">0</value>                  "+
				     "         </item>                                                "+
				     "       </value>                                                 "+
				     "     </item>                                                    "+
					"	  <item>                                                     "+
					"		<key xsi:type=\"xsd:string\">adjustment_positive</key>     "+
					"		<value xsi:type=\"xsd:double\">5</value>                   "+
					"	  </item>                                                    "+
				     "   </item>                                                      "+
					 " </args> " +
					 "</ns1:call>" +
					 "</SOAP-ENV:Body>" +
					 "</SOAP-ENV:Envelope>";
			responseString = HTTPUtils.httpPostByXML(url, requestXML);
			doc = DocumentHelper.parseText(responseString);
			System.out.println(doc.asXML());*/
   			/*requestXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:Magento\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
					 "<SOAP-ENV:Body>" +
					 "	<ns1:call>" +
					 "		<sessionId xsi:type=\"xsd:string\">" + ack +"</sessionId>" +
					 "		<resourcePath xsi:type=\"xsd:string\">sales_order.info</resourcePath>" +
					 "		<args xsi:type=\"xsd:string\">100088132</args>" +
					 "	</ns1:call>" +
					 "</SOAP-ENV:Body>" +
					 "</SOAP-ENV:Envelope>";
			responseString = HTTPUtils.httpPostByXML(url, requestXML);
			doc = DocumentHelper.parseText(responseString);
			ParseLightOrderItemXMLModel parseItem = new ParseLightOrderItemXMLModel(doc, "");
			List<Map<String, Object>> parseItemResult = parseItem.getResult();
			System.out.println(parseItemResult);
			
			ParseLightOrderXMLNodeOne parse = new ParseLightOrderXMLNodeOne(doc, "");
			List<Map<String, Object>> parseResult = parse.getResult();
			Map<String, Object> parseOrderResult = parseResult.get(0);
			System.out.println(parseOrderResult);*/
			/*requestXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:Magento\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
					"<SOAP-ENV:Body>" +
					"	<ns1:call>" +
					"		<sessionId xsi:type=\"xsd:string\">" + ack +"</sessionId>" +
					"		<resourcePath xsi:type=\"xsd:string\">sales_order.cancel</resourcePath>" +
					"		<args xsi:type=\"xsd:string\">100088085</args>" +
					"	</ns1:call>" +
					"</SOAP-ENV:Body>" +
					"</SOAP-ENV:Envelope>";
	        responseString = HTTPUtils.httpPostByXML(url, requestXML);
			doc = DocumentHelper.parseText(responseString);
			System.out.println(doc.asXML());
   			String a = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:Magento\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
					 "<SOAP-ENV:Body>" +
					 "	<ns1:call>" +
					 "		<sessionId xsi:type=\"xsd:string\">" + ack +"</sessionId>" +
					 "		<resourcePath xsi:type=\"xsd:string\">sales_order.info</resourcePath>" +
					 "		<args xsi:type=\"xsd:string\">${orderId}</args>" +
					 "	</ns1:call>" +
					 "</SOAP-ENV:Body>" +
					 "</SOAP-ENV:Envelope>";
   			*/
	        
	        /*requestXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:Magento\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
						 "<SOAP-ENV:Body>" + 
						 	"<ns1:call>" +
						         "<sessionId xsi:type=\"xsd:string\">" + ack +"</sessionId>" +
						         "<resourcePath xsi:type=\"xsd:string\">order_shipment.create</resourcePath>" +
						         "<args xsi:type=\"xsd:string\">100088098</args>" +
						     "</ns1:call>" +
						   "</SOAP-ENV:Body>" +
						 "</SOAP-ENV:Envelope>";
	        responseString = HTTPUtils.httpPostByXML(url, requestXML);
			doc = DocumentHelper.parseText(responseString);
	        Element element = (Element) ((Element) ((Element) (doc.getRootElement().elements().get(0))).elements()
					.get(0)).elements().get(0);
			String shipmentIncrementId = element.getStringValue();
			requestXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:Magento\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns2=\"http://xml.apache.org/xml-soap\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
					 "<SOAP-ENV:Body>" + 
					"<ns1:call>" +
					         "<sessionId xsi:type=\"xsd:string\">" + ack +"</sessionId>" +
					         "<resourcePath xsi:type=\"xsd:string\">sales_order_shipment.addTrack</resourcePath>" +
					         "<args xsi:type=\"ns2:Map\">" +
					         "<item>"
					         + "<key xsi:type=\"xsd:string\">shipmentIncrementId</key>"
					         + "<value xsi:type=\"xsd:string\">100068186</value>"
					         + "</item>" +
					         "<item>"
					         + "<key xsi:type=\"xsd:string\">carrier</key>"
					         + "<value xsi:type=\"xsd:string\">fedex</value>"
					         + "</item>" +
					         "<item>"
					         + "<key xsi:type=\"xsd:string\">title</key>"
					         + "<value xsi:type=\"xsd:string\">tracking title</value>"
					         + "</item>" +
					         "<item>"
					         + "<key xsi:type=\"xsd:string\">trackNumber</key>"
					         + "<value xsi:type=\"xsd:string\">123123</value>"
					         + "</item>" +
					         "</args>" +
					      "</ns1:call>" +
					   "</SOAP-ENV:Body>" +
					"</SOAP-ENV:Envelope>";
			responseString = HTTPUtils.httpPostByXML(url, requestXML);
			doc = DocumentHelper.parseText(responseString);//69744
			System.out.println(doc.asXML());
			Element element = (Element) ((Element) ((Element) (doc.getRootElement().elements().get(0))).elements()
					.get(0)).elements().get(0);
			String name = element.getName();
			String string = element.getStringValue();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
