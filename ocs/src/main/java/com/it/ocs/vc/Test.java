//package com.it.ocs.vc;
//
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//import java.util.TimeZone;
//
//import org.jsoup.Connection;
//import org.jsoup.Connection.Response;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import com.google.common.base.Joiner;
//
//public class Test {
//
//	public static void main(String[] args) {
//		final String[] agents = new String[] {
//				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1",
//				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0",
//				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
//				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; Tablet PC 2.0; .NET4.0E)",
//				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; InfoPath.3)",
//				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB7.0)",
//				"Mozilla/5.0 (Windows; U; Windows NT 6.1; ) AppleWebKit/534.12 (KHTML, like Gecko) Maxthon/3.0 Safari/534.12",
//				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)",
//				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E; SE 2.X MetaSr 1.0)",
//				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.33 Safari/534.3 SE 2.X MetaSr 1.0",
//				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)",
//				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1 QQBrowser/6.9.11079.201",
//				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E) QQBrowser/6.9.11079.201",
//				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36" };
//		Connection conn = Jsoup.connect("https://www.amazon.co.jp/dp/B01LGXHBT2");
//		// Connection conn = Jsoup.connect("https://www.amazon.com/dp/B00ZZYPV8W");
//		// https://www.amazon.co.jp/dp/B01LGX8EJS
//		Response response = null;
//		try {
//			response = conn.header("User-Agent", agents[new Random().nextInt(agents.length)])
//					.header("Cache-Control", "no-cache")
//					.header("accept",
//							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
//					.header("accept-encoding", "gzip, deflate, br").header("accept-language", "zh-CN,zh;q=0.9")
//					.header("Proxy-Authorization", getAuthHeader()).proxy("s5.proxy.mayidaili.com", 8123, null)
//					.validateTLSCertificates(false).timeout(1000 * 20).execute();
//			Document doc = response.parse();
//			System.out.println(doc);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public static String getAuthHeader() {
//
//		// 创建参数表
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("app_key", "19441445");
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 使用中国时间，以免时区不同导致认证错误
//		paramMap.put("timestamp", format.format(new Date()));
//
//		// 对参数名进行排序
//		String[] keyArray = paramMap.keySet().toArray(new String[0]);
//		Arrays.sort(keyArray);
//
//		// 拼接有序的参数名-值串
//		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("1658bfdfcf4fee812b86f63185cadeca");
//		for (String key : keyArray) {
//			stringBuilder.append(key).append(paramMap.get(key));
//		}
//
//		stringBuilder.append("1658bfdfcf4fee812b86f63185cadeca");
//		String codes = stringBuilder.toString();
//
//		// MD5编码并转为大写， 这里使用的是Apache codec
//		String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(codes).toUpperCase();
//
//		paramMap.put("sign", sign);
//
//		// 拼装请求头Proxy-Authorization的值，这里使用 guava 进行map的拼接
//		String authHeader = "MYH-AUTH-MD5 " + Joiner.on('&').withKeyValueSeparator("=").join(paramMap);
//
//		return authHeader;
//	}
//}
