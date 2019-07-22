package com.it.ocs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IOperatingProfitLinkService;
import com.it.ocs.sys.vo.OperatingProfitLinkVo;

@Controller
@RequestMapping("/operatingProfitLink")
public class OperatingProfitLinkController {
	
	@Autowired
	private IOperatingProfitLinkService operatingProfitLinkService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/sys/operatingProfitLink";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<OperatingProfitLinkVo> findAll(RequestParam param) {
		return operatingProfitLinkService.findAll(param);
	}
}
