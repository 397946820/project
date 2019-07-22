package com.it.ocs.synchronou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/PaymentModule")
@Controller
public class PaymentManageController {

	
	@RequestMapping(value="/show")
	public String show(){
		return "admin/ebaySynchronous/paymentManage";
	}
}
