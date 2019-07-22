package com.it.ocs.sys.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.vo.ReturnGoodsOrderVo;
import com.it.ocs.sys.vo.ReturnOrderVo;

public interface IReturnOrderService {

	List<Map<String, String>> getReturnOrderCause(String id);
	
	List<Map<String, String>> getReturnOrderCauseAll();

	OperationResult saveEditReturnOrder(Map<String, Object> map, String orderType);

	ResponseResult<ReturnOrderVo> findAll(RequestParam param);

	List<String> getAccountByPlatform(String platform);

	List<String> getSiteByPlatformAndAccount(String platform, String account);

	OperationResult orderRefund(Map<String, Object> map);

	OperationResult orderReissue(Map<String, Object> map, String id);

	OperationResult cancelApplication(String id);
	
	OperationResult getAddress(Map<String, Object> map);
	
	OperationResult saveEditReturnGoodsOrder2(Map<String, Object> map, String orderType) throws Exception;
	
	/**
	 * 保存退货申请单
	 * @deprecated 已过时，请使用同类的<code>saveEditReturnGoodsOrder2</code>函数替代
	 * @User wangqiang
	 * @date 2018年3月22日
	 * @param map
	 * @param orderType
	 * @return
	 */
	OperationResult saveEditReturnGoodsOrder(Map<String, Object> map, String orderType);
	/**
	 * 返回RMA的序列
	 * @User wangqiang
	 * @date 2018年3月22日
	 * @return
	 */
	Integer findOrderReturnSeq();
	/**
	 * 查询已申请的退货的sku数量
	 * @User wangqiang
	 * @date 2018年3月23日
	 * @return
	 */
	Integer findAlreadyApplyReturnSkuNum( Map<String, Object> map);

	/**
	 * 查询得到已经申请退货的订单相关信息
	 * @param orderId 订单ID
	 * @return
	 */
	ResponseResult<ReturnGoodsOrderVo> findAppliedReturnOrders(String orderId);

}
