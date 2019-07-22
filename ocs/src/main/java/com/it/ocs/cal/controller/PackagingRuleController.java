package com.it.ocs.cal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cal.service.IPackagingRuleService;
import com.it.ocs.cal.vo.PackagingRuleVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.core.SystemLog;

@Controller
@RequestMapping("/packagingRule")
public class PackagingRuleController {
	
	@Autowired
	private IPackagingRuleService packagingRuleService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/packagingRule";
	}

	// 查询
	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<PackagingRuleVo> findAll(RequestParam param) {
		return packagingRuleService.findAll(param);

	}

	// 保存和修改
	@RequestMapping("/saveEdit")
	@ResponseBody
	@SystemLog("AU规则添加或者修改")
	public OperationResult saveEdit(PackagingRuleVo packagingRule) {
		return packagingRuleService.saveEdit(packagingRule);
	}
	
}
