package com.it.ocs.salereport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salereport.service.ISaleReportService;
import com.it.ocs.salereport.vo.SaleReportVO;

@Controller
@RequestMapping("/saleReport")
public class SaleReportController {
	private ISaleReportService saleReportService;
	@RequestMapping("/findAll")
	public @ResponseBody ResponseResult<SaleReportVO> findAll(RequestParam param) throws Exception {
		return saleReportService.queryByPage(param);
	}
}
