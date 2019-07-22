package com.it.ocs.cal.service;

import com.it.ocs.cal.model.SmallTaximeterModel;
import com.it.ocs.cal.vo.SmallTaximeterVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ISmallTaximeterService {

	ResponseResult<SmallTaximeterVo> findAll(RequestParam param);

	OperationResult editSmallTaximeter(SmallTaximeterModel model);

	OperationResult refresh();

}
