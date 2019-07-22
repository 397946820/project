package com.it.ocs.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.sys.dao.IOperatingProfitSkuFeeDao;
import com.it.ocs.sys.model.OperatingProfitSkuFeeModel;
import com.it.ocs.sys.service.IOperatingProfitSkuFeeService;
import com.it.ocs.sys.vo.OperatingProfitSkuFeeVo;

@Service
@Transactional
public class OperatingProfitSkuFeeService implements IOperatingProfitSkuFeeService {

	@Autowired
	private IOperatingProfitSkuFeeDao operatingProfitSkuFeeDao;

	@Override
	public ResponseResult<OperatingProfitSkuFeeVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitSkuFeeVo> result = new ResponseResult<>();
		OperatingProfitSkuFeeVo operatingProfitSkuFee = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitSkuFeeVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitSkuFeeModel> list = operatingProfitSkuFeeDao.query(operatingProfitSkuFee,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitSkuFeeModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitSkuFeeVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public OperationResult saveEdit(OperatingProfitSkuFeeVo operatingProfitSkuFee) {
		OperationResult result = new OperationResult();
		try {
			operatingProfitSkuFee.setUpdatedAt(new Date());
			operatingProfitSkuFeeDao.update(operatingProfitSkuFee);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
