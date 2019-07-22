package com.it.ocs.sys.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.model.OrderApproveModel;
import com.it.ocs.sys.model.ReturnOrderItemModel;

public interface IOrderApproveService {

	public ResponseResult<OrderApproveModel> list(RequestParam param);

	public List<ReturnOrderItemModel> getInfoById(Map<String, Object> map);
	
	public OperationResult saveApproveData(Map<String, Object> data);

	public boolean cancel(Long id) throws Exception;	

}
