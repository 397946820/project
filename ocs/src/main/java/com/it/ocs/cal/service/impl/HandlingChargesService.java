package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.IHandlingChargesDao;
import com.it.ocs.cal.model.LightHandlingChargesModel;
import com.it.ocs.cal.service.IHandlingChargesService;
import com.it.ocs.cal.vo.LightHandlingChargesVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;

@Service
@Transactional
public class HandlingChargesService implements IHandlingChargesService {

	@Autowired
	private IHandlingChargesDao handlingChargesDao;

	@Override
	public ResponseResult<LightHandlingChargesVo> findAll(RequestParam param) {
		ResponseResult<LightHandlingChargesVo> result = new ResponseResult<>();

		LightHandlingChargesVo chargesVo = BeanConvertUtil.mapToObject(param.getParam(), LightHandlingChargesVo.class);

		if (chargesVo == null) {
			chargesVo = new LightHandlingChargesVo();
		}

		PageHelper.startPage(param.getPage(), param.getRows());

		List<LightHandlingChargesModel> list = handlingChargesDao.query(chargesVo,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<LightHandlingChargesModel> pageInfo = new PageInfo<>(list);

		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), LightHandlingChargesVo.class));

		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult saveEdit(LightHandlingChargesVo chargesVo) {
		OperationResult result = new OperationResult();
		try {
			chargesVo.setUpdatedAt(new Date());
			if (chargesVo.getId() == null) {
				chargesVo.setCreatedAt(new Date());
				handlingChargesDao.add(chargesVo);
			} else {
				handlingChargesDao.update(chargesVo);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}
}
