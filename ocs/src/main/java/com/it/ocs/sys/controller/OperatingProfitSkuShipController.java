package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitSkuShipService;
import com.it.ocs.sys.vo.OperatingProfitSkuShipVo;

@Controller
@RequestMapping("/operatingProfitSkuShip")
public class OperatingProfitSkuShipController {

	@Autowired
	private IOperatingProfitSkuShipService operatingProfitSkuShipService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/sys/operatingProfitSkuShip";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitSkuShipVo> findAll(RequestParam param) {
		return operatingProfitSkuShipService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(OperatingProfitSkuShipVo operatingProfitSkuShip) {
		return operatingProfitSkuShipService.saveEdit(operatingProfitSkuShip);
	}
}
