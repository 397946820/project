package com.it.ocs.salereport.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salereport.model.InventoryWarningModel;

public interface IInventoryWarningService {

	public ResponseResult<InventoryWarningModel> findInventoryWarningList(RequestParam param);

	public OperationResult updateInventoryWarning();

}
