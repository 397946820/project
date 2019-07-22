package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ILightEbayTaxService;
import com.it.ocs.cal.vo.LightEbayTaxVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/lightEbayTax")
public class LightEbayTaxController {

	@Autowired
	private ILightEbayTaxService lightEbayTaxService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/lightEbayTax";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<LightEbayTaxVo> findAll(RequestParam param) {
		return lightEbayTaxService.findAll(param);
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(LightEbayTaxVo tax) {
		return lightEbayTaxService.saveEdit(tax);
	}

}
