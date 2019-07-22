package com.it.ocs.cal.service;

import com.it.ocs.cal.vo.SmallRateVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ISmallRateService {

	ResponseResult<SmallRateVo> findAll(RequestParam param);

	OperationResult saveEdit(SmallRateVo smallRateVo);

}
