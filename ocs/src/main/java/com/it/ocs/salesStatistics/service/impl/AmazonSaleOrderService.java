package com.it.ocs.salesStatistics.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.salesStatistics.dao.IAmazonSaleOrderDao;
import com.it.ocs.salesStatistics.model.AmazonOrderItemModel;
import com.it.ocs.salesStatistics.model.AmazonOrderModel;
import com.it.ocs.salesStatistics.service.IAmazonSaleOrderService;
import com.it.ocs.salesStatistics.vo.AmazonOrderItemVo;
import com.it.ocs.salesStatistics.vo.AmazonOrderVo;
import com.it.ocs.salesStatistics.vo.EntryBean;
import com.it.ocs.salesStatistics.vo.SaleOrderRefundVo;
import com.it.ocs.sys.dao.IReturnOrderDao;

@Service
@Transactional
public class AmazonSaleOrderService implements IAmazonSaleOrderService {

	@Autowired
	private IAmazonSaleOrderDao amazonSaleOrderDao;
	
	@Autowired
	private IReturnOrderDao returnOrderDao;

	@Override
	public ResponseResult<AmazonOrderVo> findAll(RequestParam param) {
		ResponseResult<AmazonOrderVo> result = new ResponseResult<>();
		AmazonOrderModel model = BeanConvertUtil.mapToObject(param.getParam(), AmazonOrderModel.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<AmazonOrderModel> list = amazonSaleOrderDao.queryList(model,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<AmazonOrderModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), AmazonOrderVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public ResponseResult<AmazonOrderItemVo> getAmazonOrderItemById(String parentId, String platform) {
		ResponseResult<AmazonOrderItemVo> result = new ResponseResult<>();
		List<AmazonOrderItemModel> list = amazonSaleOrderDao.getAmazonOrderItemById(new Long(parentId), platform);
		result.setRows(CollectionUtil.beansConvert(list, AmazonOrderItemVo.class));
		return result;
	}

	@Override
	public List<Map<String, String>> getChannelByPlatform(String platform) {
		return amazonSaleOrderDao.getChannelByPlatform(platform);
	}

	@Override
	public Map<String, Object> getSaleOrderRefundByParentId(String id, String platform) {
		Map<String, Object> result = Maps.newHashMap();
		List<SaleOrderRefundVo> list = amazonSaleOrderDao.getSaleOrderRefundByParentId(new Long(id), platform);
		result.put("rows", list);
		result.put("total", list.size());
		result.put("edaOrderNum", returnOrderDao.findOrderReturnSeq());
		return result;
	}

	@Override
	public List<EntryBean> customerNameLike(String keyword) {
		List<EntryBean> beans = new ArrayList<EntryBean>();
		if(StringUtils.isNotBlank(keyword)) {
			List<String> customers = this.amazonSaleOrderDao.customerNameLike(keyword);
			if(!CollectionUtil.isNullOrEmpty(customers)) {
				for (String customer : customers) {
					beans.add(new EntryBean(customer, customer));
				}
			}
		}
		return beans;
	}
}
