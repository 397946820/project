package com.it.ocs.amazon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.amazon.model.OrderReportVO;
import com.it.ocs.amazon.service.IAmazonOrderReportService;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.ComboBoxVO;

@Controller
@RequestMapping("/amazonReport")
public class AmazonReportController {
	@Autowired
	private IAmazonOrderReportService amazonOrderReportService;
	
	@RequestMapping("/show")
	public String show() {
		return "admin/salesStatistics/amazonOrderReport";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<OrderReportVO> findOrderReportList(RequestParam param){  
		return amazonOrderReportService.findOrderReportList(param);
	}
	
	@RequestMapping("/getOrderTypeBySite")
	@ResponseBody
	public List<ComboBoxVO> getOrderTypeBySite(String site){
		return amazonOrderReportService.getOrderTypeBySite(site);
	}
}
