package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitFeeService;
import com.it.ocs.sys.vo.OperatingProfitFeeVo;

@Controller
@RequestMapping("/operatingProfitFee")
public class OperatingProfitFeeController {

	@Autowired
	private IOperatingProfitFeeService operatingProfitFeeService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/sys/operatingProfitFee";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitFeeVo> findAll(RequestParam param) {
		return operatingProfitFeeService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(OperatingProfitFeeVo operatingProfitFee) {
		return operatingProfitFeeService.saveEdit(operatingProfitFee);
	}
}
