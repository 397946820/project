package com.it.ocs.cal.service;

import com.it.ocs.cal.vo.LightEbayRateVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ILightEbayRateService {

	ResponseResult<LightEbayRateVo> findAll(RequestParam param);

	OperationResult saveEdit(LightEbayRateVo rateVo);

	Boolean isExist(LightEbayRateVo rateVo);

}
