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
import com.it.ocs.sys.dao.IOperatingProfitStockDao;
import com.it.ocs.sys.model.OperatingProfitStockModel;
import com.it.ocs.sys.service.IOperatingProfitStockService;
import com.it.ocs.sys.vo.OperatingProfitStockVo;

@Service
@Transactional
public class OperatingProfitStockService implements IOperatingProfitStockService {

	@Autowired
	private IOperatingProfitStockDao operatingProfitStockDao;

	@Override
	public ResponseResult<OperatingProfitStockVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitStockVo> result = new ResponseResult<>();
		OperatingProfitStockVo operatingProfitStock = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitStockVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitStockModel> list = operatingProfitStockDao.query(operatingProfitStock,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitStockModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitStockVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

}
