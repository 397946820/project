package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitSkuFeeService;
import com.it.ocs.sys.vo.OperatingProfitSkuFeeVo;

@Controller
@RequestMapping("/operatingProfitSkuFee")
public class OperatingProfitSkuFeeController {

	@Autowired
	private IOperatingProfitSkuFeeService operatingProfitSkuFeeService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/sys/operatingProfitSkuFee";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitSkuFeeVo> findAll(RequestParam param) {
		return operatingProfitSkuFeeService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(OperatingProfitSkuFeeVo operatingProfitSkuFee) {
		return operatingProfitSkuFeeService.saveEdit(operatingProfitSkuFee);
	}
}
