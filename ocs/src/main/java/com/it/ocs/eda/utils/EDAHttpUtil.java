package com.it.ocs.eda.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.it.ocs.eda.model.EDARequestModel;


public class EDAHttpUtil {
	private final static Logger logger = Logger.getLogger(EDAHttpUtil.class);
	
	public static String httpPost(EDARequestModel requestModel){
		String url = requestModel.getUrl();
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送post请求
            HttpPost request = new HttpPost(url);
            StringEntity entity = new StringEntity(requestModel.getRequestData(), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            request.setEntity(entity);
            logger.info("[ eda request entity ] - " + EntityUtils.toString(request.getEntity()));
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                logger.info("[ eda response entity ] - " + strResult);
                return strResult;
            } else {
            	 String strResult = EntityUtils.toString(response.getEntity());
            	 logger.info("请求EDA错误:"+strResult);
            }
        } catch (IOException e) {
            logger.error("请求提交失败:" + url, e);
        }
		return null;
	}
}
