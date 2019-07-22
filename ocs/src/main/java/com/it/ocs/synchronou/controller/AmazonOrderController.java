package com.it.ocs.synchronou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.service.ISyncAmazonOrderService;


@Controller
@RequestMapping("/amazonOrder")
public class AmazonOrderController {
	
	@Autowired
	private ISyncAmazonOrderService syncAmazonOrderService;
	
	@RequestMapping(value="/syncOrderList",method = RequestMethod.GET)
	@ResponseBody
	public OperationResult syncAmazonOrder(String startTime,String endTime){
		return syncAmazonOrderService.syncAmazonOrder(startTime,endTime);
	}
}
