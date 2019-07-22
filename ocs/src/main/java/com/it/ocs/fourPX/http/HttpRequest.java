package com.it.ocs.fourPX.http;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.it.ocs.fourPX.vo.FourPXRequestVO;
import com.it.ocs.fourPX.vo.InventoryRequestVO;

public class HttpRequest {
	private final static Logger logger = Logger.getLogger(HttpRequest.class);
	public static String post(FourPXRequestVO fv) {
		String url = fv.getUrl();
		try {
			// 创建httpclient工具对象
			DefaultHttpClient client = new DefaultHttpClient();
			// 创建post请求方法
			HttpPost request = new HttpPost(url);
			request.addHeader("Content-Type","application/json;charset=UTF-8");
			request.addHeader("Accept","application/json;charset=UTF-8");
			if (StringUtils.isNotBlank(fv.getContent())) {
				StringEntity entity = new StringEntity(fv.getContent(), Charset.forName("UTF-8"));
	            entity.setContentEncoding("UTF-8");
	            request.setEntity(entity);
			}
			HttpResponse response = client.execute(request);
			String strResult = EntityUtils.toString(response.getEntity());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return strResult;
			} else {
				logger.error("请求4px错误:" + strResult);
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		}
		return null;
	}
	public static void main(String[] args) {
		FourPXRequestVO request = new FourPXRequestVO("http://apisandbox.4px.com/api/service/woms/item/getInventory");
		InventoryRequestVO in = new InventoryRequestVO();
		String skus[] = {"3200001"};
		in.setLstSku(skus);
		in.setWarehouseCode("USNY");//USLA
		request.setContent(in.toJSONString());
		HttpRequest.post(request);
	}
}
