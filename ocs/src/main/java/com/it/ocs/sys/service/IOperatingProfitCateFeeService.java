package com.it.ocs.sys.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.vo.OperatingProfitCateFeeVo;

public interface IOperatingProfitCateFeeService {

	ResponseResult<OperatingProfitCateFeeVo> findAll(RequestParam param);

	OperationResult saveEdit(OperatingProfitCateFeeVo operatingProfitCateFee);

}
