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
import com.it.ocs.sys.dao.IOperatingProfitFeeDao;
import com.it.ocs.sys.model.OperatingProfitFeeModel;
import com.it.ocs.sys.service.IOperatingProfitFeeService;
import com.it.ocs.sys.vo.OperatingProfitFeeVo;

@Service
@Transactional
public class OperatingProfitFeeService implements IOperatingProfitFeeService {

	@Autowired
	private IOperatingProfitFeeDao operatingProfitFeeDao;

	@Override
	public ResponseResult<OperatingProfitFeeVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitFeeVo> result = new ResponseResult<>();
		OperatingProfitFeeVo operatingProfitFee = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitFeeVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitFeeModel> list = operatingProfitFeeDao.query(operatingProfitFee,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitFeeModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitFeeVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public OperationResult saveEdit(OperatingProfitFeeVo operatingProfitFee) {
		OperationResult result = new OperationResult();
		try {
			operatingProfitFee.setUpdatedAt(new Date());
			operatingProfitFeeDao.update(operatingProfitFee);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
