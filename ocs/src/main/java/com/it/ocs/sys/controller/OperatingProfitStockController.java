package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitStockService;
import com.it.ocs.sys.vo.OperatingProfitStockVo;

@Controller
@RequestMapping("/operatingProfitStock")
public class OperatingProfitStockController {

	@Autowired
	private IOperatingProfitStockService operatingProfitStockService;

	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitStockVo> findAll(RequestParam param) {
		return operatingProfitStockService.findAll(param);
	}
}
