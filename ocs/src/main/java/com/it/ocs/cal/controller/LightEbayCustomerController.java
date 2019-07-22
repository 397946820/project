package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ILightEbayCustomerService;
import com.it.ocs.cal.vo.LightEbayCustomerVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/lightEbayCustomer")
public class LightEbayCustomerController {
	
	@Autowired
	private ILightEbayCustomerService lightEbayCustomerService;

	// 跳转到主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/lightEbayCustomer";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<LightEbayCustomerVo> findAll(RequestParam param) {
		return lightEbayCustomerService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(LightEbayCustomerVo customer) {
		return lightEbayCustomerService.saveEdit(customer);
	}
}
