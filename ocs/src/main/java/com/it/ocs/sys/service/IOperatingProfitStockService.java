package com.it.ocs.sys.service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.vo.OperatingProfitStockVo;

public interface IOperatingProfitStockService {

	ResponseResult<OperatingProfitStockVo> findAll(RequestParam param);

}
