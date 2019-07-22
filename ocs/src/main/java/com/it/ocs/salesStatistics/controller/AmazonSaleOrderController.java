package com.it.ocs.salesStatistics.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.service.IAmazonSaleOrderService;
import com.it.ocs.salesStatistics.vo.AmazonOrderItemVo;
import com.it.ocs.salesStatistics.vo.AmazonOrderVo;
import com.it.ocs.salesStatistics.vo.EntryBean;

@Controller
@RequestMapping("/amazonSaleOrder")
public class AmazonSaleOrderController {

	@Autowired
	private IAmazonSaleOrderService amazonSaleOrderService;

	// 跳转到主页
	@RequestMapping("/tolist")
	public String todetails() {
		return "admin/salesStatistics/amazonSaleOrder";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<AmazonOrderVo> findAll(RequestParam param) throws Exception {
		return amazonSaleOrderService.findAll(param);
	}

	@RequestMapping("/likeCustomerName")
	@ResponseBody
	public List<EntryBean> likeCustomerName(@org.springframework.web.bind.annotation.RequestParam("keyword") String keyword) throws Exception {
		return amazonSaleOrderService.customerNameLike(keyword);
	}

	@RequestMapping("/getAmazonOrderItemById/{id}/{platform}")
	@ResponseBody
	public ResponseResult<AmazonOrderItemVo> getAmazonOrderItemById(@PathVariable("id") String parentId,
			@PathVariable("platform") String platform) {
		return amazonSaleOrderService.getAmazonOrderItemById(parentId, platform);
	}

	@RequestMapping("/getChannel/{platform}")
	@ResponseBody
	public List<Map<String, String>> getChannelByPlatform(@PathVariable("platform") String platform) {
		return amazonSaleOrderService.getChannelByPlatform(platform);
	}

	@RequestMapping("/getSaleOrderRefundByParentId/{id}/{platform}")
	@ResponseBody
	public Map<String, Object> getSaleOrderRefundByParentId(@PathVariable("id") String id,
			@PathVariable("platform") String platform) {
		return amazonSaleOrderService.getSaleOrderRefundByParentId(id, platform);
	}
}
