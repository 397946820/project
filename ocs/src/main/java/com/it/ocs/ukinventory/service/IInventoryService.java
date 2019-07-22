package com.it.ocs.ukinventory.service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.ukinventory.vo.InventoryUKVO;

public interface IInventoryService {
	public ResponseResult<InventoryUKVO> queryInventory(RequestParam param);
}
