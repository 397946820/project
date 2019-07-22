package com.it.ocs.salesStatistics.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.controller.IBaseController;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.salesStatistics.service.ISalesStatisticsService;
import com.it.ocs.salesStatistics.vo.SalesStatisticsAllVo;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;
import com.it.ocs.sys.vo.PermissionVO;

/**
 * 销售统计控制层
 * 
 * @author yecaiqing
 *
 */
@Controller
@RequestMapping("/salesStatistics")
public class SalesStatisticsController extends IBaseController {

	@Autowired
	private ISalesStatisticsService salesStatisticsService;

	// 跳转到主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/salesStatistics/list";
	}

	// 跳转到主页
	@RequestMapping("/todetails")
	public String todetails() {
		return "admin/salesStatistics/orderList";
	}

	@RequestMapping("/findAll")
	public @ResponseBody ResponseResult<SalesStatisticsVo> findAll(RequestParam param) throws Exception {
		return salesStatisticsService.findAll(param, getColumns(UserUtils.getUserId(), "XSTJ_"), getPermissionVO("SJGLX_PF"),
				isAllSourceFlag(UserUtils.getUserId()),"", getColumns(UserUtils.getUserId(), "XSTJTJ_"));
	}
	
	@RequestMapping("/findAllSoure")
	public @ResponseBody ResponseResult<SalesStatisticsAllVo> findAllSoure(RequestParam param) throws Exception {
		return salesStatisticsService.findAllSoure(param, getColumns(UserUtils.getUserId(), "XSTJ_"), getPermissionVO("SJGLX_PF"),
				isAllSourceFlag(UserUtils.getUserId()),"", getColumns(UserUtils.getUserId(), "XSTJTJ_"));
	}

	@RequestMapping("/querySkuDetails")
	public @ResponseBody ResponseResult<SalesStatisticsVo> querySkuDetails(RequestParam param) throws Exception {
		return salesStatisticsService.querySkuDetails(param,"");
	}

	@RequestMapping("/findOrderDetails")
	public @ResponseBody ResponseResult<SalesStatisticsVo> findOrderDetails(RequestParam param) throws Exception {
		return salesStatisticsService.findOrderDetails(param);
	}

	@RequestMapping("/getPermission")
	public @ResponseBody PermissionVO getPermission() {
		return getPermissionVO("SJGLX_PF");
	}

	// 导出
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@org.springframework.web.bind.annotation.RequestParam("json") String param) {
		salesStatisticsService.exportExcel(request, response, param, getColumns(UserUtils.getUserId(), "XSTJ_")
				,getPermissionVO("SJGLX_PF"),isAllSourceFlag(UserUtils.getUserId()), getColumns(UserUtils.getUserId(), "XSTJTJ_"));
	}

	// 导出
	@RequestMapping("/detailsExcel")
	public void detailsExcel(HttpServletRequest request, HttpServletResponse response,
			@org.springframework.web.bind.annotation.RequestParam("json") String param) {
		salesStatisticsService.detailsExcel(request, response, param, getColumns(UserUtils.getUserId(), "XSTJXQ_"));
	}
	
	@RequestMapping("/getEbayStation")
	@ResponseBody
	public List<String> getEbayStation() {
		return salesStatisticsService.getEbayStation();
	}
}
