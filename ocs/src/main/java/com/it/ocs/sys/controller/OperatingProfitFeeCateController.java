package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitFeeCateService;
import com.it.ocs.sys.vo.OperatingProfitFeeCateVo;

@Controller
@RequestMapping("/operatingProfitFeeCate")
public class OperatingProfitFeeCateController {

	@Autowired
	private IOperatingProfitFeeCateService operatingProfitFeeCateService;

	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitFeeCateVo> findAll(RequestParam param) {
		return operatingProfitFeeCateService.findAll(param);
	}

}
