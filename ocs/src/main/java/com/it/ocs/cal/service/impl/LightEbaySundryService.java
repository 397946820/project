package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.ILightEbaySundryDao;
import com.it.ocs.cal.model.LightEbaySundryModel;
import com.it.ocs.cal.service.ILightEbaySundryService;
import com.it.ocs.cal.vo.LightEbaySundryVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;

@Service
@Transactional
public class LightEbaySundryService implements ILightEbaySundryService {

	@Autowired
	private ILightEbaySundryDao lightEbaySundryDao;

	@Override
	public ResponseResult<LightEbaySundryVo> findAll(RequestParam param) {
		ResponseResult<LightEbaySundryVo> result = new ResponseResult<>();

		LightEbaySundryVo sundryVo = BeanConvertUtil.mapToObject(param.getParam(), LightEbaySundryVo.class);

		if (sundryVo == null) {
			sundryVo = new LightEbaySundryVo("US");
		}

		PageHelper.startPage(param.getPage(), param.getRows());

		List<LightEbaySundryModel> list = lightEbaySundryDao.query(sundryVo, param.getSort(), param.getOrder());
		
		PageInfo<LightEbaySundryModel> pageInfo = new PageInfo<>(list);
		
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), LightEbaySundryVo.class));
		result.setTotal((int) pageInfo.getTotal());
		result.setSource(sundryVo.getCountry());

		return result;
	}

	@Override
	public OperationResult saveEdit(LightEbaySundryVo rateVo) {
		OperationResult result = new OperationResult();
		try {
			LightEbaySundryModel param = lightEbaySundryDao.queryByCountry(rateVo.getCountry());
			rateVo.setUpdatedAt(new Date());
			if (param == null) {
				// 保存
				rateVo.setCreatedAt(new Date());
				lightEbaySundryDao.add(rateVo);
			} else {
				// 修改
				rateVo.setEntityId(param.getEntityId());
				lightEbaySundryDao.update(rateVo);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return result;
	}

}
