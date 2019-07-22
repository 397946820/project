package com.it.ocs.seller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/returnPolicy")
public class ReturnPolicysController {
	
	@RequestMapping(value="/show")
	public String show(){
		return "admin/moduleManagement/returnPolicy";
	}
}
