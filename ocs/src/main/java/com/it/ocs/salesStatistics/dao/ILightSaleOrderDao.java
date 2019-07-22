package com.it.ocs.salesStatistics.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.salesStatistics.model.LightOrderItemModel;
import com.it.ocs.salesStatistics.model.LightOrderModel;
import com.it.ocs.salesStatistics.vo.ReturnGoodsOrderVo;
import com.it.ocs.salesStatistics.vo.SaleOrderRefundVo;

@Repository
public interface ILightSaleOrderDao {

	List<LightOrderModel> queryList(@Param(value = "param") LightOrderModel model,
			@Param(value = "sort") String sort, @Param(value = "order") String order);

	List<LightOrderItemModel> getLightOrderItemById(@Param(value = "parentId") Long parentId);

	LightOrderModel getLightOrderById(Long entityId);

	LightOrderModel getLightOrderByOrderId(String orderId);

	List<SaleOrderRefundVo> getSaleOrderRefundByParentId(@Param(value = "parentId") Long parentId);
	
	List<Map<String, Object>> getOrderItemIdAndQtyByOrderId(String orderId);
	
	int getDays(String orderId);
	
	/**
	 * 获取订单信息
	 * @User wangqiang
	 * @date 2018年3月22日
	 * @param parentId
	 * @return
	 */
	List<ReturnGoodsOrderVo> getSaleOrderInformationByParentId(@Param(value = "parentId") Long parentId);
	/**
	 * 获取退货订单信息
	 * @User wangqiang
	 * @date 2018年3月22日
	 * @param parentId
	 * @return
	 */
	List<ReturnGoodsOrderVo> findReturnInformationByParentId(@Param(value = "parentId") Long parentId);

	/**
	 * 处理补发单跟踪号信息
	 * @param param
	 */
	void handleReissueTranNumber(@Param(value = "param") Map<String, Object> param);

	/**
	 * 获取线下单的account
	 * @param param
	 * @return
	 */
	String getOCSAccount(@Param(value = "param") Map<String, Object> param);

}
