package com.it.ocs.task.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.service.IRealTimeSynchronouService;

@Controller
@RequestMapping(value="/Synchronou")
public class RealTimeSynchronou {

	@Autowired
	private IRealTimeSynchronouService synchronouService;
	
	@RequestMapping("/show")
	public String show(){
		
		return "admin/task/show";
	}
	
	@RequestMapping("/sysnchronouOrderAndOrderItem")
	@ResponseBody
	public OperationResult sysnchronouOrderAndOrderItem(){
		
		return synchronouService.sysnchronouOrderAndOrderItem();
	}
	@RequestMapping("/lightSynchronou")
	@ResponseBody
	public OperationResult lightSynchronou(@RequestBody Map<String, String> map){
		return synchronouService.lightSynchronou(map);
	}
	@RequestMapping("/amazonSynchronou")
	@ResponseBody
	public OperationResult amazonSynchronou(@RequestBody Map<String, String> map){
		return synchronouService.amazonSynchronou(map);
	}
	@RequestMapping("/ebaySynchronou")
	@ResponseBody
	public OperationResult ebaySynchronou(@RequestBody Map<String, String> map){
		return synchronouService.ebaySynchronou(map);
	}
}
