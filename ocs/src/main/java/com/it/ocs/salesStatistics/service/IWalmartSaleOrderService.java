package com.it.ocs.salesStatistics.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.vo.WalmartOrderLineVo;
import com.it.ocs.salesStatistics.vo.WalmartOrderVo;

public interface IWalmartSaleOrderService {

	ResponseResult<WalmartOrderVo> findAll(RequestParam param);

	ResponseResult<WalmartOrderLineVo> getWalmartOrderLineById(String parentId, int page, int rows);

	OperationResult cancelOrder(String id, String orderCase);

	OperationResult updateOrder(String purchaseOrderId);

	OperationResult refundOrder(Map<String, Object> map);

	Boolean isExist(String id);

	ResponseResult<WalmartOrderLineVo> getExistWalmartOrderLineById(String id, int page, int rows);

	ResponseResult<WalmartOrderLineVo> getById(String id);

	OperationResult uploadWalmartSaleTranNumber(Map<String, Object> map);

	Map<String, Object> getSaleOrderRefundByParentId(Map<String, Object> map);

	OperationResult uploadTranNumber(String orderId, List<Map<String, Object>> list);

	Long countOrderByStatus(Map<String, Object> map);
	
	public OperationResult uploadTranNumberByJSON(String orderId, List<Map<String, Object>> list);

}
