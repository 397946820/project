package com.it.ocs.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.sys.dao.IOperatingProfitFeeCateDao;
import com.it.ocs.sys.model.OperatingProfitFeeCateModel;
import com.it.ocs.sys.service.IOperatingProfitFeeCateService;
import com.it.ocs.sys.vo.OperatingProfitFeeCateVo;

@Service
@Transactional
public class OperatingProfitFeeCateService implements IOperatingProfitFeeCateService {

	@Autowired
	private IOperatingProfitFeeCateDao operatingProfitFeeCateDao;

	@Override
	public ResponseResult<OperatingProfitFeeCateVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitFeeCateVo> result = new ResponseResult<>();
		OperatingProfitFeeCateVo operatingProfitFeeCate = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitFeeCateVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitFeeCateModel> list = operatingProfitFeeCateDao.query(operatingProfitFeeCate,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitFeeCateModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitFeeCateVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}


}
