package com.it.ocs.fourPX.dao;

import java.util.Map;

public interface AccountManagerDao {

	/**
	 * 获取US区4PX请求身份信息
	 * @param code
	 * @return
	 */
	Map<String, Object> selectOneByCode(String code);

	/**
	 * 获取DE区4PX请求身份信息
	 * @param code
	 * @return
	 */
	Map<String, Object> selectDeOneByCode(String code);
}
