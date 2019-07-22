package com.it.ocs.cal.service;

import com.it.ocs.cal.vo.ProductUserVO;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IProductRelationService {
	public ResponseResult<ProductUserVO> queryParam(RequestParam param);
	
	public OperationResult castSku(ProductUserVO param);
	
	public ResponseResult<ProductUserVO> queryExistByParam(RequestParam param);
	
	public OperationResult cancelSku(ProductUserVO param);
}
