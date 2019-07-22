package com.it.ocs.excel.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.common.OperationResult;
import com.it.ocs.excel.service.AExcelDynamicExport;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.sys.core.SystemLog;

@Controller
@RequestMapping(value = "/excel")
public class ExcelController {

	@RequestMapping(value = "/import/{beanName}", method = RequestMethod.POST)
	@ResponseBody
	@SystemLog("Excel导入")
	public OperationResult importExcel(@RequestParam("file") MultipartFile file,
			@PathVariable("beanName") String beanName, HttpServletRequest request) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		AExcelImport aExcelImport = (AExcelImport) wac.getBean(beanName);
		OperationResult result = new OperationResult();
		Map<String, Object> data = null;
		
		boolean enabledCatch = "true".equals(request.getParameter("catch")); //是否启用catch（捕获并处理异常）
		try {
			data = aExcelImport.excute(file,request);
		} catch (Exception e) {
			if(enabledCatch) {
				result.setDescription(e.getMessage());
				result.setErrorCode(1);
			} else {
				throw e;
			}
		}
		result.setData(data);
		return result;
	}

	@RequestMapping(value = "/export/{beanName}")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("beanName") String beanName) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		AExcelExport aExcelExport = (AExcelExport) wac.getBean(beanName);
		aExcelExport.excute(request, response);
	}
	
	@RequestMapping(value = "/dynamicExport/{beanName}")
	public void dynamicExport(HttpServletRequest request, HttpServletResponse response, @PathVariable("beanName") String beanName) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		AExcelDynamicExport aExcelExport = (AExcelDynamicExport) wac.getBean(beanName);
		aExcelExport.excute(request, response);
	}

	@RequestMapping(value = "/template/{beanName}", method = RequestMethod.GET)
	public void template(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("beanName") String beanName) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		AExcelImport aExcelImport = (AExcelImport) wac.getBean(beanName);
		aExcelImport.getTemplate(request, response);
	}
}
