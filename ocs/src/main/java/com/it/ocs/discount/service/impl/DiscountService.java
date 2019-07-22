package com.it.ocs.discount.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.discount.dao.IDiscountDao;
import com.it.ocs.discount.model.DiscountModel;
import com.it.ocs.discount.service.IDiscountService;
import com.it.ocs.discount.util.DiscountUtils;
import com.it.ocs.discount.vo.DiscountVo;

@Service
@Transactional
public class DiscountService extends BaseService implements IDiscountService {

	@Autowired
	private IDiscountDao discountDao;

	@Override
	public ResponseResult<DiscountVo> findAll(RequestParam param) {

		ResponseResult<DiscountVo> result = new ResponseResult<>();
		
		DiscountVo discount = BeanConvertUtil.mapToObject(param.getParam(), DiscountVo.class);

		List<DiscountModel> discounts = discountDao.queryByPage(discount,param.getStartRow(),param.getEndRow());

		List<DiscountVo> vos = CollectionUtil.beansConvert(discounts, DiscountVo.class);

		for (DiscountVo discountVo : vos) {
			discountVo.setValidity(DiscountUtils.getTime(discountVo.getStartTime()) + "," + DiscountUtils.getTime(discountVo.getEndTime()));
		}

		int total = discountDao.count(discount);

		result.setRows(vos);
		result.setTotal(total);

		return result;
	}
	
	@Override
	public OperationResult saveEditDiscount(DiscountVo discount) {
		OperationResult result = new OperationResult();

		try {
			if (discount.getId() == null) {
				insertInit(discount);
				discount.setStartTime(Timestamp.valueOf(discount.getStart()));
				discount.setEndTime(Timestamp.valueOf(discount.getEnd()));
				discountDao.add(discount);
			} else {
				updateInit(discount);
				discount.setStartTime(Timestamp.valueOf(discount.getStart()));
				discount.setEndTime(Timestamp.valueOf(discount.getEnd()));
				discountDao.update(discount);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}

		return result;
	}

	@Override
	public OperationResult removeDiscount(String ids) {
		OperationResult result = new OperationResult();

		try {
			String[] split = ids.split(",");
			for (String id : split) {
				//先查找
				DiscountVo model = (DiscountVo) discountDao.getById(new Long(id));
				updateInit(model);
				model.setEnabledFlag("N");
				//修改
				discountDao.update(model);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("删除失败");
			throw new RuntimeException();
		}
		return result;
	}

}
