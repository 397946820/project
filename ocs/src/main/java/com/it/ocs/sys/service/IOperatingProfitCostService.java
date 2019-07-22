package com.it.ocs.sys.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.vo.OperatingProfitCostVo;

public interface IOperatingProfitCostService {

	ResponseResult<OperatingProfitCostVo> findAll(RequestParam param);

	OperationResult saveEdit(OperatingProfitCostVo operatingProfitCost);

}
