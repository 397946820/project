package com.it.ocs.ukinventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.ukinventory.service.IInventoryFlowService;
import com.it.ocs.ukinventory.vo.InventoryFlowUKVO;

@Controller
@RequestMapping(value = "/uk_inventory")
public class InventoryFlowController {
	@Autowired
	private IInventoryFlowService inventoryFlowService;
	@RequestMapping(value = "/flow", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<InventoryFlowUKVO> list(RequestParam param) {
		ResponseResult<InventoryFlowUKVO> result = inventoryFlowService.queryInventoryFlow(param);
		return result;
	}
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "admin/roymail/inventory_flow";
	}
}
