package com.it.ocs.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.vo.ReturnGoodsOrderVo;
import com.it.ocs.sys.service.IReturnOrderService;
import com.it.ocs.sys.vo.ReturnOrderVo;

/**
 * 
 * @author yecaiqing
 *
 */
@Controller
@RequestMapping("/returnOrder")
public class ReturnOrderController {

	@Autowired
	private IReturnOrderService returnOrderService;

	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/salesStatistics/returnOrder";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<ReturnOrderVo> findAll(RequestParam param) {
		return returnOrderService.findAll(param);
	}

	@RequestMapping("/getReturnOrderCause/{id}")
	@ResponseBody
	public List<Map<String, String>> getReturnOrderCause(@PathVariable("id") String id) {
		return returnOrderService.getReturnOrderCause(id);
	}

	@RequestMapping("/getReturnOrderCauseAll")
	@ResponseBody
	public List<Map<String, String>> getReturnOrderCauseAll() {
		return returnOrderService.getReturnOrderCauseAll();
	}

	@RequestMapping("/getAccount/{platform}")
	@ResponseBody
	public List<String> getAccount(@PathVariable("platform") String platform) {
		return returnOrderService.getAccountByPlatform(platform);
	}

	@RequestMapping("/getSite")
	@ResponseBody
	public List<String> getSite(String platform, String account) {
		return returnOrderService.getSiteByPlatformAndAccount(platform, account);
	}

	@RequestMapping("/saveEditReturnOrder/{orderType}")
	@ResponseBody
	public OperationResult saveEditReturnOrder(@RequestBody Map<String, Object> map,
			@PathVariable("orderType") String orderType) {
		return returnOrderService.saveEditReturnOrder(map, orderType);
	}

	@RequestMapping("/orderRefund")
	@ResponseBody
	public OperationResult orderRefund(@RequestBody Map<String, Object> map) {
		return returnOrderService.orderRefund(map);
	}

	@RequestMapping("/orderReissue/{id}")
	@ResponseBody
	public OperationResult orderReissue(@RequestBody Map<String, Object> map, @PathVariable("id") String id) {
		return returnOrderService.orderReissue(map, id);
	}

	@RequestMapping("/cancelApplication/{id}")
	@ResponseBody
	public OperationResult cancelApplication(@PathVariable("id") String id) {
		return returnOrderService.cancelApplication(id);
	}

	@RequestMapping("/getAddress")
	@ResponseBody
	public OperationResult getAddress(@RequestBody Map<String, Object> map) {
		return returnOrderService.getAddress(map);
	}

	@RequestMapping("/saveEditReturnGoodsOrder/{orderType}")
	@ResponseBody
	public OperationResult saveEditReturnGoodsOrder(@RequestBody Map<String, Object> map,
			@PathVariable("orderType") String orderType) {
		OperationResult result = null;
		try {
			result = returnOrderService.saveEditReturnGoodsOrder2(map, orderType);
		} catch (Exception e) {
			result = new OperationResult();
			result.setErrorCode(1);
			result.setDescription("保存失败！");
			result.setError("EX_ERROR");
		}
		return result;
	}

	@RequestMapping("/findOrderReturnSeq")
	@ResponseBody
	public Integer findOrderReturnSeq() {
		return returnOrderService.findOrderReturnSeq();
	}

	@RequestMapping("/findAlreadyApplyReturnSkuNum")
	@ResponseBody
	public Integer findAlreadyApplyReturnSkuNum(@RequestBody Map<String, Object> map) {
		return returnOrderService.findAlreadyApplyReturnSkuNum(map);
	}

	@RequestMapping("/findAppliedReturnOrders/{entityId}")
	@ResponseBody
	public ResponseResult<ReturnGoodsOrderVo> findAppliedReturnOrders(@PathVariable("entityId") String entityId) {
		return returnOrderService.findAppliedReturnOrders(entityId);
	}
}
