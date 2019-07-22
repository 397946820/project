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
import com.it.ocs.salesStatistics.service.ILightSaleOrderService;
import com.it.ocs.salesStatistics.vo.LightOrderItemVo;
import com.it.ocs.salesStatistics.vo.LightOrderVo;
import com.it.ocs.salesStatistics.vo.LightShipmentVo;
import com.it.ocs.salesStatistics.vo.ReturnGoodsOrderVo;

@Controller
@RequestMapping("/lightSaleOrder")
public class LightSaleOrderController {

	@Autowired
	private ILightSaleOrderService lightSaleOrderService;

	// 跳转到主页
	@RequestMapping("/tolist")
	public String todetails() {
		return "admin/salesStatistics/lightSaleOrder";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<LightOrderVo> findAll(RequestParam param) throws Exception {
		return lightSaleOrderService.findAll(param);
	}

	@RequestMapping("/getLightOrderItemById/{id}")
	@ResponseBody
	public ResponseResult<LightOrderItemVo> getLightOrderItemById(@PathVariable("id") String parentId,int page,int rows) {
		return lightSaleOrderService.getLightOrderItemById(parentId,page,rows);
	}
	
	@RequestMapping("/getSaleOrderRefundByParentId")
	@ResponseBody
	public Map<String, Object> getSaleOrderRefundByParentId(@RequestBody Map<String, Object> map) {
		return lightSaleOrderService.getSaleOrderRefundByParentId(map);
	}

	@RequestMapping("/updateOrderByOrderId/{id}/{platform}/{entityId}")
	@ResponseBody
	public OperationResult updateOrderByOrderId(@PathVariable("id") String orderId,
			@PathVariable("platform") String platform, @PathVariable("entityId") String entityId) {
		return lightSaleOrderService.updateOrderByOrderId(orderId, platform, entityId);
	}
	
	@RequestMapping("/cancelOrderById/{id}")
	@ResponseBody
	public OperationResult cancelOrderById(@PathVariable("id") String id) {
		return lightSaleOrderService.cancelOrderById(id);
	}
	
	@RequestMapping("/getLightOrderById/{id}")
	@ResponseBody
	public ResponseResult<LightOrderVo> getLightOrderById(@PathVariable("id") String entityId) {
		return lightSaleOrderService.getLightOrderById(entityId);
	}
	
	@RequestMapping("/getLightShips/{id}")
	@ResponseBody
	public ResponseResult<LightShipmentVo> getLightShips(@PathVariable("id") String orderId) {
		return lightSaleOrderService.getLightShips(orderId);
	}
	
	@RequestMapping("/uploadLightSaleTranNumber")
	@ResponseBody
	public OperationResult uploadLightSaleTranNumber(@RequestBody Map<String, Object> map) {
		return lightSaleOrderService.uploadLightSaleTranNumber(map);
	}
	
	@RequestMapping("/trackNumberExist")
	@ResponseBody
	public Boolean trackNumberExist(LightShipmentVo shipment) {
		return lightSaleOrderService.trackNumberExist(shipment);
	}
	@RequestMapping("/getSaleOrderInformationByParentId/{id}")
	@ResponseBody
	public ResponseResult<ReturnGoodsOrderVo> getSaleOrderInformationByParentId(@PathVariable("id") String parentId) {
		return lightSaleOrderService.getSaleOrderInformationByParentId(parentId);
	}
	@RequestMapping("/findReturnInformationByParentId/{id}")
	@ResponseBody
	public ResponseResult<ReturnGoodsOrderVo> findReturnInformationByParentId(@PathVariable("id") String parentId) {
		return lightSaleOrderService.findReturnInformationByParentId(parentId);
	}

}
