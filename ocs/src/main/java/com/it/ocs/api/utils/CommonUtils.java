package com.it.ocs.api.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.it.ocs.api.constant.WarehouseConstant;
import com.it.ocs.api.dao.IWmsProviderTokenDao;
import com.it.ocs.synchronou.util.HTTPUtils;
import com.it.ocs.synchronou.util.JsonConvertUtil;

/**
* @ClassName: CommonUtils 
* @Description: 一些公共方法工具类 
* @author wgc 
* @date 2018年4月10日 上午11:25:37 
*
 */
@Component
public class CommonUtils {
	
	private static final Logger logger = Logger.getLogger(CommonUtils.class);
	
	@Autowired
	private IWmsProviderTokenDao wmsProviderTokenDao;
	
	/**
	* @Title: validateRequestToken 
	* @Description: 校验服务商请求key
	* @param @param token
	* @param @return    设定文件 
	* @return Map<String,String>    返回类型 
	* @throws
	 */
	public Map<String,Object> validateRequestToken(String token){
        Map<String,Object> responseMap = new HashMap<String,Object>();
        if(token == null){
            responseMap.put("resultFlag", "false");
            responseMap.put("error", "the parameter 'token' is required");
            responseMap.put("resultCode", "-10");
            
            return responseMap;
        }
        if(!token.equals(String.valueOf(SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_WMS_TOKEN)))) {
            responseMap.put("resultFlag", "false");
            responseMap.put("error", "the token is invalid");
            responseMap.put("resultCode", "-11");
            return responseMap;
        }
        responseMap.put("resultFlag", "true");
        responseMap.put("success", "true");
        responseMap.put("resultCode", "10");
        return responseMap;
	}
	
	/**
	* @Title: getDeWmsToken 
	* @Description: 获取德国仓wms请求token
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getDeWmsToken(){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userid", SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DEWMSAPPKEY));
		paramMap.put("password", SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DEWMSAPPSECRET));
		String url = SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_WMS_URL) + WarehouseConstant.CHECKANDGETUSERINFO;
		String result = HTTPUtils.httpPostByJson(url, paramMap);
		
		@SuppressWarnings("unchecked")
		Map<String,Object> resultMap = (Map<String, Object>) JsonConvertUtil.json2Obj(result, Map.class);
		if(null != resultMap){
			//System.out.println("拿到的token是："+resultMap.get("token"));
			logger.info("new token: " + resultMap.get("token"));
			return String.valueOf(resultMap.get("token"));
		}
		return "";
	}
	
	/**
	* @Title: httpPostJson2DeWms 
	* @Description: 请求德国WMS方法(主要含token过期重新连接)
	* @param @param url
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public Map<String,Object> httpPostJson2DeWms(String url, Map<String,Object> paramMap){
		logger.info("01-request-wms: " + JsonConvertUtil.obj2Json(paramMap));
		
		String resultStr = HTTPUtils.httpPostByJson(url, paramMap);
		
		logger.info("01-wms-response: " + resultStr);
		
		@SuppressWarnings("unchecked")
		Map<String,Object> resultMap1 = (Map<String, Object>) JsonConvertUtil.json2Obj(resultStr,Map.class);
		if(2 == Integer.parseInt(resultMap1.get("resultCode")+"")){//2代表token过期,需要重新获取
			String tempToken = this.getDeWmsToken();
			
			logger.info("02-get-wms-token: " + tempToken);
			
			Map<String,Object> identity = CommonUtils.newIdentity();
			identity.put("token", tempToken);
			wmsProviderTokenDao.updateProviderTokenByMap(identity);
			
			paramMap.put("token", tempToken);
			
			logger.info("03-request-wms:" + JsonConvertUtil.obj2Json(paramMap));
			
			resultStr = HTTPUtils.httpPostByJson(url, paramMap);
			
			logger.info("03-wms-response: " + resultStr);
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> resultMap2 = (Map<String, Object>) JsonConvertUtil.json2Obj(resultStr, Map.class);
		return resultMap2;
	}
	
	/**
	* @Title: getDeWmsSkuQty 
	* @Description: 根据wms的sku获取其库存
	* @param @param sku
	* @param @param token
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public Map<String,Object> getDeWmsSkuQty(String sku,String token){
		Map<String,Object> searchMap = new HashMap<String,Object>();
		List<Map<String,Object>> itemList = new ArrayList<Map<String,Object>>();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Map<String,Object> pageInfofoMap = new HashMap<String,Object>();
		Map<String,Object> queryparamstersMap = new HashMap<String,Object>();
		Map<String,Object> totalMap = new HashMap<String,Object>();
		
		String url = SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_WMS_URL) + WarehouseConstant.GETINVENTORYLISTBYQUERY;
		
		Map<String,Object> mapsku  = new HashMap<String,Object>();
		mapsku.put("Field", "SKU_ID");
		mapsku.put("Method", "Equal");
		mapsku.put("Value", sku);
		mapsku.put("Prefix", "");
		mapsku.put("OrGroup", "");
		itemList.add(mapsku);

		Map<String,Object> maploc  = new HashMap<String,Object>();
		maploc.put("Field", "LOC");
		maploc.put("Method", "NotEqual");
		maploc.put("Value", "AMAZON");
		itemList.add(maploc);
		
		pageInfofoMap.put("SortField", "SKU_ID DESC");
		pageInfofoMap.put("CurrentPage", 1);
		pageInfofoMap.put("PageSize", 20);
		pageInfofoMap.put("IsPaging", false);
		
		modelMap.put("Items", itemList);
		searchMap.put("QueryModel", modelMap);
		searchMap.put("PageInfo", pageInfofoMap);
		
		queryparamstersMap.put("Search", searchMap);
		queryparamstersMap.put("token", token);
		
		totalMap.put("queryparamsters", queryparamstersMap);
		totalMap.put("token", token);
		totalMap.put("whgid", SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DESTORECODE));
		
		return this.httpPostJson2DeWms(url,totalMap);
		
	}
	
	/**
	 * 创建一个身份
	 * @return 
	 * {@link java.util.Map} (key={@link java.lang.String}; value={@link java.lang.Object})</br>
	 * 必定包含<code>key=providerName</code>: 德国WMS仓库代码</br>
	 * 必定包含<code>key=appKey</code>: 德国WMS仓库的用户名</br>
	 * 必定包含<code>key=appSecret</code>: 德国WMS仓库的用户密码
	 */
	public static Map<String, Object> newIdentity() {
		Map<String, Object> identity = new HashMap<String, Object>();
		identity.put("providerName", SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DESTORECODE));
		identity.put("appKey", SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DEWMSAPPKEY));
		identity.put("appSecret", SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_DEWMSAPPSECRET));
		return identity;
	}

	/**
	 * 处理指定的Map参数对象：将内部以空格分割而支持多个值字符串转换成List
	 * @param paramTarget
	 * @param targetKey
	 */
	public static void handleParamMutli2list(Map<String, Object> paramTarget, String targetKey) {
		String value = (String) paramTarget.get(targetKey);
		if(StringUtils.isNotBlank(value)) {
			List<String> list = new ArrayList<String>();
			for (String str : value.trim().replaceAll("\\s+", " ").split(" ")) {
				list.add(str);
			}
			paramTarget.put(targetKey, null);
			paramTarget.put(targetKey + "s", list);
		} else {
			paramTarget.remove(targetKey);
		}
	}
	
	public static String status2text(String status) {
		if("0".equals(status)) {
			return "待推送";
		} else if("1".equals(status)) {
			return "已推送";
		} else if("2".equals(status)) {
			return "已反馈";
		} else if("3".equals(status)) {
			return "异常单";
		} else if("4".equals(status)) {
			return "已取消";
		} else {
			return "";
		}
	}
	
	public static boolean validIsSendWms(String isSendWms) {
		return WarehouseConstant.IS_SEND_WMS_NO.equals(isSendWms) || WarehouseConstant.IS_SEND_WMS_YES.equals(isSendWms)
				|| WarehouseConstant.IS_SEND_WMS_FEEDBACK.equals(isSendWms) || WarehouseConstant.IS_SEND_WMS_FAILED.equals(isSendWms);
	}
}
