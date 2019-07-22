package com.it.ocs.amazon.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
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

/**
 * amazon 签名服务类
 * @author chenyong
 *
 */
public class AmazonSignature {
	private final static Logger log = Logger.getLogger(AmazonSignature.class);
	public static final String CHARACTER_ENCODING = "UTF-8";
	public final static String ALGORITHM = "HmacSHA256";

	/**
	 * 生成签名字符串
	 * @param parameters
	 * @param awsSecretAccessKey
	 * @param url
	 * @return
	 * @throws SignatureException
	 */
	public static String signParameters(Map<String, String> parameters, String awsSecretAccessKey,String url) throws SignatureException {
		String stringToSign  = calculateStringToSignV2(parameters,url);
		return calculateRFC2104HMAC(stringToSign,awsSecretAccessKey);
	}


	
	 /**
	 * 签名
     * Computes RFC 2104-compliant HMAC signature.
     * * @param data
     * The signed data.
     * @param key
     * The signing key.
     * @return
     * The Base64-encoded RFC 2104-compliant HMAC signature.
     * @throws
     * java.security.SignatureException when signature generation fails
     */
    public static String calculateRFC2104HMAC(String data, String key)throws java.security.SignatureException{
        String result;
        try {
            // Get an hmac_sha256 key from the raw key bytes.
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF8"), ALGORITHM);
            // Get an hmac_sha256 Mac instance and initialize with the signing key.
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(signingKey);
            // Compute the hmac on input data bytes.
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF8"));
            // Base64-encode the hmac by using the utility in the SDK
            result = new String(Base64.encodeBase64(rawHmac),"UTF8");//BinaryUtils.toBase64(rawHmac);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
	
    /**
     * 签名前参数格式化
     * @param parameters
     * @param url
     * @return
     */
	private static String calculateStringToSignV2(Map<String, String> parameters,String url) {
		StringBuilder data = new StringBuilder();
		data.append("POST");
		data.append("\n");
		URI endpoint = null;
		try {
			endpoint = new URI(url);
		} catch (URISyntaxException ex) {
			log.error("URI Syntax Exception,解析url地址失败", ex);
			throw new RuntimeException();
		}
		data.append(endpoint.getHost());
		data.append("\n");
		String uri = endpoint.getPath();
		if (uri == null || uri.length() == 0) {
			uri = "/";
		}
		data.append(uri);
		data.append("\n");
		Map<String, String> sorted = new TreeMap<String, String>();
		sorted.putAll(parameters);
		Iterator<Map.Entry<String, String>> pairs = sorted.entrySet().iterator();
		while (pairs.hasNext()) {
			Map.Entry<String, String> pair = pairs.next();
			String key = pair.getKey();
			data.append(key);
			data.append("=");
			String value = pair.getValue();
			data.append(urlEncode(value));
			if (pairs.hasNext()) {
				data.append("&");
			}
		}
		log.info(data.toString());
		return data.toString();
	}
	/**
	 * url编码
	 * @param rawValue
	 * @return
	 */
	private static String urlEncode(String rawValue) {
		String value = rawValue == null ? "" : rawValue;
		String encoded = null;
		try {
			encoded = URLEncoder.encode(value, CHARACTER_ENCODING).replace("+", "%20").replace("*", "%2A").replace("%7E","~");
		} catch (UnsupportedEncodingException ex) {
			log.error("Unsupported Encoding Exception", ex);
			throw new RuntimeException(ex);
		}
		return encoded;
	}
}