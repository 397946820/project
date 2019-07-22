package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ILightEbaySundryService;
import com.it.ocs.cal.vo.LightEbaySundryVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/lightEbaySundry")
public class LightEbaySundryController {
	
	@Autowired
	private ILightEbaySundryService lightEbaySundryService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/lightEbaySundry";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<LightEbaySundryVo> findAll(RequestParam param) {
		return lightEbaySundryService.findAll(param);
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(LightEbaySundryVo rateVo) {
		return lightEbaySundryService.saveEdit(rateVo);
	}
}
