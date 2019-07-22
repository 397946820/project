package com.it.ocs.customerCenter.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.vo.CustomerFeedbackVO;

public interface ICustomerFeedbackService {

	public ResponseResult<CustomerFeedbackVO> selectCustomerFeecBacks(RequestParam param);
	
	public OperationResult remove(Long feedbacks_id);
	
	public  OperationResult batchSaveFeedbacks(CustomerFeedbackVO[] customerFeedbackVOs);
	
	public OperationResult updateFeedbacks(List<CustomerFeedbackVO> customerFeedbackVOs);
	
	public OperationResult insertFeedbacks(List<CustomerFeedbackVO> customerFeedbackVOs);
	
}
