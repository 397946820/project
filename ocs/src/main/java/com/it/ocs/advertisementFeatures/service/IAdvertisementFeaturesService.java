package com.it.ocs.advertisementFeatures.service;

import com.it.ocs.advertisementFeatures.vo.AdvertisingFeaturesVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IAdvertisementFeaturesService {
	
	/**
	 * 分页查询
	 * @param param
	 * @return
	 */
	ResponseResult<AdvertisingFeaturesVo> findAll(RequestParam param);

	/**
	 * 保存，修改广告特色
	 * @param featuresVo
	 * @return
	 */
	OperationResult saveEditAdvertisingFeatures(AdvertisingFeaturesVo featuresVo);
	
	/**
	 * 逻辑删除
	 * @param ids 要删除的id
	 * @return
	 */
	OperationResult removeAdvertisingFeatures(String ids);
	
}
