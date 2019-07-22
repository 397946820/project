package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.IHandlingChargesService;
import com.it.ocs.cal.vo.LightHandlingChargesVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/handlingCharges")
public class HandlingChargesController {

	@Autowired
	private IHandlingChargesService handlingChargesService;
	
	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/handlingCharges";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<LightHandlingChargesVo> findAll(RequestParam param) {
		return handlingChargesService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(LightHandlingChargesVo chargesVo) {
		return handlingChargesService.saveEdit(chargesVo);
	}
}
