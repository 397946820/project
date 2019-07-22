package com.it.ocs.salereport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salereport.model.InventoryWarningModel;
import com.it.ocs.salereport.service.IInventoryWarningService;

@Controller
@RequestMapping("/inventoryWarning")
public class InventoryWarningController {
	
	@Autowired
	private IInventoryWarningService inventoryWarningService;
	
	@RequestMapping("/show")
	public String show() {
		return "admin/sys/inventoryWarning";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<InventoryWarningModel> findInventoryWarningList(RequestParam param){  
		return inventoryWarningService.findInventoryWarningList(param);
	}	
	
	@RequestMapping(value = "/updateInventoryWarning", method = RequestMethod.GET)
	@ResponseBody
	public OperationResult updateInventoryWarning() {
		return inventoryWarningService.updateInventoryWarning();
	}
}
