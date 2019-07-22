package com.it.ocs.synchronou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.service.IEBayReturnPolicyDetailService;
import com.it.ocs.synchronou.vo.ReturnPolicyDetailVO;

@Controller
@RequestMapping(value="/ReturnPolicyDetails")
public class ReturnPolicyDetailsController {

//	@Autowired
	private IEBayReturnPolicyDetailService returnPolicyService;
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/ebaySynchronous/returnPolicyDetails";
	}
	
	@RequestMapping(value="/synchronouReturnPolicyDetail")
	@ResponseBody
	public OperationResult synchronouReturnPolicyDetail(Long site_id){
		
		return returnPolicyService.synchronouReturnPolicyDetail(site_id);
	}
	@RequestMapping(value="selectReturnPolicyDetails")
	@ResponseBody
	public ResponseResult<ReturnPolicyDetailVO> selectReturnPolicyDetails(RequestParam param){
		
		return returnPolicyService.selectReturnPolicyDetails(param);
	}
}
