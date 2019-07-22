package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ILightEbayRateService;
import com.it.ocs.cal.vo.LightEbayRateVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/lightEbayRate")
public class LightEbayRateController {

	@Autowired
	private ILightEbayRateService lightEbayRateService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/lightEbayRate";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<LightEbayRateVo> findAll(RequestParam param) {
		return lightEbayRateService.findAll(param);
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(LightEbayRateVo rateVo) {
		return lightEbayRateService.saveEdit(rateVo);
	}
	
	@RequestMapping("/isExist")
	@ResponseBody
	public Boolean isExist(LightEbayRateVo rateVo) {
		return lightEbayRateService.isExist(rateVo);
	}
}
