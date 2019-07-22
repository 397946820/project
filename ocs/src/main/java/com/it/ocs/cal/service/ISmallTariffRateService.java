package com.it.ocs.cal.service;

import com.it.ocs.cal.vo.SmallTariffRateVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ISmallTariffRateService {

	ResponseResult<SmallTariffRateVo> findAll(RequestParam param);

	OperationResult saveEdit(SmallTariffRateVo smallTariffRate);

}
