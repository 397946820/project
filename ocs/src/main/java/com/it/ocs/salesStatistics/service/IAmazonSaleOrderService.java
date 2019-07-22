package com.it.ocs.salesStatistics.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.vo.AmazonOrderItemVo;
import com.it.ocs.salesStatistics.vo.AmazonOrderVo;
import com.it.ocs.salesStatistics.vo.EntryBean;

public interface IAmazonSaleOrderService {

	ResponseResult<AmazonOrderVo> findAll(RequestParam param);

	ResponseResult<AmazonOrderItemVo> getAmazonOrderItemById(String parentId, String platform);

	List<Map<String, String>> getChannelByPlatform(String platform);

	Map<String, Object> getSaleOrderRefundByParentId(String id, String platform);

	List<EntryBean> customerNameLike(String keyword);

}
