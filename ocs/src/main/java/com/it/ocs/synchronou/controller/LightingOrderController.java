package com.it.ocs.synchronou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.service.ISyncLightingOrderService;

@RequestMapping("/lightOrder")
@Controller
public class LightingOrderController {
	
	@Autowired
	private ISyncLightingOrderService syncLightingOrderService;
	
	
	@RequestMapping(value="/syncOrderList",method = RequestMethod.GET)
	@ResponseBody
	public OperationResult syncLightingOrder(){
		return syncLightingOrderService.syncLightingOrder();
	}
}
