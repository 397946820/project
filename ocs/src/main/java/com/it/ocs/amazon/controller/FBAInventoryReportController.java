package com.it.ocs.amazon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.amazon.model.MyiUnsuppressedVO;
import com.it.ocs.amazon.service.ReportDataSaveSupport;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/fbainventory")
public class FBAInventoryReportController {
	@Autowired
	private ReportDataSaveSupport reportDataSaveSupport;
	
	@RequestMapping("/index")
	public String index() {
		return "admin/fbainventory/myi_unsuppressed";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<MyiUnsuppressedVO> findMyiUnsuppressed(RequestParam param){  
		return reportDataSaveSupport.findMyiUnsuppressed(param);
	}
}
