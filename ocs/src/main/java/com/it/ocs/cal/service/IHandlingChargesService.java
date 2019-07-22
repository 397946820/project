package com.it.ocs.cal.service;

import com.it.ocs.cal.vo.LightHandlingChargesVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IHandlingChargesService {

	ResponseResult<LightHandlingChargesVo> findAll(RequestParam param);

	OperationResult saveEdit(LightHandlingChargesVo chargesVo);

}
