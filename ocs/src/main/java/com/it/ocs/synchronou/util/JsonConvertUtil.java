package com.it.ocs.synchronou.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;

/**
 * json转换工具类
 * @author wengguochao 
 * 2018-04-04
 *
 */
public class JsonConvertUtil {
	/**
	 * 将对象转换成json字符串格式
	 * @param obj
	 * @return
	 * @throws Exception
	 */
    public static String obj2Json(Object obj) {
    	String json = null;
    	ObjectMapper mapper = new ObjectMapper();
    	try{
			json = mapper.writeValueAsString(obj);
		}catch (Exception e){
		}
    	 return json;
    }


	/**
	 * 将对象转换成json字符串格式
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String getJsonString(Object obj) {
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		try{
			json = mapper.writeValueAsString(obj);
		}catch (Exception e){
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将对象转换成json字符串格式
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static void writeValue(HttpServletResponse resp, Object obj) {
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(resp.getOutputStream(),obj);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

    /**
     * 将json字符串转换成java对象
     * @param requestCode
     * @param tr
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static Object json2Obj(String requestCode, TypeReference tr) {
    	Object ret = null;
    	ObjectMapper mapper = new ObjectMapper();
    	try{
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ret =  mapper.readValue(requestCode, tr);
		}catch (Exception e){e.printStackTrace();}
    	return ret;
    }

    /**
     * 将json字符串转换成java对象
     * @param requestCode
     * @param clz
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static Object json2Obj(String requestCode, Class clz) {
    	Object ret = null;
    	ObjectMapper mapper = new ObjectMapper();
    	try{
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ret =  mapper.readValue(requestCode, clz);
		}catch (Exception e){e.printStackTrace();}
    	return ret;
    }

}
