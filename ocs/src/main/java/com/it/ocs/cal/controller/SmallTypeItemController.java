package com.it.ocs.cal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ISmallTypeItemService;
import com.it.ocs.cal.vo.SmallTypeItemVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

/**
 * 
 * @author yecaiqing
 *
 */
@Controller
@RequestMapping("/smallTypeItem")
public class SmallTypeItemController {

	@Autowired
	private ISmallTypeItemService smallTypeItemService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/smallTypeItem";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<SmallTypeItemVo> findAll(RequestParam param) {
		return smallTypeItemService.findAll(param);
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(SmallTypeItemVo smallTypeItem) {
		return smallTypeItemService.saveEdit(smallTypeItem);
	}

	@RequestMapping("/getTypeName")
	@ResponseBody
	public List<Map<String, Object>> getTypeName() {
		return smallTypeItemService.getTypeName();
	}
	
	@RequestMapping("/getShippingTypeByTypeName")
	@ResponseBody
	public List<Map<String, Object>> getShippingTypeByTypeName(@RequestBody Map<String, Object> map) {
		return smallTypeItemService.getShippingTypeByTypeName(map);
	}
}
