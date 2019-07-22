package com.it.ocs.cal.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.LightEbayRateModel;
import com.it.ocs.cal.vo.LightEbayRateVo;

@Transactional
public interface ILightEbayRateDao extends IBaseDao<LightEbayRateModel> {

}
