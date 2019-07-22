package com.it.ocs.customerCenter.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.vo.CustomerRefundVO;

public interface ICustomerRefundService {
	

	public ResponseResult<CustomerRefundVO> selectCustomerRefunds(RequestParam param);
	
	public OperationResult remove(Long report_id);
	
	public OperationResult batchSaveRefunds(CustomerRefundVO[] customerRefundVos);
	
	public OperationResult insertRefunds(List<CustomerRefundVO> customerRefundVos);
	
	public OperationResult updateRefunds(List<CustomerRefundVO> customerRefundVos);
}
