package com.it.ocs.api.dao;

import java.util.Map;

import com.it.ocs.api.model.WmsProviderToken;

/**
* @ClassName: IWmsProviderTokenDao 
* @Description: 仓储供应商dao
* @author wgc 
* @date 2018年4月23日 上午11:17:32 
*
 */
public interface IWmsProviderTokenDao {
	/**
	* @Title: getProviderToken 
	* @Description: 通过map获取供应商token信息 
	* @param @param map
	* @param @return    设定文件 
	* @return WmsProviderToken    返回类型 
	* @throws
	 */
	public WmsProviderToken getProviderToken(Map<String,Object> map);
	
	/**
	* @Title: updateProviderTokenByMap 
	* @Description: 通过map获取供应商token信息 
	* @param @param map    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateProviderTokenByMap(Map<String,Object> map);
}
