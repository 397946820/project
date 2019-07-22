package com.it.ocs.salesStatistics.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.vo.LightOrderItemVo;
import com.it.ocs.salesStatistics.vo.LightOrderVo;
import com.it.ocs.salesStatistics.vo.LightShipmentVo;
import com.it.ocs.salesStatistics.vo.ReturnGoodsOrderVo;

public interface ILightSaleOrderService {

	ResponseResult<LightOrderVo> findAll(RequestParam param);

	ResponseResult<LightOrderItemVo> getLightOrderItemById(String parentId, int page, int rows);

	OperationResult updateOrderByOrderId(String orderId, String platform, String entityId);

	OperationResult cancelOrderById(String id);

	ResponseResult<LightOrderVo> getLightOrderById(String entityId);

	OperationResult uploadLightSaleTranNumber(Map<String, Object> map);

	OperationResult uploadTranNumber(String orderId, List<Map<String, Object>> list, List<Map<String, Object>> itemQty);

	ResponseResult<LightShipmentVo> getLightShips(String orderId);

	Boolean trackNumberExist(LightShipmentVo shipment);

	Map<String, Object> getSaleOrderRefundByParentId(Map<String, Object> map);

	OperationResult refundOrder(Map<String, Object> map);

	/**
	 * 获取订单信息
	 * 
	 * @User wangqiang
	 * @date 2018年3月22日
	 * @param parentId
	 * @return
	 */
	ResponseResult<ReturnGoodsOrderVo> getSaleOrderInformationByParentId(String parentId);

	/**
	 * 获取退货订单信息
	 * 
	 * @User wangqiang
	 * @date 2018年3月22日
	 * @param parentId
	 * @return
	 */
	ResponseResult<ReturnGoodsOrderVo> findReturnInformationByParentId(String parentId);

	/**
	 * 处理补发单跟踪号
	 * @param param
	 */
	OperationResult handleReissueTranNumber(Map<String, Object> param);
}
