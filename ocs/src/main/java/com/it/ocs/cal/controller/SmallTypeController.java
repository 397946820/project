package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ISmallTypeService;
import com.it.ocs.cal.vo.SmallTypeVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

/**
 * 
 * @author yecaiqing
 *
 */
@Controller
@RequestMapping("/smallType")
public class SmallTypeController {

	@Autowired
	private ISmallTypeService smallTypeService;

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<SmallTypeVo> findAll(RequestParam param) {
		return smallTypeService.findAll(param);
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(SmallTypeVo smallType) {
		return smallTypeService.saveEdit(smallType);
	}
}
