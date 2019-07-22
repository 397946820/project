package com.it.ocs.vc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.vc.service.IAmazonVcService;
import com.it.ocs.vc.vo.AmazonVcVO;

@Controller
@RequestMapping("/vc")
public class AmazonVcController {
	@Autowired
	private IAmazonVcService amazonVcService;
	@RequestMapping("/show")
	public String show() {
		return "admin/vc/vc_list";
	}
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<AmazonVcVO> list(RequestParam param) {
		return amazonVcService.queryAmazonVcInfos(param);
	}

}
