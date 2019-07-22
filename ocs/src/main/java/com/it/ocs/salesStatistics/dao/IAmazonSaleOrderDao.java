package com.it.ocs.salesStatistics.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.salesStatistics.model.AmazonOrderItemModel;
import com.it.ocs.salesStatistics.model.AmazonOrderModel;
import com.it.ocs.salesStatistics.vo.SaleOrderRefundVo;

@Repository
public interface IAmazonSaleOrderDao {

	List<AmazonOrderModel> queryList(@Param(value = "param") AmazonOrderModel model, @Param(value = "sort") String sort,
			@Param(value = "order") String order);

	List<AmazonOrderItemModel> getAmazonOrderItemById(@Param(value = "parentId") Long parentId,
			@Param(value = "platform") String platform);

	List<Map<String, String>> getChannelByPlatform(@Param(value = "platform") String platform);

	List<SaleOrderRefundVo> getSaleOrderRefundByParentId(@Param(value = "parentId") Long parentId,
			@Param(value = "platform") String platform);

	List<String> customerNameLike(@Param(value = "keyword") String keyword);

}
