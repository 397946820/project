package com.it.ocs.cal.service;

import com.it.ocs.cal.vo.LightEbayCustomerVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ILightEbayCustomerService {

	ResponseResult<LightEbayCustomerVo> findAll(RequestParam param);

	OperationResult saveEdit(LightEbayCustomerVo customer);

}
