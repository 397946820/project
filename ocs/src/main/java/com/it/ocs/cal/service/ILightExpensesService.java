package com.it.ocs.cal.service;

import com.it.ocs.cal.vo.LightExpensesVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ILightExpensesService {

	ResponseResult<LightExpensesVo> findAll(RequestParam param);

	OperationResult saveEdit(LightExpensesVo expenses);

}
