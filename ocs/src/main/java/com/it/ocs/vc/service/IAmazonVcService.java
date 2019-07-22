package com.it.ocs.vc.service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.vc.vo.AmazonVcVO;

public interface IAmazonVcService {
	public ResponseResult<AmazonVcVO> queryAmazonVcInfos(RequestParam param);
}
