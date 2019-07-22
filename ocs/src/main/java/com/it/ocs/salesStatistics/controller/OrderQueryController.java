package com.it.ocs.salesStatistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.controller.IBaseController;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.salesStatistics.service.IOrderQueryService;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;

@Controller
@RequestMapping("/orderQuery")
public class OrderQueryController extends IBaseController {
	
	@Autowired
	private IOrderQueryService orderQueryService;

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<SalesStatisticsVo> findAll(RequestParam param) throws Exception {
		return orderQueryService.findAll(param, getColumns(UserUtils.getUserId(), "XSTJ_"), getPermissionVO("SJGLX_PF"),
				isAllSourceFlag(UserUtils.getUserId()),"", getColumns(UserUtils.getUserId(), "XSTJTJ_"));

	}
}
