package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.ILightEbayCustomerDao;
import com.it.ocs.cal.model.LightEbayCustomerModel;
import com.it.ocs.cal.service.ILightEbayCustomerService;
import com.it.ocs.cal.vo.LightEbayCustomerVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;

@Service
@Transactional
public class LightEbayCustomerService implements ILightEbayCustomerService {

	@Autowired
	private ILightEbayCustomerDao lightEbayCustomerDao;

	@Override
	public ResponseResult<LightEbayCustomerVo> findAll(RequestParam param) {
		ResponseResult<LightEbayCustomerVo> result = new ResponseResult<>();

		LightEbayCustomerVo customer = BeanConvertUtil.mapToObject(param.getParam(), LightEbayCustomerVo.class);

		PageHelper.startPage(param.getPage(), param.getRows());

		List<LightEbayCustomerModel> list = lightEbayCustomerDao.query(customer, null, null);

		PageInfo<LightEbayCustomerModel> pageInfo = new PageInfo<>(list);

		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), LightEbayCustomerVo.class));

		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult saveEdit(LightEbayCustomerVo customer) {
		OperationResult result = new OperationResult();
		try {
			customer.setUpdatedAt(new Date());
			if (customer.getEntityId() == null) {
				customer.setCreatedAt(new Date());
				lightEbayCustomerDao.add(customer);
			} else {
				lightEbayCustomerDao.update(customer);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}
}
