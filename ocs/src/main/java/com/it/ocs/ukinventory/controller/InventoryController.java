package com.it.ocs.ukinventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.ukinventory.service.IInventoryService;
import com.it.ocs.ukinventory.vo.InventoryUKVO;

@Controller
@RequestMapping(value = "/uk_inventory_day")
public class InventoryController {
	@Autowired
	private IInventoryService inventoryService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<InventoryUKVO> list(RequestParam param) {
		ResponseResult<InventoryUKVO> result = inventoryService.queryInventory(param);
		return result;
	}
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "admin/roymail/inventory";
	}
}
