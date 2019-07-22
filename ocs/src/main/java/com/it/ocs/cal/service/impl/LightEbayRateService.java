package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.ILightEbayRateDao;
import com.it.ocs.cal.model.LightEbayRateModel;
import com.it.ocs.cal.service.ILightEbayRateService;
import com.it.ocs.cal.vo.LightEbayRateVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;

@Service
@Transactional
public class LightEbayRateService implements ILightEbayRateService {

	@Autowired
	private ILightEbayRateDao lightEbayRateDao;

	@Override
	public ResponseResult<LightEbayRateVo> findAll(RequestParam param) {
		ResponseResult<LightEbayRateVo> result = new ResponseResult<>();

		LightEbayRateVo rateVo = BeanConvertUtil.mapToObject(param.getParam(), LightEbayRateVo.class);

		PageHelper.startPage(param.getPage(), param.getRows());

		List<LightEbayRateModel> list = lightEbayRateDao.query(rateVo,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<LightEbayRateModel> pageInfo = new PageInfo<>(list);

		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), LightEbayRateVo.class));

		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult saveEdit(LightEbayRateVo rateVo) {
		OperationResult result = new OperationResult();
		try {
			LightEbayRateModel param = lightEbayRateDao.queryByParam(
					new LightEbayRateModel(rateVo.getPlatform(), rateVo.getCountry(), rateVo.getShippingType()));
			rateVo.setUpdatedAt(new Date());
			if (param == null) {
				rateVo.setCreatedAt(new Date());
				lightEbayRateDao.add(rateVo);
			} else {
				rateVo.setEntityId(param.getEntityId());
				lightEbayRateDao.update(rateVo);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}

	@Override
	public Boolean isExist(LightEbayRateVo rateVo) {
		if (StringUtils.isBlank(rateVo.getPlatform()) || StringUtils.isBlank(rateVo.getCountry())) {
			return false;
		}
		LightEbayRateModel model = lightEbayRateDao.queryByParam(rateVo);
		if (model == null) {
			return false;
		} else {
			return true;
		}
	}
}
