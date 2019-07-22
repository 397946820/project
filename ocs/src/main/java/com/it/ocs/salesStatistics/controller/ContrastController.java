package com.it.ocs.salesStatistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.service.IContrastService;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;

@Controller
@RequestMapping("/contrast")
public class ContrastController {
	
	@Autowired
	private IContrastService contrastService;
	
	// 跳转到主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/salesStatistics/contrast";
	}
	
	@RequestMapping("/uploadExcel")
	@ResponseBody
	public OperationResult uploadExcel(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) {
		return contrastService.uploadExcel(file);
		
	}
	
	@RequestMapping("/findAllOriginal")
	@ResponseBody
	public ResponseResult<SalesStatisticsVo> findAllOriginal(RequestParam param) throws Exception {
		return contrastService.findAllOriginal(param);
		
	}
	
	@RequestMapping("/findAllOracle")
	@ResponseBody
	public ResponseResult<SalesStatisticsVo> findAllOracle(RequestParam param) throws Exception {
		return contrastService.findAllOracle(param);
		
	}
	
	@RequestMapping("/findAllMysql")
	@ResponseBody
	public ResponseResult<SalesStatisticsVo> findAllMysql(RequestParam param) throws Exception {
		return contrastService.findAllMysql(param);
		
	}
}
