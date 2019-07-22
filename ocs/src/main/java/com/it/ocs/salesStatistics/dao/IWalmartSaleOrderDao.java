package com.it.ocs.salesStatistics.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.salesStatistics.model.WalmartOrderLineModel;
import com.it.ocs.salesStatistics.model.WalmartOrderModel;
import com.it.ocs.salesStatistics.vo.SaleOrderRefundVo;

@Repository
public interface IWalmartSaleOrderDao {

	List<WalmartOrderModel> queryList(@Param(value = "param") WalmartOrderModel model,
			@Param(value = "sort") String sort, @Param(value = "order") String order);

	List<WalmartOrderLineModel> getWalmartOrderLineById(@Param(value = "parentId") Long parentId);

	Double getTotalPriceById(@Param(value = "parentId") Long parentId);

	WalmartOrderLineModel getById(@Param(value = "id") Long id);

	List<String> isExist(@Param(value = "parentId") Long parentId);

	List<WalmartOrderLineModel> getExistWalmartOrderLineById(@Param(value = "parentId") Long parentId);

	List<SaleOrderRefundVo> getSaleOrderRefundByParentId(@Param(value = "parentId") Long parentId);

	WalmartOrderLineModel getWalmartOrderLineModelByLineNumber(@Param(value = "orderId") String orderId,
			@Param(value = "lineNumber") Integer lineNumber);

	Long getCount(@Param(value = "param") WalmartOrderModel model);

	WalmartOrderModel getByOrderId(String orderId);

	public int hasSuccess(Map<String, Object> map);

}
