package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitSalaryService;
import com.it.ocs.sys.vo.OperatingProfitSalaryVo;

@Controller
@RequestMapping("/operatingProfitSalary")
public class OperatingProfitSalaryController {

	@Autowired
	private IOperatingProfitSalaryService operatingProfitSalaryService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/sys/operatingProfitSalary";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitSalaryVo> findAll(RequestParam param) {
		return operatingProfitSalaryService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(OperatingProfitSalaryVo operatingProfitSalary) {
		return operatingProfitSalaryService.saveEdit(operatingProfitSalary);
	}
}
