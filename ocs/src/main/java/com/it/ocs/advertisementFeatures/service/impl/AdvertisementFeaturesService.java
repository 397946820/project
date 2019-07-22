package com.it.ocs.advertisementFeatures.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.ocs.advertisementFeatures.dao.IAdvertisementFeaturesDao;
import com.it.ocs.advertisementFeatures.model.AdvertisingFeaturesModel;
import com.it.ocs.advertisementFeatures.service.IAdvertisementFeaturesService;
import com.it.ocs.advertisementFeatures.vo.AdvertisingFeaturesVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.CollectionUtil;

@Service
@Transactional
public class AdvertisementFeaturesService extends BaseService implements IAdvertisementFeaturesService {

	@Autowired
	private IAdvertisementFeaturesDao advertisementFeaturesDao;

	@Override
	public ResponseResult<AdvertisingFeaturesVo> findAll(RequestParam param) {
		ResponseResult<AdvertisingFeaturesVo> result = new ResponseResult<>();

		AdvertisingFeaturesVo features = BeanConvertUtil.mapToObject(param.getParam(), AdvertisingFeaturesVo.class);

		List<AdvertisingFeaturesModel> models = advertisementFeaturesDao.queryByPage(features, param.getStartRow(),
				param.getEndRow());

		List<AdvertisingFeaturesVo> vos = CollectionUtil.beansConvert(models, AdvertisingFeaturesVo.class);

		int total = advertisementFeaturesDao.count(features);

		result.setRows(vos);

		result.setTotal(total);

		return result;
	}

	@Override
	public OperationResult saveEditAdvertisingFeatures(AdvertisingFeaturesVo featuresVo) {
		OperationResult result = new OperationResult();
		try {
			if (featuresVo.getId() == null) {
				insertInit(featuresVo);
				advertisementFeaturesDao.add(featuresVo);
			} else {
				updateInit(featuresVo);
				advertisementFeaturesDao.update(featuresVo);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}

	@Override
	public OperationResult removeAdvertisingFeatures(String ids) {
		OperationResult result = new OperationResult();
		try {
			String[] split = ids.split(",");
			for (String id : split) {
				AdvertisingFeaturesVo model = (AdvertisingFeaturesVo) advertisementFeaturesDao.getById(new Long(id));
				updateInit(model);
				model.setEnabledFlag("N");
				advertisementFeaturesDao.update(model);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("删除失败");
			throw new RuntimeException();
		}
		return result;
	}
}
