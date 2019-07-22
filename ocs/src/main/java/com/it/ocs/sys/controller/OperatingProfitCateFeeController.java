package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitCateFeeService;
import com.it.ocs.sys.vo.OperatingProfitCateFeeVo;

@Controller
@RequestMapping("/operatingProfitCateFee")
public class OperatingProfitCateFeeController {

	@Autowired
	private IOperatingProfitCateFeeService operatingProfitCateFeeService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/sys/operatingProfitCateFee";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitCateFeeVo> findAll(RequestParam param) {
		return operatingProfitCateFeeService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(OperatingProfitCateFeeVo operatingProfitCateFee) {
		return operatingProfitCateFeeService.saveEdit(operatingProfitCateFee);
	}
}
