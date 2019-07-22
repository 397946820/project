package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ISmallRateService;
import com.it.ocs.cal.vo.SmallRateVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/smallRate")
public class SmallRateController {
	
	@Autowired
	private ISmallRateService smallRateService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/smallRate";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<SmallRateVo> findAll(RequestParam param) {
		return smallRateService.findAll(param);
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(SmallRateVo smallRateVo) {
		return smallRateService.saveEdit(smallRateVo);
	}

}
