package com.it.ocs.salesStatistics.controller;

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
import com.it.ocs.salesStatistics.service.IWalmartSaleOrderService;
import com.it.ocs.salesStatistics.vo.WalmartOrderLineVo;
import com.it.ocs.salesStatistics.vo.WalmartOrderVo;

@Controller
@RequestMapping("/walmartSaleOrder")
public class WalmartSaleOrderController {

	@Autowired
	private IWalmartSaleOrderService walmartSaleOrderService;

	// 跳转到主页
	@RequestMapping("/tolist")
	public String todetails() {
		return "admin/salesStatistics/walmartSaleOrder";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<WalmartOrderVo> findAll(RequestParam param) throws Exception {
		return walmartSaleOrderService.findAll(param);
	}

	@RequestMapping("/getWalmartOrderLineById/{id}")
	@ResponseBody
	public ResponseResult<WalmartOrderLineVo> getWalmartOrderLineById(@PathVariable("id") String parentId, int page,
			int rows) {
		return walmartSaleOrderService.getWalmartOrderLineById(parentId, page, rows);
	}

	@RequestMapping("/cancelOrder/{id}/{orderCase}")
	@ResponseBody
	public OperationResult cancelOrder(@PathVariable("id") String id, @PathVariable("orderCase") String orderCase) {
		return walmartSaleOrderService.cancelOrder(id, orderCase);
	}

	@RequestMapping("/updateOrder/{id}")
	@ResponseBody
	public OperationResult updateOrder(@PathVariable("id") String purchaseOrderId) {
		return walmartSaleOrderService.updateOrder(purchaseOrderId);
	}

	@RequestMapping("/isExist/{id}")
	@ResponseBody
	public Boolean isExist(@PathVariable("id") String id) {
		return walmartSaleOrderService.isExist(id);
	}

	@RequestMapping("/getExistWalmartOrderLineById/{id}")
	@ResponseBody
	public ResponseResult<WalmartOrderLineVo> getExistWalmartOrderLineById(@PathVariable("id") String id, int page,
			int rows) {
		return walmartSaleOrderService.getExistWalmartOrderLineById(id,page,rows);
	}

	@RequestMapping("/refundOrder")
	@ResponseBody
	public OperationResult refundOrder(@RequestBody Map<String, Object> map) {
		return walmartSaleOrderService.refundOrder(map);
	}
	
	@RequestMapping("/getById/{id}")
	@ResponseBody
	public ResponseResult<WalmartOrderLineVo> getById(@PathVariable("id") String id) {
		return walmartSaleOrderService.getById(id);
	}
	
	@RequestMapping("/uploadWalmartSaleTranNumber")
	@ResponseBody
	public OperationResult uploadWalmartSaleTranNumber(@RequestBody Map<String, Object> map) {
		 return walmartSaleOrderService.uploadWalmartSaleTranNumber(map);
	}
	
	@RequestMapping("/getSaleOrderRefundByParentId")
	@ResponseBody
	public Map<String, Object> getSaleOrderRefundByParentId(@RequestBody Map<String, Object> map) {
		return walmartSaleOrderService.getSaleOrderRefundByParentId(map);
	}
	
	@RequestMapping("/countOrderByStatus")
	@ResponseBody
	public Long countOrderByStatus(@RequestBody Map<String, Object> map) {
		return walmartSaleOrderService.countOrderByStatus(map);
	}
}
