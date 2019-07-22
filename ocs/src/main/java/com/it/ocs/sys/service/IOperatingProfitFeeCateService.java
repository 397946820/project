package com.it.ocs.sys.service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.vo.OperatingProfitFeeCateVo;

public interface IOperatingProfitFeeCateService {

	ResponseResult<OperatingProfitFeeCateVo> findAll(RequestParam param);

}
