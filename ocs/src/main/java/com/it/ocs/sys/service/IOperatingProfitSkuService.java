package com.it.ocs.sys.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.vo.OperatingProfitSkuVo;

public interface IOperatingProfitSkuService {

	ResponseResult<OperatingProfitSkuVo> findAll(RequestParam param);

	OperationResult saveEdit(OperatingProfitSkuVo operatingProfitSku);

}
