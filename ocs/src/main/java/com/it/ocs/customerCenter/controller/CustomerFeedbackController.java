package com.it.ocs.customerCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.dao.ICustomerFeedbackDao;
import com.it.ocs.customerCenter.service.ICustomerFeedbackService;
import com.it.ocs.customerCenter.vo.CustomerFeedbackVO;

@Controller
@RequestMapping(value="/Feedbacks")
public class CustomerFeedbackController {
	
	@Autowired
	private ICustomerFeedbackService feedbackService;
	
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/customerCenter/Feedbacks";
	}
	@RequestMapping(value="/list")
	@ResponseBody
	public ResponseResult<CustomerFeedbackVO> list(RequestParam param){
		
		return feedbackService.selectCustomerFeecBacks(param);
	}
	@RequestMapping(value="/remove")
	@ResponseBody
	public OperationResult remove(Long feedbacks_id){
		
		return feedbackService.remove(feedbacks_id);
	}
	@RequestMapping(value="/batchSaveFeedbacks")
	@ResponseBody
	public OperationResult batchSaveFeedbacks(@RequestBody CustomerFeedbackVO[] customerFeedbackVOs){
		
		return feedbackService.batchSaveFeedbacks(customerFeedbackVOs);
	}
	
}
