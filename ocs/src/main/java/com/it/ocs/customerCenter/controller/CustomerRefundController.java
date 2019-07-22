package com.it.ocs.customerCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.service.ICustomerRefundService;
import com.it.ocs.customerCenter.vo.CustomerRefundVO;

@Controller
@RequestMapping(value="/RefundRecord")
public class CustomerRefundController {
	@Autowired
	private ICustomerRefundService refundService;
	@RequestMapping(value="/show")
	public String show(){
		return "admin/customerCenter/RefundRecord";
	}
	@RequestMapping(value="/list")
	@ResponseBody
	public ResponseResult<CustomerRefundVO> list(RequestParam param){
		return refundService.selectCustomerRefunds(param);
	}
	
	@RequestMapping(value="/remove")
	@ResponseBody
	public OperationResult remove(Long report_id){
		return refundService.remove(report_id);
	}
	@RequestMapping(value="/batchSaveRefund")
	@ResponseBody
	public OperationResult batchSaveRefund(@RequestBody CustomerRefundVO[] customerRefundVos){
		return refundService.batchSaveRefunds(customerRefundVos);
	}
}
