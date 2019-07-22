package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitCostService;
import com.it.ocs.sys.vo.OperatingProfitCostVo;

@Controller
@RequestMapping("/operatingProfitCost")
public class OperatingProfitCostController {

	@Autowired
	private IOperatingProfitCostService operatingProfitCostService;

	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitCostVo> findAll(RequestParam param) {
		return operatingProfitCostService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(OperatingProfitCostVo operatingProfitCost) {
		return operatingProfitCostService.saveEdit(operatingProfitCost);
	}
}
