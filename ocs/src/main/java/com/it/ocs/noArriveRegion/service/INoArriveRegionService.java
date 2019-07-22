package com.it.ocs.noArriveRegion.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.noArriveRegion.vo.NoArriveRegionVo;

public interface INoArriveRegionService {

	/**
	 * 分页查找
	 * @param param
	 * @return
	 */
	ResponseResult<NoArriveRegionVo> findAll(RequestParam param);

	/**
	 * 保存/修改不运送地区
	 * @param region
	 * @return
	 */
	OperationResult saveEdit(NoArriveRegionVo region);

	/**
	 * 逻辑删除不运送地区
	 * @param ids 要删除的id
	 * @return
	 */
	OperationResult removeNoArriveRegion(String ids);

}
