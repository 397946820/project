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
import com.it.ocs.sys.dao.IOperatingProfitSalaryDao;
import com.it.ocs.sys.model.OperatingProfitSalaryModel;
import com.it.ocs.sys.service.IOperatingProfitSalaryService;
import com.it.ocs.sys.vo.OperatingProfitSalaryVo;

@Service
@Transactional
public class OperatingProfitSalaryService implements IOperatingProfitSalaryService {

	@Autowired
	private IOperatingProfitSalaryDao operatingProfitSalaryDao;

	@Override
	public ResponseResult<OperatingProfitSalaryVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitSalaryVo> result = new ResponseResult<>();
		OperatingProfitSalaryVo operatingProfitSalary = BeanConvertUtil.mapToObject(param.getParam(),
				OperatingProfitSalaryVo.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitSalaryModel> list = operatingProfitSalaryDao.query(operatingProfitSalary,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<OperatingProfitSalaryModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitSalaryVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public OperationResult saveEdit(OperatingProfitSalaryVo operatingProfitSalary) {
		OperationResult result = new OperationResult();
		try {
			operatingProfitSalary.setUpdatedAt(new Date());
			operatingProfitSalaryDao.update(operatingProfitSalary);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
