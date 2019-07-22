package com.it.ocs.salesStatistics.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.controller.IBaseController;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.salesStatistics.service.ISalesOrderService;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;

@Controller
@RequestMapping("/salesOrder")
public class SalesOrderController extends IBaseController {

	@Autowired
	public ISalesOrderService salesOrderService;

	// 跳转到主页
	@RequestMapping("/tolist")
	public String todetails() {
		return "admin/salesStatistics/salesOrder";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<SalesStatisticsVo> findAll(RequestParam param) throws Exception {
		return salesOrderService.findAll(param, getPermissionVO("SJGLX_PF"), isAllSourceFlag(UserUtils.getUserId()),"");
	}

	// 导出
	@RequestMapping("/salesOrderExport")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@org.springframework.web.bind.annotation.RequestParam("json") String param) throws Exception {
		salesOrderService.exportExcel(request, response,param,getPermissionVO("SJGLX_PF"),isAllSourceFlag(UserUtils.getUserId()));
	}

}
