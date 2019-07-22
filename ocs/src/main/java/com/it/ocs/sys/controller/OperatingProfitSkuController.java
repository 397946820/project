package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitSkuService;
import com.it.ocs.sys.vo.OperatingProfitSkuVo;

@Controller
@RequestMapping("/operatingProfitSku")
public class OperatingProfitSkuController {
	
	@Autowired
	private IOperatingProfitSkuService operatingProfitSkuService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/sys/operatingProfitSku";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitSkuVo> findAll(RequestParam param) {
		return operatingProfitSkuService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(OperatingProfitSkuVo operatingProfitSku) {
		return operatingProfitSkuService.saveEdit(operatingProfitSku);
	}
}
