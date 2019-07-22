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
import com.it.ocs.sys.dao.IOperatingProfitCateFeeDao;
import com.it.ocs.sys.model.OperatingProfitCateFeeModel;
import com.it.ocs.sys.service.IOperatingProfitCateFeeService;
import com.it.ocs.sys.vo.OperatingProfitCateFeeVo;

@Service
@Transactional
public class OperatingProfitCateFeeService implements IOperatingProfitCateFeeService {

	@Autowired
	private IOperatingProfitCateFeeDao operatingProfitCateFeeDao;

	@Override
	public ResponseResult<OperatingProfitCateFeeVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitCateFeeVo> result = new ResponseResult<>();
		OperatingProfitCateFeeVo operatingProfitCateFee = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitCateFeeVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitCateFeeModel> list = operatingProfitCateFeeDao.query(operatingProfitCateFee,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitCateFeeModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitCateFeeVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public OperationResult saveEdit(OperatingProfitCateFeeVo operatingProfitCateFee) {
		OperationResult result = new OperationResult();
		try {
			operatingProfitCateFee.setUpdatedAt(new Date());
			operatingProfitCateFeeDao.update(operatingProfitCateFee);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
