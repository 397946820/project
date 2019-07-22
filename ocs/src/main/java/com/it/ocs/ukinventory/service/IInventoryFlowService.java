package com.it.ocs.ukinventory.service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.ukinventory.vo.InventoryFlowUKVO;

public interface IInventoryFlowService {
	public ResponseResult<InventoryFlowUKVO> queryInventoryFlow(RequestParam param);
	
	public void syncCustomerRefoud();
}
