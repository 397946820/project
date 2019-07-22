package com.it.ocs.cal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.service.ICurrencyRateService;
import com.it.ocs.cal.vo.CurrencyRateVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.core.SystemLog;

@Controller
@RequestMapping("/currencyRate")
public class CurrencyRateController {

	@Autowired
	private ICurrencyRateService currencyRateService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/currencyRate";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<CurrencyRateVo> findAll(RequestParam param) {
		return currencyRateService.findAll(param,"");
	}

	@RequestMapping("/saveEdit")
	@ResponseBody
	@SystemLog("汇率添加或者修改")
	public OperationResult saveEdit(CurrencyRateVo currencyRate) {
		return currencyRateService.saveEdit(currencyRate);
	}

	// 导出
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, 
			@org.springframework.web.bind.annotation.RequestParam("template")String template) {
		currencyRateService.exportExcel(request, response, template);
	}

	// 导入
	@RequestMapping("/uploadExcel")
	@ResponseBody
	@SystemLog("汇率导入")
	public OperationResult uploadExcel(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) {
		return currencyRateService.uploadExcel(file);
	}
}
