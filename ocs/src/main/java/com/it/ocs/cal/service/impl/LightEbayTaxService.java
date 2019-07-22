package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.ILightEbayTaxDao;
import com.it.ocs.cal.dao.ILightTaximeterDao;
import com.it.ocs.cal.dao.IProductEntityDao;
import com.it.ocs.cal.model.LightEbayTaxModel;
import com.it.ocs.cal.service.ILightEbayTaxService;
import com.it.ocs.cal.vo.LightEbayTaxVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;

@Service
@Transactional
public class LightEbayTaxService implements ILightEbayTaxService {

	@Autowired
	private ILightEbayTaxDao lightEbayTaxDao;

	@Autowired
	private IProductEntityDao productEntityDao;

	@Autowired
	private ILightTaximeterDao lightTaximeterDao;

	@Override
	public ResponseResult<LightEbayTaxVo> findAll(RequestParam param) {

		ResponseResult<LightEbayTaxVo> result = new ResponseResult<>();

		LightEbayTaxModel tax = BeanConvertUtil.mapToObject(param.getParam(), LightEbayTaxModel.class);

		PageHelper.startPage(param.getPage(), param.getRows());

		List<LightEbayTaxModel> list = lightEbayTaxDao.query(tax,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<LightEbayTaxModel> pageInfo = new PageInfo<>(list);

		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), LightEbayTaxVo.class));
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult saveEdit(LightEbayTaxVo tax) {
		OperationResult result = new OperationResult();
		try {
			LightEbayTaxModel param = lightEbayTaxDao.queryByCountryAndSku(tax.getCountry(), tax.getSku());
			tax.setUpdatedAt(new Date());
			if (param == null) {
				// 保存
				tax.setCreatedAt(new Date());
				lightEbayTaxDao.add(tax);
			} else {
				// 修改
				tax.setEntityId(param.getEntityId());
				lightEbayTaxDao.update(tax);
			}
			List<Long> userIds = productEntityDao.findUserIdBySku(tax.getSku());
			CollectionUtil.each(userIds, new IAction<Long>() {
				@Override
				public void excute(Long userId) {
					lightTaximeterDao.refresh(tax.getSku(), userId);
				}
			});

		} catch (Exception e) {
			throw new RuntimeException();
		}
		return result;
	}

}
