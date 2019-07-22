package com.it.ocs.fourPX.service;

import com.it.ocs.fourPX.vo.FourPXRequestVO;

public interface AccountManagerService {

	/**
	 * 获取US区4PX请求身份信息
	 * @param code
	 * @return
	 */
	FourPXRequestVO selectOneByCode(String code);

	/**
	 * 获取DE区4PX请求身份信息
	 * @param code
	 * @return
	 */
	FourPXRequestVO selectDeOneByCode(String code);
}
