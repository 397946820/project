package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.IProductRelationService;
import com.it.ocs.cal.vo.ProductUserVO;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/productRelation")
public class ProductRelationController {
	@Autowired
	private IProductRelationService productRelationService;
	@RequestMapping("/query")
	public @ResponseBody ResponseResult<ProductUserVO> query(RequestParam param) {
		return productRelationService.queryParam(param);
	}
	@RequestMapping(value = "/castSku", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult castRole(@RequestBody ProductUserVO param) {
		return productRelationService.castSku(param);
	}
}
