package com.it.ocs.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.api.service.IDeOperLogService;
import com.it.ocs.api.utils.SupportTimestampCustomDateEditor;
import com.it.ocs.api.vo.OperateLogVO;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/wms/operlog")
public class DeOperLogController {

	@Autowired
	private IDeOperLogService deOperLogService;

	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new SupportTimestampCustomDateEditor(dateFormat));  
	}
	
	@RequestMapping("/index")
	public String index() {
		return "admin/wms/operlog";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<OperateLogVO> list(com.it.ocs.common.RequestParam param) {
		return deOperLogService.queryByPage(param);
	}
}
