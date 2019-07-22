package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.ILightExpensesDao;
import com.it.ocs.cal.model.LightExpensesModel;
import com.it.ocs.cal.service.ILightExpensesService;
import com.it.ocs.cal.vo.LightExpensesVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;

@Service
@Transactional
public class LightExpensesService implements ILightExpensesService {

	@Autowired
	private ILightExpensesDao ligthExpensesDao;

	@Override
	public ResponseResult<LightExpensesVo> findAll(RequestParam param) {
		ResponseResult<LightExpensesVo> result = new ResponseResult<>();

		LightExpensesModel expenses = BeanConvertUtil.mapToObject(param.getParam(), LightExpensesModel.class);

		PageHelper.startPage(param.getPage(), param.getRows());

		List<LightExpensesModel> list = ligthExpensesDao.query(expenses,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<LightExpensesModel> pageInfo = new PageInfo<>(list);

		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), LightExpensesVo.class));
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult saveEdit(LightExpensesVo expenses) {

		OperationResult result = new OperationResult();
		try {
			LightExpensesModel param = ligthExpensesDao.queryByParam(new LightExpensesModel(expenses.getCountry()));
			expenses.setUpdatedAt(new Date());
			if (param == null) {
				// 保存
				expenses.setCreatedAt(new Date());
				ligthExpensesDao.add(expenses);
			} else {
				// 修改
				expenses.setEntityId(param.getEntityId());
				ligthExpensesDao.update(expenses);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return result;
	}

}
