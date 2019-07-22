package com.it.ocs.product.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.product.vo.ProductVo;

public interface IProductService {

	/**
	 * 分页查找 
	 * @param param
	 * @return
	 */
	ResponseResult<ProductVo> findAll(RequestParam param);

	/**
	 * 保存修改
	 * @param product
	 * @return
	 */
	OperationResult saveEdit(ProductVo product);

	/**
	 * 逻辑删除
	 * @param ids
	 * @return
	 */
	OperationResult remove(String ids);

}
