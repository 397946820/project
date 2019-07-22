package com.it.ocs.cal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.service.IFbaRuleService;
import com.it.ocs.cal.vo.FbaRuleVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.core.SystemLog;

@Controller
@RequestMapping("/fbaRule")
public class FbaRuleController {

	@Autowired
	private IFbaRuleService fbaRuleService;
	
	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/fbaRule";
	}
	
	//查询
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<FbaRuleVo> findAll(RequestParam param) {
		return fbaRuleService.findAll(param,"");
		
	}
	
	//保存和修改
	@RequestMapping("/saveEdit")
	@ResponseBody
	@SystemLog("FBA规则添加或者修改")
	public OperationResult saveEdit(FbaRuleVo fbaRule) {
		return fbaRuleService.saveEdit(fbaRule);
	}
	
	//导出
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response, 
			@org.springframework.web.bind.annotation.RequestParam("template")String template) {
		fbaRuleService.exportExcel(request,response,template);
	}
	
	//导入
	@RequestMapping("/uploadExcel")
	@ResponseBody
	@SystemLog("FBA规则导入")
	public OperationResult uploadExcel(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) {
		return fbaRuleService.uploadExcel(file);
	}
}
