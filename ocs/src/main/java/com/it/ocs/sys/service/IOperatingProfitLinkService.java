package com.it.ocs.sys.service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.vo.OperatingProfitLinkVo;

public interface IOperatingProfitLinkService {

	ResponseResult<OperatingProfitLinkVo> findAll(RequestParam param);

}
