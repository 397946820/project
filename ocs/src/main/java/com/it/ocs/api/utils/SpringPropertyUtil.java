package com.it.ocs.api.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
* @ClassName: SpringPropertyUtil 
* @Description: 获取配置文件工具类
* @author wgc
* @date 2018年4月8日 上午10:10:49 
*
 */
public class SpringPropertyUtil extends PropertyPlaceholderConfigurer {
	
	private static final String ENV = "env";

	/**
	 * 用于获取德国仓WMS接口地址的NAME
	 */
	public static final String NAME_WMS_URL = "wms_url";

	/**
	 * 用于获取德国仓WMS身份认证信息的NAME
	 */
	public static final String NAME_WMS_TOKEN = "wms_token";
	
	/**
	 * 用于获取德国仓WMS货主代码的NAME
	 */
	public static final String NAME_DEOWNERCODE = "de.ownercode";

	/**
	 * 用于获取德国仓WMS仓库代码的NAME
	 */
	public static final String NAME_DESTORECODE = "de.storecode";

	/**
	 * 用于获取德国仓WMS仓库用户名的NAME
	 */
	public static final String NAME_DEWMSAPPKEY = "de.wmsappkey";

	/**
	 * 用于获取德国仓WMS仓库用户密码的NAME
	 */
	public static final String NAME_DEWMSAPPSECRET = "de.wmsappsecret";
	
	private static Map<String,Object> ctxPropertiesMap;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		ctxPropertiesMap = new HashMap<String,Object>();
		
		for (Iterator localIterator = props.keySet().iterator();localIterator.hasNext();) {
			Object key = localIterator.next();
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
		super.processProperties(beanFactoryToProcess, props);
	}
	
	/**
	 * @deprecated 已过时，请使用同类的<code>getValue</code>方法替代
	 * @param name
	 * @return
	 */
	public static Object getContextProperty(String name){
		return ctxPropertiesMap.get(name);
	}

	public static Map<String, Object> getCtxPropertiesMap() {
		return ctxPropertiesMap;
	}

	public static void setCtxPropertiesMap(Map<String, Object> ctxPropertiesMap) {
		SpringPropertyUtil.ctxPropertiesMap = ctxPropertiesMap;
	}
	
	public static Object getValue(String name) {
		if(StringUtils.isBlank(name)) {
			return null;
		}
		
		String env = (String) ctxPropertiesMap.get(ENV); 
		if(ENV.equals(name)) {
			return env;
		}
		
		String suffix = StringUtils.isBlank(env) ? "" : ("." + env);
		return ctxPropertiesMap.get(name.endsWith(suffix) ? name : (name + suffix));
	}

	/**
	 * 确认当前运行环境是否是生产环境
	 * @return {@link java.lang.Boolean}: <code>true</code>-是生产环境; <code>false</code>-不是生产环境
	 */
	public static boolean isProduceEnv() {
		return "prod".equals(SpringPropertyUtil.getValue(ENV));
	}
	
	/*
	@SuppressWarnings({ "static-access", "resource" })
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		SpringPropertyUtil spu = (SpringPropertyUtil) context.getBean("propertyConfigurer");
		System.out.println((String) spu.getValue(ENV));
		System.out.println(spu.isProduceEnv());
		System.out.println((String) spu.getValue(SpringPropertyUtil.NAME_WMS_URL));
		System.out.println((String) spu.getValue(SpringPropertyUtil.NAME_WMS_TOKEN));
		System.out.println((String) spu.getValue(SpringPropertyUtil.NAME_DEOWNERCODE));
		System.out.println((String) spu.getValue(SpringPropertyUtil.NAME_DESTORECODE));
		System.out.println((String) spu.getValue(SpringPropertyUtil.NAME_DEWMSAPPKEY));
		System.out.println((String) spu.getValue(SpringPropertyUtil.NAME_DEWMSAPPSECRET));
	}*/
}
