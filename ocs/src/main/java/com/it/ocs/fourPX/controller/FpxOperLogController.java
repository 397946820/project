package com.it.ocs.fourPX.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.api.utils.SupportTimestampCustomDateEditor;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.service.FpxOperLogService;
import com.it.ocs.fourPX.vo.FpxOperateLogVO;

@Controller
public class FpxOperLogController {

	@Autowired
	private FpxOperLogService fpxOperLogService;

	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new SupportTimestampCustomDateEditor(dateFormat));  
	}
	
	@RequestMapping("/fourpx/operlog/index")
	public String index() {
		return "admin/fourpx/fpxoperlog";
	}
	
	@RequestMapping(value="/fourpx/operlog/list")
	@ResponseBody 
	public ResponseResult<FpxOperateLogVO> list(com.it.ocs.common.RequestParam param) {
		return this.fpxOperLogService.selectByPage(param);
	}
}
