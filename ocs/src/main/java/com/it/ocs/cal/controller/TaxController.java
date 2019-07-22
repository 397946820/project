package com.it.ocs.cal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.service.ITaxService;
import com.it.ocs.cal.vo.TaxVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.core.SystemLog;

@Controller
@RequestMapping("/tax")
public class TaxController {

	@Autowired
	private ITaxService taxService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/tax";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<TaxVo> findAll(RequestParam param) {
		return taxService.findAll(param,"");
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	@SystemLog("税添加或者修改")
	public OperationResult saveEdit(TaxVo tax) {
		return taxService.saveEdit(tax);
	}

	// 导出
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, 
			@org.springframework.web.bind.annotation.RequestParam("template")String template) {
		taxService.exportExcel(request, response, template);
	}

	// 导入
	@RequestMapping("/uploadExcel")
	@ResponseBody
	@SystemLog("税导入")
	public OperationResult uploadExcel(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) {
		return taxService.uploadExcel(file);
	}
}
