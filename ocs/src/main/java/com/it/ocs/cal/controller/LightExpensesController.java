package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.ILightExpensesService;
import com.it.ocs.cal.vo.LightExpensesVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/lightExpenses")
public class LightExpensesController {

	@Autowired
	private ILightExpensesService ligthExpensesService;
	
	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/lightExpenses";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<LightExpensesVo> findAll(RequestParam param) {
		return ligthExpensesService.findAll(param);
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	public OperationResult saveEdit(LightExpensesVo expenses) {
		return ligthExpensesService.saveEdit(expenses);
	}
}
