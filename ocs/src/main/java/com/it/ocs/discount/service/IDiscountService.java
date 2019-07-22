package com.it.ocs.discount.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.discount.vo.DiscountVo;

public interface IDiscountService {

	/**
	 * 查找全部
	 * @param param
	 * @return
	 */
	ResponseResult<DiscountVo> findAll(RequestParam param);

	/**
	 * 保存，修改折扣
	 * @param discount
	 * @return
	 */
	OperationResult saveEditDiscount(DiscountVo discount);
	
	/**
	 * 作废记录
	 * @param ids 要作废的id数组
	 * @return
	 */
	OperationResult removeDiscount(String ids);

}
