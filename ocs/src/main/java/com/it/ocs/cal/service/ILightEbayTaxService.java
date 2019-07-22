package com.it.ocs.cal.service;

import com.it.ocs.cal.vo.LightEbayTaxVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ILightEbayTaxService {

	/**
	 * 查找
	 * @param param
	 * @return
	 */
	ResponseResult<LightEbayTaxVo> findAll(RequestParam param);

	/**
	 * 保存和修改
	 * @param tax
	 * @return
	 */
	OperationResult saveEdit(LightEbayTaxVo tax);

}
