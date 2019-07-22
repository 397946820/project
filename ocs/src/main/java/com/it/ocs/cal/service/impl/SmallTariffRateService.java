package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.ISmallTariffRateDao;
import com.it.ocs.cal.model.SmallTariffRateModel;
import com.it.ocs.cal.service.ISmallTariffRateService;
import com.it.ocs.cal.vo.SmallTariffRateVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;

@Service
@Transactional
public class SmallTariffRateService implements ISmallTariffRateService {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ISmallTariffRateDao smallTariffRateDao;

	@Override
	public ResponseResult<SmallTariffRateVo> findAll(RequestParam param) {

		ResponseResult<SmallTariffRateVo> result = new ResponseResult<>();

		SmallTariffRateModel smallTariffRate = BeanConvertUtil.mapToObject(param.getParam(),
				SmallTariffRateModel.class);

		PageHelper.startPage(param.getPage(), param.getRows());

		List<SmallTariffRateModel> list = smallTariffRateDao.query(smallTariffRate,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<SmallTariffRateModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), SmallTariffRateVo.class));
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult saveEdit(SmallTariffRateVo smallTariffRate) {
		OperationResult result = new OperationResult();
		try {
			if (smallTariffRate.getEntityId() == null) {
				smallTariffRateDao.add(smallTariffRate);
			} else {
				smallTariffRate.setUpdatedAt(new Date());
				smallTariffRateDao.update(smallTariffRate);
			}
		} catch (Exception e) {
			logger.error("小包计价器欧洲专线关税添加或者修改失败!", e);
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
