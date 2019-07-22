package com.it.ocs.templateSetting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.templateSetting.service.ITemplateSettingService;
import com.it.ocs.templateSetting.vo.TemplateSettingVo;

/**
 * 模板设置控制层
 * 
 * @author yecaiqing
 *
 */

@Controller
@RequestMapping("/templateSetting")
public class TemplateSettingController {

	@Autowired
	private ITemplateSettingService templateSettingService;

	// 跳转到模板设置主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/templateSetting/list";
	}
	
	@RequestMapping("/findAll")
	public @ResponseBody ResponseResult<TemplateSettingVo> findAll(RequestParam param) {
		return templateSettingService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	public @ResponseBody OperationResult saveEdit(TemplateSettingVo templateSetting) {
		return templateSettingService.saveEdit(templateSetting);
	}
	
	@RequestMapping("/remove")
	public @ResponseBody OperationResult removeTemplateSetting(String ids) {
		return templateSettingService.removeTemplateSetting(ids);
	}
}
