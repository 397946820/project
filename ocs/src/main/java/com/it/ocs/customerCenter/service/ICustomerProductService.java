package com.it.ocs.customerCenter.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.vo.CustomerProductVO;

public interface ICustomerProductService {
	
	public ResponseResult<CustomerProductVO> selectCustomerProducts(RequestParam param);
	
	public OperationResult remove(Long product_id);
	
	public OperationResult batchSaveProducts(CustomerProductVO[] customerProductVOs);
	
	public OperationResult insertProducts(List<CustomerProductVO> customerProductVOs);
	
	public OperationResult updateProducts(List<CustomerProductVO> customerProductVOs);
}
